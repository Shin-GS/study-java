## indent()

- 자바 12에서 추가됨

```java
String str = """
         A
         BC
         DEF
        """.indent(3);
```

- 각 줄마다 앞에 지정한 수만큼의 공백을 추가해 들여쓰기 처리함
- 인자로 **음수**를 줄 경우, 해당 수만큼 **공백을 제거**함

```java
String str = """
         A
         BC
         DEF
        """.indent(-1);
```

[출력 결과]

```
A
BC
DEF
```
