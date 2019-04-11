package com.lambdaschool.movingspinner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MovingSpinner extends View {
    Paint  paint;
    Path   path;

    Shape shape;

    float touchX, touchY;

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

        path = new Path();
        /*final ArrayList<Point> verticies = new ArrayList<>();
        verticies.add(new Point(100, 600));
        verticies.add(new Point(600, 600));
        verticies.add(new Point(600, 100));
        verticies.add(new Point(100, 100));
        shape = new Shape(verticies, paint)*/;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
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
        if (shape != null) {
            canvas.drawPath(shape.getPath(), shape.getPaint());
        }
        super.onDraw(canvas);
    }
}
