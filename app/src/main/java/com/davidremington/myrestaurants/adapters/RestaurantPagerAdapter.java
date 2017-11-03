package com.davidremington.myrestaurants.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.davidremington.myrestaurants.models.Restaurant;
import com.davidremington.myrestaurants.ui.RestaurantDetailFragment;

import java.util.ArrayList;

public class RestaurantPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Restaurant> restaurants;

    public RestaurantPagerAdapter(FragmentManager fm, ArrayList<Restaurant> restaurants) {
        super(fm);
        this.restaurants = restaurants;
    }

    @Override
    public Fragment getItem(int position) {
        return RestaurantDetailFragment.newInstance(restaurants.get(position));
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return restaurants.get(position).getName();
    }
}