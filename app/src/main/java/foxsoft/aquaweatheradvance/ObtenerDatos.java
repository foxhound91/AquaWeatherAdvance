package foxsoft.aquaweatheradvance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import foxsoft.aquaweatheradvance.custom.DataBaseHelper;
import foxsoft.aquaweatheradvance.parser.PuertosSaxParser;
import foxsoft.aquaweatheradvance.custom.clsStation;

public class ObtenerDatos extends Activity {
	
	private ArrayList<TextView> arrayT1Velocidad;
	private ArrayList<ImageView> arrayT1Direccion;
	
	private ArrayList<TextView> arrayT1OlasAltura;
	private ArrayList<ImageView> arrayT1OlasDireccion;
	
	private ArrayList<TextView> arrayT1Temperatura;
	private ArrayList<TextView> arrayT1Pressure;
	
	private ArrayList<ImageView> arrayT1Weather;
	private ArrayList<TextView> arrayT1Precipitation;
	
	private TextView textT1Velocidad00;
	private TextView textT1Velocidad03;
	private TextView textT1Velocidad06;
	private TextView textT1Velocidad09;
	private TextView textT1Velocidad12;
	private TextView textT1Velocidad15;
	private TextView textT1Velocidad18;
	private TextView textT1Velocidad21;
	private ImageView textT1Direccion00;
	private ImageView textT1Direccion03;
	private ImageView textT1Direccion06;
	private ImageView textT1Direccion09;
	private ImageView textT1Direccion12;
	private ImageView textT1Direccion15;
	private ImageView textT1Direccion18;
	private ImageView textT1Direccion21;
	
	private TextView textT1OlasAltura00;
	private TextView textT1OlasAltura03;
	private TextView textT1OlasAltura06;
	private TextView textT1OlasAltura09;
	private TextView textT1OlasAltura12;
	private TextView textT1OlasAltura15;
	private TextView textT1OlasAltura18;
	private TextView textT1OlasAltura21;
	private ImageView textT1OlasDireccion00;
	private ImageView textT1OlasDireccion03;
	private ImageView textT1OlasDireccion06;
	private ImageView textT1OlasDireccion09;
	private ImageView textT1OlasDireccion12;
	private ImageView textT1OlasDireccion15;
	private ImageView textT1OlasDireccion18;
	private ImageView textT1OlasDireccion21;
	
	private TextView textT1Temperatura00;
	private TextView textT1Temperatura03;
	private TextView textT1Temperatura06;
	private TextView textT1Temperatura09;
	private TextView textT1Temperatura12;
	private TextView textT1Temperatura15;
	private TextView textT1Temperatura18;
	private TextView textT1Temperatura21;
	private TextView textT1Pressure00;
	private TextView textT1Pressure03;
	private TextView textT1Pressure06;
	private TextView textT1Pressure09;
	private TextView textT1Pressure12;
	private TextView textT1Pressure15;
	private TextView textT1Pressure18;
	private TextView textT1Pressure21;
	
	private ImageView textT1Weather00;
	private ImageView textT1Weather03;
	private ImageView textT1Weather06;
	private ImageView textT1Weather09;
	private ImageView textT1Weather12;
	private ImageView textT1Weather15;
	private ImageView textT1Weather18;
	private ImageView textT1Weather21;
	private TextView textT1Precipitation00;
	private TextView textT1Precipitation03;
	private TextView textT1Precipitation06;
	private TextView textT1Precipitation09;
	private TextView textT1Precipitation12;
	private TextView textT1Precipitation15;
	private TextView textT1Precipitation18;
	private TextView textT1Precipitation21;
	
	private clsStation selectedStation;
	
	private TextView stationName;
	
	private ProgressDialog dialogoEspera;
	
	private AdView adView;
	
	private AsyncTaskCargaDatos ATCargaDatos;
	
	private DecimalFormat dfT, dfP;
	
	private Button button_loadStation;
	
	private Context mContext;
	
