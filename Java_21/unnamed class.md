## unnamed class

- 자바 21에서 preview feature로 처음 등장
- 2025년 6월 기준 아직 preview 상태
- 학습자 진입 장벽을 낮추기 위해 **메인 함수(main method)**의 문법이 간소화됨.
- 이를 통해 **패키지, 클래스 선언 없이** 단일 파일로도 실행 가능한 자바 프로그램 작성 가능.

---

### 기존의 main 함수

- 초보자가 이해하기 어려운 키워드(public, static, void, String[])가 많음

```java
package org.api;

public class Main {
    public static void main(String[] args) {
        // ...
    }
}
```

---

### 간소화된 main 함수

- `package`, `class`, `static`, `String[] args` 없이도 실행 가능
- 위의 형태를 `unnamed class` 라고 부름

```java
void main() {
    System.out.println("Hello World");
}
```

---

### 구성 요소

#### ✅ unnamed package

- `package` 선언이 없는 경우 자동으로 unnamed package로 간주됨
- named package에서는 unnamed package의 클래스를 참조할 수 없음

#### ✅ unnamed class

- `public class` 선언이 생략된 경우
- 필드나 메서드 정의 가능
- 단, 다른 클래스에서 접근할 수 없음

```java
String greeting() {
    return "Hello, World!";
}

String greeting2 = "Hello, World!";

void main() {
    System.out.println(greeting()); // greeting2도 접근 가능
}
```

---

### main 함수의 허용된 시그니처와 우선순위

1. `static void main(String[] args)`
2. `static void main()`
3. `void main(String[] args)`
4. `void main()`

- 위 순서대로 JVM이 실행 가능한 메인 함수를 탐색함

---

### 요약

| 항목         | 설명                            |
|------------|-------------------------------|
| 도입         | Java 21                       |
| 핵심 기능      | 클래스/패키지 없이 단일 파일 실행 가능        |
| main 함수 형태 | `void main()`도 허용             |
| 학습자 장점     | Java 입문자가 빠르게 실행 가능한 코드 작성 가능 |
| 용도         | 스크립트형 실습, 데모 코드, 간단한 테스트      |
