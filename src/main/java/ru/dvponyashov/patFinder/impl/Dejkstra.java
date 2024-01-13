package ru.dvponyashov.patFinder.impl;

import ru.dvponyashov.patFinder.PathFinder;
import ru.dvponyashov.point.MapPoint;
import ru.dvponyashov.point.impl.FinderPoint;
import ru.dvponyashov.utils.PointUtil;
import ru.dvponyashov.visualizer.Visualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dejkstra implements PathFinder {
    private final Visualizer visualizer;
    private final int stopValue;

    public Dejkstra(Visualizer visualizer, int stopValue) {
        this.visualizer = visualizer;
        this.stopValue = stopValue;
    }

    @Override
    public List<MapPoint> path(List<MapPoint> map, MapPoint start, MapPoint finish) {
        List<FinderPoint> closeList = new ArrayList<>();
        List<FinderPoint> finderMap = map.stream()
                .map(FinderPoint::copyFromMapPoint)
                .collect(Collectors.toList());
        FinderPoint finderFinish = FinderPoint.copyFromMapPoint(finish);
        finderMap.forEach(p -> p.setPathFromStart(Double.MAX_VALUE));
        FinderPoint currentPoint = FinderPoint.copyFromMapPoint(start);

        currentPoint.setPathFromStart(PointUtil.startIndex());
        while (map.size() > closeList.size()) {
            // >>>>> visualization >>>>>
            if (visualizer != null) {
                visualizer.show(map, PointUtil.getPathForPoint(currentPoint), closeList.stream()
                        .map(p -> (MapPoint) p).collect(Collectors.toList()));
            }
            // <<<<<<<<<<<<<<<<<<<<<<<<<
            if (currentPoint.equals(finderFinish)) {
                finderFinish.setComeFrom(currentPoint.getComeFrom());
                break;
            }
            closeList.add(currentPoint);
            for (FinderPoint neighbor : PointUtil.getNeighbors(currentPoint, finderMap)) {
                if (neighbor.slowdown() >= stopValue) {
                    continue;
                }
                int closeIndex = closeList.indexOf(neighbor);
                if (closeIndex >= PointUtil.startIndex()) {
                    continue;
                }
                double lengthPathOld = neighbor.getPathFromStart();
                double lengthPathNew = PointUtil.pathFromStartLength(currentPoint, neighbor) +
                        PointUtil.realLength_g(currentPoint, neighbor);
                if (lengthPathOld > lengthPathNew) {
                    neighbor.setComeFrom(currentPoint);
                    neighbor.setPathFromStart(lengthPathNew);
                }
            }
            currentPoint = PointUtil.getMinFreePointOnMap(FinderPoint.copyFromMapPoint(start), finderMap,
                    closeList, stopValue);
        }
        return PointUtil.getPathForPoint(finderFinish);
    }
}
