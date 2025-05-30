## Java 9: Collection 기능 추가

---

### 개요

* Java 9부터 `List`, `Set`, `Map` 에 **불변 컬렉션(immutable collection)** 을 생성할 수 있는 `of()` 정적 팩토리 메소드가 추가되었다.
* 기존에는 `Arrays.asList()`, `Collections.unmodifiableXXX()` 등을 사용해야 했지만, Java 9의 `of()` 메소드는 보다 간결하고 메모리 효율적인 방식으로 불변 컬렉션을
  생성한다.

---

### 사용 예시

#### List 생성

```java
// Java 8 이전
List<Integer> oldList = Arrays.asList(1, 2);

// Java 9부터
List<Integer> newList = List.of(1, 2);
```

#### Set 생성

```java
// Java 8 이전
Set<Integer> oldSet = new HashSet<>(Arrays.asList(1, 2));

// Java 9부터
Set<Integer> newSet = Set.of(1, 2);
```

#### Map 생성

```java
void example() {
    // Java 8 이전
    Map<String, Integer> oldMap = new HashMap<>();
    oldMap.put("A", 1);
    oldMap.put("B", 2);

    // Java 9부터
    Map<String, Integer> newMap = Map.of("A", 1, "B", 2);

    // 다수의 key-value 쌍이 있는 경우
    Map<String, Integer> map = Map.ofEntries(
            Map.entry("A", 1),
            Map.entry("B", 2)
    );
}
```

---

### 불변 컬렉션

* `List.of()`, `Set.of()`, `Map.of()` 로 생성된 컬렉션은 **불변**이다.
* 요소를 추가하거나 삭제하려고 하면 `UnsupportedOperationException` 예외가 발생한다.

```java
void example() {
    List<Integer> nums = List.of(1, 2);
    nums.add(3);        // 예외 발생
    nums.remove(0);     // 예외 발생
}
```

---

### 기존의 `Collections.unmodifiableXXX` 와의 차이점

| 기준       | `Collections.unmodifiableList()` | `List.of()`                  |
|----------|----------------------------------|------------------------------|
| 불변성      | 제공됨                              | 제공됨                          |
| 구현체      | `UnmodifiableList`               | `ImmutableCollections.ListN` |
| 메모리 효율성  | 낮음 (내부에 별도 컬렉션 보관)               | 높음 (필드를 직접 보관)               |
| 예외 발생 시점 | 런타임                              | 런타임                          |

* `List.of()` 등은 **최소화된 구현체**(`ImmutableCollections.ListN`, `Set12` 등)를 사용함
* 예: `Set.of(1, 2)` → `ImmutableCollections.Set12` 사용, 두 값을 **필드에 직접 저장**하여 메모리 절약

---

### 정리

* Java 9에서는 `List.of()`, `Set.of()`, `Map.of()` 등의 정적 팩토리 메소드가 추가됨
* 생성된 컬렉션은 **불변 컬렉션**이며, 수정 시 예외 발생
* 기존의 `Collections.unmodifiableXXX()`와는 구현체 및 메모리 구조가 다르며, 더 효율적임
