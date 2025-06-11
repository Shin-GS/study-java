## splitWithDelimiters()

- **구분자(delimiter)**까지 포함해서 나눠줌
- 텍스트 파싱이나 구조 보존이 필요한 상황에서 유용

```java
String str = "A;B;C";

String[] result1 = str.split(";"); // [A, B, C]
String[] result2 = str.splitWithDelimiters(";", -1); // [A, ;, B, ;, C]
```
