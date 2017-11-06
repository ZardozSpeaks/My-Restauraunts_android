
package com.davidremington.myrestaurants.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.davidremington.myrestaurants.R;
import com.davidremington.myrestaurants.adapters.RestaurantPagerAdapter;
import com.davidremington.myrestaurants.models.Restaurant;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.davidremington.myrestaurants.Constants.POSITION;
import static com.davidremington.myrestaurants.Constants.RESTAURANTS;

public class RestaurantDetailActivity extends BaseActivity {
    ArrayList<Restaurant> restaurants = new ArrayList<>();

    @BindView(R.id.viewPager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);
        restaurants = Parcels.unwrap(getIntent().getParcelableExtra(RESTAURANTS));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra(POSITION));
        RestaurantPagerAdapter adapterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(), restaurants);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(startingPosition);
    }
}