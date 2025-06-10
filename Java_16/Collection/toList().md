## toList()

- 자바 16에서 추가됨

```java
List<Integer> r = nums.stream()
        .filter(num -> num % 2 == 0)
        .toList();
```

- 이전에는 `collect(Collectors.toUnmodifiableList())` 를 사용해야 했음
- `toList()`는 **불변 리스트**를 반환하는 종결 연산으로, 코드가 훨씬 간결해짐
