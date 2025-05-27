# Java Deep Reflection 정리

## Deep Reflection이란?

Deep Reflection은 일반 리플렉션보다 더 깊이 JVM 구조에 접근하는 기법을 의미한다.  
`private`, `final`, `static` 필드나 메서드를 조작하거나, JVM 내부 클래스나 메모리 구조에 접근하는 것도 포함된다.

### 주요 기술

- `Field.setAccessible(true)` 로 private 멤버 접근
- `AccessibleObject.setAccessible()` 로 접근 우회
- `sun.misc.Unsafe` 클래스 사용
- `MethodHandles.Lookup` 으로 private 메서드 접근
- `Field.modifiers` 필드를 변경해서 `final` 제거

---

## JPMS에서의 제한

Java 9 이상에서 JPMS를 사용하는 경우, Deep Reflection은 기본적으로 차단된다.  
`private`, `final` 필드 등에 접근 시 다음과 같은 예외 발생:

```
java.lang.reflect.InaccessibleObjectException: 
Unable to make field private final java.lang.String ... accessible
```

### 차단 예시

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

- open : 특정 모듈에 존재하는 모든 코드에 대해 deep reflection 사용 허가

```java
open module com.domain {
    exports org.domain;
}
```

- opens : 특정 패키지에만 deep reflection 사용 허가

```java
module com.domain {
    exports org.domain;
    opens org.domain; // open 대신 opens를 사용
}
```

- opens ... to : 특정 패키지를 특정 모듈에 대해서만 deep reflection 사용 허가

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

| 항목         | 내용                                          |
|------------|---------------------------------------------|
| 정의         | 리플렉션으로 private/final/static 등 깊은 접근         |
| 주요 수단      | setAccessible, Unsafe, Lookup, modifiers 조작 |
| JPMS에서의 차단 | 기본적으로 차단됨 (`InaccessibleObjectException`)   |
| 우회 방법      | --add-opens, opens, privateLookupIn         |
| 보안 위험      | 캡슐화 해제, 내부 상태 위조 가능성                        |
| 사용 권장 상황   | 테스트, 프레임워크 내부 등 제한된 환경                      |

---
## Deep Reflection VS Shallow Reflection

- Deep Reflection은 보안 제약을 우회하여 내부 구조까지 건드리는 방식이며, Java 9부터는 모듈 경계로 인해 추가 설정 없이 불가능
- Shallow Reflection은 접근 가능한 멤버(public 등)에 한정되며 Java 9 이후에도 큰 제한 없이 사용 가능

| 구분              | Deep Reflection                                                                     | Shallow Reflection                                  |
|-----------------|-------------------------------------------------------------------------------------|-----------------------------------------------------|
| 이름              | 깊은 리플렉션                                                                             | 얕은 리플렉션                                             |
| 의미              | `setAccessible(true)` 등을 통해 **private** 또는 **package-private** 멤버에 접근하는 리플렉션        | 접근 제어자(public/protected 등)를 **준수하며** 사용하는 일반적인 리플렉션 |
| Java 9 이후(JPMS) | 기본적으로 `setAccessible(true)`만으로는 **모듈 간** 접근 불가<br/>→ `--add-opens` 또는 `opens` 설정 필요 | 공개된(public) API에 대해 별다른 설정 없이 접근 가능                 |

- ex)Java 9 이후 JPMS 적용된 코드에서 Deep Reflection 적용(public, private 멤버 접근)

```java
// module-info.java
open module com.domain {
    exports org.domain;
}

/* open 키워드 제거시 private 수정 불가
module com.domain {
    exports org.domain;
}
*/
```

```java
// [domain 모듈] org.domain 패키지 안의 DomainPerson 클래스
public class DomainPerson {
    public String name;
    private int weight;

    @Override
    public String toString() {
        return "DomainPerson{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
```

```java
// [api 모듈] org.api 패키지 안의 Main 클래스
public static void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("org.domain.DomainPerson");
    DomainPerson person = new DomainPerson();

    Field name = clazz.getDeclaredField("name"); // public 멤버는 별도 설정 필요없음
    name.set(person, "angel");

    Field weight = clazz.getDeclaredField("weight");
    weight.setAccessible(true); // weight는 private 변수이므로 setAccessible을 호출해 주어야 한다.
    weight.set(person, 100);

    System.out.println(person); //DomainPerson{name='angel', weight=100}
}
```
