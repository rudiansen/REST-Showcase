package introsde.rest.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import introsde.rest.utils.DateAdapter;

@XmlRootElement(name="measure")
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="mid")
	private int idMeasureHistory;
	@XmlElement(name="value")
	private String value;
	@XmlElement(name="created")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date timestamp;
	@XmlTransient
	private MeasureDefinition measureDefinition;
	@XmlTransient
	private Person person;

	public HealthMeasureHistory() {
	}

	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}

	public String getTimestamp() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(timestamp);
	}

	public void setTimestamp(String timestamp) throws ParseException{
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    	Date date = format.parse(timestamp);
        this.timestamp = date;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MeasureDefinition getMeasureDefinition() {
	    return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
	    this.measureDefinition = param;
	}

	public Person getPerson() {
	    return person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}
}
