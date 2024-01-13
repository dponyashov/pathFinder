package ru.dvponyashov.patFinder.impl;

import ru.dvponyashov.patFinder.PathFinder;
import ru.dvponyashov.point.MapPoint;
import ru.dvponyashov.point.impl.FinderPoint;
import ru.dvponyashov.utils.PointUtil;
import ru.dvponyashov.visualizer.Visualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AStar implements PathFinder {
    private final Visualizer visualizer;
    private final int stopValue;

    public AStar(Visualizer visualizer, int stopValue) {
        this.visualizer = visualizer;
        this.stopValue = stopValue;
    }

    @Override
    public List<MapPoint> path(List<MapPoint> map, MapPoint start, MapPoint finish) {

        List<FinderPoint> openList = new ArrayList<>();
        List<FinderPoint> closeList = new ArrayList<>();
        List<FinderPoint> finderMap = map.stream()
                .map(FinderPoint::copyFromMapPoint)
                .collect(Collectors.toList());
        FinderPoint finderFinish = FinderPoint.copyFromMapPoint(finish);

        openList.add(FinderPoint.copyFromMapPoint(start));
        while (!openList.isEmpty()) {
            FinderPoint currentPoint = getPointFromListWithMinRating(openList, finderFinish);
            // >>>>> visualization >>>>>
            if (visualizer != null) {
                visualizer.show(map, PointUtil.getPathForPoint(currentPoint), closeList.stream()
                        .map(p -> (MapPoint) p)
                        .collect(Collectors.toList()));
            }
            // <<<<<<<<<<<<<<<<<<<<<<<<<
            if (currentPoint.equals(finderFinish)) {
                finderFinish.setComeFrom(currentPoint.getComeFrom());
                break;
            }
            openList.remove(currentPoint);
            closeList.add(currentPoint);
            for(FinderPoint neighbor : PointUtil.getNeighbors(currentPoint, finderMap)) {
                if (neighbor.slowdown() >= stopValue) {
                    continue;
                }
                if (closeList.indexOf(neighbor) >= PointUtil.startIndex()) {
                    continue;
                }
                double pathLength = PointUtil.pathFromStartLength(currentPoint, neighbor);
                if (openList.indexOf(neighbor) >= PointUtil.startIndex()) {
                    if (pathLength < neighbor.getPathFromStart()) {
                        neighbor.setComeFrom(currentPoint);
                        neighbor.setPathFromStart(pathLength);
                    }
                } else {
                    neighbor.setPathFromStart(pathLength);
                    openList.add(neighbor);
                    neighbor.setComeFrom(currentPoint);
                }
            }
        }
        return PointUtil.getPathForPoint(finderFinish);
    }

    private FinderPoint getPointFromListWithMinRating(List<FinderPoint> list, FinderPoint finish) {
        double value = PointUtil.startIndex();
        FinderPoint result = null;
        for (FinderPoint point : list) {
            if ((result == null) || (value > PointUtil.rating_f(point, finish))) {
                result = point;
                value = PointUtil.rating_f(point, finish);
            }
        }
        return result;
    }
}
