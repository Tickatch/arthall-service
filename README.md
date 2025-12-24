# Tickatch ArtHall Service

티켓 예매 플랫폼 Tickatch의 아트홀 관리 마이크로서비스입니다.

## 프로젝트 소개

ArtHall Service는 아트홀, 스테이지(Stage), 스테이지 좌석(StageSeat) 정보를 관리하며
예매 및 티켓 도메인의 기준이 되는 공간 구조와 좌석 배치 데이터를 제공합니다.

아트홀 구조 변경, 좌석 상태 변경 등 아트홀 인프라 관리의 중심 역할을 담당합니다.

## 기술 스택
| 분류            | 기술              |
| ------------- | --------------- |
| Framework     | Spring Boot 3.x |
| Language      | Java 21         |
| Database      | PostgreSQL      |
| Messaging     | RabbitMQ        |
| Query         | JPA / QueryDSL  |
| Security      | Spring Security |

## 아키텍처

### 시스템 구성

```
┌────────────────────────────────────────────────────────────┐
│                          Tickatch Platform                 │
├──────────────┬──────────────┬──────────────┬───────────────┤
│     User     │     Auth     │   Product    │    ArtHall    │
│   Service    │   Service    │   Service    │    Service    │
├──────────────┼──────────────┼──────────────┼───────────────┤
│ Reservation  │    Ticket    │ Reservation  │    Payment    │
│   Service    │   Service    │     Seat     │    Service    │
│              │              │   Service    │               │
├──────────────┴──────────────┴──────────────┴───────────────┤
│    Notification Service     │         Log Service          │
└────────┬────────────────────┴──────────┬───────────────────┘
         │                               │
         └────────────────────┬──────────┘
                              │
                          RabbitMQ
```

### 레이어 구조

