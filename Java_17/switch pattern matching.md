## switch pattern matching

- 자바 17에서 preview feature로 처음 등장
- 자바 21부터 정식 기능으로 추가됨.
- `switch pattern matching`은 `switch`문에서 객체의 타입 검사 및 분해를 **간결하게 처리할 수 있는 기능**
- `instanceof` 조건문과 비슷한 역할을 하며, `sealed class/interface`와 함께 사용하면 더욱 강력함

---

### 코드 비교

#### 1) 기존 사용법

- switch 대상: `char`, `byte`, `short`, `int`, `String`, `enum` 만 가능
- case 값은 **상수**만 허용

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

#### 2) switch pattern matching

- switch의 대상이 reference type (예: Object, Animal 등) 가능
- case 라벨에서 패턴 매칭 가능 (`case Dog dog`)
- `sealed class`일 경우 하위 타입이 모두 명확하므로 `default` 생략 가능

```java
public String sound(Animal animal) {
    return switch (animal) {
        case Dog dog -> dog.bark();
        case Cat cat -> cat.purr();
        default -> throw new IllegalArgumentException("다른 경우의 수는 없음");
    };
}
```

---

### when 절 사용

- `when` 조건절을 통해 **추가 조건 필터링 가능**
- 매칭 순서는 위에서 아래로, **먼저 매칭되는 case가 우선**

```java
public String sound(Animal animal) {
    return switch (animal) {
        case Dog dog when dog.isQuite() -> "";
        case Dog dog -> dog.bark();
        case Cat cat -> cat.purr();
    };
}
```

참고) 컴파일 오류

- case가 아래를 막는 경우
- `when`절이 있는 경우에는 도달 가능성이 있으므로 컴파일 에러 없음

```java
public String sound(Animal animal) {
    return switch (animal) {
        case Animal a -> ""; // 아래 case가 도달 불가능
        case Dog dog -> dog.bark(); // 컴파일 에러
        case Cat cat -> cat.purr();
    };
}
```

---

### null 처리

- `case null`을 통해서 null 처리도 가능
- `case null`과 `default`를 함께 쓸 수도 있음

```java
public String sound(Animal animal) {
    return switch (animal) {
        case Dog dog -> dog.bark();
        case Cat cat -> cat.purr();
        case null -> ""; // null 처리도 case로 가능
    };
}
```

---

### 요약

- `switch pattern matching`은 `record pattern`, `sealed class/interface`와 함께 쓰면 복잡한 분기 로직을 매우 깔끔하게 작성할 수 있음.

| 항목  | 설명                                                    |
|-----|-------------------------------------------------------|
| 도입  | Java 17 (preview), Java 21 (정식 기능)                    |
| 대상  | 객체 참조 타입 전부                                           |
| 장점  | instanceof 분기 제거, 가독성 향상, default 생략 가능 (sealed 활용 시) |
| 확장성 | when 조건절, null 처리, switch expression 가능               |
