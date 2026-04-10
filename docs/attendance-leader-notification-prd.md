# PRD: 플랫폼 리더 출석 알림

## 1. 배경

현재 출석 체크 시스템은 멤버 개인에게만 알림을 보낸다 (출석 시작 전, 시작 시, 마감 임박).
QR 출석을 깜빡하거나 실수로 놓치는 멤버가 있어도, **플랫폼 리더가 마감 전에 파악하고 독려할 수 있는 수단이 없다.**

## 2. 목표

출석/지각 마감 **N분 전**에 각 플랫폼 리더에게 아직 출석하지 않은 멤버 명단을 FCM 푸시로 발송하여,
리더가 마감 전에 해당 멤버에게 출석을 독려할 수 있도록 한다.

## 3. 사용자 시나리오

```
설정: LEADER_NOTI_BEFORE_MINUTES=3 (환경변수)

14:00  출석 체크 시작 (attendanceCheckStartedAt)
14:07  ★ 리더에게 푸시: "Spring에서 아직 출석하지 않은 멤버가 있어요: 홍길동, 김철수"
       (출석 마감 3분 전, 리더가 홍길동/김철수에게 직접 연락)
14:10  출석 체크 마감 (attendanceCheckEndedAt)

14:17  ★ 리더에게 푸시: "Spring에서 출석하지 못한 멤버가 있어요: 홍길동"
       (지각 마감 3분 전, 마지막 기회 독려)
14:20  지각 체크 마감 (latenessCheckEndedAt)
```

- 마감 N분 전 시점에 미출석자가 없으면 발송하지 않는다.
- N은 환경변수로 설정 가능하며, 기본값은 3분이다.

## 4. 기능 요구사항

### 4.1 리더 식별

- `AdminMember` 엔티티에 `memberId` (Long, nullable) 컬럼을 추가한다.
- `AdminMember.memberId` → `Member.id` 참조로 운영진 계정과 멤버 계정을 연결한다.
- 리더 대상: `Position`이 `*_LEADER` 또는 `*_SUBLEADER`인 AdminMember.
- 각 Position의 `Team[] authorities`로 담당 플랫폼을 식별한다.
- `MASHUP_LEADER`, `MASHUP_SUBLEADER`는 모든 플랫폼 알림을 수신한다.

### 4.2 알림 1: 지각 예정자 알림 (출석 마감 N분 전)

- **트리거**: `attendanceCheckEndedAt` N분 전 시점
- **대상**: 아직 출석하지 않은 멤버 명단 → 해당 플랫폼 리더에게 발송
- **제목**: "출석 현황 알림"
- **본문**: "{플랫폼명}에서 아직 출석하지 않은 멤버가 있어요: {이름1}, {이름2}, ..."
- **목적**: 리더가 마감 전에 해당 멤버에게 출석을 독려

### 4.3 알림 2: 결석 예정자 알림 (지각 마감 N분 전)

- **트리거**: `latenessCheckEndedAt` N분 전 시점
- **대상**: 아직 출석/지각 체크를 하지 않은 멤버 명단 → 해당 플랫폼 리더에게 발송
- **제목**: "출석 현황 알림"
- **본문**: "{플랫폼명}에서 출석하지 못한 멤버가 있어요: {이름1}, {이름2}, ..."
- **목적**: 마감 직전 마지막 독려 기회 제공

### 4.4 N분 전 설정

- 환경변수: `attendance.leader-noti.before-minutes` (application.yml)
- 기본값: `3` (분)
- 값을 `0`으로 설정하면 마감 시점에 발송 (기존 동작과 동일)

### 4.5 공통 사항

- 기존 `@Scheduled(cron = "0 * * * * *")` 패턴을 따라 매분 체크한다.
- 기존 FCM 인프라 (`PushNotiEventPublisher` → `PushNotiEventListener`) 활용.
- 딥링크: `MAIN`
- `AdminMember.memberId`가 null이면 해당 리더에게는 발송하지 않는다.
- 해당 플랫폼에 미출석자가 0명이면 발송하지 않는다.

## 5. 시간 흐름과 알림 관계 (N=3분 예시)

```
 attendanceCheckStartedAt     attendanceCheckEndedAt        latenessCheckEndedAt
         │                          │                            │
         ├── 출석 인정 구간 ────────┤── 지각 인정 구간 ──────────┤
         │                          │                            │
         │  QR 체크 → 출석         │  QR 체크 → 지각            │  이후 → 결석
         │                          │                            │
         │              ★ 지각예정 알림(N분전)      ★ 결석예정 알림(N분전)
         │              (리더가 독려)               (마지막 기회)
```

## 6. 설정

```yaml
# application.yml
attendance:
  leader-noti:
    before-minutes: 3
```

## 7. 영향 범위

### mashup-domain (공유 도메인)

| 파일 | 변경 | 내용 |
|------|------|------|
| `AdminMember.java` | 수정 | `memberId` (Long, nullable) 컬럼 추가 |
| `Position.java` | 수정 | `isLeaderOrSubLeader()`, `toPlatform(Team)` 헬퍼 메서드 추가 |
| `AdminMemberRepository.java` | 수정 | `findAllByPositionInAndMemberIdIsNotNull()` 쿼리 추가 |
| `AdminMemberService.java` | 수정 | `getLeadersWithMemberId()` 메서드 추가 |
| `AttendanceCodeRepository.java` | 수정 | `attendanceCheckEndedAt`, `latenessCheckEndedAt` 범위 쿼리 |
| `AttendanceCodeService.java` | 수정 | 래핑 메서드 추가 |
| `AttendanceLateForLeaderVo.java` | **신규** | 지각 예정자 알림 VO |
| `AttendanceAbsentForLeaderVo.java` | **신규** | 결석 예정자 알림 VO |
| `MemberService.java` | 수정 | 예외 없는 배치 조회 `findAllByIds()` 추가 |

### mashup-member (멤버 API 서버)

| 파일 | 변경 | 내용 |
|------|------|------|
| `AttendanceFacadeService.java` | 수정 | 스케줄러 2개 + 리더 푸시 로직 + N분 전 설정 주입 |

## 8. 데이터 모델 변경

```sql
ALTER TABLE admin_member ADD COLUMN member_id BIGINT NULL;
```

운영진 계정과 멤버 계정을 연결하기 위한 컬럼.
관리자 생성/수정 시 해당 운영진의 Member ID를 수동으로 연결해야 한다.

## 9. 범위 외 (Out of Scope)

- Discord/Slack 웹훅 연동
- 출석 완료 시 본인에게 보내는 푸시
- 관리자 화면에서 memberId 연결 UI
- 기존 멤버 대상 출석 알림 (Starting, Started, Ending) 변경
