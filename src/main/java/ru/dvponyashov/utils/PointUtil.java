package ru.dvponyashov.utils;

import ru.dvponyashov.point.MapPoint;
import ru.dvponyashov.point.impl.FinderPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PointUtil {
    private static final int[][] SHIFTS = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 },
            { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };
    private static final int X_INDEX = 0;
    private static final int Y_INDEX = 1;
    private static final int SLOW_MULTIPLICATION = 25;
    private static final int DISTANCE_MULTIPLICATION = 10;
    private static final int POW_VALUE = 2;
    private static final int START_INDEX = 0;
    private static final double DIRECTLY_VALUE = 1.0;
    private static final double DIAGONALLY_VALUE = 1.4;

    public static List<FinderPoint> getNeighbors(FinderPoint point, List<FinderPoint> map) {
        List<FinderPoint> result = new ArrayList<>();
        for (int[] shift : SHIFTS) {
            int newX = point.x() + shift[X_INDEX];
            int newY = point.y() + shift[Y_INDEX];
            FinderPoint newPoint = getPointFromMapWithXY(newX, newY, map);
            if (newPoint != null) {
                result.add(newPoint);
            }
        }
        return result;
    }

    public static double pathFromStartLength(FinderPoint start, FinderPoint point) {
        return start.getPathFromStart() + realLength_g(start, point)  + point.slowdown() * SLOW_MULTIPLICATION;
    }

    public static FinderPoint getMinFreePointOnMap(FinderPoint statrtPoint, List<FinderPoint> map,
                                                   List<FinderPoint> closeList, int stopValue) {
        return map.stream()
                .filter(p -> (!closeList.contains(p) && p.slowdown() < stopValue))
                .min(Comparator.comparingDouble(p -> pathFromStartLength(p, statrtPoint)))
                .get();
    }

    public static double geomLength_h(FinderPoint point, FinderPoint finishPoint) {
        return Math.sqrt(Math.pow(finishPoint.x() - point.x(), POW_VALUE) +
                Math.pow(finishPoint.y() - point.y(), POW_VALUE)) * DISTANCE_MULTIPLICATION;
    }

    public static double realLength_g(MapPoint point, MapPoint newPoint) {
        int dx = point.x() - newPoint.x();
        int dy = point.y() - newPoint.y();
        double result = DIAGONALLY_VALUE;
        if ((dx == START_INDEX) || (dy == START_INDEX)) {
            result = DIRECTLY_VALUE;
        }
        return result * DISTANCE_MULTIPLICATION;
    }

    public static double rating_f(FinderPoint point, FinderPoint finishPoint) {
        return point.getPathFromStart() + geomLength_h(point, finishPoint);
    }

    public static List<MapPoint> getPathForPoint(FinderPoint finishPoint) {
        List<MapPoint> result = new ArrayList<>();
        FinderPoint current = finishPoint;
        while (current != null) {
            result.add(START_INDEX, current);
            current = current.getComeFrom();
        }
        return result;
    }

    public static int startIndex(){
        return START_INDEX;
    }

    private static FinderPoint getPointFromMapWithXY(int newX, int newY, List<FinderPoint> map) {
        return map.stream()
                .filter((p) -> (p.x() == newX && p.y() == newY))
                .findFirst().orElse(null);
    }
}