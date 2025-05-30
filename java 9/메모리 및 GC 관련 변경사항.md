## 메모리 및 GC 관련 변경사항

#### G1GC가 기본 GC로 변경

* Java 9부터 **G1 GC (Garbage-First Garbage Collector)** 가 기본 GC로 설정됨
* Java 8까지는 **Parallel GC** 가 기본값이었고, 상황에 따라 CMS GC 또는 G1 GC를 수동 설정해야 했음
* Java 9에서 **CMS GC는 deprecated 처리됨**

#### G1GC의 특징

* 메모리를 고정된 영역으로 나누는 대신, **region** 단위로 분할해 관리
* 각 region은 논리적으로 Young, Old 등으로 할당됨
* GC는 필요한 region만 수집하여, **Stop-The-World 시간(중단 시간)** 을 줄임

#### OutOfMemoryError 대응 옵션 추가

| 옵션                             | 설명                                   |
|--------------------------------|--------------------------------------|
| `-XX:+ExitOnOutOfMemoryError`  | OOM 발생 시 JVM을 즉시 종료                  |
| `-XX:+CrashOnOutOfMemoryError` | OOM 발생 시 JVM을 크래시시키고 가능하면 힙 덤프 파일 생성 |

* 애플리케이션이 OOM 후 무의미하게 남아있는 상태를 방지하거나, 디버깅을 위해 유용하게 사용됨
