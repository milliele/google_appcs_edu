/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class TourMap extends View {

    private Bitmap mapImage;
    private CircularLinkedList[] allList = {new CircularLinkedList(), new CircularLinkedList(), new CircularLinkedList()};
    private int curList = 0;
    private CircularLinkedList list = allList[curList];
    private String insertMode = "Add";

    private void setList() {
        if (insertMode.equals("Closest")) {
            curList = 1;
        } else if (insertMode.equals("Smallest")) {
            curList = 2;
        } else {
            curList = 0;
        }
        list = allList[curList];
    }

    public TourMap(Context context) {
        super(context);
        mapImage = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.map);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mapImage, 0, 0, null);
        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        for (Point p : list) {
            /**
             **
             **  YOUR CODE GOES HERE
             **
             **/
            canvas.drawCircle(p.x, p.y, 20, pointPaint);
        }
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Paint[] linePaint = {new Paint(), new Paint(), new Paint()};
        for(Paint paint:linePaint) paint.setStrokeWidth(5);
        linePaint[0].setColor(Color.BLACK);
        linePaint[1].setColor(Color.GREEN);
        linePaint[2].setColor(Color.BLUE);
        if (insertMode.equals("All")) {
            for (int i=0;i<3;++i) {
                Point prev = null;
                for (Point p : allList[i]) {
                    if(prev != null) {
                        canvas.drawLine(prev.x,prev.y,p.x,p.y,linePaint[i]);
                    }
                    prev = p;
                }
            }
        } else {
            Point prev = null;
            for (Point p : list) {
                /**
                 **
                 **  YOUR CODE GOES HERE
                 **
                 **/
                if(prev != null) {
                    canvas.drawLine(prev.x,prev.y,p.x,p.y,linePaint[curList]);
                }
                prev = p;
            }
        }
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point p = new Point((int) event.getX(), (int)event.getY());
                allList[0].insertBeginning(p);
                allList[1].insertNearest(p);
                allList[2].insertSmallest(p);
                TextView message = (TextView) ((Activity) getContext()).findViewById(R.id.game_status);
                if (message != null) {
                    if(insertMode.equals("All")) {
                        message.setText(String.format("Tour length is now %.2f[Beginning], %.2f[Closet], %.2f[Smallest]",
                                allList[0].totalDistance(),allList[1].totalDistance(),allList[2].totalDistance()));
                    } else {
                        message.setText(String.format("Tour length is now %.2f", list.totalDistance()));
                    }
                }
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void reset() {
        for(CircularLinkedList l:allList) l.reset();
        invalidate();
    }

    public void setInsertMode(String mode) {
        insertMode = mode;
        setList();
    }
}
