package com.lambdaschool.movingspinner;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.snatik.polygon.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    private List<Point> startingVerticies;
    private List<Point> actualVerticies;
    private Paint       paint;

    private float xOffset, yOffset, rotation;

    /*Shape(int numVerticies, Paint paint) {
        this.startingVerticies = new ArrayList<>();
        this.paint = paint;
    }*/

    Shape(List<Point> startingVerticies, Paint paint) {
        this.startingVerticies = startingVerticies;
        this.actualVerticies = new ArrayList<>(startingVerticies);
        this.paint = paint;
        this.xOffset = 0;
        this.yOffset = 0;
        this.rotation = 0;
    }



    public void addxOffset(float additionalxOffset) {
        this.xOffset += additionalxOffset;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void addyOffset(float additionalyOffset) {
        this.yOffset += additionalyOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public void addRotation(float additionalrotation) {
        this.rotation += additionalrotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setStartingVerticies(List<Point> startingVerticies) {
        this.startingVerticies = startingVerticies;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public float centerX() {
        float totalX = 0;
        for (Point pt : startingVerticies) {
            totalX += pt.x;
        }
        return totalX / startingVerticies.size();
    }

    public float centerY() {
        float totalY = 0;
        for (Point pt : startingVerticies) {
            totalY += pt.y;
        }
        return totalY / startingVerticies.size();
    }

    // approximate based on rectangle built with max/min edges
    public boolean contains(int x, int y) {
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;

        for (Point pt : actualVerticies) {
            if(pt.x > maxX) {
                maxX = pt.x;
            }
            if(pt.x < minX) {
                minX = pt.x;
            }
            if(pt.y > maxY) {
                maxY = pt.y;
            }
            if(pt.y < minY) {
                minY = pt.y;
            }
        }

        return ((x < maxX && x > minX) &&
         (y < maxY && y > minY));
    }

    // uses 3rd party library
    public boolean containsFine(int x, int y) {
        Polygon.Builder builder = Polygon.Builder();
        for(Point point: actualVerticies) {
            builder.addVertex(new com.snatik.polygon.Point(point.x, point.y));
        }

        return builder.build().contains(new com.snatik.polygon.Point(x, y));
    }

    /*public void move(int x, int y) {
        for(int i = 0; i < startingVerticies.size(); ++i) {
            final Point pt = startingVerticies.get(i);
            startingVerticies.set(i, new Point(pt.x + x, pt.y + y));
        }
    }*/

    public Path getPath(int x, int y) {
        Path path = new Path();
        path.moveTo(startingVerticies.get(0).x + x, startingVerticies.get(0).y + y);
        for (int i = 1; i < startingVerticies.size(); ++i) {
            path.lineTo(startingVerticies.get(i).x + x, startingVerticies.get(i).y + y);
        }
        path.close();
        return path;
    }

    public Path getPath() {
        int centerX = (int) centerX();
        int centerY = (int) centerY();

        actualVerticies.clear();

        for (Point pt : startingVerticies) {
            actualVerticies.add(new Point(pt.x - centerX, pt.y - centerY));
        }

        for (int i = 0; i < actualVerticies.size(); ++i) {
            Point pt = new Point(
                    (int) ((actualVerticies.get(i).x * Math.cos(Math.toRadians(rotation))) - (actualVerticies.get(i).y * Math.sin(Math.toRadians(rotation))) + centerX + xOffset),
                    (int) ((actualVerticies.get(i).x * Math.sin(Math.toRadians(rotation))) + (actualVerticies.get(i).y * Math.cos(Math.toRadians(rotation))) + centerY + yOffset)
            );
            actualVerticies.set(i, pt);
        }

        Path path = new Path();
        path.moveTo(actualVerticies.get(0).x, actualVerticies.get(0).y);
        for (int i = 1; i < actualVerticies.size(); ++i) {
            path.lineTo(actualVerticies.get(i).x, actualVerticies.get(i).y);
        }
        path.close();
        return path;
    }

}
