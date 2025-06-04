# Flow API

## 리액티브 프로그래밍

### 리액티브 프로그래밍이란?

- 비동기 스트림 데이터를 다루기 위한 프로그래밍 모델.
- 데이터가 생기면 반응(react)해서 처리하는 방식.
- 콜백 지옥 없이 복잡한 비동기 흐름을 간결하게 표현할 수 있음.

### ex) 카페

- **전통적인 방식(동기)**  
  손님이 카운터에 주문 → 바리스타가 커피 완성할 때까지 기다림 → 손님에게 전달  
  => 손님은 기다리는 동안 아무것도 못 함


- **리액티브 방식(비동기)**  
  손님이 카운터에 주문 → 바리스타는 주문만 받고 커피 준비 → 손님은 호출될 때까지 다른 일을 함  
  => 커피가 완성되면 바리스타가 손님에게 알림 (푸시 방식)

### Spring MVC vs Spring WebFlux

- **Spring MVC**: 스레드 당 요청 1개 처리 (블로킹 방식)
- **Spring WebFlux**: 이벤트 루프 기반, 비동기 논블로킹 방식, 리액티브 스트림 지원

### Subscriber & Publisher

```java
// Publisher
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}

// Subscriber
public interface Subscriber<T> {
    void onSubscribe(Subscription subscription);

    void onNext(T item);

    void onError(Throwable throwable);

    void onComplete();
}
```

- **Publisher**: 데이터 또는 이벤트를 발행
- **Subscriber**: 데이터 또는 이벤트를 구독해서 처리

### Reactive Streams

- 자바에서 비동기 스트림을 처리하기 위한 표준 명세
- 주요 인터페이스: Publisher, Subscriber, Subscription, Processor
- 백프레셔(backpressure) 지원 → 생산자와 소비자의 속도 조절 가능

---

## Flow API

- Java 9에서 도입된 공식 리액티브 스트림 표준 구현
- `java.util.concurrent.Flow` 패키지에 포함
- Reactive Streams 인터페이스를 그대로 가져온 것
- 외부 라이브러리 없이 JDK만으로 리액티브 프로그래밍 가능

---

## Flow API

대표 인터페이스

```java
public final class Flow {
    public interface Publisher<T> {
        void subscribe(Subscriber<? super T> subscriber);
    }

    public interface Subscriber<T> {
        void onSubscribe(Subscription subscription);

        void onNext(T item);

        void onError(Throwable throwable);

        void onComplete();
    }

    public interface Subscription {
        void request(long n);

        void cancel();
    }

    public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
    }
}
```

- `Publisher`는 구독자를 받아서 알림을 보냄
- `Subscriber`는 데이터를 처리함
- `Subscription`으로 백프레셔 제어 가능 (`request(n)`)

---

## RxJava, reactor, Flow API 비교

| 항목        | RxJava        | Reactor           | Flow API      |
|-----------|---------------|-------------------|---------------|
| JDK 버전    | 8 미만에서도 사용 가능 | 8 이상에서만 사용 가능     | 9 이상에서만 사용 가능 |
| 활용        | 클라이언트 중심      | Spring WebFlux 전용 | 표준 호환성 라이브러리  |
| 리액티브 스트림즈 | 약간 다름         | 완전히 동일            | 완전히 동일        |

---
