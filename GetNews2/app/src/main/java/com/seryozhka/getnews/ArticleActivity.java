package com.seryozhka.getnews;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by seryozhka on 27.11.16.
 */
public class ArticleActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {


    FragmentArticle myFragment;
    private GestureLibrary mGestureLib;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Log.d("SERZH", getIntent().getStringExtra("articleTitle"));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        myFragment = new FragmentArticle();
        fragmentTransaction.add(R.id.container_for_fragment, myFragment);
        fragmentTransaction.commit();


        mGestureLib = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!mGestureLib.load()) {
            finish();
        }

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.container_for_fragment);
        //gestures.addOnGesturePerformedListener(this);
        gestures.addOnGesturePerformedListener(this);

        //refreshActivity();
        //onRestart();
    }


    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = mGestureLib.recognize(gesture);
        if (predictions.size() > 0) {
            Prediction prediction = predictions.get(0);
            if (prediction.score > 1.0) {
                if (prediction.name.equals("previous")) {
                    Log.d("SERZH", "PREVIOUS");
                    myFragment.goToPreviousArticle();
                }
                else if (prediction.name.equals("next")) {
                    Log.d("SERZH", "NEXT");
                    myFragment.goToNextArticle();
                }
                else if (prediction.name.equals("play")) {
                    Log.d("SERZH", "PLAY");
                    finish();
                }
                else if (prediction.name.equals("cross")) {
                    Log.d("SERZH", "cross");

                }
            }
        }
    }

    public void saveDataToIntent(String data){
        Intent intent = new Intent();
        intent.putExtra("articleTitle", data);
        setResult(RESULT_OK, intent);
    }
}