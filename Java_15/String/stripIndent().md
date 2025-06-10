## stripIndent()

- 자바 15에서 추가됨
- 텍스트 블록(Text Block)을 좌측 기준으로 최대한 붙여줌
- 실제 문자열에서 들여쓰기의 기준을 맞춰주고 싶을 때 유용함

```java
void example() {
    String str = " A\n  B\n C";
    System.out.println(str.stripIndent());
}
```

[출력 결과]

```
A
 B
C
```

- `B` 앞에는 원래 공백이 2칸 있었기 때문에, 가장 작은 공백(1칸 기준)만 제거됨
