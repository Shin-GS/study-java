# ZGC란?

- Java 11에서 **실험적(Experimental)** 가비지 컬렉터로 처음 도입됨
- **매우 짧은 pause time(멈춤 시간)**을 목표로 하는 저지연(低遲延) GC
- G1GC와 유사하게 메모리를 바둑판처럼 나누어 관리하지만, 훨씬 **큰 힙 크기**와 **대규모 애플리케이션**을 타겟으로 설계됨

---

## 특징

- **Pause Time:** 몇 밀리초 수준
- **Heap 크기:** 수 테라바이트까지 대응 가능
- **Concurrent 처리:** 대부분의 작업을 애플리케이션 스레드와 동시에 수행
- **컴파일러 친화적:** 최신 JVM 내부 구조 반영

---

## Java 버전별 상태

| Java 버전 | 상태                       |
|---------|--------------------------|
| Java 11 | 실험적 (Experimental)       |
| Java 15 | 정식 기능 (Production Ready) |

---

## 사용 방법

```bash
# Java 11 이상에서 ZGC 활성화
java -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -jar app.jar
```

> Java 15 이상에서는 `-XX:+UnlockExperimentalVMOptions` 없이도 사용 가능

---

## 요약

| 항목         | 내용                        |
|------------|---------------------------|
| 이름         | Z Garbage Collector (ZGC) |
| 주요 목적      | 초저지연 GC                   |
| Java 11 상태 | Experimental              |
| Java 15 이후 | Production Ready          |
| 지원 힙 크기    | 수 TB 이상                   |

> ZGC는 지연 시간이 중요한 대규모 시스템에서 매우 유용한 선택지가 될 수 있음

---
