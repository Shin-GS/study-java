## teeing()

- 자바 12에서 추가됨
- `Collectors` 클래스에 `teeing()` 함수가 추가됨.
- 두 개의 Collector를 실행한 뒤, 결과를 하나로 합치는 BiFunction을 적용함
- 스트림을 **한 번만 순회**하면서도 두 가지 결과를 동시에 얻을 수 있음

```java
void example() {
    List<FruitDto> fruits = List.of(
            new FruitDto("사과", 100),
            new FruitDto("바나나", 200),
            new FruitDto("사과", 300),
            new FruitDto("수박", 500)
    );

    fruits.stream()
            .collect(Collectors.teeing(
                    Collectors.minBy(Comparator.comparingInt(FruitDto::price)),
                    Collectors.maxBy(Comparator.comparingInt(FruitDto::price)),
                    (f1, f2) -> {
                        f1.ifPresent(f -> System.out.printf("가장 싼 과일은 %s 입니다.\n", f.name()));
                        f2.ifPresent(f -> System.out.printf("가장 비싼 과일은 %s 입니다.\n", f.name()));
                        return 0; // 예제이므로 0을 반환
                    }
            ));
}
```

---

### 매개변수 설명

`teeing()`은 세 개의 인자를 받음:

1. 첫 번째 Collector → 예: `Collectors.minBy(...)`
2. 두 번째 Collector → 예: `Collectors.maxBy(...)`
3. 위 두 결과를 받아 최종 결과를 반환하는 `BiFunction`

---

### 활용 포인트

- Stream을 **두 번 반복하지 않고**, 하나의 Stream으로 두 가지 집계 결과를 동시에 얻고 싶을 때 사용
- 평균 + 합계, 최소값 + 최대값, 개수 + 조건 만족 여부 등 다양한 조합 가능

---

### 참고

- `teeing()`은 Java 12부터 사용 가능
- 결과 타입이 무엇이든 유연하게 처리 가능하므로 래퍼 클래스를 조합해서 응답 DTO로 만들 수도 있음
