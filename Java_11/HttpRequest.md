# 새로운 `HttpClient`

---

## 기존 문제점

- 자바 표준 HTTP API는 매우 오래되어 현대적 요구사항을 충족하지 못했음
    - HTTP/1.1, HTTP/2.0 미지원
    - 비동기 처리 불가
    - 유지보수 어려움

- 대안으로 Apache HttpClient, OkHttp, Jetty, Spring의 RestTemplate 등 사용

---

## 새로운 `HttpClient` 등장

- Java 9: 인큐베이팅 모듈로 소개
- Java 11: 정식 표준 API로 채택
- 패키지: `java.net.http.HttpClient`
- 특징:
    - HTTP/1.1, HTTP/2.0 지원
    - WebSocket 지원
    - `CompletableFuture` 기반 비동기 지원
    - 람다 등 최신 자바 기능과의 호환성

---

## 모듈 시스템과 의존성

```java
// module-info.java 사용 시
module com.api {
    requires java.net.http;
}
```

- `module-info.java`가 없는 경우 별도 설정 없이 사용 가능

---

## 주요 클래스

| 클래스               | 역할             |
|-------------------|----------------|
| `HttpClient`      | 요청을 전송하는 클라이언트 |
| `HttpRequest`     | HTTP 요청 구성     |
| `HttpResponse<T>` | HTTP 응답        |

---

## 동기 GET 요청 예제

```java
void example() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(URI.create("https://postman-echo.com/get"))
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.statusCode());
    System.out.println(response.body());
}
```

> `send()`는 동기 방식이며, HTTP 응답을 즉시 반환함

---

## 비동기 GET 요청 예제

```java
void example() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(URI.create("https://postman-echo.com/get"))
            .GET()
            .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenAccept(response -> {
                System.out.println(response.statusCode());
                System.out.println(response.body());
            });
}
```

> `sendAsync()`는 `CompletableFuture`를 반환하여 비동기 처리 가능  
> 기본 실행 스레드는 `ForkJoinPool.commonPool`

---

## POST 요청 예제

```java
void example() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(URI.create("https://postman-echo.com/post"))
            .POST(HttpRequest.BodyPublishers.ofString("{\"num\":1}"))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.statusCode());
    System.out.println(response.body());
}
```

- `BodyPublishers.ofString()` 사용하여 문자열을 직접 HTTP Body에 넣음
- JSON 직렬화는 직접 수행해야 함

---

## 요약 및 의의

| 특징        | 설명                                                    |
|-----------|-------------------------------------------------------|
| HTTP 지원   | HTTP/1.1, HTTP/2.0                                    |
| 비동기 지원    | `sendAsync()` + `CompletableFuture`                   |
| 외부 의존성 없음 | JDK 내장 표준 HTTP 클라이언트                                  |
| 모듈 기반     | `java.net.http` 모듈                                    |
| 실제 적용 사례  | Spring Framework 6.1의 `JdkClientHttpRequestFactory` 등 |

> 외부 라이브러리 의존 없이 사용할 수 있는 현대적인 HTTP Client 제공

---

## 참고 자료

[Java HTTP Client 비교 (2023)](https://www.wiremock.io/post/java-http-client-comparison)

---
