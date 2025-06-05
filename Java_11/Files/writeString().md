# writeString()이란?

- Java 11부터 `Files.writeString()` 메서드가 추가됨
- 문자열을 간편하게 파일에 기록할 수 있음

---

## 기본 사용 예

```java
void example() {
    Path path = Paths.get("output.txt");
    Files.writeString(path, "Hello, Java 11!");
}
```

- 파일이 없으면 생성하고, 있으면 기존 내용을 덮어씀

---

## 기본 동작 옵션

- 명시하지 않으면 다음 옵션이 기본 적용됨:
    - `StandardOpenOption.CREATE`
    - `StandardOpenOption.TRUNCATE_EXISTING`
    - `StandardOpenOption.WRITE`

---

## 다른 문자셋으로 기록하기

```java
void example() {
    Files.writeString(path, "내용입니다", StandardCharsets.UTF_16);
}
```

- 인코딩 방식을 지정할 수 있음

---

## 옵션 지정하기

```java
void example() {
    Files.writeString(path, "추가 내용", StandardOpenOption.APPEND);
}
```

- 기존 파일에 이어쓰기 등 다양한 옵션 지정 가능

---

## 요약

| 메서드                                        | 설명               |
|--------------------------------------------|------------------|
| `writeString(Path, CharSequence)`          | 문자열을 UTF-8로 기록   |
| `writeString(Path, CharSequence, Charset)` | 지정한 인코딩으로 문자열 기록 |
| `writeString(..., OpenOption...)`          | 파일 동작 방식 제어 가능   |

> 간단한 문자열 저장 작업이 훨씬 간결해짐

---