```
src/main/java/com/tickatch/arthallservice
├── ArthallServiceApplication.java
│
├── global
│   ├── config
│   │   ├── RabbitMQ
│   │   │   └── RabbitMQConfig.java
│   │   ├── audit
│   │   │   └── AuditConfig.java
│   │   ├── querydsl
│   │   │   └── QuerydslConfig.java
│   │   └── security
│   │       ├── ActorExtractor.java
│   │       └── ArthallServiceSecurityConfig.java
│   └── domain
│       ├── AbstractAuditEntity.java
│       └── AbstractTimeEntity.java
│
├── arthall
│   ├── application
│   │   ├── dto
│   │   │   ├── ArtHallRegisterCommand.java
│   │   │   ├── ArtHallResult.java
│   │   │   ├── ArtHallStatusUpdateCommand.java
│   │   │   └── ArtHallUpdateCommand.java
│   │   ├── port
│   │   │   └── ArtHallLogPort.java
│   │   └── service
│   │       ├── ArtHallDeleteService.java
│   │       ├── ArtHallQueryService.java
│   │       ├── ArtHallRegisterService.java
│   │       ├── ArtHallStatusUpdateService.java
│   │       └── ArtHallUpdateService.java
│   ├── domain
│   │   ├── ArtHall.java
│   │   ├── ArtHallAddress.java
│   │   ├── ArtHallName.java
│   │   ├── ArtHallStatus.java
│   │   ├── exception
│   │   │   └── ArtHallErrorCode.java
│   │   ├── repository
│   │   │   └── ArtHallRepository.java
│   │   └── service
│   │       ├── ArtHallFinder.java
│   │       └── ArtHallCascadeDeleter.java
│   ├── infrastructure
│   │   ├── ArtHallRelatedEntitiesDeleter.java
│   │   ├── adapter
│   │   │   ├── ArtHallFinderAdapter.java
│   │   │   └── ArtHallRepositoryAdapter.java
│   │   ├── log
│   │   │   └── publisher
│   │   │       └── ArtHallLogPublisher.java
│   │   ├── messaging
│   │   │   └── event
│   │   │       └── ArtHallLogEvent.java
│   │   ├── query
│   │   │   └── ArtHallQuery.java
│   │   └── repository
│   │       └── ArtHallJpaRepository.java
│   └── presentation
│       ├── ArtHallApi.java
│       ├── ArtHallQueryApi.java
│       └── dto
│           ├── request
│           │   ├── ArtHallRegisterRequest.java
│           │   ├── ArtHallStatusUpdateRequest.java
│           │   └── ArtHallUpdateRequest.java
│           └── response
│               ├── ArtHallDetailResponse.java
│               ├── ArtHallListResponse.java
│               ├── ArtHallRegisterResponse.java
│               ├── ArtHallStatusUpdateResponse.java
│               └── ArtHallUpdateResponse.java
│
├── stage
│   ├── application
│   │   ├── dto
│   │   │   ├── StageRegisterCommand.java
│   │   │   ├── StageResult.java
│   │   │   ├── StageStatusUpdateCommand.java
│   │   │   └── StageUpdateCommand.java
│   │   ├── port
│   │   │   └── StageLogPort.java
│   │   └── service
│   │       ├── StageDeleteService.java
│   │       ├── StageQueryService.java
│   │       ├── StageRegisterService.java
│   │       ├── StageStatusUpdateService.java
│   │       └── StageUpdateService.java
│   ├── domain
│   │   ├── Stage.java
│   │   ├── StageName.java
│   │   ├── StageStatus.java
│   │   ├── exception
│   │   │   └── StageErrorCode.java
│   │   ├── repository
│   │   │   ├── StageRepository.java
│   │   │   └── StageQueryRepository.java
│   │   └── service
│   │       └── StageCascadeDeleter.java
│   ├── infrastructure
│   │   ├── StageRelatedEntitiesDeleter.java
│   │   ├── adapter
│   │   │   ├── StageJpaRepositoryAdapter.java
│   │   │   └── StageQueryRepositoryAdapter.java
│   │   ├── log
│   │   │   └── publisher
│   │   │       └── StageLogPublisher.java
│   │   ├── messaging
│   │   │   └── event
│   │   │       └── StageLogEvent.java
│   │   └── repository
│   │       └── StageJpaRepository.java
│   └── presentation
│       ├── StageApi.java
│       ├── StageQueryApi.java
│       └── dto
│           ├── request
│           │   ├── StageRegisterRequest.java
│           │   ├── StageStatusUpdateRequest.java
│           │   └── StageUpdateRequest.java
│           └── response
│               ├── StageDetailResponse.java
│               ├── StageListResponse.java
│               ├── StageRegisterResponse.java
│               ├── StageStatusUpdateResponse.java
│               └── StageUpdateResponse.java
│
└── stageseat
    ├── application
    │   ├── dto
    │   │   ├── StageSeatDetailResult.java
    │   │   ├── StageSeatListResult.java
    │   │   ├── StageSeatRegisterCommand.java
    │   │   ├── StageSeatResult.java
    │   │   ├── StageSeatStatusUpdateCommand.java
    │   │   └── StageSeatUpdateCommand.java
    │   └── service
    │       ├── StageSeatDeleteService.java
    │       ├── StageSeatDetailService.java
    │       ├── StageSeatListService.java
    │       ├── StageSeatRegisterService.java
    │       ├── StageSeatStatusUpdateService.java
    │       └── StageSeatUpdateService.java
    ├── domain
    │   ├── StageSeat.java
    │   ├── StageSeatLocation.java
    │   ├── StageSeatNumber.java
    │   ├── StageSeatStatus.java
    │   ├── VectorValue.java
    │   ├── PostgresVectorType.java
    │   ├── exception
    │   │   └── StageSeatErrorCode.java
    │   └── repository
    │       ├── StageSeatRepository.java
    │       └── StageSeatQueryRepository.java
    ├── infrastructure
    │   └── adapter
    │       └── StageSeatQueryRepositoryAdapter.java
    └── presentation
        ├── StageSeatApi.java
        ├── StageSeatQueryApi.java
        └── dto
            ├── request
            │   ├── StageSeatDeleteRequest.java
            │   ├── StageSeatRegisterRequest.java
            │   ├── StageSeatStatusUpdateRequest.java
            │   └── StageSeatUpdateRequest.java
            └── response
                ├── StageSeatDetailResponse.java
                ├── StageSeatListResponse.java
                ├── StageSeatRegisterResponse.java
                ├── StageSeatStatusUpdateResponse.java
                └── StageSeatUpdateResponse.java

```

## 도메인 모델

---

## ArtHall (Aggregate Root)

아트홀의 기본 정보와 운영 상태를 관리하는 최상위 애그리거트입니다.  
Stage, StageSeat 도메인의 기준이 되는 루트 엔티티입니다.

### 속성

| 필드 | 타입 | 설명 |
|---|---|---|
| artHallId | LONG | 아트홀 ID |
| name | ArtHallName | 아트홀 이름 |
| address | ArtHallAddress | 아트홀 주소 |
| status | ArtHallStatus | 아트홀 운영 상태 |
| createdAt | LocalDateTime | 생성 시각 |
| createdBy | String | 생성자 |
| updatedAt | LocalDateTime | 수정 시각 |
| updatedBy | String | 수정자 |
| deletedAt | LocalDateTime | 삭제 시각 |
| deletedBy | String | 삭제자 |