	private DataBaseHelper db_helper;

	private final String TAG = this.getClass().toString();
	
	private final static int DAY = 90000;
	private final static int NIGHT = 210000;
	
	private static final int B_REQUEST = 0;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mContext = this;
        
        //FORMAT FOR THE DOUBLE VALUES
      	dfT = new DecimalFormat("#.#");
      	dfP = new DecimalFormat("#");
        
        //INSERT ALL TEXTVIEWS IN A SINGLE ARRAY
        arrayT1Velocidad = new ArrayList<TextView>();
        arrayT1Velocidad.add(textT1Velocidad00 = (TextView)findViewById(R.id.TV_T1_VEL_00));
        arrayT1Velocidad.add(textT1Velocidad03 = (TextView)findViewById(R.id.TV_T1_VEL_03));
		arrayT1Velocidad.add(textT1Velocidad06 = (TextView)findViewById(R.id.TV_T1_VEL_06));
		arrayT1Velocidad.add(textT1Velocidad09 = (TextView)findViewById(R.id.TV_T1_VEL_09));
		arrayT1Velocidad.add(textT1Velocidad12 = (TextView)findViewById(R.id.TV_T1_VEL_12));
		arrayT1Velocidad.add(textT1Velocidad15 = (TextView)findViewById(R.id.TV_T1_VEL_15));
		arrayT1Velocidad.add(textT1Velocidad18 = (TextView)findViewById(R.id.TV_T1_VEL_18));
		arrayT1Velocidad.add(textT1Velocidad21 = (TextView)findViewById(R.id.TV_T1_VEL_21));
		arrayT1Direccion = new ArrayList<ImageView>();
		arrayT1Direccion.add(textT1Direccion00 = (ImageView)findViewById(R.id.TV_T1_DIR_00));
		arrayT1Direccion.add(textT1Direccion03 = (ImageView)findViewById(R.id.TV_T1_DIR_03));
		arrayT1Direccion.add(textT1Direccion06 = (ImageView)findViewById(R.id.TV_T1_DIR_06));
		arrayT1Direccion.add(textT1Direccion09 = (ImageView)findViewById(R.id.TV_T1_DIR_09));
		arrayT1Direccion.add(textT1Direccion12 = (ImageView)findViewById(R.id.TV_T1_DIR_12));
		arrayT1Direccion.add(textT1Direccion15 = (ImageView)findViewById(R.id.TV_T1_DIR_15));
		arrayT1Direccion.add(textT1Direccion18 = (ImageView)findViewById(R.id.TV_T1_DIR_18));
		arrayT1Direccion.add(textT1Direccion21 = (ImageView)findViewById(R.id.TV_T1_DIR_21));
		arrayT1OlasAltura = new ArrayList<TextView>();
		arrayT1OlasAltura.add(textT1OlasAltura00 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_00));
		arrayT1OlasAltura.add(textT1OlasAltura03 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_03));
		arrayT1OlasAltura.add(textT1OlasAltura06 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_06));
		arrayT1OlasAltura.add(textT1OlasAltura09 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_09));
		arrayT1OlasAltura.add(textT1OlasAltura12 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_12));
		arrayT1OlasAltura.add(textT1OlasAltura15 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_15));
		arrayT1OlasAltura.add(textT1OlasAltura18 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_18));
		arrayT1OlasAltura.add(textT1OlasAltura21 = (TextView)findViewById(R.id.TV_T1_OLAS_ALT_21));
		arrayT1OlasDireccion = new ArrayList<ImageView>();
		arrayT1OlasDireccion.add(textT1OlasDireccion00 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_00));
		arrayT1OlasDireccion.add(textT1OlasDireccion03 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_03));
		arrayT1OlasDireccion.add(textT1OlasDireccion06 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_06));
		arrayT1OlasDireccion.add(textT1OlasDireccion09 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_09));
		arrayT1OlasDireccion.add(textT1OlasDireccion12 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_12));
		arrayT1OlasDireccion.add(textT1OlasDireccion15 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_15));
		arrayT1OlasDireccion.add(textT1OlasDireccion18 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_18));
		arrayT1OlasDireccion.add(textT1OlasDireccion21 = (ImageView)findViewById(R.id.TV_T1_OLAS_DIR_21));
		arrayT1Precipitation = new ArrayList<TextView>();
		arrayT1Precipitation.add(textT1Precipitation00 = (TextView)findViewById(R.id.TV_T1_RAC_00));
		arrayT1Precipitation.add(textT1Precipitation03 = (TextView)findViewById(R.id.TV_T1_RAC_03));
		arrayT1Precipitation.add(textT1Precipitation06 = (TextView)findViewById(R.id.TV_T1_RAC_06));
		arrayT1Precipitation.add(textT1Precipitation09 = (TextView)findViewById(R.id.TV_T1_RAC_09));
		arrayT1Precipitation.add(textT1Precipitation12 = (TextView)findViewById(R.id.TV_T1_RAC_12));
		arrayT1Precipitation.add(textT1Precipitation15 = (TextView)findViewById(R.id.TV_T1_RAC_15));
		arrayT1Precipitation.add(textT1Precipitation18 = (TextView)findViewById(R.id.TV_T1_RAC_18));
		arrayT1Precipitation.add(textT1Precipitation21 = (TextView)findViewById(R.id.TV_T1_RAC_21));
		arrayT1Temperatura = new ArrayList<TextView>();
		arrayT1Temperatura.add(textT1Temperatura00 = (TextView)findViewById(R.id.TV_T1_TEMP_00));
		arrayT1Temperatura.add(textT1Temperatura03 = (TextView)findViewById(R.id.TV_T1_TEMP_03));
		arrayT1Temperatura.add(textT1Temperatura06 = (TextView)findViewById(R.id.TV_T1_TEMP_06));
		arrayT1Temperatura.add(textT1Temperatura09 = (TextView)findViewById(R.id.TV_T1_TEMP_09));
		arrayT1Temperatura.add(textT1Temperatura12 = (TextView)findViewById(R.id.TV_T1_TEMP_12));
		arrayT1Temperatura.add(textT1Temperatura15 = (TextView)findViewById(R.id.TV_T1_TEMP_15));
		arrayT1Temperatura.add(textT1Temperatura18 = (TextView)findViewById(R.id.TV_T1_TEMP_18));
		arrayT1Temperatura.add(textT1Temperatura21 = (TextView)findViewById(R.id.TV_T1_TEMP_21));
		arrayT1Weather = new ArrayList<ImageView>();
		arrayT1Weather.add(textT1Weather00 = (ImageView)findViewById(R.id.TV_T1_WEA_00));
		arrayT1Weather.add(textT1Weather03 = (ImageView)findViewById(R.id.TV_T1_WEA_03));
		arrayT1Weather.add(textT1Weather06 = (ImageView)findViewById(R.id.TV_T1_WEA_06));
		arrayT1Weather.add(textT1Weather09 = (ImageView)findViewById(R.id.TV_T1_WEA_09));
		arrayT1Weather.add(textT1Weather12 = (ImageView)findViewById(R.id.TV_T1_WEA_12));
		arrayT1Weather.add(textT1Weather15 = (ImageView)findViewById(R.id.TV_T1_WEA_15));
		arrayT1Weather.add(textT1Weather18 = (ImageView)findViewById(R.id.TV_T1_WEA_18));
		arrayT1Weather.add(textT1Weather21 = (ImageView)findViewById(R.id.TV_T1_WEA_21));
		arrayT1Pressure = new ArrayList<TextView>();
		arrayT1Pressure.add(textT1Pressure00 = (TextView)findViewById(R.id.TV_T1_PRE_00));
		arrayT1Pressure.add(textT1Pressure03 = (TextView)findViewById(R.id.TV_T1_PRE_03));
		arrayT1Pressure.add(textT1Pressure06 = (TextView)findViewById(R.id.TV_T1_PRE_06));
		arrayT1Pressure.add(textT1Pressure09 = (TextView)findViewById(R.id.TV_T1_PRE_09));
		arrayT1Pressure.add(textT1Pressure12 = (TextView)findViewById(R.id.TV_T1_PRE_12));
		arrayT1Pressure.add(textT1Pressure15 = (TextView)findViewById(R.id.TV_T1_PRE_15));
		arrayT1Pressure.add(textT1Pressure18 = (TextView)findViewById(R.id.TV_T1_PRE_18));
		arrayT1Pressure.add(textT1Pressure21 = (TextView)findViewById(R.id.TV_T1_PRE_21));
		
		stationName = (TextView)findViewById(R.id.TV_STATION_NAME);
		
		//CONFIG BANNER
        adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
        	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        	.addTestDevice("1F1CEBDC284CA66C3E7154C4E146F6D3")
        	.build();
        try{
        	adView.loadAd(adRequest);
        } catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
        
        //CONFIG BUTTON
        try{
	        button_loadStation = (Button) findViewById(R.id.button1) ;
	        button_loadStation.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Intent bIntent;
	            	bIntent = new Intent(mContext, SelectionScreen.class);
	                startActivityForResult(bIntent, B_REQUEST);
	            }
	        });
        } catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
        
		dialogoEspera = ProgressDialog.show(this, "Loading", "please wait..");
		
		//GET LAST STATION SETTED BY THE USER
		db_helper = new DataBaseHelper(this);
		try {
			db_helper.createDataBase();
			db_helper.openDataBaseWritable();
			selectedStation = db_helper.getLastStation();
			//db_helper.close();
		} catch (SQLException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
		
		ATCargaDatos = new AsyncTaskCargaDatos(this);
		try{
			ATCargaDatos.execute();
        } catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
    }
    
    /** EXECUTED AFTER THE SELECTION SCREEN */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == B_REQUEST && resultCode == Activity.RESULT_OK) {
        	try{
        		dialogoEspera = ProgressDialog.show(this, "Loading", "please wait..");
        		Bundle extras = data.getExtras();
            	String datas= extras.getString("EXTRA_ID");
            	Log.d(TAG, "Value getted from onActivityResult, StationID = "+datas);
            	selectedStation.setId(datas);
            	ATCargaDatos = new AsyncTaskCargaDatos(this);
            	ATCargaDatos.execute();
        	} catch (Exception e) {
    			Log.e(TAG, "Exception", e);
    		}
        }
    }
    
    @Override
    public void onStart() {
      super.onStart();
      try{
    	  Log.d(TAG, "onStart");
    	  //EasyTracker.getInstance(this).activityStart(this);
      } catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
    }
    
    @Override
    public void onStop() {
      super.onStop();
      try{
    	  Log.d(TAG, "onStop");
    	  //EasyTracker.getInstance(this).activityStop(this);
      } catch (Exception e) {
			Log.e(TAG, "Exception", e);
	  }
    }
    
    @Override
    public void onDestroy() {
      super.onDestroy();
      try{
    	  Log.d(TAG, "onDestroy");
    	  db_helper.updateLastStation(selectedStation);
      } catch (SQLException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
      } catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}
    }
    
    public class AsyncTaskCargaDatos extends AsyncTask<Void, Void, Void> {
    	
    	private PuertosSaxParser saxparser;
    	private clsStation Station;
        Context mContext;
        
        AsyncTaskCargaDatos(Context context) {
            mContext = context;
        }
        
    	/** Reads the XML data and executes the parser in background */
    	@Override
    	protected Void doInBackground(Void... params) {
    		Log.d(TAG, "Trying to load data from feed");
    		try {
                //saxparser = new PuertosSaxParser("http://www.windfinder.com/wind-cgi/xmlforecast.pl?CUSTOMER=igoogle&STATIONS="+selectedStation.getId());
                saxparser = new PuertosSaxParser("http://frandroid.hostei.com/example.xml");
    			Station = saxparser.parseXML();
    			while (Station == null) {
    				Station = saxparser.parseXML();
    			}
    		} catch (Exception e) {
    			Log.e(TAG, "Exception", e);
    		}
    		Log.d(TAG, "Data loaded sucessfully from feed for " + Station.getName());
            selectedStation = new clsStation();
			selectedStation.setName(Station.getName());
			selectedStation.setId(Station.getId());
    		return null;
    	}
    	
    	/** Invoked on the UI thread after the background computation finishes
    	 * assign the Ports data to the TextViews in the layout */
    	@Override
    	protected void onPostExecute(Void result) {
    		Log.d(TAG, "Trying to assign data in the layout");
    		try {
    			stationName.setText(Station.getName());
    			for(int i=0; i<8; i++){
    				Log.d(TAG, "Assigning values for time " + Station.getForecast().get(i).getTime());
    				arrayT1Velocidad.get(i).setText(Station.getForecast().get(i).getWind_speed());
    				arrayT1OlasAltura.get(i).setText(Station.getForecast().get(i).getWave_height());
    				arrayT1Temperatura.get(i).setText(dfT.format(Station.getForecast().get(i).getAir_temperature())+"°");
    				arrayT1Pressure.get(i).setText(dfP.format(Station.getForecast().get(i).getAir_pressure())+"hPa");
    				arrayT1Precipitation.get(i).setText(Station.getForecast().get(i).getPrecipitation()+"mm");
    			}
    			decodeWindDirection(Station, arrayT1Direccion);
    			decodeWaveDirection(Station, arrayT1OlasDireccion);
    			decodeWeather(Station, arrayT1Weather);
    			dialogoEspera.dismiss();
    		} catch (Exception e) {
    			Log.e(TAG, "Exception", e);
    		}
    	}
    }
    
    /** DECODES THE WIND DIRECTION TO ASSIGN A PICTURE */
    private void decodeWindDirection (clsStation Station, ArrayList<ImageView> arrayWindDireccion){
    	for(int i=0; i<8; i++){
			Log.d(TAG, "Decoding wind direction for time " + Station.getForecast().get(i).getTime());
			if (Station.getForecast().get(i).getWind_direction().equals("ESE")){
    			arrayWindDireccion.get(i).setImageResource(R.drawable.wd_ese);
			}else if (Station.getForecast().get(i).getWind_direction().equals("ENE")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_ene);
			}else if(Station.getForecast().get(i).getWind_direction().equals("WSW")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_wsw);
			}else if (Station.getForecast().get(i).getWind_direction().equals("WNW")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_wnw);
			}else if (Station.getForecast().get(i).getWind_direction().equals("SSW")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_ssw);
			}else if (Station.getForecast().get(i).getWind_direction().equals("SSE")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_sse);
			}else if (Station.getForecast().get(i).getWind_direction().equals("NNW")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_nnw);
			}else if (Station.getForecast().get(i).getWind_direction().equals("NNE")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_nne);
			}else if (Station.getForecast().get(i).getWind_direction().equals("SW")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_sw);
			}else if (Station.getForecast().get(i).getWind_direction().equals("SE")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_se);
			}else if (Station.getForecast().get(i).getWind_direction().equals("NW")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_nw);
			}else if (Station.getForecast().get(i).getWind_direction().equals("NE")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_ne);
			}else if (Station.getForecast().get(i).getWind_direction().equals("S")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_s);
			}else if (Station.getForecast().get(i).getWind_direction().equals("W")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_w);
			}else if (Station.getForecast().get(i).getWind_direction().equals("E")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_e);
			}else if (Station.getForecast().get(i).getWind_direction().equals("N")){
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_n);
			}else{
				arrayWindDireccion.get(i).setImageResource(R.drawable.wd_var);
			}
		}
    }
    
    /** DECODES THE WAVE DIRECTION TO ASSIGN A PICTURE */
    private void decodeWaveDirection (clsStation Station, ArrayList<ImageView> arrayWavesDirection){
    	for(int i=0; i<8; i++){
    		Log.d(TAG, "Decoding wave direction for time " + Station.getForecast().get(i).getTime());
    		if (Station.getForecast().get(i).getWave_direction().equals("ESE")){
    			arrayWavesDirection.get(i).setImageResource(R.drawable.wd_ese);
			}else if (Station.getForecast().get(i).getWave_direction().equals("ENE")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_ene);
			}else if(Station.getForecast().get(i).getWave_direction().equals("WSW")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_wsw);
			}else if (Station.getForecast().get(i).getWave_direction().equals("WNW")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_wnw);
			}else if (Station.getForecast().get(i).getWave_direction().equals("SSW")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_ssw);
			}else if (Station.getForecast().get(i).getWave_direction().equals("SSE")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_sse);
			}else if (Station.getForecast().get(i).getWave_direction().equals("NNW")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_nnw);
			}else if (Station.getForecast().get(i).getWave_direction().equals("NNE")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_nne);
			}else if (Station.getForecast().get(i).getWave_direction().equals("SW")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_sw);
			}else if (Station.getForecast().get(i).getWave_direction().equals("SE")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_se);
			}else if (Station.getForecast().get(i).getWave_direction().equals("NW")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_nw);
			}else if (Station.getForecast().get(i).getWave_direction().equals("NE")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_ne);
			}else if (Station.getForecast().get(i).getWave_direction().equals("S")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_s);
			}else if (Station.getForecast().get(i).getWave_direction().equals("W")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_w);
			}else if (Station.getForecast().get(i).getWave_direction().equals("E")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_e);
			}else if (Station.getForecast().get(i).getWave_direction().equals("N")){
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_n);
			}else{
				arrayWavesDirection.get(i).setImageResource(R.drawable.wd_var);
			}
    	}
    }

    /** DECODES THE WEATHER TO ASSIGN A PICTURE */
    private void decodeWeather (clsStation Station, ArrayList<ImageView> arrayWeather){
    	int value;
    	for(int i=0; i<8; i++){
    		value = Integer.parseInt(Station.getForecast().get(i).getTime());
    		Log.d(TAG, "Decoding weather for time " + value);
    		if (Station.getForecast().get(i).getClouds().equals("BKN")){
    			if (value >= DAY && value < NIGHT){
    				arrayWeather.get(i).setImageResource(R.drawable.d_bkn);
    			}else{
    				arrayWeather.get(i).setImageResource(R.drawable.n_bkn);
    			}
			}else if (Station.getForecast().get(i).getClouds().equals("FEW")){
				if (value >= DAY && value < NIGHT){
    				arrayWeather.get(i).setImageResource(R.drawable.d_few);
    			}else{
    				arrayWeather.get(i).setImageResource(R.drawable.n_few);
    			}
			}else if (Station.getForecast().get(i).getClouds().equals("SKC")){
				if (value >= DAY && value < NIGHT){
    				arrayWeather.get(i).setImageResource(R.drawable.d_skc);
    			}else{
    				arrayWeather.get(i).setImageResource(R.drawable.n_skc);
    			}
			}else if(Station.getForecast().get(i).getClouds().equals("OVC")){
				if (value >= DAY && value < NIGHT){
    				arrayWeather.get(i).setImageResource(R.drawable.d_ovc);
    			}else{
    				arrayWeather.get(i).setImageResource(R.drawable.n_ovc);
    			}
			}else if(Station.getForecast().get(i).getClouds().equals("SCT")){
				if (value >= DAY && value < NIGHT){
    				arrayWeather.get(i).setImageResource(R.drawable.d_sct);
    			}else{
    				arrayWeather.get(i).setImageResource(R.drawable.n_sct);
    			}
			}else{
				arrayWeather.get(i).setImageResource(R.drawable.obs);
			}
    	}
    }
    
}
