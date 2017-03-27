package com.laioffer.laiofferproject;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {
    private ListView listView;
    private DataService dataService;

    // Required empty public constructor
    public RestaurantListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        listView = (ListView) view.findViewById(R.id.restaurant_list);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                getRestaurantNames());

        // Assign adapter to ListView.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback.onItemSelected(i);
            }
        });
        listView.setAdapter(new RestaurantAdapter(getActivity()));*/

        // Set a listener to ListView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = (Restaurant) listView.getItemAtPosition(position);
                // Create explicit intent to start map activity class
                // Prepare all the data we need to start map activity.
                Bundle bundle = new Bundle(); // Bundle is similar to Hashmap
                bundle.putParcelable(
                        RestaurantMapActivity.EXTRA_LATLNG, // key
                        new LatLng(r.getLat(), r.getLng())); // value
                Intent intent = new Intent(view.getContext(), RestaurantMapActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        dataService = new DataService(); // Initiate dataService field
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshRestaurantList(dataService);
    }

    // Make a async call to get restaurant data.
    private void refreshRestaurantList(DataService dataService) {
        new GetRestaurantsNearbyAsyncTask(this, dataService).execute();
    }

    //create AsyncTask background thread task
    private class GetRestaurantsNearbyAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {
        private Fragment fragment;
        private DataService dataService;
        private Clock clock;

        public GetRestaurantsNearbyAsyncTask(Fragment fragment, DataService dataService) {
            this.fragment = fragment;
            this.dataService = dataService;
            this.clock = new Clock();
            this.clock.reset();
        }

        // Output of this function will be passed into onPostExecute()
        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            clock.start();
            List<Restaurant> list = dataService.getNearbyRestaurants();
            clock.stop();
            Log.e("Latency", Long.toString(clock.getCurrentInterval()));
            return list;
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {
            if (restaurants != null) {
                super.onPostExecute(restaurants);
                RestaurantAdapter adapter = new  RestaurantAdapter(fragment.getActivity(),
                        restaurants);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(fragment.getActivity(), "Data service error.",
                        Toast.LENGTH_LONG);
            }
        }
    }

    OnItemSelectListener mCallback;

    // Container Activity must implement this interface
    public interface OnItemSelectListener {
        public void onItemSelected(int position);
    }

    @Override
    public void onAttach(Context context) { // here context is the RestaurantListActivity
        super.onAttach(context);
        try {
            // initialize mCallback: cast RestaurantListActivity to OnItemSelectListener interface
            mCallback = (OnItemSelectListener) context;
        } catch (ClassCastException e) {
            //do something
        }
    }

    private String[] getRestaurantNames() {
        String[] names= {
                "Restaurant1", "Restaurant2", "Restaurant3",
                "Restaurant4", "Restaurant5", "Restaurant6",
                "Restaurant7", "Restaurant8", "Restaurant9",
                "Restaurant10"};
        return names;
    }
}
