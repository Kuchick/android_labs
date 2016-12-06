package com.seryozhka.getnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seryozhka.getnews.database.DBHelper;

import java.util.List;

/**
 * Created by seryozhka on 2.12.16.
 */
public class InnerJoinActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("123", "SAFDSFDFG");
        setContentView(R.layout.activity_inner_join);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.inner_join_linear_layout);

        DBHelper dbHelper = new DBHelper(this);

        List<String> answer = dbHelper.innerJoin();
        Log.d("SERZHIK", answer.toString());

        for (String note : answer) {
            TextView textView = new TextView(InnerJoinActivity.this);
            textView.setText(note);
            linearLayout.addView(textView);
            Log.d("SERZHIK", note);
        }
    }
}
