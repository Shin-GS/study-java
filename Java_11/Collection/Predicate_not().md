# Predicate.not()란?

- Java 11부터 `Predicate` 인터페이스에 **static 메서드**인 `not()`이 추가됨
- 기존의 조건을 **반대로 적용**할 수 있음 (negation)

---

## 예제

```java
void example() {
    List<String> strings = List.of("A", " ", " ");
    List<String> result = strings.stream()
            .filter(Predicate.not(String::isBlank))  // 공백이 아닌 문자열만 필터링
            .collect(Collectors.toList());
}
```

- `String::isBlank`은 공백이면 `true`
- `Predicate.not()`으로 조건을 반전시켜 공백이 **아닌** 문자열만 필터링함
- `String::isNotBlank`는 존재하지 않기 때문에 유용하게 사용 가능

---

## 기존 방식과 비교

```java
void example() {
    // Java 10 이하 방식
    // .filter(s -> !s.isBlank())

    // Java 11 이후 방식
    // .filter(Predicate.not(String::isBlank))
}
```

- 메서드 레퍼런스를 그대로 유지할 수 있어 **가독성 향상**

---

## 요약

| 메서드                | 설명                      |
|--------------------|-------------------------|
| `Predicate.not(p)` | 주어진 predicate의 조건을 반전시킴 |

> 조절할 수 없는 메서드의 결과를 반전해야 할 때 유용함

---
