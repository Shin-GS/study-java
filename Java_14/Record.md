# Record Class

## 등장 배경

- 자바 14에서 preview로 도입되고, 자바 16에서 정식 기능이 됨
- 목적은 "데이터 전달 전용 클래스"를 간결하게 만들기 위함임
- 기존 DTO 클래스는 너무 장황하고 반복 코드가 많았음

---

## 기존 DTO 방식

```java
public final class FruitDto {
    private final String name;
    private final int price;
    private final LocalDate date;

    public FruitDto(String name, int price, LocalDate date) {
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public String name() {
        return name;
    }

    public int price() {
        return price;
    }

    public LocalDate date() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        // ...
    }

    @Override
    public int hashCode() {
        // ...
    }

    @Override
    public String toString() {
        // ...
    }
}
```

- 필드 선언, 생성자, getter, equals, hashCode, toString 등 반복 코드가 많았음

---

## record class 사용

```java
public record FruitDtoV2(
        String name,
        int price,
        LocalDate date
) {
}
```

- 이 한 줄로 기존 FruitDto와 동일한 기능 제공함
- 컴포넌트(Component)라고 부르는 필드들을 선언만 하면 됨

### 자동 생성되는 항목들

1. 모든 필드는 `private final`로 생성됨
2. 모든 필드를 인자로 받는 생성자 자동 생성됨
3. getter 메소드 생성 (이름은 필드명 그대로)
4. `equals`, `hashCode`, `toString` 자동 생성됨
5. 클래스는 암묵적으로 `final`이며, 상속 불가함

---

## 확장 기능

```java
public record FruitDtoV2(String name, int price, LocalDate date) {
    private static final double DISCOUNT_RATE = 0.3;

    public int getDiscountPrice() {
        return (int) (price * (1.0 - DISCOUNT_RATE));
    }
}
```

- `static 필드`, `static 메소드`, `인스턴스 메소드` 정의 가능함
- 일반 클래스처럼 내부 로직 추가도 가능함
- 단, `인스턴스 필드`는 정의 불가능함 → 컴파일 에러남

---

## compact constructor

```java
public record FruitDtoV2(String name, int price, LocalDate date) {
    public FruitDtoV2 {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 양수여야 함");
        }
    }
}
```

- 위 방식은 매개변수를 생략한 생성자 재정의인 **compact constructor**임
- 검증 로직만 작성하면 자바가 자동으로 `this.name = name` 과 같은 할당 코드를 만들어줌
- 따라서 필드 초기화 코드는 작성하지 않아도 됨

---

## compact constructor에서 값 재할당 관련 주의점

record의 compact constructor는 **검증은 가능하지만, 필드 직접 재할당은 불가능**함.  
하지만 생성자의 **매개변수(지역 변수)** 값을 바꾸는 건 가능함 → 필드에 반영됨.

### ✅ 가능한 경우 (매개변수 재할당)

```java
public record Fruit(String name, int price) {
    public Fruit {
        if (price < 0) {
            price = 0; // ✅ 가능: 지역 변수 값을 바꾸는 것
        }
    }
}
```

- 이 경우 최종적으로 자바가 `this.price = price;` 코드를 생성하므로 변경된 값이 반영됨

### ❌ 불가능한 경우 (필드 직접 재할당 시도)

```java
public record Fruit(String name, int price) {
    public Fruit {
        this.price = 0; // ❌ 컴파일 에러: final 필드에 직접 재할당 불가
    }
}
```

- `this.price`는 자바가 자동 생성한 `final` 필드임
- compact constructor 안에서 직접 필드에 값을 대입할 수 없음

---

## 일반 생성자를 쓰면 값 변경 가능

```java
public record Fruit(String name, int price) {
    public Fruit(String name, int price) {
        this.name = name;
        this.price = price < 0 ? 0 : price; // ✅ 직접 초기화 가능
    }
}
```

---

## 요약

| 생성자 유형              | 값 수정 가능   | 설명                                |
|---------------------|-----------|-----------------------------------|
| compact constructor | ⚠️ 조건부 가능 | 매개변수(지역 변수) 수정은 가능, 필드 직접 재할당은 불가 |
| 일반 생성자              | ✅ 가능      | 직접 `this.field = ...` 할당 가능       |

---

## 어노테이션 사용

```java
public record FruitDtoV2(
        String name,
        @MyAnnotation int price,
        LocalDate date
) {
}
```

- 어노테이션은 **필드**, **생성자 파라미터**, **접근자 메소드** 모두에 붙음
- 특정 위치에만 붙이고 싶으면 `@Target`을 지정해야 함

```java

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MyAnnotation {
}
```

---

## Spring + record

```java
public record FruitCreateRequest(String name, int price, LocalDate date) {
}

@PostMapping("/xxx")
public void saveFruit(@RequestBody FruitCreateRequest request) {
    System.out.printf("이름: %s, 가격: %s, 날짜: %s%n", request.name(), request.price(), request.date());
}
```

- Spring Boot 2.5+ (jackson 2.12+) 부터 `@RequestBody`에 record 적용 가능함
- 쿼리 파라미터, 폼 데이터에도 활용 가능함

---

## 📌 요약

| 항목      | 설명                                  |
|---------|-------------------------------------|
| 도입 버전   | Java 14 (preview), Java 16 (정식)     |
| 주요 기능   | DTO 자동화 (필드, 생성자, getter, equals 등) |
| 상속 여부   | 클래스 상속 불가, 인터페이스 구현 가능              |
| 제약 사항   | 인스턴스 필드 직접 선언 불가                    |
| 확장성     | 메소드, static 필드/메소드 추가 가능            |
| 실무 활용 예 | Spring Request DTO, 응답 객체 등에 활용 유용함 |
