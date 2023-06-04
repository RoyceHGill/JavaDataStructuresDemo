
import application.library.IApp;
import application.library.ShutdownInterceptor;
import application.screens.MainScreen;

public enum Main{
    ;

    public static void main(String[] args) {
        MainScreen app = new MainScreen();
        ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(app);
        Runtime.getRuntime().addShutdownHook(shutdownInterceptor);
        app.start();

    }


}