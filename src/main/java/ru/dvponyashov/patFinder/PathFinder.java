package ru.dvponyashov.patFinder;

import ru.dvponyashov.point.MapPoint;

import java.util.List;

public interface PathFinder {
    List<MapPoint> path(List<MapPoint> map, MapPoint start, MapPoint finish);
}
