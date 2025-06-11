## repeat()

- `StringBuilder` / `StringBuffer` 개선
- 반복 문자열을 효율적으로 버퍼에 추가할 수 있음

```java
void example() {
    StringBuilder sb = new StringBuilder();
    sb.repeat("ABC ", 3);
    System.out.println(sb); // "ABC ABC ABC "
}
```
