## Stack-Walking API 추가

#### StackWalker 개요

* Java 9에서는 스택 트레이스를 더 유연하게 탐색할 수 있는 `StackWalker` API가 도입됨
* 스택 프레임 정보를 스트림 형태로 가공 및 필터링 가능

#### 예제

```java
public class Main {
    public static void main(String[] args) {
        callA();
    }

    private static void callA() {
        callB();
    }

    private static void callB() {
        callC();
    }

    private static void callC() {
        List<String> walk = StackWalker.getInstance()
                .walk(s -> s.map(StackWalker.StackFrame::getMethodName)
                        .collect(Collectors.toList())
                );

        for (String s : walk) {
            System.out.println("Stack: " + s);
        }
    }
}
```

#### 출력 예시

```
Stack: callC
Stack: callB
Stack: callA
Stack: main
```

#### 옵션 사용 예시

```java
void example() {
    StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES);
    StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES);
}
```
