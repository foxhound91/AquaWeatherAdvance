package foxsoft.aquaweatheradvance.custom;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper {

    private final String TAG = this.getClass().toString();

	//The Android's default system path of your application database.
    private static final String DB_PATH = "/data/data/com.softxpress.aquaweather/databases/";
 
    private static final String DB_NAME = "ar_ur.sqlite";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
    	//super(context, DB_NAME, null, 1);
        this.myContext = context;
		//DB_PATH = myContext.getFilesDir().getPath();
		System.out.println("DEBUG - DIR: " + myContext.getFilesDir().getPath());
    }	
 
  /** Creates a empty database on the system and rewrites it with your own database */
    public void createDataBase() throws IOException {
    	if(!checkDataBase()) copyDataBase();
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS); //FIXME Failed to open database '/data/data/com.softxpress.aquaweather/databases/america.sqlite'.
    	}catch(SQLiteCantOpenDatabaseException e){
            Log.w(TAG, "DB doesn't exist");
        }
    	if(checkDB != null){
    		checkDB.close();
            return true;
    	} else {
            return false;
        }
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * @throws IOException 
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    public void openDataBase() {
        String myPath = DB_PATH + DB_NAME;
        try{
        	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(Exception e){
    		throw e;
    	}
    }
    
    public void openDataBaseWritable() {
        String myPath = DB_PATH + DB_NAME;
        try{
        	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(Exception e){
    		throw e;
    	}
    }
    
    public ArrayList<clsStation> getStations(String regionID) throws Exception{
    	ArrayList<clsStation> stations = new ArrayList<>();
    	clsStation station;
    	String selectQuery = "SELECT ID, NAME, REGION_ID FROM STATIONS WHERE REGION_ID = '"+regionID+"'";
    	try{
    		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
        	if (cursor.moveToFirst()) {
        		do {
        			station = new clsStation();
        			station.setId(cursor.getString(0));
        			station.setName(cursor.getString(1));
        			station.setRegion_Id(cursor.getString(2));
        			stations.add(station);
        		} while (cursor.moveToNext());
        	}
    	}catch(Exception e){
    		throw new Exception("SQL ERROR getStations");
    		//throw e;
    	}
    	return stations;
    }
    
    public ArrayList<clsRegion> getRegions(String countryID) throws Exception{
    	ArrayList<clsRegion> regions = new ArrayList<>();
    	clsRegion region;
    	String selectQuery = "SELECT REGION_ID, ID, NAME, COUNTRY_ID FROM REGIONS WHERE COUNTRY_ID = '"+countryID+"'";
    	try{
    		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
        	if (cursor.moveToFirst()) {
        		do {
        			region = new clsRegion();
        			region.setRegion_Id(cursor.getString(0));
        			region.setId(cursor.getString(1));
        			region.setName(cursor.getString(2));
        			region.setCountry_Id(cursor.getString(3));
        			regions.add(region);
        		} while (cursor.moveToNext());
        	}
    	}catch(Exception e){
    		throw new Exception("SQL ERROR getRegions");
    		//throw e;
    	}
    	return regions;
    }
    
    public ArrayList<clsCountry> getCountries(String continentID) throws Exception{
    	ArrayList<clsCountry> countries = new ArrayList<>();
    	clsCountry country;
    	String selectQuery = "SELECT COUNTRY_ID, ID, NAME, CONTINENT_ID FROM COUNTRIES WHERE CONTINENT_ID = '"+continentID+"'";
    	try{
    		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
        	if (cursor.moveToFirst()) {
        		do {
        			country = new clsCountry();
        			country.setCountry_Id(cursor.getString(0));
        			country.setId(cursor.getString(1));
        			country.setName(cursor.getString(2));
        			country.setContinent_Id(cursor.getString(3));
        			countries.add(country);
        		} while (cursor.moveToNext());
        	}
    	}catch(Exception e){
    		throw new Exception("SQL ERROR getCountries");
    		//throw e;
    	}
    	return countries;
    }
    
    public ArrayList<clsContinent> getContients() throws Exception{
    	ArrayList<clsContinent> continents = new ArrayList<>();
    	clsContinent continent;
    	String selectQuery = "SELECT CONTINENT_ID, ID, NAME FROM CONTINENTS";
    	try{
    		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
        	if (cursor.moveToFirst()) {
        		do {
        			continent = new clsContinent();
        			continent.setContinent_Id(cursor.getString(0));
        			continent.setId(cursor.getString(1));
        			continent.setName(cursor.getString(2));
        			continents.add(continent);
        		} while (cursor.moveToNext());
        	}
    	}catch(Exception e){
    		throw new Exception("SQL ERROR getContients");
    		//throw e;
    	}
    	return continents;
    }
    
    public clsStation getLastStation() throws Exception{
    	clsStation station = null;
    	String selectQuery = "SELECT ID, NAME, REGION_ID FROM LAST_STATION";
    	try{
    		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
        	if (cursor.moveToFirst()) {
        		do {
        			station = new clsStation();
        			station.setId(cursor.getString(0));
        			station.setName(cursor.getString(1));
        			station.setRegion_Id(cursor.getString(2));
        		} while (cursor.moveToNext());
        	}
    	}catch(Exception e){
    		throw new Exception("SQL ERROR getStation");
    		//throw e;
    	}
    	return station;
    }
    
    public void updateLastStation(clsStation station) throws SQLException{
    	String updateQuery = "UPDATE LAST_STATION SET ID = '"+station.getId()
    											+"', NAME = '"+station.getName()
    											+"', REGION_ID = '"+station.getRegion_Id()
    											+"' WHERE ROWID = 1";
    	myDataBase.execSQL(updateQuery);
    }
    
    /*
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	*/
}