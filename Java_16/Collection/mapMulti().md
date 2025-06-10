## mapMulti()

- 자바 16에서 추가됨
- `mapMulti()`는 기존의 `flatMap()` + `filter()` + `map()` 과 같은 기능을 한 번에 처리할 수 있는 중간 연산자임.

---

### 기존 방식 (flatMap + filter + map)

```java
void example() {
    List<List<Number>> nums = List.of(List.of(1.0, 2.0), List.of(3, 4, 5));

    List<Double> r = nums.stream()
            .flatMap(Collection::stream)
            .filter(n -> n instanceof Double)
            .map(n -> (double) n)
            .toList();
}
```

---

### `mapMulti()` 사용 방식

```java
void example() {
    List<List<Number>> nums = List.of(List.of(1.0, 2.0), List.of(3, 4, 5));

    List<Double> r = nums.stream()
            .<Double>mapMulti((numberList, consumer) -> {
                for (Number num : numberList) {
                    if (num instanceof Double) {
                        consumer.accept((double) num);
                    }
                }
            })
            .toList();
}
```

---

### 특징

- `BiConsumer`를 매개변수로 받음:
    - 첫 번째 인자 → 스트림 원소
    - 두 번째 인자 → 다음 스트림으로 전달할 데이터를 넘기는 `Consumer`

- 선언형 스타일보다 명령형 스타일에 가까움
- 비어 있는 Stream을 생성하지 않기 때문에 `flatMap()`보다 메모리 효율적

