package application.library;

public class ShutdownInterceptor extends Thread {

    private final IApp app;

    public ShutdownInterceptor(IApp app) {
        this.app = app;
    }

    public void run() {
        System.out.println("Call the shutdown routine");
        app.shutDown();
    }
}