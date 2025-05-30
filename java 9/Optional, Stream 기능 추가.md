## Java 9: Optional과 Stream 기능 추가

---

### Optional 기능 추가

#### 1. `ifPresentOrElse(action, emptyAction)`

* Java 8의 `ifPresent()`는 값이 존재할 때만 동작 가능했지만,
* Java 9에서는 값의 존재 여부에 따라 **두 가지 동작을 지정할 수 있는** API가 추가됨

```java
void example() {
    Optional<String> opt = Optional.of("hello");
    opt.ifPresentOrElse(
            val -> System.out.println("값 있음: " + val),
            () -> System.out.println("값 없음")
    );
}
```

#### 2. `or(Supplier<? extends Optional<T>>)`

* Optional이 비어 있을 경우, **다른 Optional로 대체**할 수 있음

```java
Optional<Integer> opt = getOptionalNum()
        .or(() -> Optional.of(3));
```

#### 3. `stream()`

* Optional을 Stream으로 변환 가능
* 값이 있으면 해당 값을 가진 1개의 Stream, 없으면 빈 Stream

```java
Optional<Integer> opt = Optional.of(10);
Stream<Integer> stream = opt.stream();
```

---

### Stream 기능 추가

#### 1. `takeWhile(Predicate<? super T> predicate)`

* 조건이 **true인 동안만** 요소 유지 → 첫 `false` 이후 모든 요소는 제거됨

```java
List<Integer> result = Stream.of(10, 5, 15, 3, 20)
        .takeWhile(n -> n <= 10)
        .collect(Collectors.toList());
// 결과: [10, 5]
```

#### 2. `dropWhile(Predicate<? super T> predicate)`

* 조건이 **true인 동안 요소 제거** → 첫 `false` 이후 모든 요소 유지

```java
List<Integer> result = Stream.of(10, 5, 15, 3, 20)
        .dropWhile(n -> n <= 10)
        .collect(Collectors.toList());
// 결과: [15, 3, 20]
```

#### 3. `ofNullable(T element)`

* null 허용 값으로 Stream 생성
* 값이 null이면 빈 Stream, 아니면 단일 요소 Stream

```java
Stream<String> s1 = Stream.ofNullable("abc"); // ["abc"]
Stream<String> s2 = Stream.ofNullable(null);  // []
```

#### 4. 개선된 `iterate()` 메소드

* 기존 `iterate()`는 무한 스트림 생성 → 제한을 위해 `limit()` 필요

```java
void example() {
    Stream.iterate(0, i -> i + 2)
            .limit(5)
            .forEach(System.out::println); // 0 2 4 6 8
}
```

* Java 9부터는 **종료 조건**을 직접 명시할 수 있음 → 유한 스트림 생성 가능

```java
void example() {
    // 마치 for문과 유사한 구조
    for (int i = 0; i < 10; i += 2) {
        // i는 0, 2, 4, 6, 8
    }

    Stream.iterate(0, i -> i < 10, i -> i + 2)
            .forEach(System.out::println); // 0 2 4 6 8
}
```

---

### 정리

| 항목       | 추가된 메소드             | 기능 요약                     |
|----------|---------------------|---------------------------|
| Optional | `ifPresentOrElse()` | 존재 여부에 따라 다른 동작 수행        |
|          | `or()`              | 대체 Optional 제공            |
|          | `stream()`          | Optional → Stream 변환      |
| Stream   | `takeWhile()`       | 조건이 false 될 때까지 요소 유지     |
|          | `dropWhile()`       | 조건이 false 되기 전까지 요소 제거    |
|          | `ofNullable()`      | null-safe 단일 요소 Stream 생성 |
|          | 개선된 `iterate()`     | 종료 조건 있는 유한 스트림 생성        |
