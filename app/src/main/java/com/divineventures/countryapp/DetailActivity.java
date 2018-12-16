package com.divineventures.countryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(getActionBar() != null)
        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView detail_txt = findViewById(R.id.details_text);

        if(getIntent() != null) {
            String details = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            detail_txt.setText(details);
        }
    }
}
