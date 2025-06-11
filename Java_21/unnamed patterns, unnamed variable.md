## unnamed patterns & unnamed variable

- 자바 21에서 preview feature로 처음 등장
- 2025년 6월 기준 아직 preview 상태
- 필요 없는 **변수 이름, 타입을 생략**할 수 있어 코드가 더 간결해짐

---

### 기존 코드 예시

- 이 경우, `y1`, `y2`는 사용되지 않음 → 불필요한 변수 선언 발생

```java
public static void findDistance(Object object) {
    if (object instanceof Line(Point(var x1, var y1), Point(var x2, var y2))) {
        double distance = Math.hypot(x2 - x1, y2 - y1);
        System.out.printf("두 점 사이의 거리는 %.3f입니다.\n", distance);
    }
}
```

---

### unnamed patterns / unnamed variable 사용 예시

#### ✅ 완전 생략 (`_`만 사용)

```java
public static void findDistance(Object object) {
    if (object instanceof Line(Point(var x1, _), Point(var x2, _))) {
        System.out.println("x 좌표 사이의 거리는 %.3f입니다.".formatted(x1 - x2));
    }
}
```

#### ✅ 변수 이름만 생략

```java
public static void findDistance(Object object) {
    if (object instanceof Line(Point(var x1, var _), Point(var x2, var _))) {
        System.out.println("x 좌표 사이의 거리는 %.3f입니다.".formatted(x1 - x2));
    }
}
```

---

### 사용 가능한 문맥

- record pattern
- 지역 변수
- `for`문
- 람다 파라미터
- `catch` 블록 예외 처리 등

---

### 활용 예시

```java
void example() {
    // 값은 필요 없고 반복 횟수만 중요할 때
    for (var _ : list) {
    }

    // 결과 필요 없을 때
    Runnable r = () -> {
        var _ = computeSomething();
    };

    // 예외 타입만 체크하고 변수는 필요 없음
    try {
        // ...
    } catch (IOException _) {
    }
}
```

---

### 요약

- 이 기능은 다른 언어(Kotlin, Python 등)의 `_` 문법과 유사하며, 자바 코드의 선언적 표현력을 높여주는 역할을 함.

| 항목 | 설명                                                        |
|----|-----------------------------------------------------------|
| 도입 | Java 21 (preview feature)                                 |
| 목적 | 불필요한 변수 선언 제거                                             |
| 문맥 | 패턴 매칭, 람다, for-each, 예외 처리 등                              |
| 장점 | 가독성 향상, 코드 간결화                                            |
| 문법 | `_`, `var _`, `(var x, _)`, `(var _, var _)` 등 다양하게 사용 가능 |
