## try-with-resources

---

### Java 7에서의 try-with-resources

* `try-with-resources` 구문은 명시적으로 닫아야 하는 자원(`AutoCloseable` 구현체)을 `try()` 괄호 안에 선언하면, try 블록이 종료된 후 자원이 자동으로 닫히도록 해주는
  기능이다.
* 이를 통해 `finally` 블록에서 자원을 수동으로 닫아야 했던 번거로움을 줄일 수 있다.

#### 예시 (Java 7 이전 방식)

```java
void example() {
    Resource resource = new Resource();
    try {
        // 작업
    } finally {
        resource.close();
    }
}
```

#### 예시 (Java 7의 try-with-resources)

```java
void example() {
    try (Resource resource = new Resource()) {
        // 작업
    }
}
```

---

### Java 9에서의 try-with-resources

#### 개선 사항

* Java 7에서는 `try()` 안에 자원을 **직접 선언**해야만 `try-with-resources` 구문을 사용할 수 있었음.
* 자바 9부터는 `try` 바깥에서 선언된 자원도, 해당 변수가 `final`이거나 **사실상 final**(값이 이후 변경되지 않는 경우)이면 `try-with-resources` 구문에 사용할 수 있도록
  확장되었다.

#### Resource 클래스 예시:

```java
public class Resource implements AutoCloseable {
    private String name;

    public Resource(String name) {
        this.name = name;
    }

    @Override
    public void close() {
        System.out.format("Close: %s\n", name);
    }
}
```

#### Java 8 이하에서는 아래 코드에서 컴파일 에러 발생:

```java
public class Main {
    public static void main(String[] args) {
        Resource r1 = new Resource("r1");
        try (
                r1; // 컴파일 에러 (Java 8 이하)
        ) {
        }
    }
}
```

#### Java 9부터는 다음 코드처럼 가능:

```java
public class Main {
    public static void main(String[] args) {
        Resource r1 = new Resource("r1");
        final Resource r2 = new Resource("r2");

        try (r1; r2) {
            // 작업
        }
    }
}
```

---

### 정리

* Java 7: `try-with-resources` 도입. 반드시 `try()` 안에서 자원 선언 필요
* Java 9: 외부에서 선언된 `final` 또는 사실상 `final` 자원도 `try-with-resources` 가능
