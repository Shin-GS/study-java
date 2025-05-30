## 인터페이스에서의 private 메소드 (Java 9)

---

### Java 8: 인터페이스에 `default` 메소드 도입

* Java 8부터 인터페이스에 `default` 메소드를 정의할 수 있게 되면서, **일부 구현을 인터페이스 내에서 제공**할 수 있게 되었음
* 이로 인해 추상 클래스와 인터페이스의 차이는 더욱 좁혀졌으며, 주된 차이는 **상태(필드)를 가질 수 있는지 여부**로 구분됨

```java
public interface List<E> {
    int size();

    void add(E element);

    E get(int index);

    default boolean contains(E element) {
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(element)) {
                return true;
            }
        }
        return false;
    }
}
```

---

### Java 9: 인터페이스에서 `private` 메소드 사용 가능

* Java 9부터 인터페이스 내부에서 `private` 메소드를 정의할 수 있게 되었음
* 목적: **default 메소드나 static 메소드 간에 중복되는 로직을 재사용**할 수 있도록 하기 위함
* `private` 메소드는 인터페이스 내부에서만 접근 가능하며, 외부에서는 접근할 수 없음

```java
public interface MyLogger {

    default void logInfo(String msg) {
        log("INFO", msg);
    }

    default void logError(String msg) {
        log("ERROR", msg);
    }

    private void log(String level, String msg) {
        System.out.println("[" + level + "] " + msg);
    }
}

public class Main implements MyLogger {
    public static void main(String[] args) {
        Main app = new Main();
        app.logInfo("시작합니다.");
        app.logError("에러 발생!");
    }
}
```

---

### 정리

* **Java 8**: `default` 메소드 도입으로 인터페이스에서 구현 가능
* **Java 9**: `private` 메소드 도입으로 `default`, `static` 메소드 간 **공통 로직 분리** 가능
* 인터페이스 내부의 코드 재사용성과 구조화가 더욱 향상됨
