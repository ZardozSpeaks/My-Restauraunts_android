package com.davidremington.myrestaurants.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davidremington.myrestaurants.Constants;
import com.davidremington.myrestaurants.R;
import com.davidremington.myrestaurants.models.Restaurant;
import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.davidremington.myrestaurants.Constants.RESTAURANT;


public class RestaurantDetailFragment extends Fragment {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @BindView(R.id.restaurantImageView) ImageView imageLabel;
    @BindView(R.id.restaurantNameTextView) TextView nameLabel;
    @BindView(R.id.cuisineTextView) TextView categoriesLabel;
    @BindView(R.id.ratingTextView) TextView ratingLabel;
    @BindView(R.id.websiteTextView) TextView websiteLabel;
    @BindView(R.id.phoneTextView) TextView phoneLabel;
    @BindView(R.id.addressTextView) TextView addressLabel;
    @BindView(R.id.saveRestaurantButton) TextView saveRestaurantButton;

    private Restaurant restaurant;

    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(RESTAURANT, Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle parcel = Parcels.unwrap(getArguments());
        if(parcel != null) {
            restaurant = parcel.getParcelable(RESTAURANT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(restaurant.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(imageLabel);

        nameLabel.setText(restaurant.getName());
        categoriesLabel.setText(TextUtils.join(", ", restaurant.getCategories()));
        ratingLabel.setText(String.format("%s/5", Double.toString(restaurant.getRating())));
        phoneLabel.setText(restaurant.getPhone());
        addressLabel.setText(TextUtils.join(", ", restaurant.getAddress()));

        return view;
    }

    @OnClick(R.id.websiteTextView)
    public void navigateToWebPage() {
        Intent webIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(restaurant.getWebsite()));
        startActivity(webIntent);
    }

    @OnClick(R.id.phoneTextView)
    public void callRestaurant() {
        Intent phoneIntent = new Intent(
                Intent.ACTION_DIAL,
                Uri.parse(String.format("tel:%s", restaurant.getPhone())));
        startActivity(phoneIntent);
    }

    @OnClick(R.id.addressTextView)
    public void navigateToRestaurant() {
        Intent mapIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(String.format(Locale.US,
                        "geo:%f,%f?q=(%s)",
                        restaurant.getLatitude(),
                        restaurant.getLongitude(),
                        restaurant.getName())));
        startActivity(mapIntent);
    }

    @OnClick(R.id.saveRestaurantButton)
    public void saveRestaurantToFavorites() {
        Firebase ref = new Firebase(Constants.FIREBASE_URL_RESTAURANTS);
        ref.push().setValue(restaurant);
        Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
    }
}