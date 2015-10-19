package skeleton.sensor;

import ch.ethz.inf.vs.vs_fabianu_webservices.HtmlSensor;
import ch.ethz.inf.vs.vs_fabianu_webservices.JsonSensor;
import ch.ethz.inf.vs.vs_fabianu_webservices.RawHttpSensor;
import ch.ethz.inf.vs.vs_fabianu_webservices.XmlSensor;

public abstract class SensorFactory {
	public static Sensor getInstance(Type type) {
		switch (type) {
		case RAW_HTTP:
			// return Sensor implementation using a raw HTTP request
			return new RawHttpSensor();
		case HTML:
			// return Sensor implementation using text/html representation
			return new HtmlSensor();
		case JSON:
			// return Sensor implementation using application/json representation
			return new JsonSensor();
		//TODO
		case XML:
			// return Sensor implementation using application/xml representation
			return new XmlSensor();
		/*case SOAP:
			// return Sensor implementation using a SOAPObject
			return new SoapSensor();*/
		default:
			return null;
		}
	}
	
	public enum Type {
		RAW_HTTP, HTML, JSON, XML, SOAP;
	}
}
