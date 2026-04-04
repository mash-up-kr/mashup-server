# PRD: 플랫폼 리더 출석 알림

## 1. 배경

현재 출석 체크 시스템은 멤버 개인에게만 알림을 보낸다 (출석 시작 전, 시작 시, 마감 임박).
출석 시간이 끝난 후 **누가 지각/결석했는지 플랫폼 리더가 파악할 수 있는 수단이 없다.**

## 2. 목표

출석/지각 마감 시점에 각 플랫폼 리더에게 해당 플랫폼의 지각자/결석자 명단을 FCM 푸시 알림으로 발송한다.

## 3. 사용자 시나리오

```
14:00  출석 체크 시작 (attendanceCheckStartedAt)
14:10  출석 체크 마감 (attendanceCheckEndedAt)
       → 리더에게 푸시: "Spring 지각자: 홍길동, 김철수"
       
14:20  지각 체크 마감 (latenessCheckEndedAt)
       → 리더에게 푸시: "Spring 결석자: 홍길동"
```

- 해당 플랫폼에 지각자/결석자가 없으면 발송하지 않는다.

## 4. 기능 요구사항

### 4.1 리더 식별

- `AdminMember` 엔티티에 `memberId` (Long, nullable) 컬럼을 추가한다.
- `AdminMember.memberId` → `Member.id` 참조로 운영진 계정과 멤버 계정을 연결한다.
- 리더 대상: `Position`이 `*_LEADER` 또는 `*_SUBLEADER`인 AdminMember.
- 각 Position의 `Team[] authorities`로 담당 플랫폼을 식별한다.
- `MASHUP_LEADER`, `MASHUP_SUBLEADER`는 모든 플랫폼 알림을 수신한다.

### 4.2 알림 1: 지각자 알림

- **트리거**: `attendanceCheckEndedAt` 경과 시점
- **대상**: 출석 마감까지 체크하지 않은 멤버 명단 → 해당 플랫폼 리더에게 발송
- **제목**: "출석 현황 알림"
- **본문**: "{플랫폼명} 지각자: {이름1}, {이름2}, ..."

### 4.3 알림 2: 결석자 알림

- **트리거**: `latenessCheckEndedAt` 경과 시점
- **대상**: 지각 마감까지도 체크하지 않은 멤버 명단 → 해당 플랫폼 리더에게 발송
- **제목**: "출석 현황 알림"
- **본문**: "{플랫폼명} 결석자: {이름1}, {이름2}, ..."

### 4.4 공통 사항

- 기존 `@Scheduled(cron = "0 * * * * *")` 패턴을 따라 매분 체크한다.
- 기존 FCM 인프라 (`PushNotiEventPublisher` → `PushNotiEventListener`) 활용.
- 딥링크: `MAIN`
- `AdminMember.memberId`가 null이면 해당 리더에게는 발송하지 않는다.
- 해당 플랫폼에 지각자/결석자가 0명이면 발송하지 않는다.

## 5. 시간 흐름과 알림 관계

```
 attendanceCheckStartedAt        attendanceCheckEndedAt        latenessCheckEndedAt
         │                                │                            │
         ├── 출석 인정 구간 ──────────────┤── 지각 인정 구간 ──────────┤
         │                                │                            │
         │  QR 체크 → 출석               │  QR 체크 → 지각            │  이후 → 결석
         │                                │                            │
         │  [기존] 시작 알림 (멤버)       │  ★ 지각자 알림 (리더)      │  ★ 결석자 알림 (리더)
```

## 6. 영향 범위

### mashup-domain (공유 도메인)

| 파일 | 변경 | 내용 |
|------|------|------|
| `AdminMember.java` | 수정 | `memberId` (Long, nullable) 컬럼 추가 |
| `Position.java` | 수정 | `isLeaderOrSubLeader()`, `toPlatform(Team)` 헬퍼 메서드 추가 |
| `AdminMemberRepository.java` | 수정 | `findAllByPositionInAndMemberIdIsNotNull()` 쿼리 추가 |
| `AdminMemberService.java` | 수정 | `getLeadersWithMemberId()` 메서드 추가 |
| `AttendanceCodeRepository.java` | 수정 | `latenessCheckEndedAt` 범위 쿼리 추가 |
| `AttendanceCodeService.java` | 수정 | `findAllByLatenessEndedAtLeftOpenBetween()` 래핑 메서드 추가 |
| `AttendanceLateForLeaderVo.java` | **신규** | 지각자 알림 VO (동적 body: "{플랫폼} 지각자: {이름들}") |
| `AttendanceAbsentForLeaderVo.java` | **신규** | 결석자 알림 VO (동적 body: "{플랫폼} 결석자: {이름들}") |

### mashup-member (멤버 API 서버)

| 파일 | 변경 | 내용 |
|------|------|------|
| `AttendanceFacadeService.java` | 수정 | `sendLateNotiToLeaders()` 스케줄러 (출석 마감 시점) |
|  |  | `sendAbsentNotiToLeaders()` 스케줄러 (지각 마감 시점) |
|  |  | `sendLeaderNotiByPlatform()` 플랫폼별 집계/발송 공통 로직 |
|  |  | `getLeaderMembersForPlatform()` Position→Platform 매핑 조회 |
|  |  | `findAllLatenessEndsWithin()` 시간 범위 헬퍼 |

## 7. 데이터 모델 변경

```sql
ALTER TABLE admin_member ADD COLUMN member_id BIGINT NULL;
```

운영진 계정과 멤버 계정을 연결하기 위한 컬럼. 
관리자 생성/수정 시 해당 운영진의 Member ID를 수동으로 연결해야 한다.

## 8. 범위 외 (Out of Scope)

- Discord/Slack 웹훅 연동
- 출석 완료 시 본인에게 보내는 푸시
- 관리자 화면에서 memberId 연결 UI
- 기존 멤버 대상 출석 알림 (Starting, Started, Ending) 변경
