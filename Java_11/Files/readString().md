# readString()이란?

- Java 11부터 `Files.readString(Path)` 메서드가 추가됨
- 파일의 내용을 한 번에 문자열로 읽을 수 있음
- 기본 인코딩은 UTF-8이며, 인코딩을 지정하는 오버로드도 존재

---

## 기본 사용 예

```java
// test.txt 내용:
// Apple
// Banana

void example() {
    var path = Paths.get(Paths.get(".").toAbsolutePath() + "/test.txt");
    String str = Files.readString(path);
    System.out.println(str);
}
```

- 파일의 내용을 UTF-8로 읽어 문자열로 반환함

---

## 다른 문자셋으로 읽기

```java
String str = Files.readString(path, StandardCharsets.ISO_8859_1);
```

- 두 번째 인자로 `Charset`을 넘기면 해당 인코딩으로 읽을 수 있음

---

## 요약

| 메서드                         | 설명                  |
|-----------------------------|---------------------|
| `readString(Path)`          | UTF-8로 문자열 전체 읽기    |
| `readString(Path, Charset)` | 지정한 인코딩으로 문자열 전체 읽기 |

> 파일에서 간단하게 전체 내용을 읽고 싶을 때 유용한 메서드

---
