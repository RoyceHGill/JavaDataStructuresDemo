import application.library.IApp;
import application.library.ShutdownInterceptor;
import application.screens.MainScreen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void applicationOpens() {
        var mainScreen = new MainScreen();
        assertTrue((mainScreen.isFocusableWindow()));
    }

    @Test
    void applicationOpensWithGracefulShutdown() {
        MainScreen mainScreen = new MainScreen();
        ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(mainScreen);
        Runtime.getRuntime().addShutdownHook(shutdownInterceptor);
        mainScreen.start();
        mainScreen.shutDown();
        assertEquals("Shutdown is called", mainScreen.outMessage);
    }


}