## sealed class

- 자바 15에서 preview feature로 처음 등장
- 자바 17부터 정식 기능으로 추가됨.
- "sealed"는 '봉인된'이라는 뜻인데, sealed class는 특정 클래스만 상속을 허용할 수 있게 만들어주는 기능임.
- 즉, 상속 가능한 하위 클래스를 명시적으로 제한할 수 있음.

---

### 기존 방식

- 아래 코드는 문제없이 동작함
- 하지만, `Animal`을 상속할 수 있는 클래스가 `Dog`, `Cat`만 되도록 제한하고 싶을 수 있음.
- 예를 들어 라이브러리나 프레임워크를 설계할 때 외부에서 임의로 상속 못하게 하고 싶은 경우에 유용함.

```java
public abstract class Animal {
}

class Dog extends Animal {
    public String bark() {
        return "강아지는 멍멍";
    }
}

class Cat extends Animal {
    public String purr() {
        return "고양이는 골골";
    }
}
```

---

### sealed class 적용 방법

sealed class를 사용하려면 두 가지 변화가 필요함:

#### 1. 부모 클래스의 변경

- `sealed` 키워드 사용
- `permits` 키워드로 하위 클래스를 명시적으로 나열

#### 2. 자식 클래스의 변경

- 자식 클래스도 반드시 `final`, `sealed`, `non-sealed` 중 하나의 키워드를 가져야 함

- `final` : 더 이상 상속 불가능
- `sealed` : 자식 클래스도 sealed 계층 구조를 유지 (손자까지 봉인 가능)
- `non-sealed` : 더 이상 봉인되지 않음 (봉인을 해제함)

```java
public sealed class Animal permits Dog, Cat {
}

final class Dog extends Animal {
    public String bark() {
        return "강아지는 멍멍";
    }
}

final class Cat extends Animal {
    public String purr() {
        return "고양이는 골골";
    }
}
```

---

### 위치 제한

sealed class를 사용할 때 자식 클래스의 위치에도 제약이 있음:

- `module-info.java`를 사용하는 *named module*에서는 자식 클래스가 같은 모듈 안에 있어야 함
- `module-info.java`를 사용하지 않는 *unnamed module*에서는 자식 클래스가 같은 패키지 안에 있어야 함
- 부모 클래스와 자식 클래스가 같은 파일 안에 있을 경우, `permits` 생략 가능 (자동 인식)

이런 제약은 하위 클래스를 명확하게 제한하기 위한 설계적 결정임.

---

### sealed class의 장점

1. **상위 클래스 설계 시 호환성 걱정을 덜 수 있음**  
   → 외부에서 상속을 못 하므로 구조를 안전하게 설계할 수 있음

2. **컴파일 타임에 가능한 하위 클래스가 명확함**  
   → `switch` 문에서 마치 `enum`처럼 sealed class를 사용할 수 있음 (패턴 매칭)

```java
void example() {
    // Java 21부터는 공식 지원
    switch (animal) {
        case Dog d -> System.out.println(d.bark());
        case Cat c -> System.out.println(c.purr());
    }
}
```

⚠️ 참고: switch 패턴 매칭은 자바 17에서는 preview feature였고, 자바 21부터 정식 기능으로 추가됨

---

### sealed interface

- sealed class와 동일한 개념을 인터페이스에도 적용할 수 있음
- `record`는 `interface`를 구현할 수 있기 때문에 `sealed interface + record` 조합이 가능함
- 이 조합은 **DTO 계층 구조 정의**에 유용함

예시 상황:  
`GET /animal` API에서 고양이 정보와 강아지 정보를 하나의 응답 타입으로 반환하고 싶을 때, sealed interface를 사용해서 공통 부모 타입 정의 가능

---

### 마무리

- sealed class는 자바 17에서 정식 도입된 개념
- 추상 클래스나 인터페이스의 하위 구현을 제어하고 싶을 때 강력한 수단이 됨.
- 복잡한 계층 구조보다도 “정해진 클래스만 사용하게 제한하고 싶다”는 고민이 있다면 sealed class를 고려해보면 됨.
