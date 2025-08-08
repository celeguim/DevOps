public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello from Java CI/CD Pipeline!");
        while (true) {
            System.out.println("Still running...");
            Thread.sleep(30000);
        }
    }
}
