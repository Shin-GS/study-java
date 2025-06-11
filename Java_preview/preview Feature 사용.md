## preview feature 사용(Intellij 기준)

- Java의 preview feature는 명시적으로 설정을 해줘야 사용할 수 있음.
- IntelliJ 환경에서는 다음과 같은 **3가지 설정**이 필요함.

---

### ✅ 설정 1: Language Level 설정

- `File > Project Structure > Project > Project Language Level`
- 사용하려는 Java 버전의 preview level 선택 (예: `21 (Preview)`)

---

### ✅ 설정 2: 컴파일러 옵션 추가 (`build.gradle.kts` 기준)

```kotlin
tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
}
```

- `--enable-preview` 옵션을 통해 컴파일 단계에서 preview 기능 활성화

---

### ✅ 설정 3: 실행 VM 옵션 설정

- `Run > Edit Configurations > VM Options`
- 다음 옵션 추가:

```
--enable-preview
```

- 실행 시에도 preview feature를 사용하기 위해 필요함

---

### ⚠️ 에러가 계속 발생한다면?

- IntelliJ 버전을 확인할 것
- Java 21의 preview feature는 **2023.3.3 이상 버전**의 IntelliJ에서 안정적으로 지원됨

---

### 💡 대안: CLI 사용

IntelliJ에서 문제가 발생하면, CLI 환경에서도 preview feature를 사용할 수 있음

```bash
# 컴파일 (예: Main.java)
javac -source 21 --enable-preview Main.java

# 실행 (패키지 없는 경우)
java --enable-preview Main

# 실행 (패키지 있는 경우, 상위 디렉터리에서)
java --enable-preview org.api.Main
```

---

### 요약

- preview feature는 Java의 최신 문법 실험에 필수적이므로 위 설정을 통해 IntelliJ에서 안정적으로 테스트 환경을 갖추는 것이 중요함.

| 설정 항목                  | 설명                           |
|------------------------|------------------------------|
| Project Language Level | Preview 활성화된 Java 버전으로 지정    |
| Compiler 설정            | `--enable-preview` 옵션 추가     |
| VM 실행 설정               | 실행 시에도 `--enable-preview` 필요 |
| IntelliJ 최소 권장 버전      | 2023.3.3 이상                  |
| 대안                     | CLI로 preview 컴파일 및 실행 가능     |

