public class CoffeeMain {
    public static void main(String[] args) throws InterruptedException {
        CoffeeSubscriber subscriber = new CoffeeSubscriber();
        CoffeePublisher publisher = new CoffeePublisher();
        publisher.subscribe(subscriber);

        Thread.sleep(5000); // 프로그램이 바로 종료되면 확인이 어려우니 대기시킨
    }
}
