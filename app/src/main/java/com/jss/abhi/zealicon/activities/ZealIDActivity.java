package com.jss.abhi.zealicon.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jss.abhi.zealicon.R;

public class ZealIDActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_zeal_id);
    findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
        //nulify prev entries
      }
    });

  }
  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }
}
