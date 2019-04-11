package com.lambdaschool.movingspinner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MovingSpinner extends View {
    Paint paint;

    Rect rect;

    float x = 0, y = 0;
    float touchX, touchY;
    float rotation = 0;

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

        rect = new Rect();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                /*move = ((touchX < rectEndX && touchX > rectStartX) &&
                        (touchY < rectEndY && touchY > rectStartY));*/
                move = rect.contains((int)touchX, (int)touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!move) {
                    // S04M04-5 touch and drag to rotate
                    float diffX = event.getX() - touchX;

                    rotation += diffX / 3;

                    touchX = event.getX();
                    invalidate();
                } else {

                    // S04M04-4 touch and drag
                    float diffX = event.getX() - touchX;
                    float diffY = event.getY() - touchY;
                    x += diffX;
                    y += diffY;
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

        canvas.rotate(rotation, rect.centerX(), rect.centerY());

        // first 2 params are start x,y last two are end x,y
        rectStartX = 100 + x;
        rectStartY = 100 + y;

        rectEndX = 100 + width + x;
        rectEndY = 100 + height + y;

        rect.left = (int)rectStartX;
        rect.top = (int)rectStartY;

        rect.right = (int)rectEndX;
        rect.bottom = (int)rectEndY;

        canvas.drawRect(rect, paint);

//        canvas.drawRect(rectStartX, rectStartY, rectEndX, rectEndY, paint);

        super.onDraw(canvas);
    }
}
