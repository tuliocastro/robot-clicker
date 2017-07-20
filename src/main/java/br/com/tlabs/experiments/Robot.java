package br.com.tlabs.experiments;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.logging.Logger;

public class Robot implements NativeKeyListener, NativeMouseInputListener {

    private static final int TIMEOUT = 43;

    private static volatile boolean run = false;

    private static Point lastPoint;

    private PositionBuilder positionBuilder;

    private Logger logger;

    public Robot() {
        positionBuilder = PositionBuilder.getInstance();
        logger = Logger.getLogger(Robot.class.getName());
    }

    private void doClick(java.awt.Robot r, Point p) {

        r.mouseMove(p.x, p.y);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);

    }


    private void initThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    java.awt.Robot r = new java.awt.Robot();

                    while (run) {

                        for (Point point : positionBuilder.getPositions()) {

                            if (!run) {
                                break;
                            }

                            doClick(r, point);
                            Thread.sleep(TIMEOUT);

                        }

                    }

                } catch (Exception e) {
                    logger.severe(e.getMessage());
                    System.exit(1);
                }
            }

        }).start();

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        switch (e.getKeyCode()) {

            case NativeKeyEvent.VC_PAUSE:

                if (run) {
                    logger.info("Stop running...");
                    run = false;
                    return;
                }

                logger.info("Start running...");
                run = true;
                initThread();

                break;

            case NativeKeyEvent.VC_PRINTSCREEN:


                if (lastPoint != null) {

                    logger.info("Point added!");
                    positionBuilder.add(lastPoint);

                }

                lastPoint = null;

                break;

        }


    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

        if (run) {
            return;
        }

        lastPoint = nativeMouseEvent.getPoint();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }
}
