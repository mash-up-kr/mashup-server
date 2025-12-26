# Mash-Up Server API Specification

## Overview

| 항목 | 내용 |
|------|------|
| **프로젝트 명** | Mash-Up Server |
| **빌드 시스템** | Gradle (멀티 모듈) |
| **Java 버전** | 11 |
| **Spring Boot 버전** | 2.6.2 |
| **Base URL** | `/api/v1` |
| **인증 방식** | Bearer Token (JWT) |
| **API 문서** | Swagger 2.0 / Springfox |

## Module Structure

```
mashup-server/
├── mashup-domain        (공유 도메인/엔티티 모델)
├── mashup-recruit       (채용 지원 서비스)
├── mashup-member        (회원 관리 서비스)
├── mashup-admin         (관리자 대시보드 서비스)
└── mashup-batch         (배치 작업)
```

---

## 1. Member Service (`mashup-member`)

회원 관리, 출석, 일정, 게임(당근/마숑) 등 회원 대상 기능을 제공합니다.

### 1.1 Members & Authentication

회원 인증 및 계정 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/members` | 회원 정보 조회 | O |
| `GET` | `/members/{id}` | ID 중복 확인 | X |
| `GET` | `/members/{id}/exists` | ID 존재 여부 확인 | X |
| `POST` | `/members/login` | 로그인 | X |
| `POST` | `/members/signup` | 회원가입 | X |
| `GET` | `/members/code` | 회원가입 초대 코드 검증 | X |
| `DELETE` | `/members` | 회원 탈퇴 | O |
| `POST` | `/members/token` | 액세스 토큰 재발급 | O |
| `PATCH` | `/members/push-notification` | 푸시 알림 설정 변경 | O |
| `PUT` | `/members/{id}/password` | 비밀번호 변경 | X |

### 1.2 Member Profiles

회원 프로필 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/member-profiles/my` | 내 프로필 조회 | O |
| `PATCH` | `/member-profiles/my` | 내 프로필 수정 | O |
| `GET` | `/member-profiles/{memberId}` | 특정 회원 프로필 조회 | X |

### 1.3 Member Generations

회원 기수 정보 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/member-generations/my` | 내 기수 정보 조회 | O |
| `PATCH` | `/member-generations/my/{id}` | 내 기수 정보 수정 | O |

### 1.4 Attendance (출석)

QR 코드 기반 출석 체크 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/attendance/check` | 출석 체크 (암호화 코드 + 위치 검증) | O |
| `GET` | `/attendance/platforms` | 전체 플랫폼별 출석 현황 | X |
| `GET` | `/attendance/platforms/{platformName}` | 특정 플랫폼 출석 현황 | X |
| `GET` | `/attendance/schedules/{scheduleId}` | 특정 일정 개인 출석 현황 | O |

### 1.5 Schedules (일정)

일정 조회 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/schedules/{id}` | 일정 상세 조회 | X |
| `GET` | `/schedules/generations/{generationNumber}` | 기수별 일정 목록 조회 | O |

### 1.6 Danggn (당근 흔들기 게임)

당근 흔들기 게임 및 랭킹 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/danggn/score` | 당근 흔들기 점수 추가 | O |
| `GET` | `/danggn/rank/member` | 개인 랭킹 조회 | O |
| `GET` | `/danggn/rank/member/all` | 전체 회원 랭킹 조회 | O |
| `GET` | `/danggn/rank/platform` | 플랫폼별 랭킹 조회 | O |
| `GET` | `/danggn/golden-danggn-percent` | 황금 당근 확률 조회 | O |
| `GET` | `/danggn/random-today-message` | 오늘의 랜덤 메시지 조회 | O |
| `GET` | `/danggn/ranking-round` | 전체 랭킹 라운드 조회 | O |
| `GET` | `/danggn/ranking-round/{danggnRankingRoundId}` | 특정 랭킹 라운드 조회 | O |
| `POST` | `/danggn/ranking-reward-comment/{danggnRankingRewardId}` | 랭킹 보상 코멘트 작성 | O |

### 1.7 Mashong (마숑 펫)

