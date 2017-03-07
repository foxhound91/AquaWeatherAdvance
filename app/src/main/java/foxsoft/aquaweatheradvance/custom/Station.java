package foxsoft.aquaweatheradvance.custom;

import java.util.ArrayList;


public class Station extends Location {
	
	private String region_Id;
	private String timezone;
	private ArrayList<foxsoft.aquaweatheradvance.custom.Forecast> Forecast;
	
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public ArrayList<foxsoft.aquaweatheradvance.custom.Forecast> getForecast() {
		return Forecast;
	}
	public void setForecast(ArrayList<foxsoft.aquaweatheradvance.custom.Forecast> forecast) {
		Forecast = forecast;
	}
	public String getRegion_Id() {
		return region_Id;
	}
	public void setRegion_Id(String region_Id) {
		this.region_Id = region_Id;
	}
	
}