### 행위

- `register(name, address, status)`
- `updateInfo(name, address, status)`
- `changeStatus(status)`
- `softDelete()` (soft delete)

### 규칙

1. 아트홀 이름과 주소는 공백일 수 없다.
2. 아트홀 상태가 `INACTIVE`이면 스테이지를 생성할 수 없다.
3. 아트홀 삭제 시 소속 스테이지도 함께 삭제된다.

### Value Objects

| VO | 타입 | 설명 |
|---|---|---|
| ArtHallName | VARCHAR(50) | 아트홀 이름 |
| ArtHallAddress | VARCHAR(255) | 아트홀 주소 |

### 아트홀 운영 상태 (ArtHallStatus)

ENUM

- ACTIVE
- INACTIVE

---

## Stage (Aggregate Root)

아트홀에 소속된 개별 무대를 관리하는 애그리거트입니다.

### 속성

| 필드 | 타입 | 설명 |
|---|---|---|
| stageId | LONG | 스테이지 ID |
| artHallId | LONG | 소속 아트홀 ID |
| name | StageName | 스테이지 이름 |
| status | StageStatus | 스테이지 운영 상태 |
| createdAt | LocalDateTime | 생성 시각 |
| createdBy | String | 생성자 |
| updatedAt | LocalDateTime | 수정 시각 |
| updatedBy | String | 수정자 |
| deletedAt | LocalDateTime | 삭제 시각 |
| deletedBy | String | 삭제자 |

### 행위

- `register(artHallId, name, status)`
- `updateInfo(name, status)`
- `changeStatus(status)`
- `delete()` (soft delete)

### 규칙

1. 스테이지는 반드시 ArtHall에 종속되어야 한다.
2. 스테이지가 `INACTIVE`이면 좌석을 생성할 수 없다.
3. 스테이지 삭제 시 좌석을 모두 삭제 처리한다.

### Value Objects

| VO | 타입 | 설명 |
|---|---|---|
| StageName | VARCHAR(50) | 스테이지 이름 |

### 스테이지 운영 상태 (StageStatus)

ENUM

- ACTIVE
- INACTIVE

---

## StageSeat (Aggregate Root)

스테이지에 속한 좌석 정보를 관리하는 애그리거트입니다.

### 속성

| 필드 | 타입 | 설명 |
|---|---|---|
| stageSeatId | LONG | 좌석 ID |
| stageId | LONG | 소속 스테이지 ID |
| seatNumber | StageSeatNumber | 좌석 번호 (A1, B7 등) |
| status | StageSeatStatus | 좌석 상태 |
| location | StageSeatLocation | 좌석 위치 정보 |
| createdAt | LocalDateTime | 생성 시각 |
| createdBy | String | 생성자 |
| updatedAt | LocalDateTime | 수정 시각 |
| updatedBy | String | 수정자 |
| deletedAt | LocalDateTime | 삭제 시각 |
| deletedBy | String | 삭제자 |

### 행위

- `create(stageId, seatNumber, status)`
- `changeStatus(status)`
- `updateLocation(location)`
- `delete()` (soft delete)

### 규칙

1. 좌석 번호는 스테이지 내에서 고유해야 한다.
2. 좌석 상태가 `INACTIVE`이면 예매할 수 없다.
3. 스테이지가 `INACTIVE`이면 좌석 활성화 및 예약이 불가능하다.
4. `location.row`, `location.col`은 0 이상의 정수여야 한다.
5. `location.vector`는 null일 수 없으며 pgvector 형식이어야 한다.

### Value Objects

#### StageSeatNumber

- 타입: `VARCHAR(10)`
- 규칙: 영문 대문자 + 숫자(0~999) 조합만 허용 (A1, B21 등)

#### StageSeatLocation

좌석의 논리적 위치와 실제 공간 좌표를 함께 표현하는 Value Object

| 필드 | 타입 | 설명 |
|---|---|---|
| row | int | 논리적 행 번호 (≥ 0) |
| col | int | 논리적 열 번호 (≥ 0) |
| vector | VectorValue | pgvector 기반 실제 좌표 |

> row/col은 좌석 배열 구조를 위한 논리 정보이며,  
> vector는 UI에서 좌석을 그리기 위한 실제 물리 좌표이다.

### 스테이지 좌석 운영 상태 (StageSeatStatus)

ENUM

- ACTIVE
- INACTIVE

## 주요 기능

