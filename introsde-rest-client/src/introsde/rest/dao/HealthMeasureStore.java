package introsde.rest.dao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.model.HealthMeasureHistory;

@XmlRootElement(name="healthMeasureHistories")
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthMeasureStore {

	@XmlElement(name="measure")
	private List<HealthMeasureHistory> data = new ArrayList<HealthMeasureHistory>();
	
	public HealthMeasureStore () {
	}

	public List<HealthMeasureHistory> getData() {
		return data;
	}

	public void setData(List<HealthMeasureHistory> data) {
		this.data = data;
	}
}