마숑 펫 육성 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/mashong/attend` | 펫 출석 | O |
| `POST` | `/mashong/popcorn` | 팝콘 지급 | O |
| `POST` | `/mashong/popcorn/feed` | 펫에게 팝콘 먹이기 | O |
| `POST` | `/mashong/level-up` | 펫 레벨업 | O |
| `GET` | `/mashong/popcorn` | 팝콘 개수 조회 | O |
| `GET` | `/mashong/status` | 펫 상태 조회 | O |
| `GET` | `/mashong/with-mashong-days` | 펫과 함께한 일수 조회 | O |

### 1.8 Mashong Missions (마숑 미션)

마숑 미션 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/mashong-mission/status` | 미션 상태 목록 조회 | O |
| `GET` | `/mashong-mission/attendances` | 펫 출석 상태 조회 | O |

### 1.9 Birthdays (생일)

회원 생일 조회 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/birthdays/upcoming` | 다가오는 생일 목록 조회 | O |

### 1.10 Birthday Cards (생일 카드)

생일 카드 전송 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/birthday-cards/default-images` | 기본 카드 이미지 목록 | X |
| `GET` | `/birthday-cards/images/presigned-url` | S3 이미지 업로드용 Presigned URL | O |
| `POST` | `/birthday-cards` | 생일 카드 전송 | O |
| `GET` | `/birthday-cards` | 내 생일 카드 조회 | O |
| `GET` | `/birthday-cards/random-message` | 랜덤 생일 메시지 조회 | X |

### 1.11 Member Popups (팝업)

회원 팝업 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/member-popups` | 활성화된 팝업 목록 조회 | O |
| `PATCH` | `/member-popups/{popupType}/disabled` | 팝업 비활성화 | O |
| `PATCH` | `/member-popups/{popupType}/last-viewed` | 팝업 마지막 조회 시간 업데이트 | O |
| `DELETE` | `/member-popups` | 회원 팝업 삭제 (dev only) | O |

### 1.12 Score History (점수 이력)

회원 점수 이력 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/score-history` | 내 점수 이력 조회 | O |
| `GET` | `/score-history/{memberId}` | 특정 회원 점수 조회 | X |

### 1.13 Push History (푸시 이력)

푸시 알림 이력 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/push-histories` | 푸시 이력 조회 | O |
| `POST` | `/push-histories` | 푸시 이력 조회 (확인 포함) | O |

### 1.14 Storage (Key-Value 저장소)

키-값 저장소 조회 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/storage/key/{key}` | 키로 값 조회 | X |

### 1.15 RNB Meta

RNB 메타데이터 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/meta/rnb` | RNB 메타데이터 조회 | O |

### 1.16 Health Check

서비스 상태 확인 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/ping` | 헬스 체크 | X |
| `GET` | `/v2/ping` | 헬스 체크 v2 | X |
| `GET` | `/time` | 서버 시간 조회 | X |

---

## 2. Admin Service (`mashup-admin`)

관리자용 회원 관리, 일정 관리, 채용 관리 등의 기능을 제공합니다.

### 2.1 Admin Authentication

관리자 인증 API입니다.

| Method | Endpoint | Description | Auth | Role |
|--------|----------|-------------|------|------|
| `POST` | `/admin-members/login` | 관리자 로그인 | X | - |
| `GET` | `/admin-members/me` | 현재 관리자 정보 조회 | O | - |
| `POST` | `/admin-members/password/reset` | 비밀번호 초기화 | O | - |
| `POST` | `/admin-members/password/change` | 비밀번호 변경 | O | - |
| `DELETE` | `/admin-members` | 관리자 삭제 | O | - |
| `GET` | `/admin-members` | 관리자 목록 조회 | O | - |
| `POST` | `/admin-members` | 관리자 생성 | O | MASHUP_LEADER, MASHUP_SUBLEADER |

### 2.2 Applications (지원서 관리)

