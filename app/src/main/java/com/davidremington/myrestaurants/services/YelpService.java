package com.davidremington.myrestaurants.services;


import com.davidremington.myrestaurants.Constants;
import com.davidremington.myrestaurants.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;
import timber.log.Timber;

import static com.davidremington.myrestaurants.Constants.ADDRESS;
import static com.davidremington.myrestaurants.Constants.BUSINESSES;
import static com.davidremington.myrestaurants.Constants.CATEGORIES;
import static com.davidremington.myrestaurants.Constants.COORDINATE;
import static com.davidremington.myrestaurants.Constants.IMAGE_URL;
import static com.davidremington.myrestaurants.Constants.LATITUDE;
import static com.davidremington.myrestaurants.Constants.LOCATION;
import static com.davidremington.myrestaurants.Constants.LONGITUDE;
import static com.davidremington.myrestaurants.Constants.NAME;
import static com.davidremington.myrestaurants.Constants.PHONE;
import static com.davidremington.myrestaurants.Constants.RATING;
import static com.davidremington.myrestaurants.Constants.WEBSITE_URL;
import static com.davidremington.myrestaurants.Constants.YELP_BASE_URL;
import static com.davidremington.myrestaurants.Constants.YELP_LOCATION_QUERY_PARAMETER;

/* OkHttp always returns a possible NullPointer in lint outside of the
 * anonymous onResponse method. It is guaranteed safe however.
 * https://github.com/square/okhttp/issues/3635
 * */

@SuppressWarnings("ConstantConditions")
public class YelpService {

    public static void findRestaurants(String location, Callback callback) {
        String CONSUMER_KEY = Constants.YELP_CONSUMER_KEY;
        String CONSUMER_SECRET = Constants.YELP_CONSUMER_SECRET;
        String TOKEN = Constants.YELP_TOKEN;
        String TOKEN_SECRET = Constants.YELP_TOKEN_SECRET;
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        String jsonData;
        try {
            jsonData = response.body().string();
            JSONObject yelpJSON = new JSONObject(jsonData);
            JSONArray businessesJSON = yelpJSON.getJSONArray(BUSINESSES);
            for (int i = 0; i < businessesJSON.length(); i++) {
                JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                String name = restaurantJSON.getString(NAME);
                String phone = restaurantJSON.getString(PHONE);
                String website = restaurantJSON.getString(WEBSITE_URL);
                double rating = restaurantJSON.getDouble(RATING);
                String imageUrl = restaurantJSON.getString(IMAGE_URL);
                double latitude = restaurantJSON.getJSONObject(LOCATION)
                        .getJSONObject(COORDINATE).getDouble(LATITUDE);
                double longitude = restaurantJSON.getJSONObject(LOCATION)
                        .getJSONObject(COORDINATE).getDouble(LONGITUDE);
                ArrayList<String> address = new ArrayList<>();
                JSONArray addressJSON = restaurantJSON.getJSONObject(LOCATION)
                        .getJSONArray(ADDRESS);
                for (int y = 0; y < addressJSON.length(); y++) {
                    address.add(addressJSON.get(y).toString());
                }

                ArrayList<String> categories = new ArrayList<>();
                JSONArray categoriesJSON = restaurantJSON.getJSONArray(CATEGORIES);

                for (int y = 0; y < categoriesJSON.length(); y++) {
                    categories.add(categoriesJSON.getJSONArray(y).get(0).toString());
                }
                Restaurant restaurant = new Restaurant(name, phone, website, rating,
                        imageUrl, address, latitude, longitude, categories);
                restaurants.add(restaurant);
            }
        } catch (IOException | JSONException e) {
            Timber.e(e.getMessage());
        }
        return restaurants;
    }
}