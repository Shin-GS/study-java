# Java 9 모듈 시스템 (JPMS)과 빌드 도구 정리

## 자바에서 모듈(Module)이란?

Java 9부터 도입된 기능으로, 클래스와 패키지를 묶어 더 큰 단위로 관리하는 구조.  
패키지보다 상위 개념이고, 모듈 단위로 의존성이나 접근 범위를 정의할 수 있음.

### 핵심 개념

- `module-info.java` 파일을 통해 모듈 정의
- 어떤 모듈을 사용하는지 (`requires`)
- 어떤 패키지를 외부에 공개할지 (`exports`)

예시:

```java
module com.myapp {
    requires com.util;
    exports com.myapp.service;
}
```

---

## 모듈과 Gradle, Maven 빌드 도구

JPMS는 JDK 레벨에서 모듈화 지원을 추가한 것이고, Gradle이나 Maven은 빌드 도구.  
두 개념은 별개지만 같이 사용할 수 있다. JPMS로 코드를 모듈화하고, 빌드 도구로 컴파일, 패키징, 의존성 관리를 하면 된다.

### Gradle에서 `implementation` vs `api`

- `implementation`: 의존성은 내부적으로만 사용, 외부에는 노출되지 않음  
  → 모듈 내부에서만 사용하고, 이 모듈을 사용하는 다른 모듈에는 전달되지 않음
- `api`: 의존성을 외부에도 노출  
  → 이 모듈을 사용하는 쪽에서도 해당 의존성에 접근 가능

Gradle 예시:

```groovy
dependencies {
    implementation 'com.google.guava:guava:31.0.1-jre'
    api 'org.apache.commons:commons-lang3:3.12.0'
}
```

---

## JPMS (Java Platform Module System)란?

Java 9에서 도입된 모듈 시스템. 기존의 클래스패스(Classpath) 방식에서 벗어나, 명시적인 모듈 선언으로 의존성과 API 노출을 관리할 수 있게 됨.

### 주요 특징

- 빌드 도구 없이도 모듈 구성 가능
- `module-info.java` 파일로 명확하게 의존성과 공개 범위를 정의
- JDK 자체도 모듈화되어 불필요한 모듈을 제거 가능

---

### JDK 9 이전과 이후 비교

| 항목        | JDK 8 이하    | JDK 9 이상 (JPMS 도입)      |
|-----------|-------------|-------------------------|
| 의존성 관리    | 클래스패스 기반    | 명시적 모듈 선언 (`requires`)  |
| 패키지 공개 범위 | 기본적으로 전체 공개 | `exports`로 명시적으로 공개해야 함 |
| 리플렉션 제한   | 없음          | 리플렉션 접근 제어 가능           |

---

## Modular Runtime Image

JPMS 덕분에 JDK 자체도 모듈화되었고, 필요한 모듈만 포함해 커스텀 런타임 이미지 생성 가능.  
`jlink` 도구를 사용하면 용량이 작고 보안성이 높은 실행 환경을 만들 수 있어서, Docker나 경량화 환경에 유리함.

---

## JPMS의 장점

- 빌드 도구 없이도 자바 자체만으로 모듈 구성 가능
- 빌드 도구와 병행 사용 가능 (Gradle, Maven 등과 함께 사용해도 문제 없음)
- 세밀한 접근 제어 가능
    - 특정 패키지만 외부로 노출 가능
    - 리플렉션 허용 여부 제어 (`--add-opens` 등으로도 설정 가능)
- 모듈 간 순환 의존성 방지

---

## JPMS 적용법

### module-info.java

- 파일명은 `module-info.java`
- 하이픈(-)은 일반적으로 Java 식별자에 허용되지 않지만, 파일명으로는 예외적으로 사용 가능
- 기본 형식:

```java
module 모듈이름 {
    requires 다른모듈;
    exports 공개할패키지;
}
```

예시:

```java
module com.api {
    requires com.domain;
}
```

모듈은 기본적으로 다른 모듈에 API를 노출하지 않음.  
API를 외부에서 사용하려면 `exports`로 패키지를 명시적으로 공개해야 함.

---

## JPMS 명령어 요약

| 키워드                   | 설명                              |
|-----------------------|---------------------------------|
| `requires`            | 다른 모듈 사용 선언                     |
| `requires static`     | 컴파일 시 의존, 런타임에는 필요 없음           |
| `requires transitive` | 하위 모듈까지 전파됨 (Gradle의 `api`와 유사) |
| `exports`             | 외부에 공개할 패키지                     |
| `exports ... to`      | 특정 모듈에게만 공개                     |

예시:

```java
module com.domain {
    exports org.domain.user;
    exports org.domain.admin to com.admin;
    requires com.common;
}
```

---

## Named Module vs Unnamed Module

- **Named Module**: `module-info.java`로 정의된 정식 모듈
- **Unnamed Module**: `module-info.java`가 없는 클래스패스 기반 코드  
  → 기존 라이브러리는 대부분 unnamed module로 처리됨