지원서 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/applications` | 지원서 목록 조회 (필터링) | O |
| `GET` | `/applications/{applicationId}` | 지원서 상세 조회 | O |
| `POST` | `/applications/update-result` | 다수 지원서 결과 업데이트 | O |
| `POST` | `/applications/{applicationId}/update-result` | 단일 지원서 결과 업데이트 | O |

### 2.3 Application Forms (지원 양식)

지원 양식 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/application-forms` | 지원 양식 목록 조회 | O |
| `GET` | `/application-forms/{applicationFormId}` | 지원 양식 상세 조회 | O |
| `POST` | `/application-forms` | 지원 양식 생성 | O |
| `PUT` | `/application-forms/{applicationFormId}` | 지원 양식 수정 | O |
| `DELETE` | `/application-forms/{applicationFormId}` | 지원 양식 삭제 | O |

### 2.4 Application Excel Export

지원서 Excel 내보내기 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/application-excel` | 지원서 Excel 다운로드 | O |

### 2.5 Generations (기수 관리)

기수 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/generations` | 전체 기수 조회 | O |
| `POST` | `/generations` | 기수 생성 | O |
| `POST` | `/generations/update` | 기수 정보 수정 | O |

### 2.6 Members (회원 관리)

관리자용 회원 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/members/{generationNumber}` | 기수별 전체 회원 조회 | O |
| `GET` | `/members/{generationNumber}/{memberId}` | 회원 상세 조회 | O |
| `PATCH` | `/members/{id}/reset/password` | 회원 비밀번호 초기화 | O |
| `DELETE` | `/members` | 회원 완전 삭제 | O |
| `DELETE` | `/members/drop-out` | 특정 기수에서 탈퇴 처리 | O |
| `POST` | `/members/status/{generationNumber}` | 회원 상태 변경 | O |
| `POST` | `/members/transfer` | 회원 기수 이동 | O |

### 2.7 Teams (팀 관리)

팀 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/teams` | 전체 팀 조회 | O |
| `POST` | `/teams` | 팀 생성 | O |

### 2.8 Schedules (일정 관리)

일정 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/schedules` | 일정 목록 조회 (필터링) | O |
| `GET` | `/schedules/{scheduleId}` | 일정 상세 조회 | O |
| `POST` | `/schedules` | 일정 생성 | O |
| `POST` | `/schedules/{scheduleId}/publish` | 일정 공개 | O |
| `POST` | `/schedules/{scheduleId}/hide` | 일정 숨김 | O |
| `PUT` | `/schedules/{scheduleId}` | 일정 수정 | O |
| `DELETE` | `/schedules/{scheduleId}` | 일정 삭제 | O |
| `POST` | `/schedules/{scheduleId}/event/{eventId}/qr` | QR 코드 업데이트 | O |

### 2.9 Recruitment Schedules (채용 일정)

채용 일정 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/recruitment-schedules` | 채용 일정 조회 | O |
| `POST` | `/recruitment-schedules` | 채용 일정 생성 | O |
| `PUT` | `/recruitment-schedules/{recruitmentScheduleId}` | 채용 일정 수정 | O |
| `DELETE` | `/recruitment-schedules/{recruitmentScheduleId}` | 채용 일정 삭제 | O |

### 2.10 Invitations (초대 코드)

초대 코드 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/invite-code` | 전체 초대 코드 조회 | O |
| `POST` | `/invite-code` | 초대 코드 생성 | O |
| `POST` | `/invite-code/{inviteCodeId}` | 초대 코드 수정 | O |
| `DELETE` | `/invite-code/{inviteCodeId}` | 초대 코드 삭제 | O |

### 2.11 Push Notifications (푸시 알림)

푸시 알림 발송 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/push-notis/broadcast` | 전체 회원 푸시 발송 | O |
| `POST` | `/push-notis/narrowcast` | 특정 회원 푸시 발송 | O |

### 2.12 Email Notifications (이메일 알림)

이메일 알림 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/email` | 이메일 발송 이력 조회 | O |
| `GET` | `/email/{emailNotificationId}` | 이메일 상세 조회 | O |
| `POST` | `/email/send` | 이메일 발송 | O |

### 2.13 Score History (점수 관리)

점수 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/score-history/add` | 점수 추가 | O |
| `POST` | `/score-history/cancel` | 점수 취소 | O |

### 2.14 Danggn Admin (당근 관리)

