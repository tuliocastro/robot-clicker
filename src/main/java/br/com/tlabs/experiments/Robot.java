package br.com.tlabs.experiments;

import org.apache.commons.io.FileUtils;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Robot implements NativeKeyListener, NativeMouseInputListener {

    private static final String FILE_NAME = "points.txt";

    private static final int TIMEOUT = 43;

    private static Set<Point> positions = new HashSet<Point>();

    private static volatile boolean run = false;

    private static Point lastPoint;

    public Robot() {
        loadPositions();
    }

    private static void click(java.awt.Robot r, Point p) {

        r.mouseMove(p.x, p.y);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);

    }

    private static void loadPositions() {

        Path path = Paths.get(FILE_NAME);

        try (Stream<String> stream = Files.lines(path)) {

            stream.forEach((line) -> {

                if (line.contains(",")) {

                    String[] vertices = line.split(",");

                    Integer x = Integer.parseInt(vertices[0]);
                    Integer y = Integer.parseInt(vertices[1]);

                    positions.add(new Point(x, y));

                }

            });

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private static void updatePositions() {

        if (positions == null || positions.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (Point point : positions) {
            sb.append(point.x).append(",").append(point.y).append(System.lineSeparator().toString());
        }

        try {
            FileUtils.writeStringToFile(new File(FILE_NAME), sb.toString(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void iniciaRobo() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    java.awt.Robot r = new java.awt.Robot();

                    while (run) {

                        try {

                            for (Point point : positions) {

                                if (!run) {
                                    break;
                                }

                                click(r, point);
                                Thread.sleep(TIMEOUT);

                            }

                        } catch (Exception ex) {
                            System.out.println("Exception occured :" + ex.getMessage());
                            System.exit(1);
                        }

                    }
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        switch (e.getKeyCode()) {

            case NativeKeyEvent.VC_PAUSE:

                if (run) {
                    run = false;
                    return;
                }

                run = true;
                iniciaRobo();


                break;

            case NativeKeyEvent.VC_PRINTSCREEN:


                if (lastPoint != null) {

                    System.out.println("Posicao adicionada");
                    positions.add(lastPoint);

                    updatePositions();

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
