package com.davidremington.myrestaurants.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.davidremington.myrestaurants.Constants;
import com.davidremington.myrestaurants.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity {
    private DatabaseReference searchedLocationRef;
    private ValueEventListener searchedLocationRefListener;

    @BindView(R.id.findRestaurantsButton) Button findRestaurantsButton;
    @BindView(R.id.locationEditText) EditText locationEditText;
    @BindView(R.id.savedRestaurantsButton) Button savedRestaurantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        searchedLocationRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        searchedLocationRefListener = searchedLocationRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue() != null ?
                            locationSnapshot.getValue().toString() : "ERROR";
                    Timber.d("Locations updated", String.format("location: %s", location));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Timber.e(databaseError.getMessage());
            }

        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchedLocationRef.removeEventListener(searchedLocationRefListener);
    }


    @OnClick(R.id.findRestaurantsButton)
    public void navigateToRestaurantList() {
        String location = locationEditText.getText().toString();
        saveLocationToFirebase(location);
        Intent intent = new Intent(MainActivity.this, RestaurantsListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.savedRestaurantsButton)
    public void navigateToSavedRestaurantList() {
        Intent savedIntent = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
        startActivity(savedIntent);
    }

    private void saveLocationToFirebase(String location) {
        searchedLocationRef.push().setValue(location);
    }

}