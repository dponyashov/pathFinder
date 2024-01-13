package ru.dvponyashov.point;

import java.awt.*;

public abstract class ColorPoint extends Point {
    private final Color color;

    protected ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color color() {
        return this.color;
    }

    public abstract void draw(Graphics g);
}
