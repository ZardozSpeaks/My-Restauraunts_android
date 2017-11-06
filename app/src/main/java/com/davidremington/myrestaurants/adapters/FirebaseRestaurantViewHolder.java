package com.davidremington.myrestaurants.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidremington.myrestaurants.Constants;
import com.davidremington.myrestaurants.R;
import com.davidremington.myrestaurants.models.Restaurant;
import com.davidremington.myrestaurants.ui.RestaurantDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.davidremington.myrestaurants.Constants.POSITION;
import static com.davidremington.myrestaurants.Constants.RESTAURANTS;

public class FirebaseRestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private View view;
    private Context context;

    public FirebaseRestaurantViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRestaurant(Restaurant restaurant) {
        ImageView restaurantImageView = view.findViewById(R.id.restaurantImageView);
        TextView nameTextView = view.findViewById(R.id.restaurantNameTextView);
        TextView categoryTextView = view.findViewById(R.id.categoryTextView);
        TextView ratingTextView = view.findViewById(R.id.ratingTextView);

        Picasso.with(context)
                .load(restaurant.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(restaurantImageView);

        nameTextView.setText(restaurant.getName());
        categoryTextView.setText(restaurant.getCategories().get(0));
        ratingTextView.setText(String.format("%s: %s /5",
                context.getString(R.string.list_adapter_rating),
                restaurant.getRating()));
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Restaurant> restaurants = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    restaurants.add(snapshot.getValue(Restaurant.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(context, RestaurantDetailActivity.class);
                intent.putExtra(POSITION, String.valueOf(itemPosition));
                intent.putExtra(RESTAURANTS, Parcels.wrap(restaurants));

                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