당근 게임 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/danggn-today-message` | 오늘의 메시지 전체 조회 | O |
| `GET` | `/danggn-today-message/{messageId}` | 특정 메시지 조회 | O |
| `POST` | `/danggn-today-message` | 오늘의 메시지 생성 | O |
| `PUT` | `/danggn-today-message/{messageId}` | 메시지 수정 | O |
| `DELETE` | `/danggn-today-message/{messageId}` | 메시지 삭제 | O |

### 2.15 Storage Admin (저장소 관리)

키-값 저장소 관리 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/storage` | 키-값 저장 (Upsert) | O |
| `GET` | `/storage/key/{key}` | 키로 값 조회 | O |
| `GET` | `/storage/keys` | 전체 키 목록 조회 | O |

### 2.16 Health Check

서비스 상태 확인 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/ping` | 헬스 체크 | X |
| `GET` | `/v2/ping` | 헬스 체크 v2 | X |
| `GET` | `/time` | 서버 시간 조회 | X |
| `GET` | `/hello` | Hello 응답 | X |

---

## 3. Recruit Service (`mashup-recruit`)

지원자용 채용 서비스를 제공합니다.

### 3.1 Applicant Authentication

지원자 인증 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/applicants/login` | Google OAuth 로그인 | X |
| `GET` | `/applicants/me` | 지원자 정보 조회 | O |

### 3.2 Applications (지원서)

지원서 작성 및 제출 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/applications/list/{generationNumber}` | 기수별 지원 가능 양식 조회 | X |
| `POST` | `/applications` | 지원서 생성 | O |
| `PUT` | `/applications/{applicationId}` | 지원서 임시저장 | O |
| `POST` | `/applications/{applicationId}/submit` | 지원서 제출 | O |
| `GET` | `/applications` | 내 지원서 목록 조회 | O |
| `GET` | `/applications/{applicationId}` | 지원서 상세 조회 | O |
| `POST` | `/applications/{applicationId}/confirm` | 합격 결과 확인/응답 | O |
| `GET` | `/applications/schedule/{generationNumber}` | 채용 일정 조회 | X |

### 3.3 Teams (팀)

팀 조회 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/teams` | 전체 팀 조회 (기수별) | X |

### 3.4 Test Endpoints (개발용)

개발 환경 전용 테스트 API입니다.

| Method | Endpoint | Description | Auth | Env |
|--------|----------|-------------|------|-----|
| `DELETE` | `/test/applications/{applicationId}` | 지원서 삭제 | O | dev |
| `POST` | `/test/applications/{applicationId}/confirm` | 확인 상태 변경 | O | dev |

### 3.5 Health Check

서비스 상태 확인 API입니다.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/ping` | 헬스 체크 | X |
| `GET` | `/v2/ping` | 헬스 체크 v2 | X |
| `GET` | `/time` | 서버 시간 조회 | X |
| `GET` | `/hello` | Hello 응답 | X |

---

## Common Response Structure

### ApiResponse<T>

모든 API는 공통 응답 구조를 사용합니다.

```json
{
  "code": "SUCCESS",
  "message": "성공",
  "data": { ... }
}
```

### Error Response

```json
{
  "code": "ERROR_CODE",
  "message": "에러 메시지",
  "data": null
}
```

---

## Authentication

### Request Header

```
Authorization: Bearer {access_token}
```

### Token Refresh

액세스 토큰 만료 시 `/api/v1/members/token` 엔드포인트를 통해 갱신합니다.

---

## Key Features Summary

| 기능 | 설명 |
|------|------|
| **회원 관리** | 회원가입, 로그인, 프로필 관리 |
| **채용 시스템** | 지원 양식, 지원서 작성/제출, 결과 알림 |
| **출석 체크** | QR 코드 기반 출석 (위치 검증 포함) |
| **게이미피케이션** | 당근 흔들기, 마숑 펫 육성 |
| **커뮤니티** | 생일 카드, 푸시 알림, 메시징 |
| **관리자 대시보드** | 회원/일정/점수 관리 |
| **알림** | 이메일 및 푸시 알림 발송 |
| **파일 저장소** | S3 Presigned URL을 통한 이미지 업로드 |

---

## Statistics

| 항목 | 수량 |
|------|------|
| 컨트롤러 | 42개 |
| API 엔드포인트 | 100개+ |
| 모듈 | 5개 (domain, recruit, member, admin, batch) |
