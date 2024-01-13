package ru.dvponyashov.visualizer.impl;

import ru.dvponyashov.point.ColorPoint;
import ru.dvponyashov.point.MapPoint;
import ru.dvponyashov.point.impl.MapCellPoint;
import ru.dvponyashov.point.impl.PathCellPoint;
import ru.dvponyashov.utils.PassValue;
import ru.dvponyashov.utils.PointUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

public class MapGraphics extends JPanel {
    private static final Color PATH_COLOR = Color.GREEN;
    private static final Color CHECKED_COLOR = Color.ORANGE;
    private static final Color START_COLOR = Color.MAGENTA;
    private static final Color FINISH_COLOR = Color.RED;
    private static final Color BK_COLOR = Color.WHITE;

    private static final int NORESIZE_MAX_VALUE = 3;
    private static final int RESIZE_VALUE = 2;

    private List<ColorPoint> map = null;
    private List<ColorPoint> path = null;
    private List<ColorPoint> checkedList = null;
    private final int cellSize;

    public MapGraphics(Dimension dimension, int cellSize) {
        this.cellSize = cellSize;
        setBackground(BK_COLOR);
        setPreferredSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.map != null){
            this.map.forEach((p)->p.draw(g));
        }
        if(this.checkedList != null){
            this.checkedList.forEach((p) -> p.draw(g));
        }
        if(this.path != null){
            this.path.forEach((p)->p.draw(g));
        }
    }

    public void setPath(List<MapPoint> path) {
        if(path == null){
            return;
        }
        this.path = path.stream()
                .map(fp -> {
                    Color pointColor = PATH_COLOR;
                    if(fp.equals(path.get(PointUtil.startIndex()))){
                        pointColor = START_COLOR;
                    }
                    if(fp.equals(path.get(path.size() - 1))){
                        pointColor = FINISH_COLOR;
                    }
                    return new PathCellPoint(fp.x() * cellSize, fp.y() * cellSize,
                            (cellSize > NORESIZE_MAX_VALUE ? cellSize - RESIZE_VALUE : cellSize), pointColor);
                })
                .collect(Collectors.toList());
    }

    public void setCheckedList(List<MapPoint> checkedList) {
        if(checkedList == null){
            return;
        }
        this.checkedList = checkedList.stream()
                .map(fp -> new PathCellPoint(fp.x() * cellSize, fp.y() * cellSize,
                        (cellSize > NORESIZE_MAX_VALUE ? cellSize - RESIZE_VALUE : cellSize), CHECKED_COLOR))
                .collect(Collectors.toList());
    }

    public void setMap(List<MapPoint> map) {
        if(map == null){
            return;
        }
        this.map = map.stream()
                .map(fp -> new MapCellPoint(fp.x() * cellSize, fp.y() * cellSize,
                        cellSize, cellSize, colorForSlowdown(fp.slowdown())))
                .collect(Collectors.toList());
    }

    private Color colorForSlowdown(int slowdown) {
        PassValue pv = PassValue.fromValue(slowdown);
        return switch (pv) {
            case STOP_VALUE -> Color.BLACK;
            case LITTLE_SLOW -> Color.LIGHT_GRAY;
            case MIDDLE_SLOW -> Color.GRAY;
            case VERY_SLOW -> Color.DARK_GRAY;
            default -> BK_COLOR;
        };
    }
}