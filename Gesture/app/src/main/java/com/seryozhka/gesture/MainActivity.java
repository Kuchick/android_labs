package com.seryozhka.gesture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    public TextView myTextView;
    public RecisingImage recisingImage;
    float x,y;
    String sDown, sMove, sUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView = (TextView) findViewById(R.id.my_text_view);
        recisingImage = (RecisingImage) findViewById(R.id.image_view);
        myTextView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        x = event.getX();
        y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                sDown = "Down: " + x + ", " + y;
                sMove = "";
                sUp = "";
                break;
            case MotionEvent.ACTION_MOVE:
                sMove = "Move: " + x +"," + y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                sMove = "";
                sUp = "Up: " + x + "," + y;
                break;
        }
        myTextView.setText(sDown + "\n" + sMove + "\n" + sUp);
        return true;
    }

}
