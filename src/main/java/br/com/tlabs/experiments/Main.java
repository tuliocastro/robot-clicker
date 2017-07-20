package br.com.tlabs.experiments;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws InterruptedException, NativeHookException {

        GlobalScreen.registerNativeHook();

        disableLogs();

        Robot runner = new Robot();

        GlobalScreen.addNativeKeyListener(runner);
        GlobalScreen.addNativeMouseListener(runner);

    }

    private static void disableLogs() {

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Change the level for all handlers attached to the default logger.
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            handlers[i].setLevel(Level.OFF);
        }

    }
}
