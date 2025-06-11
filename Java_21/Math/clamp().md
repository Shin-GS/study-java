## clamp()

- 자바 21에서 `Math` 클래스에 `clamp()` 함수가 추가됨.
- 단어 의미 그대로 특정 값을 **최소~최대 범위 안에 고정(clamp)**하는 기능을 제공함.

---

### 함수 시그니처

```java
public static int clamp(long value, int min, int max)
```

- `value`가 `min`과 `max` 사이면 → 그대로 반환
- `value`가 `min`보다 작으면 → `min` 반환
- `value`가 `max`보다 크면 → `max` 반환

```java
void example() {
    Math.clamp(5, 1, 10);   // → 5
    Math.clamp(-3, 0, 100); // → 0
    Math.clamp(999, 0, 100); // → 100
}
```

---

### 지원 타입

| 타입     | 시그니처 예시                                       |
|--------|-----------------------------------------------|
| int    | `clamp(long value, int min, int max)`         |
| long   | `clamp(long value, long min, long max)`       |
| float  | `clamp(float value, float min, float max)`    |
| double | `clamp(double value, double min, double max)` |

---

### 용도 예시

- 사용자 입력 값이 범위를 벗어나는 경우 자동 보정
- 계산식 결과를 0 ~ 100 범위 내로 고정할 때
- 게임, 그래픽, 수치 시뮬레이션 등에서 자주 사용됨

---

### 요약

- `clamp()`는 자바 표준 라이브러리에 뒤늦게 추가된 실용적인 함수 중 하나로, 기존에 직접 구현하던 로직을 간단하게 대체할 수 있음.

| 항목    | 설명                               |
|-------|----------------------------------|
| 도입 버전 | Java 21                          |
| 기능    | 값이 min~max 사이인지 검사하고, 초과/미달 시 보정 |
| 대상 타입 | int, long, float, double         |
| 실용성   | 매우 높음 (수치 안정성 확보에 유리)            |
