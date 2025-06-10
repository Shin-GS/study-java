# instanceof Pattern Matching

## 기본 개념

- 자바 14에서 preview로 도입되고, 자바 16에서 정식 기능으로 채택된 기능임
- 기존 `instanceof`는 타입 체크만 가능했지만, 이제는 **형변환 + 변수 선언**까지 한 줄로 가능함

---

## 기존 방식

```java
public String sound(Animal animal) {
    if (animal instanceof Dog) {
        Dog dog = (Dog) animal;
        return dog.bark();
    } else if (animal instanceof Cat) {
        Cat cat = (Cat) animal;
        return cat.purr();
    }

    throw new IllegalArgumentException("다른 경우의 수는 존재하지 않음!");
}
```

- 타입 체크하고 → 형변환하고 → 변수에 할당하는 패턴이 반복됨

---

## 개선된 방식 (pattern matching 적용)

```java
public String sound(Animal animal) {
    if (animal instanceof Dog dog) {
        return dog.bark();
    } else if (animal instanceof Cat cat) {
        return cat.purr();
    }
    throw new IllegalArgumentException("다른 경우의 수는 존재하지 않음!");
}
```

- `instanceof Type var` 형식으로 선언함
- 타입 확인과 동시에 변수 선언 + 형변환이 이뤄짐

---

## 변수의 유효 범위 (스코프)

```java
public String soundIfDog(Animal animal) {
    if (!(animal instanceof Dog dog)) {
        return "강아지가 아님!";
    }

    return dog.bark(); // dog 사용 가능함
}
```

- `return`으로 흐름이 끊기면, 이후 코드에서 해당 변수 사용 가능함
- 즉, **해당 지점에 도달했을 때 타입이 확실**해야 변수 접근 가능함

```java
public String soundIfDog(Animal animal) {
    if (!(animal instanceof Dog dog)) {
        System.out.println("강아지가 아님!"); // return 하지 않음
    }

    return dog.bark(); // ❌ 컴파일 에러 - dog 유효범위 아님
}
```

---

## 논리 연산자 사용 시 유의점

### 컴파일 에러 발생

- `||`는 앞이 false면 뒤를 평가 → dog 변수는 존재하지 않을 수 있음

```java
void example() {
    if ((animal instanceof Dog dog) || dog.bark() != null) {
        //...
    }
}
```

### 컴파일 가능

- `&&`는 앞이 true일 때만 뒤 평가 → dog는 확정된 타입이기 때문에 사용 가능함

```java
void example() {
    if ((animal instanceof Dog dog) && dog.bark() != null) {
        //...
    }
}
```

---

## 결론

- `instanceof pattern matching`은 다음 두 단계를 줄여주는 기능임:
    1. 타입 확인
    2. 형변환 및 변수 선언
- 조건이 true일 때, 변수를 즉시 사용할 수 있게 만들어줌
- 이런 구조를 **패턴 매칭(pattern matching)** 이라고 부름

---

## 요약

| 항목    | 설명                              |
|-------|---------------------------------|
| 도입 버전 | Java 14 (preview), Java 16 (정식) |
| 주요 문법 | `instanceof Type var`           |
| 장점    | 타입 확인 + 형변환 + 변수 선언을 한 줄로       |
| 유효 범위 | 타입이 확정된 경로에서만 사용 가능             |
| 주의사항  | &#124;&#124; 보다 `&&`를 우선 고려할 것  |
