package ru.dvponyashov.visualizer.impl;

import ru.dvponyashov.point.MapPoint;
import ru.dvponyashov.visualizer.Visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class VisualizerImpl implements Visualizer {
    private static final int DELAY_MILLS = 25;
    private static final int CELL_SIZE = 10;

    private static final int VALUE_IF_NOT_ZERO = 1;

    private static final int X_LOCATION = 0;
    private static final int Y_LOCATION = 0;

    private JFrame frame = null;
    private MapGraphics mapGraph = null;

    @Override
    public void show(List<MapPoint> map, List<MapPoint> path, List<MapPoint> checkedList) {
        if(mapGraph == null) {
            Dimension dimension = new Dimension(getWidth(map) * CELL_SIZE, getHeight(map) * CELL_SIZE);
            mapGraph = new MapGraphics(dimension, CELL_SIZE);
            mapGraph.setMap(map);
        }
        if (frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(X_LOCATION, Y_LOCATION);
            frame.add(mapGraph);
            frame.pack();
            frame.setVisible(true);
        }
        mapGraph.setPath(path);
        mapGraph.setCheckedList(checkedList);
        mapGraph.repaint();
        try {
            Thread.sleep(DELAY_MILLS);
        } catch (Exception ignored) {}
    }

    private int getWidth(List<MapPoint> map){
        if(map == null || map.isEmpty()){
            return VALUE_IF_NOT_ZERO;
        }
        return map.stream().max(Comparator.comparingInt(MapPoint::x)).get().x() + VALUE_IF_NOT_ZERO;
    }

    private int getHeight(List<MapPoint> map){
        if(map == null || map.isEmpty()){
            return VALUE_IF_NOT_ZERO;
        }
        return map.stream().max(Comparator.comparingInt(MapPoint::y)).get().y() + VALUE_IF_NOT_ZERO;
    }
}
