# Switch Expression

## 기존 switch 문 방식

```java
private String calculateTestGrade(int score) {
    String grade = "";
    switch (score) {
        case 5:
            grade = "A";
            break;
        case 4:
        case 3:
            grade = "B";
            break;
        case 2:
        case 1:
            grade = "C";
            break;
        default:
            grade = "F";
    }
    return grade;
}
```

- 기존 switch 문은 statement였음
- 결과 값을 담을 변수 선언 → 조건 분기 → 값 할당 → 변수 반환 구조임
- `case`마다 `:`와 `break`를 꼭 써야 했음
- 여러 조건을 하나로 처리하려면 `case 4: case 3:`처럼 반복해서 써야 했음

---

## statement vs expression

- **statement**는 실행은 되지만 값은 없는 문장이었음 (예: `if`, `while`, `switch`)
- **expression**은 실행 결과가 어떤 값으로 평가되는 문장이었음

```java
void example() {
// 이 코드는 컴파일 에러임 - if는 expression이 아니라 statement임
    String result = if (score > 50) {
        "PASS";
    } else {
        "FAIL";
    }
}
```

---

## Switch Expression의 등장

- 자바 12에서 preview로 추가되고, 자바 14에서 정식 기능으로 채택됨
- 기존 switch 문을 expression으로 사용할 수 있게 바뀐 거임

```java
private String calculateTestGrade(int score) {
    return switch (score) {
        case 5:
            yield "A";
        case 4, 3:
            yield "B";
        case 2, 1:
            yield "C";
        default:
            yield "F";
    };
}
```

- `yield` 키워드를 사용해서 값을 반환함
- switch 전체가 하나의 expression으로 평가되기 때문에 `;` 붙는 구조임
- 여러 case 값을 `,`로 묶을 수 있어서 더 간결함

---

## 화살표(case →) 문법도 가능함

```java
private String calculateTestGrade(int score) {
    return switch (score) {
        case 5 -> "A";
        case 4, 3 -> "B";
        case 2, 1 -> "C";
        default -> "F";
    };
}
```

- `->` 문법을 쓰면 `yield` 없이도 바로 값 반환 가능함
- 여러 줄을 쓰고 싶을 땐 `{}` 블록 안에 `yield` 써야 함

```java
void example() {
    case 5 -> {
        System.out.println("만점입니다!");
        yield "A";
    }
}
```

---

## 모든 분기에서 값이 반환되어야 함

```java
void example() {
    // ❌ 컴파일 에러 발생 - 일부 분기에서 결과 없음
    String grade = switch (score) {
        case 5 -> "A";
        default -> {
            System.out.println("A 말고는 안 줌");
            // yield 없음
        }
    };
}
```

```java
void example() {
    // ✅ 예외 던지면 OK - 처리된 걸로 간주함
    String grade = switch (score) {
        case 5 -> "A";
        default -> {
            System.out.println("예외 발생");
            throw new IllegalArgumentException();
        }
    };
}
```

---

## Enum과 함께 사용하면 깔끔함

```java
enum Color {RED, YELLOW, GREEN}

public String getSignal(Color color) {
    return switch (color) {
        case RED -> "멈추세요.";
        case YELLOW -> "곧 빨간색으로 바뀝니다.";
        case GREEN -> "지나가세요.";
    };
}
```

- enum 타입은 컴파일 타임에 모든 값이 정해져 있으니 default 없어도 됨

---

## 요약

| 항목    | 설명                              |
|-------|---------------------------------|
| 도입 시기 | Java 12 (preview), Java 14 (정식) |
| 주요 문법 | `yield`, `->`                   |
| 반환 구조 | switch 전체가 값으로 평가됨              |
| 주의점   | 모든 분기에서 값 반환하거나 예외 던져야 함        |
| 장점    | 기존 switch보다 간결하고 가독성 좋음         |

