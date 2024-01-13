package ru.dvponyashov.point.impl;

import ru.dvponyashov.point.ColorPoint;

import java.awt.*;

public class MapCellPoint extends ColorPoint {
    private final int dx;
    private final int dy;

    public MapCellPoint(int x, int y, int dx, int dy, Color color) {
        super(x, y, color);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color());
        g2d.fillRect(x(), y(), dx, dy);
    }
}
