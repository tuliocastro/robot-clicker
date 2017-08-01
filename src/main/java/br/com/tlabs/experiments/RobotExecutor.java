package br.com.tlabs.experiments;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

public class RobotExecutor {

    private static volatile boolean running = false;

    private PositionBuilder positionBuilder;

    private ConfigurationReader configurationReader;
    private Logger logger;

    public RobotExecutor() {
        positionBuilder = PositionBuilder.getInstance();
        logger = Logger.getLogger(RobotExecutor.class.getName());
        configurationReader = ConfigurationReader.getInstance();
    }

    private void doClick(java.awt.Robot r, Point p) {

        r.mouseMove(p.x, p.y);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);

    }

    public boolean isRunning() {

        return running;
    }

    public void toggleRun() {

        if (isRunning()) {
            logger.info("Stop running...");
            pause();
            return;
        }

        logger.info("Start running...");
        start();
    }

    public void pause() {

        running = false;
    }

    public void start() {

        running = true;

        createThread();
    }

    private void createThread() {

        Integer clickDelay = getClickDelay();

        Collection<Point> positions = getPointsList();

        new Thread(new Runnable() {

            public void run() {

                try {

                    java.awt.Robot r = new java.awt.Robot();

                    while (running) {

                        for (Point point : positions) {

                            if (!running) {
                                break;
                            }

                            doClick(r, point);
                            Thread.sleep(clickDelay);

                        }

                    }

                } catch (Exception e) {
                    logger.severe(e.getMessage());
                    System.exit(1);
                }
            }
            
        }).start();

    }


    public Integer getClickDelay() {

        configurationReader.update();

        String property = configurationReader.get("click.delay", 35);

        return Integer.parseInt(property);
    }

    public Set<Point> getPointsList() {

        positionBuilder.load();

        return positionBuilder.getPositions();
    }
}
