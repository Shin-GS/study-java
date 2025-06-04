# Var

---

## var란?

- Java 10에서 도입된 지역 변수 타입 추론 기능.
- 지역 변수 선언 시 명시적인 타입 대신 `var`를 사용하면, 컴파일러가 초기값을 기반으로 타입을 추론함.

```java
int num = 3;        // 기존 방식
var num = 3;        // 타입 추론 (int)
```

---

## 타입 추론의 동작

- `var`로 선언한 변수는 타입이 고정된다.
- 다른 타입으로 값을 대입하면 컴파일 에러 발생.

```java
void example() {
    var num = 3;
    num = 10;       // OK
    num = "ABC";    // 오류: String cannot be converted to int
}
```

---

## 불변 변수로 만들기

- `final` 키워드와 함께 사용 가능.

```java
void example() {
    final var num = 3;
    num = 10;   // 오류: Cannot assign a value to final variable 'num'
}
```

---

## 제네릭 타입과의 활용

```java
// 기존 방식
Map<String, String> map1 = Map.of("A", "B");

// var 사용
var map2 = Map.of("A", "B");
```

---

## 다른 언어와의 비교

- **Kotlin**
    - `var`: 가변 변수
    - `val`: 불변 변수

```kotlin
var num1 = 3
val num2 = 10
```

- **Swift**
    - `var`: 가변 변수
    - `let`: 불변 변수

```swift
var num1 = 3
let num2 = 10
```

- **Java**는 `var`만 도입했으며 `val` 또는 `let`과 같은 키워드는 없음.

---

## 왜 `val`은 도입하지 않았을까?

1. 타입 추론이 지역 변수에만 적용되고, 지역 변수는 가변 여부에 민감하지 않음.
2. `var-val`, `var-let` 형태는 의견이 분분했으나 `var`는 수용 가능하다는 의견이 많았음.

---

## var의 특징과 제한사항

### var는 키워드가 아님 : 변수명으로 사용 가능

```java
var var = 3;  // 유효한 변수 선언
```

---

### 자바 11 이전까지는 람다식에서 사용 불가

```java
// Java 11부터 가능
Consumer<String> c = (var x) -> System.out.println(x);
```

---

### 사용할 수 없는 경우

```java
var num;          // 초기화 없이는 사용 불가
var a = null;     // null만으로는 타입 추론 불가

// 람다식은 명시적 타입이 필요
var b = (String s) -> System.out.println(s);  // 오류

// 배열 초기화 불가
var c = {"A", "B", "C"};  // 오류
```

---

### 다이아몬드 연산자와 함께 사용할 경우

```java
var nums = new ArrayList<>();  // 타입은 ArrayList<Object>로 추론됨
```

---

### 익명 클래스와 함께 사용할 경우

```java
void example() {
    Object obj1 = new Object() {};
    obj1 = new Object(); // OK

    var obj2 = new Object() {};
    obj2 = new Object(); // 오류: Object cannot be converted to <anonymous Object>
}
```

---
