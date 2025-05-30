## CompletableFuture API 기능 추가

---

### 개요

Java 9에서는 `CompletableFuture`에 **타임아웃**, **지연 실행**, **기본 Executor 조회** 등의 기능이 추가되었다. 이로써 비동기 프로그래밍을 더 간결하고 유연하게 처리할 수 있게
되었다.

---

### 1. `orTimeout(long timeout, TimeUnit unit)`

* 비동기 작업이 일정 시간 내에 완료되지 않으면 `TimeoutException`으로 완료되도록 설정
* 기존에는 직접 타이머 쓰레드로 구현해야 했던 기능을 간편하게 지원

```java
public static void main(String[] args) throws Exception {
    Runnable sleep = () -> {
        try {
            Thread.sleep(10_000L); // 10초 대기
            System.out.println(System.currentTimeMillis() + " - 작업 완료");
        } catch (InterruptedException e) {
        }
    };

    System.out.println(System.currentTimeMillis() + " - 작업 실행");

    CompletableFuture<Void> future = CompletableFuture.runAsync(sleep)
            .orTimeout(1, TimeUnit.SECONDS); // 1초 내 완료 안 되면 타임아웃

    future.get(); // TimeoutException 발생
}
```

---

### 2. `completeOnTimeout(T value, long timeout, TimeUnit unit)`

* 일정 시간 내에 완료되지 않으면, **예외 대신 지정한 값으로 완료**됨

```java
public static void main(String[] args) throws Exception {
    Runnable sleep = () -> {
        try {
            Thread.sleep(10_000L);
            System.out.println("작업 완료");
        } catch (InterruptedException e) {
        }
    };

    CompletableFuture<String> future = CompletableFuture
            .runAsync(sleep)
            .thenApply(v -> "정상 완료")
            .completeOnTimeout("타임아웃 기본값", 1, TimeUnit.SECONDS);

    System.out.println(future.get()); // "타임아웃 기본값" 출력
}
```

---

### 3. `delayedExecutor(long delay, TimeUnit unit)`

* 일정 시간이 지난 후 작업을 실행하는 `DelayedExecutor` 반환
* 작업을 지연시켜 실행하고 싶을 때 유용함

```java
public static void main(String[] args) throws Exception {
    Executor executor = CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS);

    Runnable task = () -> {
        System.out.println(System.currentTimeMillis() + " - 작업 완료");
    };

    System.out.println(System.currentTimeMillis() + " - 작업 실행");

    CompletableFuture<Void> cf = CompletableFuture.runAsync(task, executor);
    cf.get(); // 약 5초 지연 후 실행됨
}
```

---

### 정리

| 기능명                 | 설명                                           |
|---------------------|----------------------------------------------|
| `orTimeout`         | 설정된 시간 내 작업이 완료되지 않으면 `TimeoutException` 발생  |
| `completeOnTimeout` | 시간 초과 시 예외 없이 지정한 값으로 `CompletableFuture` 완료 |
| `delayedExecutor`   | 지정한 시간 이후에 작업 실행할 수 있는 Executor 반환           |

이러한 기능들을 활용하면 `CompletableFuture` 기반의 비동기 프로그래밍을 더 정교하게 컨트롤할 수 있다.
