package ru.dvponyashov.visualizer;

import ru.dvponyashov.point.MapPoint;

import java.util.List;

public interface Visualizer {
    void show(List<MapPoint> map, List<MapPoint> path, List<MapPoint> checkedList);
}
