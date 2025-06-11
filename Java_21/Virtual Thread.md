## Virtual Thread

- 자바 21에서 Project Loom의 결과물로 도입된 경량 스레드
- 기존의 플랫폼 스레드(Native Thread)와 비교해 가볍고 수천 ~ 수만 개 생성 가능

### 플랫폼 스레드 vs 가상 스레드

| 항목    | 플랫폼 스레드                   | 가상 스레드              |
|-------|---------------------------|---------------------|
| 생성 비용 | 높음 (OS 스레드 생성)            | 낮음                  |
| 풀링 필요 | 필요함                       | 대부분 불필요             |
| 스케줄링  | OS 레벨                     | JVM 내부              |
| 목적    | Thread-per-request 구조에 한계 | 동일 구조를 확장성 있게 구현 가능 |

## 가상 스레드 특징

- `Thread.ofVirtual().start(...)` 또는 `Thread.startVirtualThread(...)` 사용
- `Executors.newVirtualThreadPerTaskExecutor()`로도 실행 가능
- `mount` / `unmount` 개념 존재 (플랫폼 스레드에 탑승하거나 해제됨)
- Carrier Thread: 가상 스레드를 실행하는 플랫폼 스레드
- ThreadLocal 대신 `ScopedValue` 사용 권장
- ThreadPool 기반 구조보다 적합하지 않음

## 스프링에서 가상 스레드 사용

### Spring MVC (Spring Boot 3.2 이상)

```yaml
spring:
  threads:
    virtual:
      enabled: true
```

- Tomcat에서 가상 스레드로 요청 처리 가능
- @Async, Scheduler, RabbitMQ, Kafka Listener 등도 적용 가능

### 주의사항

- JDBC 커넥션 풀 사용 시 주의 (커넥션 고갈 가능성)
- Pinning 현상 주의 (synchronized, native 메서드 등에서 발생)
- ThreadLocal은 메모리 이슈 발생 우려 → ScopedValue 사용 권장

## StructuredTaskScope

- 여러 가상 스레드를 함께 관리하는 구조화된 동시성 도입
- 일부 실패 시 전체 취소 등 정책 지정 가능

## 가상 스레드 vs 코루틴

| 항목           | 가상 스레드 (Java)            | 코루틴 (Kotlin)      |
|--------------|--------------------------|-------------------|
| 목표           | Thread-per-request 구조 확장 | 비동기 코드를 동기처럼 작성   |
| 구현 위치        | JVM 내                    | 언어 레벨(Kotlin)     |
| Blocking I/O | 자동 Unmount               | 스레드 Blocking 발생   |
| 주요 차이점       | 기존 Thread API 호환성        | 리액티브와 유사한 비동기 스타일 |

- 코루틴은 `async/await`, `suspend` 기반
- 리액티브 프로그래밍을 더 쉽게 구현할 수 있음
- VirtualThread는 기존 코드를 변경 없이 확장성 부여

---

> 앞으로 더 많은 프레임워크와 라이브러리들이 가상 스레드를 지원하게 되면서 진정한 활용 사례가 확대될 것으로 기대됨
