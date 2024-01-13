package ru.dvponyashov.point.impl;

import ru.dvponyashov.point.ColorPoint;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PathCellPoint extends ColorPoint {
    private final int radius;

    public PathCellPoint(int x, int y, int radius, Color color) {
        super(x, y, color);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.color());
        Ellipse2D.Double circle = new Ellipse2D.Double(x(), y(), radius, radius);
        g2d.fill(circle);
    }
}
