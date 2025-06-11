## record pattern

- 자바 19에서 preview feature로 처음 등장
- 자바 21부터 정식 기능으로 추가됨.
- `record pattern`은 `record class`를 `instanceof`와 함께 사용할 때, **내부 필드에 직접 접근할 수 있는 문법**
- 다른 언어에서 흔히 볼 수 있는 **비구조화 할당(deconstruction)** 방식과 유사함

---

### 코드 비교

- 아래 코드는 `Point` 객체의 필드에 접근하기 위해 한 단계 더 들어가야 함.
- 이때 `record pattern`을 활용하면 필드 추출이 훨씬 간결해짐.

```java
record Point(double x, double y) {
}
```

```java
public static void findDistanceIfPoint(Object object) {
    // 기존 코드
    if (object instanceof Point p) {
        double distance = Math.hypot(p.x(), p.y());
        System.out.printf("원점으로부터의 거리는 %.3f입니다.\n", distance);
    }

    // record 패턴
    if (object instanceof Point(double x, double y)) {
        double distance = Math.hypot(x, y);
        System.out.printf("원점으로부터의 거리는 %.3f입니다.\n", distance);
    }

    // record 패턴: 타입을 명시하지 않고 var 키워드를 활용할 수도 있음
    if (object instanceof Point(var x, var y)) {
        double distance = Math.hypot(x, y);
        System.out.printf("원점으로부터의 거리는 %.3f입니다.\n", distance);
    }
}

```

---

### 중첩된 record class도 지원됨

```java
record Point(double x, double y) {
}

record Line(Point p1, Point p2) {
}

public static void findDistance(Object object) {
    if (object instanceof Line(Point(var x1, var y1), Point(var x2, var y2))) {
        double distance = Math.hypot(x2 - x1, y2 - y1);
        System.out.println("두 점 사이의 거리는 %.3f입니다.".formatted(distance));
    }
}
```

---

### 요약

- record pattern은 switch pattern matching과 함께 쓰면 더욱 강력해짐.
- 구조가 복잡한 데이터를 명확하게 패턴화해서 다룰 수 있음.

| 항목    | 설명                                               |
|-------|--------------------------------------------------|
| 도입    | Java 19 (preview), Java 21 (정식 기능)               |
| 개념    | `record` 클래스의 필드를 `instanceof` 조건문 안에서 직접 꺼내서 사용 |
| 장점    | 불필요한 접근자 호출 없이 간단하게 필드 분해 가능                     |
| 유사 개념 | 구조 분해 (deconstruction), 패턴 매칭                    |
