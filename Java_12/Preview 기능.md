# Preview 기능의 탄생

- 자바 12부터 자바 17까지는 언어 기능이 활발히 도입된 시기로, 그 시작에는 `preview feature`라는 개념이 도입됨.
- 이 개념은 실험적 기능을 안정적으로 시험해 볼 수 있도록 도와주며, 자바의 발전에 중요한 전환점이 되었음.
- 이와 함께 `experimental`, `incubating` 개념도 함께 등장하며 서로 다른 영역에서 실험적 기능을 제공한다.

## 주요 용어 정리

| 구분        | preview            | experimental  | incubating                  |
|-----------|--------------------|---------------|-----------------------------|
| **적용 대상** | 언어적 기능, JVM        | JVM 기능        | 라이브러리(API)                  |
| **완성도**   | 약 95% (거의 완성됨)     | 약 25% (초기 버전) | 미정 (API 구조 실험 단계)           |
| **사용 방법** | `--enable-preview` | 기능별 플래그 사용    | 모듈 의존성 설정 (`jdk.incubator`) |

> 💡 `preview`, `experimental`, `incubating`은 모두 "실험적"이라는 공통점을 가지지만, 적용 범위와 목적, 완성도 면에서 명확히 구분됨.

---

## 1. Preview Feature

- **정의**: 자바 언어 또는 JVM의 신규 기능을 "거의 완성된 상태"로 미리 사용해 볼 수 있도록 제공하는 기능.
- **특징**
    - 정식 릴리스 이전에 사용자 피드백을 받아 개선 가능.
    - **완성도 약 95%**.
    - `--enable-preview` 옵션을 통해 활성화.
    - 버전에 따라 변경 또는 제거될 수 있음.
- **예시**
    - `Text Block` (자바 13에서 preview → 자바 15에서 정식 도입)
    - `Switch Expression`, `Pattern Matching for instanceof` 등

---

## 2. Experimental Feature

- **정의**: JVM 레벨에서의 기능 초안으로, 매우 초기 단계의 기능을 실험하는 용도.
- **특징**
    - **완성도 약 25%**, 매우 불안정할 수 있음.
    - 보통 명시적인 **JVM 옵션 플래그**로 활성화.
    - 호환성 보장 어려움 → 프로덕션 사용 **비권장**.
- **예시**
    - `ZGC` (자바 11에서 experimental 기능으로 등장)

---

## 3. Incubating Feature

- **정의**: 새로운 API를 실험하기 위한 **모듈 단위**의 기능.
- **특징**
    - `jdk.incubator` 패키지 접두어가 붙음.
    - 자바 **플랫폼 모듈 시스템**(JPMS)을 사용하여 의존성 명시 필요.
    - 실험이 성공하면 정식 패키지로 전환 (`jdk.incubator → java.*`)
- **예시**
    - `java.net.http.HttpClient` (자바 9: incubating → 자바 11: 정식 API로 전환)

---

## 각 Preview 기능 코드 예시

### ✅ Text Block (자바 13 → 자바 15 정식)

```java
String text = """
        Hello,
        This is a multi-line
        text block.
        """;
```

### ✅ Switch Expression (자바 12 → 자바 14 정식)

```java
int day = 3;
String result = switch (day) {
    case 1, 2, 3 -> "초반";
    case 4, 5 -> "중반";
    default -> "후반";
};
```

### ✅ Pattern Matching for instanceof (자바 14 → 자바 16 정식)

```java
void example() {
    if (obj instanceof String s) {
        System.out.println(s.toLowerCase());
    }
}
```

### ✅ Record 클래스 (자바 14 → 자바 16 정식)

```java
public record Person(String name, int age) {
}
```

### ✅ Sealed 클래스 (자바 15 → 자바 17 정식)

```java
public sealed class Shape permits Circle, Square {
}

public final class Circle extends Shape {
}

public final class Square extends Shape {
}
```

---

## 자바 13~17의 핵심 기능 정리

| 버전    | 주요 기능                                                                               |
|-------|-------------------------------------------------------------------------------------|
| 자바 13 | Text Block (preview)                                                                |
| 자바 14 | Switch Expression (정식), Pattern Matching for instanceof (preview), Record (preview) |
| 자바 15 | Text Block (정식), Sealed Class (preview), Hidden Class                               |
| 자바 16 | Pattern Matching for instanceof (정식), Record (정식)                                   |
| 자바 17 | Sealed Class (정식), Pattern Matching for switch (preview), LTS 버전                    |

---

## 기능별 변천 과정 요약

| 기능명                           | 최초 도입 (Preview) | 정식 도입 |
|-------------------------------|-----------------|-------|
| Text Block                    | 자바 13           | 자바 15 |
| Switch Expression             | 자바 12           | 자바 14 |
| Pattern Matching (instanceof) | 자바 14           | 자바 16 |
| Record Class                  | 자바 14           | 자바 16 |
| Sealed Class                  | 자바 15           | 자바 17 |
