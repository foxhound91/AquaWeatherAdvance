package foxsoft.aquaweatheradvance.custom;

public class clsForecast {
	
	private String time = null;
	private Double air_temperature = null;
	private String water_temperature = null;
	private String wind_direction= null;
	private String wind_speed = null;
	private String wind_gusts = null;
	private String weather = null;
	private String clouds = null;
	private String precipitation = null;
	private String precipitation_type = null;
	private String wave_height = null;
	private String wave_direction = null;
	private String wave_period = null;
	private Double air_pressure = null;
	
	public Double getAir_temperature() {
		return air_temperature;
	}
	public void setAir_temperature(String air_temperature) {
		this.air_temperature = Double.valueOf(air_temperature);
	}
	public String getWater_temperature() {
		return water_temperature;
	}
	public void setWater_temperature(String water_temperature) {
		this.water_temperature = water_temperature;
	}
	public String getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(String wind_direction) {
		this.wind_direction = wind_direction;
	}
	public String getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(String wind_speed) {
		this.wind_speed = wind_speed;
	}
	public String getWind_gusts() {
		return wind_gusts;
	}
	public void setWind_gusts(String wind_gusts) {
		this.wind_gusts = wind_gusts;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getClouds() {
		return clouds;
	}
	public void setClouds(String clouds) {
		this.clouds = clouds;
	}
	public String getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}
	public String getPrecipitation_type() {
		return precipitation_type;
	}
	public void setPrecipitation_type(String precipitation_type) {
		this.precipitation_type = precipitation_type;
	}
	public String getWave_height() {
		return wave_height;
	}
	public void setWave_height(String wave_height) {
		this.wave_height = wave_height;
	}
	public String getWave_direction() {
		return wave_direction;
	}
	public void setWave_direction(String wave_direction) {
		this.wave_direction = wave_direction;
	}
	public String getWave_period() {
		return wave_period;
	}
	public void setWave_period(String wave_period) {
		this.wave_period = wave_period;
	}
	public Double getAir_pressure() {
		return air_pressure;
	}
	public void setAir_pressure(String air_pressure) {
		this.air_pressure = Double.valueOf(air_pressure);
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
