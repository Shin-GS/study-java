## Sequenced Collection

- 자바 21에서 **순서가 있는 컬렉션(Sequenced Collection)** 개념이 도입됨.
- 기존의 `Collection`, `List`, `Set`, `Map` 인터페이스 계층이 일부 확장되며 순서를 다루는 메서드가 추가됨

```java
interface SequencedCollection<E> extends Collection<E> {
    SequencedCollection<E> reversed();

    void addFirst(E);

    void addLast(E);

    E getFirst();

    E getLast();

    E removeFirst();

    E removeLast();
}
```

---

### reversed()는 복사본이 아닌 원본 컬렉션에 연결된 View

- View에 변경을 가하면 원본도 같이 변경됨

```java
void example() {
    List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5));
    List<Integer> reversed = nums.reversed();

    nums.addLast(6);
    System.out.println(reversed); // [6, 5, 4, 3, 2, 1]

    reversed.addFirst(7);
    System.out.println(nums); // [1, 2, 3, 4, 5, 6, 7]
}
```

---

### 집합(Set)에서는?

- `LinkedHashSet`: addFirst / addLast는 순서만 재조정
- `SortedSet`: addFirst / addLast는 **UnsupportedOperationException 발생**

```java
void example() {
    SequencedSet<Integer> nums = new LinkedHashSet<>(List.of(1, 2, 3, 4, 5));
    nums.addFirst(3); // 이미 있던 값 → 순서 조정됨
    System.out.println(nums); // [3, 1, 2, 4, 5]
}
```

---

### SequencedMap 인터페이스

- `firstEntry()`, `lastEntry()`로 가져온 Entry는 불변 (read-only view)
- `entrySet().iterator().next()`로 가져온 Entry는 수정 가능

```java
interface SequencedMap<K, V> extends Map<K, V> {
    SequencedMap<K, V> reversed();

    SequencedSet<K> sequencedKeySet();

    SequencedCollection<V> sequencedValues();

    SequencedSet<Entry<K, V>> sequencedEntrySet();

    V putFirst(K key, V value);

    V putLast(K key, V value);

    Entry<K, V> firstEntry();

    Entry<K, V> lastEntry();

    Entry<K, V> pollFirstEntry();

    Entry<K, V> pollLastEntry();
}
```

```java
void example() {
    SequencedMap<Integer, String> map = new LinkedHashMap<>();
    map.put(1, "A");
    map.put(2, "B");

    var entry = map.firstEntry();
    entry.setValue("D"); // UnsupportedOperationException 발생!
}
```

---

### 요약

- Sequenced API는 자바 컬렉션의 표현력과 순서 제어 능력을 대폭 향상시키는 기능으로, 구조적으로도 큰 변화임.

| 항목       | 설명                                                         |
|----------|------------------------------------------------------------|
| 도입 버전    | Java 21                                                    |
| 주요 인터페이스 | `SequencedCollection`, `SequencedSet`, `SequencedMap`      |
| 핵심 기능    | `addFirst`, `addLast`, `reversed`, `getFirst`, `getLast` 등 |
| 주의점      | `SortedSet`, `SortedMap`은 일부 기능 미지원                        |
| 활용       | 양방향 큐, 순서 기반 맵/셋, 뷰 기반 처리                                  |
