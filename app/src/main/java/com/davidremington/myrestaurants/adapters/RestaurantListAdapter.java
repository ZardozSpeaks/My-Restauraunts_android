package com.davidremington.myrestaurants.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidremington.myrestaurants.R;
import com.davidremington.myrestaurants.models.Restaurant;
import com.davidremington.myrestaurants.ui.RestaurantDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.davidremington.myrestaurants.Constants.POSITION;
import static com.davidremington.myrestaurants.Constants.RESTAURANTS;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private Context context;

    public RestaurantListAdapter(Context context, ArrayList<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        holder.bindRestaurant(restaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
       @BindView(R.id.restaurantImageView) ImageView restaurantImageView;
       @BindView(R.id.restaurantNameTextView) TextView nameTextView;
       @BindView(R.id.categoryTextView) TextView categoryTextView;
       @BindView(R.id.ratingTextView) TextView ratingTextView;

       private Context context;

        RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(view -> {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(context, RestaurantDetailActivity.class);
                intent.putExtra(POSITION, String.valueOf(itemPosition));
                intent.putExtra(RESTAURANTS, Parcels.wrap(restaurants));
                context.startActivity(intent);
            });
        }

        void bindRestaurant(Restaurant restaurant) {
            setRestaurantImage(restaurant);
            setRestaurantInfo(restaurant);
        }

        private void setRestaurantInfo(Restaurant restaurant) {
            nameTextView.setText(restaurant.getName());
            categoryTextView.setText(restaurant.getCategories().get(0));
            ratingTextView.setText(String.format("%s: %s /5",
                    context.getString(R.string.list_adapter_rating),
                    restaurant.getRating() ));
        }

        private void setRestaurantImage(Restaurant restaurant) {
            Picasso.with(context).load(restaurant.getImageUrl()).into(restaurantImageView);
            Picasso.with(context)
                    .load(restaurant.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(restaurantImageView);
        }
    }
}