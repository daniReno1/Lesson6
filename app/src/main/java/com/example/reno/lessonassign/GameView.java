package com.example.reno.lessonassign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by reno on 12/2/2015.
 */
public class GameView extends SurfaceView {
    private SurfaceHolder holder;


    private Bitmap soccer_goal;


    private  Bitmap ball;

   private Bitmap test;


    private GameThread gthread = null;


    private float sgx=-205.0f;
    private float sgy=100.0f;



    private float ballx = -25.0f;
    private float bally= -25.0f;



    private boolean ballActive = false;

    private int score = 0;
    private Paint scorePaint;
    public GameView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback( new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {


                soccer_goal = BitmapFactory.decodeResource(getResources(), R.drawable.soccer_goal);
                ball = BitmapFactory.decodeResource(getResources(),R.drawable.ball );

               // test=Bitmap.createScaledBitmap(ball, 102, 125, true);


                scorePaint = new Paint();
                scorePaint.setColor(Color.BLUE);
                scorePaint.setTextSize(50.0f);
                makeThread();

                gthread.setRunning(true);
                gthread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        } );

    }
    public void makeThread()
    {
        gthread = new GameThread(this);

    }

    public void killThread()
    {
        boolean retry = true;
        gthread.setRunning(false);
        while(retry)
        {
            try
            {
                gthread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
            }
        }
    }





    @Override
    protected void onDraw(Canvas canvas)
    {

       // Bitmap scaledBitmap = Bitmap.createScaledBitmap(ball, 102, 125, true);
       // test=Bitmap.createScaledBitmap(ball,4,125,true);

        sgx = sgx + 2.0f;
        if(sgx > getWidth())sgx = -205.0f;
        sgx++;
        canvas.drawColor(Color.WHITE);



        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);



        if(ballActive)
        {
            bally = bally - 5;

            if ( bally <=  50.0f)
            {
                ballx = -50.0f;
                bally= -101.0f;
                ballActive = false;
            }
            else
            {
                canvas.drawBitmap(ball, ballx, bally, null);

            }
        }
        canvas.drawBitmap(soccer_goal, sgx, sgy, null);
      //  canvas.drawBitmap(ball, 10,10, null);

        if ( ballx>=sgx && ballx<=sgx  + soccer_goal.getWidth()
                && bally <= sgy + soccer_goal.getHeight() && bally>=sgy + soccer_goal.getHeight() - 25.0f )
        {
            score++;

            ballActive = false;
            ballx = -50.0f;
            bally = -101.0f;
        }
    }




    public void giveBall()
    {
        ballActive = true;
        ballx = getWidth() / 2.0f - ball.getWidth() / 2;
        bally = getHeight() - ball.getHeight() - 25;
    }
    public void onDestroy()
    {
        soccer_goal.recycle();
        soccer_goal = null;
        System.gc();
    }
}
