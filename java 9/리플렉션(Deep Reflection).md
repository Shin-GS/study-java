# Java Deep Reflection 정리

## Deep Reflection이란?

Deep Reflection은 일반 리플렉션보다 더 깊이 JVM 구조에 접근하는 기법을 의미한다.  
`private`, `final`, `static` 필드나 메서드를 조작하거나, JVM 내부 클래스나 메모리 구조에 접근하는 것도 포함된다.

### 포함되는 주요 기술

- `Field.setAccessible(true)` 로 private 접근
- `AccessibleObject.setAccessible()` 로 접근 우회
- `sun.misc.Unsafe` 클래스 사용
- `MethodHandles.Lookup` 으로 private 메서드 접근
- `Field.modifiers` 필드를 변경해서 `final` 제거

---

## 예시: final static 필드 값 변경

```java
public class Config {
    public static final String MODE = "prod";
}
```

```java
Field field = Config.class.getDeclaredField("MODE");
field.setAccessible(true);

Field modifiers = Field.class.getDeclaredField("modifiers");
modifiers.setAccessible(true);
modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

field.set(null, "dev");

System.out.println(Config.MODE); // JIT 인라인 최적화로 값이 그대로일 수 있음
```

---

## JPMS에서의 제한

Java 9 이상에서 JPMS를 사용하는 경우, Deep Reflection은 기본적으로 차단된다.  
`private`, `final` 필드 등에 접근 시 다음과 같은 예외 발생:

```
java.lang.reflect.InaccessibleObjectException: 
Unable to make field private final java.lang.String ... accessible
```

### 차단 대상 예시

- 다른 모듈의 `private` 필드
- `Field.modifiers` 필드
- `Unsafe` 접근
- `privateLookupIn()` 사용 시 허용되지 않은 대상 접근

---

## 우회 방법

### 1. JVM 옵션 사용

```bash
--add-opens java.base/java.lang.reflect=ALL-UNNAMED
--add-opens com.example/com.example.secret=ALL-UNNAMED
```

### 2. module-info.java에서 opens 선언

```java
module com.example {
    opens com.example.secret to some.framework;
}
```

### 3. MethodHandles.Lookup 사용

```java
MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Target.class, MethodHandles.lookup());
MethodHandle mh = lookup.findSetter(Target.class, "secret", String.class);
mh.invoke(targetInstance, "value");
```

---

## 보안적 의미

Deep Reflection은 강력하지만 위험하다.

- 캡슐화 원칙 무력화
- 보안 매커니즘 우회
- JVM 안정성 저해
- 코드 유지보수 어려움

운영 환경에서는 사용 지양하고, 테스트 코드나 프레임워크 내부 도구에 제한적으로 사용해야 한다.

---

## 요약

| 항목               | 내용 |
|--------------------|------|
| 정의               | 리플렉션으로 private/final/static 등 깊은 접근 |
| 주요 수단          | setAccessible, Unsafe, Lookup, modifiers 조작 |
| JPMS에서의 차단     | 기본적으로 차단됨 (`InaccessibleObjectException`) |
| 우회 방법          | --add-opens, opens, privateLookupIn |
| 보안 위험          | 캡슐화 해제, 내부 상태 위조 가능성 |
| 사용 권장 상황     | 테스트, 프레임워크 내부 등 제한된 환경 |

