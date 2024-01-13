package ru.dvponyashov;

import ru.dvponyashov.patFinder.PathFinder;
import ru.dvponyashov.patFinder.impl.AStar;
import ru.dvponyashov.patFinder.impl.Dejkstra;
import ru.dvponyashov.point.MapPoint;
import ru.dvponyashov.point.impl.FinderPoint;
import ru.dvponyashov.utils.PassValue;
import ru.dvponyashov.utils.PointUtil;
import ru.dvponyashov.visualizer.impl.VisualizerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathFinderTest {
    private static final int MAP_WIDTH = 75;
    private static final int MAP_HEIGHT = 50;

    private static List<MapPoint> map;
    private static MapPoint start;
    private static MapPoint finish;

    public static void main(String[] args){
        initData();
        List<Thread> finderTasks = createTasks(map, start, finish);
        finderTasks.forEach(Thread::start);
    }

    private static void initData(){
        map = mapGenerate();
        start = getRandomPoint(map);
        finish = getRandomPoint(map);
        System.out.println("Start: " + start.toString()+ "\nFinish: " + finish.toString());
    }

    private static List<Thread> createTasks(List<MapPoint> map, MapPoint start, MapPoint finish){
        List<Thread> result = new ArrayList<>();
        result.add(createAStarTask(map, start, finish));
        result.add(createDejkstraTask(map, start, finish));
        return result;
    }

    private static Thread createAStarTask(List<MapPoint> map, MapPoint start, MapPoint finish){
        return new Thread(() -> {
            PathFinder astarFinder = new AStar(new VisualizerImpl(), PassValue.STOP_VALUE.value());
            List<MapPoint> astarPath = astarFinder.path(map, start, finish);
            printHeader("AStar", Arrays.toString(astarPath.toArray()));
        });
    }

    private static Thread createDejkstraTask(List<MapPoint> map, MapPoint start, MapPoint finish){
        return new Thread(() -> {
            PathFinder dejkstraFinder = new Dejkstra(new VisualizerImpl(), PassValue.STOP_VALUE.value());
            List<MapPoint> dejkstra = dejkstraFinder.path(map, start, finish);
            printHeader("Dejkstra", Arrays.toString(dejkstra.toArray()));
        });
    }

    private static void printHeader(String nameFinder, String path){
        System.out.println(nameFinder);
        System.out.println(path);
    }

    private static List<MapPoint> mapGenerate() {
        List<MapPoint> map = new ArrayList<>();
        for (int x = PointUtil.startIndex(); x < MAP_WIDTH; x++) {
            for (int y = PointUtil.startIndex(); y < MAP_HEIGHT; y++) {
                MapPoint point = new FinderPoint(x, y, getSlowValue().value());
                map.add(point);
            }
        }
        return map;
    }

    private static MapPoint getRandomPoint(List<MapPoint> map){
        MapPoint point;
        do {
            int index = (int) (Math.random() * map.size());
            point = map.get(index);
        } while (point.slowdown() != PassValue.FREE_POINT.value());
        return point;
    }

    private static PassValue getSlowValue() {
        int maxPercent = 100;
        int stopPercent = 20;
        int vSlowPercent = stopPercent + 5;
        int mSlowPercent = vSlowPercent + 10;
        int lSlowPercent = mSlowPercent + 15;

        int generateValue = (int) (Math.random() * maxPercent);
        if (generateValue < stopPercent) {
            return PassValue.STOP_VALUE;
        } else if (generateValue < vSlowPercent) {
            return PassValue.VERY_SLOW;
        } else if (generateValue < mSlowPercent) {
            return PassValue.MIDDLE_SLOW;
        } else if (generateValue < lSlowPercent) {
            return PassValue.LITTLE_SLOW;
        }
        return PassValue.FREE_POINT;
    }
}