## String Template

- 자바 21에서 preview feature로 처음 등장
- 자바 22에서도 preview 기능으로 제공되었으나 <span style="color:red">이후 redesign 작업이 진행되면서 자바 23에서는 이 기능이 제공되지 않음</span>
- 문자열 템플릿(String Template)** 기능이 preview feature로 도입됨.
- 기존의 `String.format()`이나 `formatted()`를 **더 직관적이고 간결하게 대체**할 수 있음.

---

### 기본 사용법

```java
String name = "홍길동";
int age = 50;

String str = STR."이름: \{name} 나이: \{age}";
```

- `\{ ... }` 는 **embedded expression**이라고 부름  
  → 변수, 연산식, 함수 호출 등 모든 expression 가능
- `STR`은 문자열을 결합해주는 **template processor**

ex)

```java
String str = STR."총합: \{1 + 2}";
String str2 = STR."메시지: \{name.toUpperCase()}";
```

```java
// Text Block과도 함께 사용 가능
String str = STR.""" 
이름: \{name}
나이: \{age}
""";
```

---

### 표준 템플릿 프로세서

| Processor | 설명                           |
|-----------|------------------------------|
| `STR`     | 단순 문자열 + 변수 결합               |
| `FMT`     | `String.format()` 스타일 포맷 지원  |
| `RAW`     | 가공 없이 `StringTemplate` 객체 반환 |

ex)

```java
double num = 1.333;
String str = FMT."숫자: %.2f\{num}"; // 출력: 숫자: 1.33
```

```java
void example() {
    String name = "홍길동";
    int age = 50;

    StringTemplate str = RAW."이름: \{name} 나이: \{age}";

    /*
        fragments() → 순수 문자열 부분 리스트 반환
        values() → 변수 목록 반환
        interpolate() → 최종 문자열 생성
     */
    System.out.println(str.fragments());   // [이름: , 나이: , ]
    System.out.println(str.values());      // [홍길동, 50]
    System.out.println(str.interpolate()); // 이름: 홍길동 나이: 50
}
```

---

### 확장성

- `StringTemplate` 인터페이스를 직접 구현해 **커스텀 템플릿 처리기**도 만들 수 있음
- 템플릿 로직을 통제해야 하는 DSL이나 템플릿 엔진 제작 시 활용 가능

---

### 요약

| 항목    | 설명                                    |
|-------|---------------------------------------|
| 도입    | Java 21 (Preview Feature)             |
| 핵심 문법 | `STR."문자열 \{변수}"`                     |
| 기타    | `FMT`는 포맷 스타일 지정, `RAW`는 내부 구조 분석에 유용 |
| 장점    | 템플릿 선언 직관적, 가독성 향상, 커스터마이징 가능         |
