package introsde.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="measureTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name="id")
	private int idMeasureDef;
	@XmlElement(name="measure")
	private String measureName;
	@XmlTransient
	private String measureType;

	public MeasureDefinition() {
	}
	
	public MeasureDefinition(int idMeasureDef) {		
		this.setIdMeasureDef(idMeasureDef);
	}

	public int getIdMeasureDef() {
		return this.idMeasureDef;
	}

	public void setIdMeasureDef(int idMeasureDef) {
		this.idMeasureDef = idMeasureDef;
	}

	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
}
