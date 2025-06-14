## jpackage

- 자바 16부터 새로운 도구로 도입됨
- `.jar` 파일을 **OS별 설치 가능한 포맷**으로 패키징 가능
    - Windows → `.exe`
    - macOS → `.dmg`
    - Linux → `.deb`, `.rpm` 등

---

### 주요 특징

- 자바 애플리케이션을 **네이티브 설치 프로그램** 형태로 패키징 가능
- **필요한 Java SE 모듈**을 함께 포함시킬 수 있음  
  → 고객은 **자바 설치 없이 실행 가능**
- 런처 아이콘, 애플리케이션 이름, 버전, 공급자 등 메타데이터 설정 지원
- GUI 기반 설치 프로그램 생성 가능 (운영체제별 기본 설치 방식 활용)

---

### 장점

| 항목                 | 설명                              |
|--------------------|---------------------------------|
| 배포 용이성             | 사용자는 설치만 하면 실행 가능 (Java 설치 불필요) |
| 통합 배포              | 애플리케이션 + JDK 모듈을 하나로 묶음         |
| 플랫폼 최적화            | OS별 기본 패키지 포맷 지원                |
| JavaFX 애플리케이션에도 유용 | GUI 애플리케이션 배포에 적합               |

---

### 사용 예시

```bash
jpackage \
  --input target/ \
  --name MyApp \
  --main-jar myapp.jar \
  --type dmg \
  --icon icon.icns
```

---

### 공식 문서

더 자세한 사용법은 아래 링크에서 확인 가능:  
🔗 https://docs.oracle.com/en/java/javase/17/docs/specs/man/jpackage.html

---

### 참고

- `jpackage`는 `jdk/bin` 경로에 포함되어 있음
- JavaFX, 스프링 부트, CLI 애플리케이션 등 다양한 형태의 앱에 적용 가능
- `jlink`와 함께 사용하면 더욱 최적화된 런타임 구성도 가능

---

### 요약

| 항목     | 내용                    |
|--------|-----------------------|
| 도입 버전  | Java 16               |
| 기능     | JAR → 설치 프로그램 패키징     |
| 플랫폼 지원 | Windows, macOS, Linux |
| 특징     | Java 런타임 포함, 배포 간편화   |

jpackage는 데스크탑 애플리케이션을 고객에게 배포하려는 상황에서 매우 유용한 도구임.
