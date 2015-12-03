package com.example.reno.lessonassign;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    private GameView gv;


    private Button shootButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        gv = new GameView(this);



        shootButton = new Button(this);
        shootButton.setWidth(350);
        shootButton.setHeight(100);
        shootButton.setBackgroundColor(Color.LTGRAY);
        shootButton.setTextColor(Color.RED);
        shootButton.setTextSize(20);
        shootButton.setText("shoot the ball ");
        shootButton.setOnClickListener(this);
        shootButton.setGravity(Gravity.CENTER);

        FrameLayout GameLayout = new FrameLayout(this);

        LinearLayout ButtonLayout = new LinearLayout (this);
        ButtonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);




        ButtonLayout.addView(shootButton);


        GameLayout.addView(gv);
        GameLayout.addView(ButtonLayout);

        setContentView(GameLayout);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        gv.killThread();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gv.onDestroy();
    }

    @Override
    public void onClick(View v)
    {
        gv. giveBall();
    }



}
