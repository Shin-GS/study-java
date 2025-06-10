## Random API의 변화

- 자바 17에서는 기존 `Random` 클래스 기반의 난수 생성 방식에 구조적인 변화가 생김.
- 기존 방식은 여전히 사용 가능하지만 보다 유연하고 성능 향상된 방식이 도입되었음.

---

### 기존 방식 (Java 17 이전까지 사용)

```java
Random random = new Random();
int value = random.nextInt(10); // 0 ~ 9 사이의 정수 생성
```

- 일반적인 난수 생성용으로는 `Random`
- 멀티스레드 환경에서는 `ThreadLocalRandom`
- 보안이 필요한 경우에는 `SecureRandom` 사용

---

### 문제점

- `Random`이 **클래스**이기 때문에 구현을 갈아끼우기 어려움
- 난수 생성기가 `Random` 클래스에 **종속적**임

---

### 새로운 방식 (Java 17 이후부터 사용)

- 자바 17의 개선: `RandomGenerator` 인터페이스 도입
- `RandomGenerator` 인터페이스를 통해 다양한 구현체를 추상화
- 주요 구현체들은 각각의 알고리즘 기반으로 동작하며 **더 나은 성능** 제공
- 단, 대부분의 구현체는 **thread-safe 하지 않음** → 멀티스레드 환경에서 주의 필요

---

### 다양한 구현체

자바 17 이상에서는 다음과 같은 다양한 난수 생성기 구현체가 존재:

- `L32X64MixRandom` (자바 21 기준 기본 구현체)
- `L128X1024MixRandom`
- `Xoshiro256PlusPlus`
- `SplittableRandom`
- 등등

---

### RandomGeneratorFactory 사용 예시

```java
import java.util.random.RandomGeneratorFactory;

voiid example() {
    RandomGeneratorFactory.all()
            .forEach(f -> System.out.println(f.name()));
}
```

- `all()` → 사용 가능한 구현체 목록 출력
- `getDefault()` → 현재 JVM의 기본 구현체 가져오기

---

### 요약

| 항목   | 내용                                          |
|------|---------------------------------------------|
| 문제점  | 기존 `Random`은 클래스라서 교체 유연성이 떨어짐              |
| 개선점  | `RandomGenerator` 인터페이스로 구조 개선              |
| 특징   | 구현체 다양화 + 성능 개선                             |
| 유틸리티 | `RandomGeneratorFactory`를 통해 목록 확인 및 기본값 제공 |

자바 17 이후로는 일반적인 난수 생성에서도 `RandomGenerator` 기반 API 사용을 고려해볼 수 있음.
