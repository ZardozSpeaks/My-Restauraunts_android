package com.davidremington.myrestaurants.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.davidremington.myrestaurants.R;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private Firebase searchedLocationRef;
    private ValueEventListener searchedLocationRefListener;

    @BindView(R.id.findRestaurantsButton) Button findRestaurantsButton;
    @BindView(R.id.savedRestaurantsButton) Button savedRestaurantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.findRestaurantsButton)
    public void navigateToRestaurantList() {
        Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.savedRestaurantsButton)
    public void navigateToSavedRestaurantList() {
        Intent savedIntent = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
        startActivity(savedIntent);
    }

}