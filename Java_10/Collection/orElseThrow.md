# `Optional.orElseThrow()`

---

## 변화된 Optional API

- Java 10부터 `Optional`에 **파라미터 없는 `orElseThrow()`**가 추가됨
- 기존에는 항상 예외를 던지는 **Supplier**를 전달해야 했음

---

## Java 10 이전 방식

```java
void example() {
    Optional.ofNullable(3)
            .orElseThrow(() -> new IllegalArgumentException("값이 없습니다."));
}
```

- 항상 예외를 명시적으로 넘겨줘야 했음

---

## Java 10 이후 방식

```java
void example() {
    Optional.ofNullable(3)
            .orElseThrow();  // 값이 없으면 NoSuchElementException 던짐
}
```

- 파라미터 없이 호출 가능
- 내부적으로 `NoSuchElementException`을 기본으로 던짐

---

## 요약

| 구분     | Java 9 이하               | Java 10 이후                   |
|--------|-------------------------|------------------------------|
| 메서드 호출 | `orElseThrow(Supplier)` | `orElseThrow()`              |
| 예외 타입  | 개발자가 Supplier로 직접 지정    | 기본: `NoSuchElementException` |
| 코드 간결성 | 비교적 복잡                  | 간단하고 읽기 쉬움                   |

---
