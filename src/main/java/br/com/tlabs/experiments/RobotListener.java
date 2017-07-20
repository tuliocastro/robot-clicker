package br.com.tlabs.experiments;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.util.logging.Logger;

public class RobotListener implements NativeKeyListener, NativeMouseInputListener {

    private RobotExecutor executor;

    private Logger logger;

    private Point lastPoint;

    private PositionBuilder positionBuilder;

    public RobotListener() {

        this.executor = new RobotExecutor();

        this.logger = Logger.getLogger(RobotListener.class.getName());

        this.positionBuilder = PositionBuilder.getInstance();

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        switch (e.getKeyCode()) {

            case NativeKeyEvent.VC_PAUSE:

                executor.toggleRun();

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
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

        if (this.executor.isRunning()) {
            return;
        }

        lastPoint = nativeMouseEvent.getPoint();
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }
}
