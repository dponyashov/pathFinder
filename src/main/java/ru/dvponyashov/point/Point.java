package ru.dvponyashov.point;

public abstract class Point{
    private final int x;
    private final int y;

    protected Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
