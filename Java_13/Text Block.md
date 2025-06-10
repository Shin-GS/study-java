# Text Block 정리

- 자바 13에서 `preview feature`로 도입되었고, 자바 15에서 정식 기능으로 채택됨
- 여러 줄에 걸친 문자열을 간결하게 표현할 수 있도록 도와주는 기능임

---

## 1. 기본 사용법

여러 줄 문자열을 만들기 위해 기존에는 `\n` 이스케이프 문자나 문자열 연결을 사용함

```java
String str = "A\nBC\nDEF";
String str = "A\n" +
        "BC\n" +
        "DEF";
```

Text Block을 사용하면 다음과 같이 간단하게 표현이 가능

```java
String str = """
        A
        BC
        DEF""";
```

- 닫는 `"""`의 위치에 따라 마지막 줄바꿈 포함 여부가 결정됨

```java
String str = """
        A
        BC
        DEF
        """; // DEF 뒤에 줄바꿈 포함됨
```

---

## 2. 특징 및 주의사항

### 1. 한 줄 사용 불가

Text Block은 시작하는 `"""` 뒤에 반드시 줄바꿈이 있어야 함

```java
// ❌ 컴파일 에러
String str = """A
                BC""";
```

---

### 2. 큰따옴표, 이스케이프 문자

- Text Block 내부에서는 `"`를 그대로 사용 가능함(`\"`을 사용하지 않아도 가능)
- `'`, `\t`등의 다른 이스케이프 문자도 정상 작동함

```java
String json = """
        {
          "name": "John",
          "age": 30
        }
        """;
```

---

### 3. 줄 끝 공백은 자동 제거됨

Text Block 내부 줄 끝에 작성한 공백은 컴파일 시 자동 제거됨

```java
String str = """
         A   // 공백 작성
         BC  
         DEF
        """;
```

→ 결과: `A\nBC\nDEF\n`

#### 공백을 유지하고 싶을 때의 해결법:

- 방법 1) `replace` 사용

```java
String str = """
         A$$
         BC$
         DEF
        """.replace('$', ' ');
```

- 방법 2) fence 문자 사용

```java
String str = """
         A |
         BC |
         DEF|
        """.replace("|\n", "\n");
```

- 방법 3) Octal escape (`\040`)

```java
String str = """
         A\040\040
         BC\040
         DEF
        """;
```

- 방법 4) `\s` escape sequence (Java 15부터 지원)

```java
String str = """
         A\s
         BC\s
         DEF
        """;
```

---

### 4. 줄바꿈 제거 (line terminator: `\`)

문자열 줄바꿈 없이 붙이고 싶을 때는 `\` 사용

```java
String str = """
         A \
         BC \
         DEF
        """;
// 결과: "A BC DEF\n"
```

---

### 5. 들여쓰기 기준선

- 가장 왼쪽에 있는 문자나 닫는 `"""` 위치가 들여쓰기 기준
- 기준선을 따라 다른 줄도 자동 정렬됨
- 공백이든 탭이든 **들여쓰기 한 칸**으로 간주되므로 **혼용하지 말 것**

```java
String str = """
             A
          BC
             DEF
        """;
```

→ `BC`와 `"""`가 가장 왼쪽이므로 A와 DEF는 해당 기준선까지 당겨짐

---

## 요약

| 항목      | 설명                                     |
|---------|----------------------------------------|
| 도입      | Java 13 (preview), Java 15 (정식)        |
| 시작 규칙   | `"""` 다음 줄부터 시작                        |
| 공백 처리   | 줄 끝 공백 자동 제거                           |
| 공백 유지법  | `replace`, `\s`, ` &#124; `, `\040` 사용 |
| 들여쓰기 기준 | 가장 왼쪽 문자 / 닫는 따옴표 기준                   |