### 아트홀 관리
- 아트홀 등록
- 아트홀 정보 수정 (이름, 주소, 상태)
- 아트홀 상태 변경 (ACTIVE / INACTIVE)
- 아트홀 소프트 삭제
- 아트홀 상세 조회
- 아트홀 목록 조회 (페이징, 키워드 검색)

### 스테이지 관리
- 특정 아트홀에 스테이지 등록
- 스테이지 정보 수정
- 스테이지 상태 변경 (ACTIVE / INACTIVE)
- 스테이지 소프트 삭제
- 스테이지 상세 조회
- 특정 아트홀의 스테이지 목록 조회 (페이징, 키워드 검색)

### 스테이지 좌석 관리
- 스테이지 좌석 다건 등록
- 좌석 위치 정보 수정 (row, col, vector)
- 좌석 상태 다건 변경
- 좌석 다건 삭제
- 좌석 상세 조회
- 특정 스테이지의 좌석 목록 조회 (좌석 번호 부분 검색 지원)

---

## 연쇄 삭제(Cascade Delete) 처리

ArtHall Service는 상위 도메인 삭제 시, 하위 도메인을 명시적으로 삭제하는
**애플리케이션 레벨의 연쇄 삭제(Cascade Delete)** 방식을 사용합니다.

DB Cascade 옵션은 사용하지 않으며, 도메인 규칙에 따라
삭제 흐름을 코드로 명확히 제어합니다.

---

### ArtHall 삭제 시 연쇄 처리

- 아트홀 삭제 요청 시, 해당 아트홀에 속한 모든 스테이지를 조회합니다.
- 각 스테이지에 대해 다음 순서로 처리합니다.
    1. 스테이지 소프트 삭제 수행
    2. 스테이지 삭제 완료 로그 발행

해당 로직은 `ArtHallCascadeDeleter` 인터페이스를 통해 추상화되어 있으며,
Infrastructure 계층에서 실제 삭제 흐름을 구현합니다.

---

### Stage 삭제 시 연쇄 처리

- 스테이지 삭제 시, 해당 스테이지에 속한 모든 좌석을 조회합니다.
- 조회된 좌석 ID 목록을 기준으로 좌석을 일괄 소프트 삭제합니다.

이 과정 또한 `StageCascadeDeleter`를 통해 분리되어 있으며,
Stage 도메인의 삭제 책임을 명확히 유지합니다.

---

### 설계 의도

- DB Cascade에 의존하지 않고 **도메인 규칙을 코드로 명시**
- 삭제 흐름을 서비스 단위로 제어하여 **로그 발행, 추가 처리 확장에 용이**
- 상위 도메인과 하위 도메인 간 결합도를 낮추고 책임을 명확히 분리

## API 명세

Base URL: `/api/v1/arthalls`

---

### 아트홀 (ArtHall)

#### 아트홀 등록
| Method | Endpoint | 설명 |
|------|---------|----|
| POST | `/` | 아트홀 등록 |

#### 아트홀 수정
| Method | Endpoint | 설명 |
|------|---------|----|
| PUT | `/{id}` | 아트홀 정보 수정 |

#### 아트홀 삭제
| Method | Endpoint | 설명 |
|------|---------|----|
| DELETE | `/{id}` | 아트홀 소프트 삭제 |

#### 아트홀 상태 변경
| Method | Endpoint | 설명 |
|------|---------|----|
| POST | `/{id}/status` | 아트홀 상태 변경 |

#### 아트홀 상세 조회
| Method | Endpoint | 설명 |
|------|---------|----|
| GET | `/{id}` | 아트홀 상세 조회 |

#### 아트홀 목록 조회
| Method | Endpoint | 설명 |
|------|---------|----|
| GET | `/` | 아트홀 목록 조회 (페이징, 키워드 검색) |

---

### 스테이지 (Stage)

#### 스테이지 등록
| Method | Endpoint | 설명 |
|------|---------|----|
| POST | `/{artHallId}/stages` | 특정 아트홀에 스테이지 등록 |

#### 스테이지 수정
| Method | Endpoint | 설명 |
|------|---------|----|
| PUT | `/stages/{stageId}` | 스테이지 정보 수정 |

#### 스테이지 삭제
| Method | Endpoint | 설명 |
|------|---------|----|
| DELETE | `/stages/{stageId}` | 스테이지 소프트 삭제 |

#### 스테이지 상태 변경
| Method | Endpoint | 설명 |
|------|---------|----|
| POST | `/stages/{stageId}/status` | 스테이지 상태 변경 |

