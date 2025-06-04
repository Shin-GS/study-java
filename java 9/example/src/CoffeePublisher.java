import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

// Publisher
public class CoffeePublisher implements Flow.Publisher<String> {
    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        subscriber.onSubscribe(new CoffeeSubscription(subscriber));
    }

    // Subscription
    public static class CoffeeSubscription implements Flow.Subscription {
        private final Flow.Subscriber<? super String> subscriber;
        private Future<?> future;

        public CoffeeSubscription(Flow.Subscriber<? super String> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            if (n < 0) {
                subscriber.onError(new IllegalArgumentException("Requested a negative number"));
            } else {
                // 커피 제작에 1초가 걸린다.
                // 바로 Thread.sleep(1000);는 안됨 -> 스레드가 멈춰있으므로
                future = CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                        subscriber.onNext("아메리카노");

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        @Override
        public void cancel() {
            if (future != null) {
                future.cancel(false);
            }
        }
    }
}
