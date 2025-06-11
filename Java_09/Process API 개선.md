## Java 9: Process API 개선

---

### 개요

Java 9에서는 **네이티브 프로세스를 제어**할 수 있는 새로운 API가 추가되었다. 이를 통해 Java 애플리케이션이 **자신의 프로세스** 또는 **다른 시스템 프로세스**에 접근하고 조작할 수 있게 되었다.

#### 핵심 인터페이스: `ProcessHandle`

#### 프로세스 관련 주요 인터페이스

- Process : 프로세스 자체를 표현
- ProcessHandle : 프로세스를 제어하는 기능들
- ProcessHandle.info : 프로세스 관련 다양한 정보 조회

---

### 주요 기능

#### 1. 현재 프로세스 정보 조회

* 현재 실행 중인 Java 프로세스의 PID(프로세스 ID)를 확인할 수 있다.

```java
public class Main {
    public static void main(String[] args) {
        long pid = ProcessHandle.current().pid();
        System.out.println("현재 프로세스 PID: " + pid);
    }
}
```

#### 2. 특정 PID 기반으로 프로세스 접근

* 특정 프로세스를 찾아서 정보 조회 또는 종료 시도 가능

```java
void exmaple() {
    Optional<ProcessHandle> process = ProcessHandle.of(12345L); // PID 지정
    process.ifPresent(ph -> {
        System.out.println("PID: " + ph.pid());
        System.out.println("실행 중인가? " + ph.isAlive());
    });
}
```

#### 3. 프로세스 정보 조회 (`ProcessHandle.Info`)

* 프로세스 생성 시간, 명령어, 사용자 등 다양한 정보 제공

```java
void example() {
    ProcessHandle.Info info = ProcessHandle.current().info();
    info.command().ifPresent(cmd -> System.out.println("명령어: " + cmd));
    info.user().ifPresent(user -> System.out.println("사용자: " + user));
    info.startInstant().ifPresent(start -> System.out.println("시작 시각: " + start));
}
```

#### 4. 프로세스 종료 시도

```java
void example() {
    ProcessHandle.of(12345L).ifPresent(ph -> {
        if (ph.isAlive()) {
            ph.destroy(); // 또는 ph.destroyForcibly();
        }
    });
}
```

---

### 관련 클래스 및 인터페이스 구조

* `ProcessHandle`: 프로세스를 제어하기 위한 인터페이스
* `ProcessHandle.Info`: 프로세스 정보 조회용 인터페이스
* `Process`: 기존 프로세스 시작/통신 API (Java 5\~)
* Java 9의 `ProcessHandle`은 `Process`와 별도로 **운영체제 수준의 PID 기반 제어**를 가능하게 함

---

### 정리

* Java 9에서는 `ProcessHandle`을 통해 **Java 프로세스 또는 외부 프로세스를 식별하고 제어**할 수 있음
* 현재 프로세스의 PID 확인, 특정 PID 검색, 프로세스 정보 조회, 종료 등이 가능
* 운영체제와 연동되는 기능이 필요한 경우 유용하게 사용됨