#### 스테이지 상세 조회
| Method | Endpoint | 설명 |
|------|---------|----|
| GET | `/stages/{stageId}` | 스테이지 상세 조회 |

#### 스테이지 목록 조회
| Method | Endpoint | 설명 |
|------|---------|----|
| GET | `/{artHallId}/stages` | 스테이지 목록 조회 (페이징, 키워드 검색) |

---

### 스테이지 좌석 (StageSeat)

#### 좌석 등록 (다건)
| Method | Endpoint | 설명 |
|------|---------|----|
| POST | `/stages/{stageId}/stage-seats` | 스테이지 좌석 다건 등록 |

#### 좌석 위치 수정
| Method | Endpoint | 설명 |
|------|---------|----|
| PUT | `/stages/stage-seats/{seatId}` | 좌석 위치 수정 |

#### 좌석 상태 변경 (다건)
| Method | Endpoint | 설명 |
|------|---------|----|
| POST | `/stages/stage-seats/status` | 좌석 상태 다건 변경 |

#### 좌석 삭제 (다건)
| Method | Endpoint | 설명 |
|------|---------|----|
| DELETE | `/stages/stage-seats` | 좌석 다건 삭제 |

#### 좌석 상세 조회
| Method | Endpoint | 설명 |
|------|---------|----|
| GET | `/stage-seats/{stageSeatId}` | 좌석 상세 조회 |

#### 좌석 목록 조회
| Method | Endpoint | 설명 |
|------|---------|----|
| GET | `/stages/{stageId}/stage-seats` | 좌석 목록 조회 (좌석 번호 부분 검색) |

## 이벤트

### 발행 이벤트 (Producer)

| 이벤트             | Routing Key   | 대상 서비스      | 설명             |
|-----------------|---------------|-------------|----------------|
| ArtHallLogEvent | `arthall.log` | Log Service | 아트홀 관련 로그 정보 발송 |

## 실행 방법

### 환경 변수

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tickatch
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  rabbitmq:
    host: localhost
    port: 5672
    username: ${RABBITMQ_USERNAME}
    password: ${RABBITMQ_PASSWORD}
```


### 실행

```bash
./gradlew bootRun
```

### 테스트

```bash
./gradlew test
```

### 코드 품질 검사

```bash
./gradlew spotlessApply spotbugsMain spotbugsTest
```

## 데이터 모델

### ERD
## 데이터 모델 (ERD)
```
┌───────────────────────────────────────────────────────────┐
│ art_hall                                                  │
├───────────────────────────────────────────────────────────┤
│ id (PK) BIGINT                                            │
│ name VARCHAR(50)                                          │
│ address VARCHAR(255)                                      │
│ status VARCHAR                                            │
│ created_at TIMESTAMP                                      │
│ created_by VARCHAR                                        │
│ updated_at TIMESTAMP                                      │
│ updated_by VARCHAR                                        │
│ deleted_at TIMESTAMP                                      │
│ deleted_by VARCHAR                                        │
└───────────────────────────────────────────────────────────┘

┌───────────────────────────────────────────────────────────┐
│ stage                                                     │
├───────────────────────────────────────────────────────────┤
│ id (PK) BIGINT                                            │
│ art_hall_id (FK) BIGINT                                   │
│ name VARCHAR(50)                                          │
│ status VARCHAR                                            │
│ created_at TIMESTAMP                                      │
│ created_by VARCHAR                                        │
│ updated_at TIMESTAMP                                      │
│ updated_by VARCHAR                                        │
│ deleted_at TIMESTAMP                                      │
│ deleted_by VARCHAR                                        │
└───────────────────────────────────────────────────────────┘

┌───────────────────────────────────────────────────────────┐
│ stage_seat                                                │
├───────────────────────────────────────────────────────────┤
│ id (PK) BIGINT                                            │
│ stage_id (FK) BIGINT                                      │
│ seat_number VARCHAR(10)                                   │
│ status VARCHAR                                            │
│ row INTEGER                                               │
│ col INTEGER                                               │
│ vector VECTOR (pgvector)                                  │
│ created_at TIMESTAMP                                      │
│ created_by VARCHAR                                        │
│ updated_at TIMESTAMP                                      │
│ updated_by VARCHAR                                        │
│ deleted_at TIMESTAMP                                      │
│ deleted_by VARCHAR                                        │
└───────────────────────────────────────────────────────────┘
```

## 관련 서비스/프로젝트

| 서비스                 | 역할    |
|---------------------|-------|
| Log Service         | 로그 관리 |

---
© 2025 Tickatch Team
