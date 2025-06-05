# 개선된 `toArray()` 메서드

- Java 11부터 `Collection` 인터페이스에 **배열 생성자 참조**를 받을 수 있는 `toArray()` 메서드가 추가됨
- 기존에는 배열을 **직접 생성해서** 넘겨야 했음

---

## 기존 방식 (Java 8 이하)

```java
List<String> strings = List.of("A", "B", "C");
String[] strArray = new String[3];
String[] result = strings.toArray(strArray);
```

- 결과: `result`와 `strArray`는 같은 배열을 참조

---

## Java 11 이후 방식

```java
List<String> strings = List.of("A", "B", "C");
String[] result = strings.toArray(String[]::new);
```

- 배열 생성자를 직접 넘길 수 있어서 **코드가 간결하고 안전**
- 크기를 맞추기 위해 배열을 미리 만들 필요가 없음

---

## 요약

| 메서드 형태                      | 설명                         |
|-----------------------------|----------------------------|
| `toArray(T[] a)`            | 직접 만든 배열을 전달해야 함           |
| `toArray(IntFunction<T[]>)` | 배열 생성자 참조 사용 가능 (Java 11+) |

> 타입 안정성과 간결한 문법을 동시에 얻을 수 있는 개선 사항

---
