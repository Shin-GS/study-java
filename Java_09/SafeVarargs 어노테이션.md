## @SafeVarargs

---

### 개요

* `@SafeVarargs`는 "이 메소드에서 사용하는 **제네릭 + 가변인자(varargs)** 조합은 타입 안정성이 보장된다"는 것을 컴파일러에게 알려주는 어노테이션이다.
* 이를 통해 컴파일 시 발생하는 `"uses unchecked or unsafe operations."` 경고 메시지를 제거할 수 있다.

### 예제 코드

```java
@SafeVarargs
public static <T> void safeMethod(T... elements) {
    for (T element : elements) {
        System.out.println(element);
    }
}
```

### 경고 메시지 예시

* `@SafeVarargs`를 사용하지 않고 제네릭 가변인자를 사용하면 다음과 같은 경고가 발생할 수 있다:

```
warning: uses unchecked or unsafe operations.
```

---

### Java 9에서의 변경사항

#### Java 9 이전

* `@SafeVarargs`는 `static` 또는 `final` 메소드에서만 사용 가능했고,
* **`private` 메소드에는 적용할 수 없었다.** (컴파일 오류 발생)

#### Java 9 이후

* **`private` 메소드에도 @SafeVarargs 사용이 가능**해졌다.
* 이는 대부분의 경우 `private` 메소드도 외부로 노출되지 않으며 호출 컨텍스트가 제한되기 때문에 안전하다고 판단했기 때문이다.

### 예제 (Java 9 이후 가능)

```java
class Example {

    @SafeVarargs
    private final <T> void printAll(T... items) {
        for (T item : items) {
            System.out.println(item);
        }
    }

    public void run() {
        printAll("a", "b", "c");
    }
}
```

---

### 정리

* `@SafeVarargs`는 제네릭 가변인자 메소드에서 타입 안정성을 명시적으로 선언하고 경고를 제거할 때 사용
* Java 9부터는 `private` 메소드에도 사용할 수 있어 활용 범위가 확장되었음
