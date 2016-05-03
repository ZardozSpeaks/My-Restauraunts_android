package com.epicodus.myrestaurants.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.myrestaurants.R;

import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {
    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.findRestaurantsButton) Button mFindRestaurantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindRestaurantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = mLocationEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, RestaurantsListActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });
    }

}
