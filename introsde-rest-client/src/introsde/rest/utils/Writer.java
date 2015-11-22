package introsde.rest.utils;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import introsde.rest.dao.HealthMeasureStore;
import introsde.rest.dao.MeasureTypeStore;
import introsde.rest.dao.PeopleStore;
import introsde.rest.model.HealthMeasureHistory;
import introsde.rest.model.LifeStatus;
import introsde.rest.model.MeasureDefinition;
import introsde.rest.model.Person;

public class Writer {

	public PeopleStore unmarshallXMLPeopleList(InputStream stream){		
		PeopleStore people = null;
		try{
			JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);    			
			//Un-marshalling from xml String to PeopleStore
    		Unmarshaller um = jc.createUnmarshaller();
    		people = (PeopleStore) um.unmarshal(stream);    		
		}
		catch(Exception e){e.printStackTrace();}		
		return people;		
	}
	
	public Person unmarshallXMLPerson(InputStream stream){		
		Person people = null;
		try{
			JAXBContext jc = JAXBContext.newInstance(Person.class);    			
			//Un-marshalling from xml String to Person
    		Unmarshaller um = jc.createUnmarshaller();
    		people = (Person) um.unmarshal(stream);
		}
		catch(Exception e){e.printStackTrace();}		
		return people;		
	}
	
	public MeasureTypeStore unmarshallXMLMeasureDef(InputStream stream){		
		MeasureTypeStore measureType = null;
		try{
			JAXBContext jc = JAXBContext.newInstance(MeasureTypeStore.class);    			
			//Un-marshalling from xml String to MeasureTypeStore
    		Unmarshaller um = jc.createUnmarshaller();
    		measureType = (MeasureTypeStore) um.unmarshal(stream);
		}
		catch(Exception e){e.printStackTrace();}		
		return measureType;		
	}
	
	public HealthMeasureStore unmarshallXMLHealthMeasureStore(InputStream stream){		
		HealthMeasureStore healthMeasure = null;
		try{
			JAXBContext jc = JAXBContext.newInstance(HealthMeasureStore.class);    			
			//Un-marshalling from xml String to HealthMeasureStore
    		Unmarshaller um = jc.createUnmarshaller();
    		healthMeasure = (HealthMeasureStore) um.unmarshal(stream);
		}
		catch(Exception e){e.printStackTrace();}		
		return healthMeasure;		
	}
	
	public HealthMeasureHistory unmarshallXMLHealthMeasureHistory(InputStream stream){		
		HealthMeasureHistory healthMeasure = null;
		try{
			JAXBContext jc = JAXBContext.newInstance(HealthMeasureStore.class);    			
			//Un-marshalling from xml String to HealthMeasureStore
    		Unmarshaller um = jc.createUnmarshaller();
    		healthMeasure = (HealthMeasureHistory) um.unmarshal(stream);
		}
		catch(Exception e){e.printStackTrace();}		
		return healthMeasure;		
	}
	
	public LifeStatus unmarshallXMLLifeStatus(InputStream stream){		
		LifeStatus lifeStatus = null;
		try{
			JAXBContext jc = JAXBContext.newInstance(LifeStatus.class);    			
			//Un-marshalling from xml String to LifeStatus
    		Unmarshaller um = jc.createUnmarshaller();
    		lifeStatus = (LifeStatus) um.unmarshal(stream);
		}
		catch(Exception e){e.printStackTrace();}		
		return lifeStatus;		
	}
	
	public void marshallXML(Object obj, Class className){		
		try{
			JAXBContext jc = JAXBContext.newInstance(className);   			
			//Marshalling object
    		Marshaller m = jc.createMarshaller();
    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    		m.marshal(obj, System.out); //marshalling into the System default output
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public Person[] unmarshallJSON(InputStream stream){		
		Person[] person = null;		
		try{			
	        // Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			person = mapper.readValue(stream, Person[].class);			
		}
		catch(Exception e){e.printStackTrace();}		
		return person;
	}
	
	public Person unmarshallJSONPerson(InputStream stream){		
		Person person = null;		
		try{			
	        // Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			person = mapper.readValue(stream, Person.class);			
		}
		catch(Exception e){e.printStackTrace();}		
		return person;
	}
	
	public MeasureDefinition[] unmarshallJSONMeasureType(InputStream stream){		
		MeasureDefinition[] measure = null;		
		try{			
	        // Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			measure = mapper.readValue(stream, MeasureDefinition[].class);			
		}
		catch(Exception e){e.printStackTrace();}		
		return measure;
	}
	
	public HealthMeasureHistory[] unmarshallJSONHealthMeasureList(InputStream stream){		
		HealthMeasureHistory[] measure = null;		
		try{			
	        // Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			measure = mapper.readValue(stream, HealthMeasureHistory[].class);			
		}
		catch(Exception e){e.printStackTrace();}		
		return measure;
	}
	
	public HealthMeasureHistory unmarshallJSONHealthMeasure(InputStream stream){		
		HealthMeasureHistory measure = null;		
		try{			
	        // Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			measure = mapper.readValue(stream, HealthMeasureHistory.class);			
		}
		catch(Exception e){e.printStackTrace();}		
		return measure;
	}
	
	public LifeStatus unmarshallJSONLifeStatus(InputStream stream){		
		LifeStatus lifestatus = null;		
		try{			
	        // Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			lifestatus = mapper.readValue(stream, LifeStatus.class);			
		}
		catch(Exception e){e.printStackTrace();}		
		return lifestatus;
	}
	
	public void marshallJSON(Person[] object){	
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			String result = mapper.writeValueAsString(object);
			System.out.println(result); 						//marshalling into the System default output
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void marshallJSON(Person object){		
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			String result = mapper.writeValueAsString(object);
			System.out.println(result); 						//marshalling into the System default output			
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void marshallJSON(MeasureDefinition[] object){		
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			String result = mapper.writeValueAsString(object);
			System.out.println(result); 						//marshalling into the System default output			
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void marshallJSON(HealthMeasureHistory[] object){		
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			String result = mapper.writeValueAsString(object);
			System.out.println(result); 						//marshalling into the System default output			
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void marshallJSON(HealthMeasureHistory object){		
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			String result = mapper.writeValueAsString(object);
			System.out.println(result); 						//marshalling into the System default output			
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void marshallJSON(LifeStatus object){		
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);			
			String result = mapper.writeValueAsString(object);
			System.out.println(result); 						//marshalling into the System default output			
		}
		catch(Exception e){e.printStackTrace();}
	}
}
