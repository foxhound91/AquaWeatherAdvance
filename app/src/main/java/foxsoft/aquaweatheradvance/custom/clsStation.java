package foxsoft.aquaweatheradvance.custom;

import java.util.ArrayList;


public class clsStation extends clsLocation {
	
	private String region_Id;
	private String timezone;
	private ArrayList<clsForecast> Forecast;
	
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public ArrayList<clsForecast> getForecast() {
		return Forecast;
	}
	public void setForecast(ArrayList<clsForecast> forecast) {
		Forecast = forecast;
	}
	public String getRegion_Id() {
		return region_Id;
	}
	public void setRegion_Id(String region_Id) {
		this.region_Id = region_Id;
	}
	
}
