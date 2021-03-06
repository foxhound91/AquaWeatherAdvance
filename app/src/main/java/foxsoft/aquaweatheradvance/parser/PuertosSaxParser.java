package foxsoft.aquaweatheradvance.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import foxsoft.aquaweatheradvance.custom.Station;

public class PuertosSaxParser {
	
	private URL rssUrl;
	
	/** Constructor where the XML's url is assigned */
	public PuertosSaxParser(String url) {
		try {
			this.rssUrl = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/** Instance the class PortsHandler to read the XML data */
	public Station parseXML() throws IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			 SAXParser parser = factory.newSAXParser();
			 PortsHandler handler = new PortsHandler();
			 parser.parse(this.getInputStream(), handler);
			 return handler.getStation();
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/** Gets the XML data doing an stream via HTTP */
	private InputStream getInputStream() throws IOException{
		return rssUrl.openConnection().getInputStream();
	}
	
}