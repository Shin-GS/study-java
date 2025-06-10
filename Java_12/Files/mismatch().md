## mismatch()

- 자바 12에서 추가됨
- 서로 다른 **두 파일의 내용이 같은지**를 간단하게 비교할 수 있음.
- 이 기능은 테스트 코드나 파일 비교 유틸리티를 만들 때 유용하게 쓸 수 있음.

```java
var file1 = Paths.get("./a.txt");
var file2 = Paths.get("./b.txt");

long result = Files.mismatch(file1, file2);
```

- 두 파일의 내용이 다를 경우:
    - **처음으로 다른 위치의 바이트 인덱스**를 `long` 타입으로 반환함
- 두 파일의 내용이 동일할 경우:
    - **-1** 반환

---

### 활용 예시

```java
void example() {
    if (Files.mismatch(file1, file2) == -1) {
        System.out.println("파일 내용이 동일함");
    } else {
        System.out.println("파일 내용이 다름");
    }
}
```
