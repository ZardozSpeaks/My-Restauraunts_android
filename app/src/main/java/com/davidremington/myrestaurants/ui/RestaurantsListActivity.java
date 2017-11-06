package com.davidremington.myrestaurants.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.davidremington.myrestaurants.R;
import com.davidremington.myrestaurants.adapters.RestaurantListAdapter;
import com.davidremington.myrestaurants.models.Restaurant;
import com.davidremington.myrestaurants.services.YelpService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.Callback;
import timber.log.Timber;

import static com.davidremington.myrestaurants.Constants.LOCATION;

public class RestaurantsListActivity extends BaseActivity {
    private RestaurantListAdapter adapter;
    public ArrayList<Restaurant> restaurants = new ArrayList<>();

    @BindView(R.id.restaurantsRecyclerView) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String location = intent.getStringExtra(LOCATION);
        getRestaurants(location);
    }

    private void getRestaurants(String location) {
        YelpService.findRestaurants(location, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Timber.e(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                restaurants = YelpService.processResults(response);

                RestaurantsListActivity.this.runOnUiThread(() -> {
                    adapter = new RestaurantListAdapter(getApplicationContext(), restaurants);
                    recyclerView.setAdapter(adapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(RestaurantsListActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                });
            }
        });
    }
}