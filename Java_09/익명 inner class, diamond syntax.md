## 익명 클래스와 다이아몬드(Diamond) 연산자

---

### 개요

* Java 7에서 도입된 **다이아몬드(Diamond) 연산자 (`<>`)** 는, 제네릭 클래스의 인스턴스를 생성할 때 우항의 타입 파라미터를 생략할 수 있게 해주는 문법이다.
* 이로 인해 코드가 간결해지고, 복잡한 제네릭 타입을 반복 작성하지 않아도 된다.

#### 예시 (Java 7 기준)

```java
// 타입을 생략하지 않음
List<Integer> numbers = new ArrayList<Integer>();

// 다이아몬드 연산자를 사용하여 타입 생략
List<Integer> numbers = new ArrayList<>();
```

---

### 내부 클래스 (Inner Class) 개념

* 클래스 내부에 정의된 클래스를 내부 클래스라고 한다.
* 내부 클래스는 크게 두 가지로 나뉜다:

```java
public class Main {
    // 외부 클래스(Main)를 참조할 수 있는 내부 클래스
    public class ReferenceClass {
    }

    // 외부 클래스(Main)를 참조하지 않는 정적(static) 내부 클래스
    public static class NoReferenceClass {
    }
}
```

* **외부 참조가 있는 내부 클래스**는 메모리 관리 및 직렬화/역직렬화가 복잡해질 수 있기 때문에, 가능한 한 정적 내부 클래스를 사용하는 것이 권장된다.

---

### 자바 9의 변경사항: 익명 클래스 + 다이아몬드 연산자 지원

* 자바 9 이전에는 **익명 클래스 생성 시** 다이아몬드 연산자 `< >` 를 사용할 수 없었기 때문에, 타입을 명시적으로 작성해야 했다.

```java
// Java 9 이전
InnerClass<Integer> ic = new InnerClass<Integer>(3) {
        };
```

* Java 9부터는 익명 클래스에서도 다이아몬드 연산자를 사용할 수 있게 되었으며, 타입 생략이 가능해졌다.

```java
// Java 9 이후
InnerClass<Integer> ic = new InnerClass<>(3) {
        };
```

#### 예제 전체 코드

```java
public class Main {
    public static void main(String[] args) {
        InnerClass<Integer> ic = new InnerClass<>(3) {
        };
    }

    public static class InnerClass<T> {
        private final T t;

        public InnerClass(T t) {
            this.t = t;
        }
    }
}
```

---

### 정리

* Java 9 이전: 익명 클래스 생성 시 다이아몬드 연산자 사용 불가 → 타입 명시 필요
* Java 9 이후: 익명 클래스에서도 다이아몬드 연산자 사용 가능 → 코드 간결성 향상
