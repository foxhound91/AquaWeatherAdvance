package foxsoft.aquaweatheradvance.parser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import foxsoft.aquaweatheradvance.custom.clsForecast;
import foxsoft.aquaweatheradvance.custom.clsStation;

public class handPuertos extends DefaultHandler{
	
	private clsStation Station = null;
	private clsForecast Forecast = null;
	private ArrayList<clsForecast> Forecasts;
	
	// In this variable we store the tags data
	private StringBuilder sbItem;

	/** Method returns an Station with loaded data */
	public clsStation getStation() {
		Station.setForecast(Forecasts);
		return Station;
	}
	
	/** Receive notification of the beginning of the document */
	public void startDocument() throws SAXException {
		super.startDocument();
		Forecasts = new ArrayList<clsForecast>();
		sbItem = new StringBuilder();
	}
	
	/** Receive notification of the beginning of an element */
	public void startElement(String uri, String localName, String name, org.xml.sax.Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (localName.equals("station")) {
			Station = new clsStation();
			Station.setId(attributes.getValue("id"));
			Station.setName(attributes.getValue("name"));
			Station.setTimezone(attributes.getValue("timezone"));
		}
		if (localName.equals("forecast")) {
			Forecast = new clsForecast();
			Forecast.setTime(attributes.getValue("time"));
		}
	}
	
	/** Receive notification of character data 
	 * The Parser will call this method to report each chunk of character data
	 **/
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		//if (this.Station != null)
			//sbItem.append(ch, start, length);
		if (this.Forecast != null)
			sbItem.append(ch, start, length);
			//sbItem.append(new String(ch, start, length));
	}
		 
	/** Receive notification of the end of an element 
	 * Assign the data from each tag to a Forecast object
	 * When the element finish we add the Forecast object to an ArrayList
	 * */
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);
		if (this.Forecast != null) {
			switch (localName) {
				case "air_temperature":
					//Forecast.setAir_temperature(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					Forecast.setAir_temperature(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "water_temperature":
					Forecast.setWater_temperature(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "wind_direction":
					Forecast.setWind_direction(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "wind_speed":
					Forecast.setWind_speed(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "wind_gusts":
					Forecast.setWind_gusts(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "weather":
					Forecast.setWeather(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "clouds":
					Forecast.setClouds(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "precipitation":
					Forecast.setPrecipitation(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "precipitation_type":
					Forecast.setPrecipitation_type(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					//Forecast.setPrecipitation_type(attributes.getValue("unit"));
					break;
				case "wave_height":
					Forecast.setWave_height(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "wave_direction":
					Forecast.setWave_direction(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "wave_period":
					Forecast.setWave_period(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "air_pressure":
					Forecast.setAir_pressure(sbItem.toString().replaceAll("\\n\\t\\t", ""));
					break;
				case "forecast":
					Forecasts.add(Forecast);
					break;
			}
			sbItem.setLength(0);
		}
	}
	
}