# strip()

- 문자열의 **앞뒤에 존재하는 white space**를 모두 제거
- 내부적으로 `Character.isWhitespace(int codePoint)`를 사용해 판단

```java
void example() {
    String str = "  A BC  ";
    System.out.println(str.strip());  // "A BC"
}
```

---

## stripLeading()

- 문자열의 **앞쪽 공백만 제거**

```java
void example() {
    String str = "  A BC  ";
    System.out.println(str.stripLeading());  // "A BC  "
}
```

---

## stripTrailing()

- 문자열의 **뒤쪽 공백만 제거**

```java
void example() {
    String str = "  A BC  ";
    System.out.println(str.stripTrailing());  // "  A BC"
}
```

---

## 요약

| 메서드               | 설명          |
|-------------------|-------------|
| `strip()`         | 앞뒤 공백 모두 제거 |
| `stripLeading()`  | 앞쪽 공백만 제거   |
| `stripTrailing()` | 뒤쪽 공백만 제거   |

> 참고: 기존의 `trim()`은 ASCII 기준의 공백만 제거하지만, `strip()` 계열은 유니코드 기반 공백 제거

---
