package ru.dvponyashov.point.impl;

import ru.dvponyashov.point.MapPoint;

public class FinderPoint extends MapPoint {
    private FinderPoint comeFrom;
    private double pathFromStart = 0;

    public FinderPoint(int x, int y, int slowdown) {
        super(x, y, slowdown);
    }

    public FinderPoint getComeFrom() {
        return this.comeFrom;
    }

    public void setComeFrom(FinderPoint comeFrom) {
        this.comeFrom = comeFrom;
    }

    public double getPathFromStart() {
        return this.pathFromStart;
    }

    public void setPathFromStart(double pathFromStart) {
        this.pathFromStart = pathFromStart;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FinderPoint finderPoint)){
            return false;
        }
        return (this.x() == finderPoint.x()) && (this.y() == finderPoint.y());
    }

    public static FinderPoint copyFromMapPoint(MapPoint point){
        return new FinderPoint(point.x(), point.y(), point.slowdown());
    }
}
