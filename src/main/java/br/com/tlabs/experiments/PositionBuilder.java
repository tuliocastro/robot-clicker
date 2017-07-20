package br.com.tlabs.experiments;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class PositionBuilder {

    private static final String FILE_NAME = "points.txt";

    private Set<Point> positions;

    private static PositionBuilder instance;

    private PositionBuilder() {

        positions = new HashSet<>();

    }

    public void add(Point point) {

        load();

        positions.add(point);

        update();

    }

    private void load() {

        this.positions = new HashSet<>();

        Path path = getPositionsFile();

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

    private Path getPositionsFile() {

        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createFile(path);
            } catch (FileAlreadyExistsException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return path;
    }

    private void update() {

        if (positions == null || positions.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (Point point : positions) {
            sb.append(point.x).append(",").append(point.y).append(System.lineSeparator());
        }

        try {
            FileUtils.writeStringToFile(new File(FILE_NAME), sb.toString(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Set<Point> getPositions() {
        load();
        return this.positions;
    }

    public static PositionBuilder getInstance() {

        if (instance == null) {
            instance = new PositionBuilder();
        }

        return instance;
    }
}
