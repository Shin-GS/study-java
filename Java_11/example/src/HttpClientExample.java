package Java_11.example.src;

/*
named 모듈에서 새로운 http client 사용시

module com.api {
    requires com.domain;
    uses org.domain.service.StringRepository;

    requires java.net.http;
}
*/

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 11버전은 HttpClient가 autoClose 되지 않음(그 위 버전은 자동으로 close 처리됨)
        try (var httpClient = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder(URI.create("https://postman-echo.com/get"))
                    .GET()
                    .build();

            // 동기 방식 호출
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            printWithThread(response.statusCode());
            printWithThread(response.body());

            // 비동기 방식 호출
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept((responseAsync) -> {
                        printWithThread(responseAsync.statusCode());
                        printWithThread(responseAsync.body());
                    });

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private static void printWithThread(Object object) {
        System.out.printf("%s %s\n", Thread.currentThread().getName(), object);
    }
}
