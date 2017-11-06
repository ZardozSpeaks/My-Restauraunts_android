package com.davidremington.myrestaurants.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.davidremington.myrestaurants.Constants;
import com.davidremington.myrestaurants.R;
import com.davidremington.myrestaurants.adapters.FirebaseRestaurantViewHolder;
import com.davidremington.myrestaurants.models.Restaurant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRestaurantListActivity extends BaseActivity {
    private DatabaseReference restaurantReference;
    private FirebaseRecyclerAdapter firebaseAdapter;

    @BindView(R.id.restaurantsRecyclerView) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurants);
        ButterKnife.bind(this);

        restaurantReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        firebaseAdapter = new FirebaseRecyclerAdapter<Restaurant, FirebaseRestaurantViewHolder>
                (Restaurant.class, R.layout.restaurant_list_item, FirebaseRestaurantViewHolder.class,
                        restaurantReference) {

            @Override
            protected void populateViewHolder(FirebaseRestaurantViewHolder viewHolder,
                                              Restaurant model, int position) {
                viewHolder.bindRestaurant(model);
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAdapter.cleanup();
    }
}
