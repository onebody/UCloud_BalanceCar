package com.ucloud_balancecar.BluetoothChat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class myView extends SurfaceView implements Callback, Runnable {

    private Boolean B = true;
    private int x = 20, y = 20;
    private int x_xin = 0, y_xin = 0;
    private int which = 0;
    private int last_which = 0;
    //	private Thread th;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint p;
    private OnFingerListener myFingerEven;

    private String str = "Hello world!";

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public OnFingerListener getMyFingerEven() {
        return myFingerEven;
    }

    public void setMyFingerEven(OnFingerListener myFingerEven) {
        this.myFingerEven = myFingerEven;
    }

    public myView(Context context) {
        super(context);
        p = new Paint();
        p.setAntiAlias(true);
        sfh = this.getHolder();
        sfh.addCallback(this);
        // th = new Thread(this);
        // this.setKeepScreenOn(true);
        setFocusable(true);
    }

    public myView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (holder.isCreating()) {
            y = this.getMeasuredHeight() / 2;
            x = this.getMeasuredWidth() / 2;

            x_xin = x;
            y_xin = y;

            Log.d("x", "" + x);
            Log.d("y", "" + y);
            // 在surfaceCreat方法中可以获取view的长度和宽度，因为此时surface已经生成
            // 在此类的构造方法中获取长度和宽度将返回0，因为Callback方法重写了surface的生成。

            B = true;
            draw();
            // th = new Thread(this);
            // th.start();
            Log.d("thread_start", "start");
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        B = false;
    }

    // void draw(){
    // canvas = sfh.lockCanvas();
    // canvas.drawColor(Color.WHITE);
    // canvas.drawCircle(x, y, 10, p);
    // sfh.unlockCanvasAndPost(canvas);
    // }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.sqrt(Math.pow(x_xin - event.getX(), 2)
                    + Math.pow(y_xin - event.getY(), 2)) <= 30) {
                which = 1;
                if (which != last_which) {
                    myFingerEven.onClick(this, which);
                    draw();
                    last_which = which;
                    Log.d("which", which + "");
                }
            } else {
                which = 0;
                if (which != last_which) {
                    myFingerEven.onClick(this, which);
                    draw();
                    last_which = which;
                    Log.d("which", which + "");

                }

            }
        }

        return true;
    }

    void draw() {

        try {

            canvas = sfh.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);
                canvas.drawCircle(x, y, 30, p);
                canvas.drawText(str, x, y + 50, p);

            }

        } catch (Exception e) {
            Log.v("Bug", "This is a bug.");

        } finally {
            if (canvas != null) {
                sfh.unlockCanvasAndPost(canvas);
            }

        }

    }

    void logic() {
        if ((x += 10) >= 200)
            x = 10;

    }

    public void run() {
        // TODO Auto-generated method stub

        while (B) {

            draw();
            // logic();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
