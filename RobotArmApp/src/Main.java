import SecondaryApplication.Library.IApp;
import SecondaryApplication.Library.ShutdownInterceptor;
import SecondaryApplication.Screens.SecondaryScreen;
import Server.CDRServer;

public class Main {
    public static void main(String[] args) {
        ThreadServer server = new ThreadServer();
        IApp app = new SecondaryScreen();
        ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(app);
        Runtime.getRuntime().addShutdownHook(shutdownInterceptor);
        app.start();
    }

    static class ThreadServer extends Thread
    {
        CDRServer server = new CDRServer(4444);
        ThreadServer()
        {
            start();
        }
    }
}