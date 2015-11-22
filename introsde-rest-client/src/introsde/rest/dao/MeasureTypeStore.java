package introsde.rest.dao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.model.MeasureDefinition;

@XmlRootElement(name="measureDefinitions")
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureTypeStore {

	@XmlElement(name="measureDefinition")
	private List<MeasureDefinition> data = new ArrayList<MeasureDefinition>();
	
	public MeasureTypeStore () {
	}

	public List<MeasureDefinition> getData() {
		return data;
	}

	public void setData(List<MeasureDefinition> data) {
		this.data = data;
	}
}
