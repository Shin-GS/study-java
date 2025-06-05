# copyOf()

---

## copyOf()란?

- Java 10부터 `List.copyOf()`, `Set.copyOf()`, `Map.copyOf()` 메서드가 추가됨
- **불변 컬렉션(immutable collection)**을 생성함
- 원본 컬렉션의 깊은 복사(deep copy)를 수행하며, 복사된 컬렉션은 수정할 수 없음

---

## 예제: 깊은 복사 확인

```java
void example() {
    List<Integer> oldNums = new ArrayList<>();
    oldNums.add(1);
    oldNums.add(2);

    List<Integer> newNums = List.copyOf(oldNums);

    oldNums.add(3);

    oldNums.forEach(x -> System.out.println("old : " + x)); // 1, 2, 3
    newNums.forEach(x -> System.out.println("new : " + x)); // 1, 2
}
```

- `oldNums`에 값을 추가해도 `newNums`에는 영향을 주지 않음
- `newNums`는 완전히 독립적인 **불변 컬렉션**

---

## Collections.unmodifiableList와의 차이점

```java
void example() {
    List<Integer> oldNums = new ArrayList<>();
    oldNums.add(1);
    oldNums.add(2);

    List<Integer> newNums = Collections.unmodifiableList(oldNums);

    oldNums.add(3);

    oldNums.forEach(x -> System.out.println("old : " + x)); // 1, 2, 3
    newNums.forEach(x -> System.out.println("new : " + x)); // 1, 2, 3 (!!)
}
```

- `Collections.unmodifiableList()`는 **원본 컬렉션의 뷰(view)**만 제공
- 원본이 변경되면, 뷰도 함께 변경됨
- 진짜 복사본이 아님

---

## Stream API와 함께 사용

- Java 10부터는 `Collectors.toUnmodifiableList()`, `toUnmodifiableSet()`, `toUnmodifiableMap()`도 제공됨

```java
List<Integer> list = Stream.of(1, 2, 3, 4, 5)
        .filter(x -> x % 2 == 0)
        .collect(Collectors.toUnmodifiableList());  // [2, 4]
```

- 결과는 **불변 리스트**
- 이후 `.add()`나 `.remove()` 시도 시 예외 발생

---
