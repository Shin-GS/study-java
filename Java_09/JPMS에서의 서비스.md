# JPMS에서의 서비스

## 소개

- JPMS(Java Platform Module System)의 기능 중 하나
- 코드 변경 없이 구현체를 교체할 수 있도록 설계된 메커니즘
- 전통적인 서비스 제공자-소비자(Service Provider-Consumer) 모델을 모듈 시스템 차원에서 지원
- Spring의 DI와는 다른 방식으로, **프레임워크 없이도 느슨한 결합(Loosely Coupled)을 가능하게 하는 정적 메커니즘**
- `ServiceLoader`를 사용해 구현체를 동적으로 탐색 가능

---

## 서비스 구성 요소

### 1. Service Provider (서비스 제공자)

- 인터페이스를 구현하여 실제 기능을 제공하는 모듈
- `provides ... with ...` 지시어로 구현체 등록

### 2. Service Consumer (서비스 소비자)

- 서비스 구현체를 사용하는 모듈
- `uses` 지시어를 통해 사용할 인터페이스 선언

### 3. Service Loader (서비스 로더)

- `ServiceLoader.load(Class)` 메서드를 통해 등록된 구현체들을 로드하는 표준 도구
- Java 6부터 존재하며, JPMS에서도 그대로 사용됨

---

## 서비스 관련 지시어 (module-info.java)

- `provides [서비스인터페이스] with [구현클래스];`  
  → 구현체를 제공자로 등록

- `uses [서비스인터페이스];`  
  → 서비스 인터페이스 사용 선언

---

## 예제 - 메모리/DB 저장 구현

### domain 모듈 (서비스 제공자)

```java
// module-info.java
module com.domain {
    exports org.service;
    provides org.service.StringRepository with
            org.service.memory.MemoryStringRepository,
            org.service.database.DatabaseStringRepository;
}
```

```java
// org.service.StringRepository
public interface StringRepository {
    void save(String newStr);
}
```

```java
// org.service.memory.MemoryStringRepository
public class MemoryStringRepository implements StringRepository {
    @Override
    public void save(String newStr) {
        System.out.println("Memory 저장");
    }
}
```

```java
// org.service.database.DatabaseStringRepository
public class DatabaseStringRepository implements StringRepository {
    @Override
    public void save(String newStr) {
        System.out.println("Database 저장");
    }
}
```

---

### api 모듈 (서비스 소비자)

```java
// module-info.java
module com.api {
    requires com.domain;
    uses org.service.StringRepository;
}
```

```java
// StringRepositoryLoader
public class StringRepositoryLoader {
    private static final String DEFAULT = "org.service.database.DatabaseStringRepository";

    public static StringRepository getDefault() {
        return getRepository(DEFAULT);
    }

    private static StringRepository getRepository(String name) {
        for (StringRepository repository : ServiceLoader.load(StringRepository.class)) {
            if (repository.getClass().getName().equals(name)) {
                return repository;
            }
        }
        throw new IllegalArgumentException("원하는 서비스를 찾을 수 없습니다");
    }
}
```

```java
// StringSaveComponent
public class StringSaveComponent {
    private final StringRepository stringRepository = StringRepositoryLoader.getDefault();

    public void mainLogic(String name) {
        stringRepository.save(name);
    }

    public static void main(String[] args) {
        StringSaveComponent c = new StringSaveComponent();
        c.mainLogic("문자열");
    }
}
```

---

## 추가 팁

- `META-INF/services`는 전통적인 방식 (Java 6~8)  
  → JPMS에서는 **`module-info.java` 기반으로 선언**
- `ServiceLoader`는 **모든 구현체를 순회**하므로, 원하는 구현체가 여럿일 경우 조건 분기 필요
- 실행 시 `module-path`에 모든 모듈 포함되어야 서비스가 인식됨
