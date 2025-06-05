# isBlank()란?

- Java 11부터 `String` 클래스에 추가된 메서드
- 문자열이 **비어있거나**, **공백 문자(whitespace)로만 이루어져 있으면** `true`를 반환
- 그렇지 않으면 `false` 반환

---

## 사용 예시

```java
void example() {
    String str1 = "A";
    System.out.println(str1.isBlank());  // false

    String str2 = " ";
    System.out.println(str2.isBlank());  // true 
}
```

---

## 요약

| 메서드         | 설명                            |
|-------------|-------------------------------|
| `isBlank()` | 문자열이 비어 있거나 공백 문자로만 이루어졌는지 확인 |

> 내부적으로 `Character.isWhitespace()`를 사용해서 공백 문자 여부를 판단함

---
