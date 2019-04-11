package com.lambdaschool.movingspinner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MovingSpinner extends View {
    Paint  paint;
    Path   path;
    Region region;
//    Rect rect;

    Shape shape;

//    float x = 0, y = 0;
    float touchX, touchY;
//    float rotation = 0;

    float rectStartX, rectEndX, rectStartY, rectEndY;

    boolean move;

    public MovingSpinner(Context context) {
        super(context);
        init();
    }

    public MovingSpinner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovingSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MovingSpinner(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(android.R.color.holo_red_dark));

//        rect = new Rect();
        path = new Path();
        region = new Region();
        final ArrayList<Point> verticies = new ArrayList<>();
        verticies.add(new Point(100, 600));
        verticies.add(new Point(600, 600));
        verticies.add(new Point(600, 100));
        verticies.add(new Point(100, 100));
        shape = new Shape(verticies, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                /*move = ((touchX < rectEndX && touchX > rectStartX) &&
                        (touchY < rectEndY && touchY > rectStartY));*/
//                move = region.contains((int) touchX, (int) touchY);
//                move = false;
                /*Path tempPath = new Path(); // Create temp Path
                tempPath.moveTo(touchX,touchY); // Move cursor to point
                RectF rectangle = new RectF(touchX - 1, touchY - 1, touchX + 1, touchY + 1); // create rectangle with size 2xp
                tempPath.addRect(rectangle, Path.Direction.CW); // add rect to temp path
                tempPath.op(shape.getPath(), Path.Op.DIFFERENCE); // get difference with our PathToCheck
                move = !tempPath.isEmpty();*/
//                move = shape.contains((int)touchX, (int)touchY);
                move = shape.containsFine((int)touchX, (int)touchY);

                break;
            case MotionEvent.ACTION_MOVE:
                if (!move) {
                    // S04M04-5 touch and drag to rotate
                    float diffX = event.getX() - touchX;

                    shape.addRotation(diffX / 3);

                    touchX = event.getX();
                    invalidate();
                } else {

                    // S04M04-4 touch and drag
                    float diffX = event.getX() - touchX;
                    float diffY = event.getY() - touchY;
                    shape.addxOffset(diffX);
                    shape.addyOffset(diffY);
                    touchX = event.getX();
                    touchY = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                // S04M04-3 lift up to move
                /*float diffX = event.getX() - touchX;
                float diffY = event.getY() - touchY;
                x += diffX;
                y += diffY;
                invalidate();*/
                break;
        }
        return true;
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // S04M04-2 draw rectangle
        final int width  = getWidth() / 2;
        final int height = getHeight() / 2;

        /*rectStartX = 100 + x;
        rectStartY = 100 + y;
        rectEndX = 100 + width + x;
        rectEndY = 100 + height + y;*/

//        shape.move((int)x, (int)y);
//        canvas.translate(width, height);
        canvas.drawPath(shape.getPath(), shape.getPaint());
//        region.setPath(shape.getPath((int)x, (int)y, (int)rotation))

//        path.moveTo(100, 100);
//        path.lineTo(600, 100);
//        path.lineTo(600, 600);
//        path.lineTo(100, 600);
//        path.close();
//
//        canvas.drawPath(path, paint);

        // first 2 params are start x,y last two are end x,y
        /*rectStartX = 100 + x;
        rectStartY = 100 + y;
        rectEndX = 100 + width + x;
        rectEndY = 100 + height + y;

        float rectCenterX = (rectEndX - rectStartX) / 2 + rectStartX;
        float rectCenterY = (rectEndY - rectStartY) / 2 + rectStartY;

//        rect.left = (int)rectStartX;
//        rect.top = (int)rectStartY;
//        rect.right = (int)rectEndX;
//        rect.bottom = (int)rectEndY;

//        float originRectStartX = rectStartX - rect.centerX();
//        float originRectStartY = rectStartY - rect.centerY();
//        float originRectEndX   = rectEndX - rect.centerX();
//        float originRectEndY   = rectEndY - rect.centerY();

//        rect.left = (int) originRectStartX;
//        rect.top = (int) originRectStartY;
//        rect.right = (int) originRectEndX;
//        rect.bottom = (int) originRectEndY;

        *//*rect.left = (int) ((originRectStartX * Math.cos(Math.toRadians(rotation))) - (originRectStartY * Math.sin(Math.toRadians(rotation))) + rect.centerX());
        rect.top = (int) ((originRectStartX * Math.sin(Math.toRadians(rotation))) + (originRectStartY * Math.cos(Math.toRadians(rotation))) + rect.centerY());
        rect.right = (int) ((originRectEndX * Math.cos(Math.toRadians(rotation))) - (originRectEndY * Math.sin(Math.toRadians(rotation))) + rect.centerX());
        rect.bottom = (int) ((originRectEndX * Math.sin(Math.toRadians(rotation))) + (originRectEndY * Math.cos(Math.toRadians(rotation))) + rect.centerY());*//*

        float originStartX = rectStartX - rectCenterX;
        float originStartY = rectStartY - rectCenterY;
        float originEndX   = rectEndX - rectCenterX;
        float originEndY   = rectEndY - rectCenterY;

        float startX = (float)((originStartX * Math.cos(Math.toRadians(rotation))) - (originStartY * Math.sin(Math.toRadians(rotation))) + rectCenterX);
        float startY = (float)((originStartX * Math.sin(Math.toRadians(rotation))) + (originStartY * Math.cos(Math.toRadians(rotation))) + rectCenterY);
        float endX = (float)((originEndX * Math.cos(Math.toRadians(rotation))) - (originEndY * Math.sin(Math.toRadians(rotation))) + rectCenterX);
        float endY = (float)((originEndX * Math.sin(Math.toRadians(rotation))) + (originEndY * Math.cos(Math.toRadians(rotation))) + rectCenterY);

        path.moveTo(startX, startY);
        path.lineTo(endX, startY);
        path.lineTo(endX, endY);
        path.lineTo(startX, endY);
        path.close();

        canvas.drawPath(path, paint);*/

//        canvas.save();
//        canvas.rotate(rotation, rect.centerX(), rect.centerY());

//        canvas.drawRect(rect, paint);
//        canvas.restore();
//        final Paint paint1 = new Paint();
//        paint1.setColor(Color.MAGENTA);
//        canvas.drawRect(rect, paint1);

//        canvas.drawRect(rectStartX, rectStartY, rectEndX, rectEndY, paint);

        super.onDraw(canvas);
    }
}
