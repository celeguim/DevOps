package celeguim.com;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello from Java CI/CD Pipeline!");
        
        while (true) {
            String agora = new java.util.Date().toString();
        	System.out.println("\n"+ agora);
            System.out.println("Still running...");
            Thread.sleep(15000);
        }
    }
}
