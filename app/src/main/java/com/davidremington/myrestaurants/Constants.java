package com.davidremington.myrestaurants;

public class Constants {
    public static final String YELP_CONSUMER_KEY = BuildConfig.YELP_CONSUMER_KEY;
    public static final String YELP_CONSUMER_SECRET = BuildConfig.YELP_CONSUMER_SECRET;
    public static final String YELP_TOKEN = BuildConfig.YELP_TOKEN;
    public static final String YELP_TOKEN_SECRET = BuildConfig.YELP_TOKEN_SECRET;
    public static final String YELP_BASE_URL = "https://api.yelp.com/v2/search?term=food";
    public static final String YELP_LOCATION_QUERY_PARAMETER = "location";
    public static final String PREFERENCES_LOCATION_KEY = "location";

    public static final String FIREBASE_LOCATION_SEARCHED_LOCATION = "searchedLocation";
    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_SEARCHED_LOCATION = FIREBASE_URL + "/" + FIREBASE_LOCATION_SEARCHED_LOCATION;
    public static final String FIREBASE_LOCATION_RESTAURANTS = "restaurants";
    public static final String FIREBASE_URL_RESTAURANTS = FIREBASE_URL + "/" + FIREBASE_LOCATION_RESTAURANTS;

    //Intent constants
    public static final String POSITION = "position";
    public static final String RESTAURANTS = "restaurants";
    public static final String RESTAURANT = "restaurant";

    //JSON constant
    public static final String BUSINESSES = "businesses";
    public static final String NAME = "name";
    public static final String PHONE = "display_phone";
    public static final String WEBSITE_URL = "url";
    public static final String RATING = "rating";
    public static final String IMAGE_URL = "image_url";
    public static final String LOCATION = "location";
    public static final String COORDINATE = "coordinate";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ADDRESS = "display_address";
    public static final String CATEGORIES = "categories";
}
