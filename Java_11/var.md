# 개선된 `var` 사용

- Java 11부터 람다식의 매개변수에도 `var` 키워드 사용 가능
- 기존에는 람다 파라미터에 `var`를 사용할 수 없었음

---

## 예제 비교

```java
// 명시적인 타입 사용
Consumer<String> c1 = (String x) -> System.out.println(x);

// Java 11부터 가능해진 var 사용
Consumer<String> c2 = (var x) -> System.out.println(x);
```

- `Consumer<String>` 덕분에 `x`의 타입은 이미 추론 가능하지만
- `var` 사용으로 인해 **어노테이션 적용** 등의 이유로 타입 명시가 가능해짐

---

## var의 유용한 사용 예

```java
// 어노테이션 적용이 필요한 경우 (예: @Nullable)
Consumer<String> c = (@Nullable var x) -> System.out.println(x);
```

- 타입을 생략하면 어노테이션을 붙일 수 없음
- `var`는 타입 추론을 유지하면서도 어노테이션 사용을 가능하게 해줌

---

## 요약

| 항목             | Java 10 이하     | Java 11 이후       |
|----------------|----------------|------------------|
| 람다 파라미터에 `var` | ❌ 불가능          | ✅ 가능             |
| 타입 추론          | 가능하지만 어노테이션 제한 | 가능 + 어노테이션 사용 가능 |
| 주요 장점          | -              | 어노테이션 활용 등       |

---
