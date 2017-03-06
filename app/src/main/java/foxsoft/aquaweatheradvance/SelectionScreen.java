package foxsoft.aquaweatheradvance;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import foxsoft.aquaweatheradvance.custom.DataBaseHelper;
import foxsoft.aquaweatheradvance.custom.clsContinent;
import foxsoft.aquaweatheradvance.custom.clsCountry;
import foxsoft.aquaweatheradvance.custom.clsRegion;
import foxsoft.aquaweatheradvance.custom.clsStation;

public class SelectionScreen extends Activity {

	private final String TAG = this.getClass().toString();
	
	private String StationID = null;

	private DataBaseHelper db_helper;
	private ArrayList<clsContinent> db_continents;
	
	private Spinner dropdownContinents;
	private Spinner dropdownCountries;
	private Spinner dropdownRegions;
	private Spinner dropdownStations;

	private ArrayAdapter<clsCountry> adapter_countries;
	private ArrayAdapter<clsRegion> adapter_regions;
	private ArrayAdapter<clsStation> adapter_stations;
	
	private clsContinent selectedContinent;
	private clsCountry selectedCountry;
	private clsRegion selectedRegion;
	private clsStation selectedStation;

	private GoogleMap mMap;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_screen);
        
        //CONFIG LAYOUT
        try{
        	//mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); //FIXME

			Display display = getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);

            //float density  = getResources().getDisplayMetrics().density;
            //int dpHeight = (int) (outMetrics.heightPixels / density);
            //int dpWidth  = (int) (outMetrics.widthPixels / density);
            int dpWidth  = outMetrics.widthPixels;

			LinearLayout linearData = (LinearLayout) findViewById(R.id.linearData);
            linearData.setLayoutParams(new LayoutParams(dpWidth,LayoutParams.MATCH_PARENT)); //FILL_PARENT

			//LinearLayout linearMap = (LinearLayout) findViewById(R.id.linearMap);
            //linearMap.setLayoutParams(new LayoutParams(dpWidth,LayoutParams.MATCH_PARENT)); //FILL_PARENT
            Log.i(TAG, "WIDTH SET AS "+dpWidth);
        } catch (Exception e){
        	Log.e(TAG, "Exception", e);
        }
        
        //GET DB DATA
        db_helper = new DataBaseHelper(this);
        try {
			db_helper.openDataBase();
			db_continents = db_helper.getContients();
        } catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
        
        //ACCEPT BUTTON
        try {
			Button button_accept = (Button) findViewById(R.id.button1);
	        button_accept.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Intent data = new Intent();
	            	data.putExtra("EXTRA_ID", StationID);
	            	setResult(Activity.RESULT_OK, data);
	            	finish();
	            }
	        });
        } catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
        
        try{
        	//CONFIG SPINNER (DROP-DOWN OF CONTINENTS)
        	dropdownContinents = (Spinner) findViewById(R.id.spinner_continents);
			ArrayAdapter<clsContinent> adapter_continents = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
	        adapter_continents.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        dropdownContinents.setAdapter(adapter_continents);
	        for(int x=0;x<db_continents.size();x++){
	        	adapter_continents.add(db_continents.get(x));
	        }
	        
        	//CONFIG SPINNER (DROP-DOWN OF COUNTRIES)
	        dropdownCountries = (Spinner) findViewById(R.id.spinner_countries);
	        adapter_countries = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
	        //adapter_countries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        adapter_countries.setDropDownViewResource(android.R.layout.simple_list_item_1);
	        dropdownCountries.setAdapter(adapter_countries);
	        	        
        	//CONFIG SPINNER (DROP-DOWN OF REGIONS)
	        dropdownRegions = (Spinner) findViewById(R.id.spinner_regions);
	        adapter_regions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
	        adapter_regions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        dropdownRegions.setAdapter(adapter_regions);
	        
			//CONFIG SPINNER (DROP-DOWN OF STATIONS)
	        dropdownStations = (Spinner) findViewById(R.id.spinner_stations);
	        adapter_stations = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
	        adapter_stations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dropdownStations.setAdapter(adapter_stations);
        }catch (Exception e){
        	Log.e(TAG, e.getMessage());
        }
        
        dropdownContinents.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	        	//dialogoEspera = ProgressDialog.show(context, "Loading", "please wait..");
	        	ArrayList<clsCountry> db_countries;
	        	try{
	        		selectedContinent = (clsContinent) dropdownContinents.getSelectedItem();
	        		adapter_countries.clear();
		        	adapter_regions.clear();
		        	adapter_stations.clear();
		        	
		        	db_countries = db_helper.getCountries(selectedContinent.getContinent_Id());
							        	
		        	Log.d(TAG, "Listing countries for continent_id = "+selectedContinent.getId());
		        	for(int x=0;x<db_countries.size();x++){
		        		if(selectedContinent.getContinent_Id().equals(db_countries.get(x).getContinent_Id())){
		        			adapter_countries.add(db_countries.get(x));
		        		}
			        }
		        	dropdownCountries.setSelection(0);//FIXME
	        	} catch (Exception e){
					Log.e(TAG, "Exception", e);
	        	}
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parentView) {
	            // your code here
	        }
	    });
        
        dropdownCountries.setOnItemSelectedListener(new OnItemSelectedListener() {
		        @Override
		        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        	//dialogoEspera = ProgressDialog.show(context, "Loading", "please wait..");
		        	ArrayList<clsRegion> db_regions;
		        	try{
		        		selectedCountry = (clsCountry) dropdownCountries.getSelectedItem();
			        	adapter_regions.clear();
			        	adapter_stations.clear();
			        	
			        	db_regions = db_helper.getRegions(selectedCountry.getCountry_Id());
									        	
			        	Log.d(TAG, "Listing regions for country_id = "+selectedCountry.getId());
			        	for(int x=0;x<db_regions.size();x++){
			        		if(selectedCountry.getCountry_Id().equals(db_regions.get(x).getCountry_Id())){
			        			adapter_regions.add(db_regions.get(x));
			        		}
						}
			        	dropdownRegions.setSelection(0);//FIXME
		        	} catch (Exception e){
		        		Log.e(TAG, "Exception", e);
		        	}
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> parentView) {
		            // your code here
		        }
		    });
		
        dropdownRegions.setOnItemSelectedListener(new OnItemSelectedListener() {
		        @Override
		        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        	//dialogoEspera = ProgressDialog.show(context, "Loading", "please wait..");
		        	ArrayList<clsStation> db_stations;
		        	selectedRegion = (clsRegion) dropdownRegions.getSelectedItem();
		        	adapter_stations.clear();
		        	try {
						db_stations = db_helper.getStations(selectedRegion.getRegion_Id());
						
			        	Log.d(TAG, "Listing stations for region_id = "+selectedRegion.getId());
			        	for(int x=0;x<db_stations.size();x++){
			        		if(selectedRegion.getRegion_Id().equals(db_stations.get(x).getRegion_Id())){
			        			adapter_stations.add(db_stations.get(x));
			        		}
						}
		        	} catch (Exception e) {
						Log.e(TAG, "Exception", e);
					}
		        	dropdownStations.setSelection(0);//FIXME
		        	//StationID = adapter_stations.getItem(0).getId();
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> parentView) {
		            // your code here
		        }
		    });
        
        dropdownStations.setOnItemSelectedListener(new OnItemSelectedListener() {
		        @Override
		        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        	StationID = null;
					selectedStation = (clsStation) dropdownStations.getSelectedItem();
			        Log.d(TAG, "Setting station_id = "+selectedStation.getId()+" for "+selectedStation.getName());
			        StationID = selectedStation.getId();
			        locateStation();
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> parentView) {
		            // your code here
		        }
		    });
	}
	
	private void locateStation(){
		String myAddress = selectedStation.getName() + ", " + selectedRegion.getName() + ", " + selectedCountry.getName();
		LatLng place;
		mMap.clear();
		place = getAddress(myAddress);
		if (place!=null){
			try{
	            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	            mMap.getUiSettings().setScrollGesturesEnabled(false);
	            //mMap.setMyLocationEnabled(true);
	            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 8));
	            mMap.addMarker(new MarkerOptions()
	                    .title(selectedStation.getName())
	                    .position(place));
	        } catch (Exception e) {
				Log.e(TAG, "Exception", e);
			}
		}
	}
	
	private LatLng getAddress(String myAddress){
		Geocoder geoCoder;
		List<Address> addresses;
		Address address;
		LatLng City = null;
        try{
        	geoCoder = new Geocoder(this);
            addresses = geoCoder.getFromLocationName(myAddress, 1);
            address = addresses.get(0);
			double latitude = address.getLatitude();
			double longitude = address.getLongitude();
            City = new LatLng(latitude, longitude);
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
		return City;
	}
	
}
