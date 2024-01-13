package ru.dvponyashov.point;

public abstract class MapPoint extends Point{
    private final int slowdown;

    protected MapPoint(int x, int y, int slowdown) {
        super(x, y);
        this.slowdown = slowdown;
    }

    public int slowdown(){
        return this.slowdown;
    }
    @Override
    public String toString(){
        return String.format("[%s:%s]", x(), y());
    }
}
