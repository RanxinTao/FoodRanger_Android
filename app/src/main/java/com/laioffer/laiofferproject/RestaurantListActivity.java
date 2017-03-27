package com.laioffer.laiofferproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RestaurantListActivity extends AppCompatActivity implements
        RestaurantListFragment.OnItemSelectListener {
    private RestaurantGridFragment gridFragment;

    @Override
    public void onItemSelected(int position) {
        gridFragment.onItemSelected(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("FoodRanger");
        setContentView(R.layout.activity_restaurant_list);
        Log.e("Life cycle test", "We are at onCreate()");

        // Test Yelp Api
        /*new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                YelpApi yelp = new YelpApi();
                yelp.searchForBusinessesByLocation("dinner", "San Francisco, CA", 20);
                return null;
            }
        }.execute();*/

        if (findViewById(R.id.fragment_container) != null) {
            Intent intent = getIntent();
            // decide which fragment to display
            if (intent.getExtras() != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_list_container, new BackendListFragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_list_container, new RestaurantListFragment()).commit();
            }
            // Show different fragments based on screen size.
        /*if (findViewById(R.id.fragment_container) != null) {
            Fragment fragment = isTablet() ?
                    new RestaurantGridFragment() : new RestaurantListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        }*/

            //add list view to the list container (see activity_restaurant_list.xml)
            //if (isTablet()) {
            //listFragment = new RestaurantListFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.fragment_list_container,
            //listFragment).commit();
            //}

            //add Gridview to the grid container (see activity_restaurant_list.xml)
            //gridFragment = new RestaurantGridFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.fragment_grid_container,
            //gridFragment).commit();
        }
    }

    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;

        // Get ListView object from xml.
        //ListView restaurantListView = (ListView) findViewById(R.id.restaurant_list);

        // Initialize an adapter.
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.activity_restaurant_list_item,
                R.id.restaurant_name,
                getRestaurantNames());*/
        //RestaurantAdapter adapter = new RestaurantAdapter(this);

        // Assign adapter to ListView.
        //restaurantListView.setAdapter(adapter);
    }

    /* A dummy function to get fake restaurant names.
     *
     * @return an array of fake restaurant names.
     */
    /*private String[] getRestaurantNames() {
        String[] names = {
                "Restaurant1", "Restaurant2", "Restaurant3",
                "Restaurant4", "Restaurant5", "Restaurant6",
                "Restaurant7", "Restaurant8", "Restaurant9",
                "Restaurant10", "Restaurant11", "Restaurant12"};
        return names;
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Life cycle test", "We are at onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Life cycle test", "We are at onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Life cycle test", "We are at onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Life cycle test", "We are at onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Life cycle test", "We are at onDestroy()");
    }

}
