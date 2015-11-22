package introsde.rest.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import introsde.rest.dao.PeopleStore;
import introsde.rest.dao.HealthMeasureStore;
import introsde.rest.dao.MeasureTypeStore;
import introsde.rest.model.HealthMeasureHistory;
import introsde.rest.model.LifeStatus;
import introsde.rest.model.MeasureDefinition;
import introsde.rest.model.Person;
import introsde.rest.utils.Writer;

public class ClientApp {

	static InputStream stream = null;
	static PeopleStore people = null;
	static MeasureTypeStore measureType = null;
	static Person person = null;	
	static Person[] personList = null;
	static MeasureDefinition[] measureTypeList = null;
	static HealthMeasureHistory[] healthMeasureList = null;
	static HealthMeasureStore healthMeasure = null;
	static HealthMeasureHistory healthMeasureHsitory = null;
	static LifeStatus lifestatus = null;
	static Response response;
			
	static int httpStatus = 0;
	static int first_person_id = 0;
	static int last_person_id = 0;
	static String results = null;
	static String resultStatus = null;
	static String[] measure_types = null;
	
	public static void main(String[] args) throws IOException, Exception {
		
		final String SERVER_URL = "http://localhost:5700/sdelab";				
			
		try{
			ClientConfig clientConfig = new ClientConfig();	
			
			Client client = ClientBuilder.newClient(clientConfig);
			WebTarget service = client.target(getBaseURI(SERVER_URL));
			
			Client jsonClient = ClientBuilder.newClient(clientConfig)
					.register(JacksonFeature.class);
							
			WebTarget jsonService = jsonClient.target(getBaseURI(SERVER_URL));
			Writer writer = new Writer();
			ClientApp clientApp = new ClientApp();
			
			//Print the RESTful server URL
			System.out.println("Server URL ==> " + SERVER_URL + "\n");
			
			/*
			 * Begin all executions using APPLICATION_XML
			 * 
			 * R#1 (GET BASE_URL/person)
			 */
//			//Get list all the people
//			response = service.path("person").request().accept(MediaType.APPLICATION_XML).get();
//			results = response.readEntity(String.class);
//			httpStatus = response.getStatus();
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall people xml String into people object
//			people = writer.unmarshallXMLPeopleList(stream);
//			//Check if the people is more than 2
//			if(people.getData().size() > 2){
//				resultStatus = "OK";
//				
//				//Get idPerson for the first and the last person
//				List<Person> p = people.getData();
//				first_person_id = p.get(0).getIdPerson();
//				last_person_id = p.get(p.size()-1).getIdPerson();
//			}
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(1, "GET", "/person", "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall people object into the system default output
//			writer.marshallXML(people, PeopleStore.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			/*
//			 * R#2 (GET BASE_URL/person/first_person_id)
//			 */
//			response = service.path("person/"+first_person_id).request().accept(MediaType.APPLICATION_XML).get();
//			results = response.readEntity(String.class);
//			httpStatus = response.getStatus();
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall person xml String into person object
//			person = writer.unmarshallXMLPerson(stream);
//			//Check if the person is not null
//			if(person != null)
//				resultStatus = "OK";		
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(2, "GET", "/person/"+first_person_id, "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall person object into the system default output
//			writer.marshallXML(person, Person.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			/*
//			 * R#3 (PUT BASE_URL/person/first_person_id)
//			 */		
//			//Change the first name of the first person to Matheo
//			person.setName("Matheo");
//			
//			//Update the person with the new first name
//			response = service.path("person/"+first_person_id).request(MediaType.APPLICATION_XML)
//						.put(Entity.entity(person, MediaType.APPLICATION_XML), Response.class);
//			httpStatus = response.getStatus();
//			
//			//Get the updated person
//			results = service.path("person/"+first_person_id).request().accept(MediaType.APPLICATION_XML).get()
//						.readEntity(String.class);
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall person xml String into person object
//			person = writer.unmarshallXMLPerson(stream);
//			//Check if the first name of the person has been changed to Matheo
//			if(person.getName().equalsIgnoreCase("Matheo"))
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(3, "PUT", "/person/"+first_person_id, "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall person object into the system default output
//			writer.marshallXML(person, Person.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			/*
//			 * R#4 (POST BASE_URL/person)
//			 */	
//			//Create a new person object with id {newIdPerson}
//			int newIdPerson = 500;
//			
//			LifeStatus weight = new LifeStatus();
//			weight.setMeasureDefinition(new MeasureDefinition(1)); 
//			weight.setValue("78.9");			
//			
//			LifeStatus height = new LifeStatus();
//			height.setMeasureDefinition(new MeasureDefinition(2));
//			height.setValue("172");
//			
//			List<LifeStatus> lifeStatusList = new ArrayList<LifeStatus>();
//			lifeStatusList.add(weight);
//			lifeStatusList.add(height);
//			
//			Person newPerson = new Person(lifeStatusList);
//			newPerson.setIdPerson(newIdPerson);
//			newPerson.setName("Chuck");
//			newPerson.setLastname("Norris");
//			newPerson.setBirthdate("1945-01-01");
//					
//			//POST a new person 
//			response = service.path("person").request(MediaType.APPLICATION_XML)
//					.post(Entity.entity(newPerson, MediaType.APPLICATION_XML), Response.class);
//			httpStatus = response.getStatus();
//			results = response.readEntity(String.class);
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall person xml String into person object
//			person = writer.unmarshallXMLPerson(stream);
//			//Check if the first name of the people has been changed to Matheo
//			if(person.getIdPerson() == newIdPerson)
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(4, "POST", "/person", "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall person object into the system default output
//			writer.marshallXML(person, Person.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			/*
//			 * R#5 (DELETE BASE_URL/person/newIdPerson)
//			 */	
//			//Delete the person which has just created with id: newIdPerson
//			service.path("person/"+newIdPerson).request().delete();		
//			
//			//Get the person with id: newIdPerson
//			response = service.path("person/"+newIdPerson).request().accept(MediaType.APPLICATION_XML).get();
//			httpStatus = response.getStatus();
//			results = response.readEntity(String.class);
//			
//			if(httpStatus == 404)
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(5, "DELETE", "/person/"+newIdPerson, "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			System.out.println(results);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			/*
//			 * R#9 (GET BASE_URL/measureTypes)
//			 */
//			//Get all the measureTypes
//			response = service.path("measureTypes").request().accept(MediaType.APPLICATION_XML).get();
//			results = response.readEntity(String.class);
//			httpStatus = response.getStatus();
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall measureTypes xml String into MeasureTypeStore object
//			measureType = writer.unmarshallXMLMeasureDef(stream);
//			//Check if the people is more than 2
//			if(measureType.getData().size() > 2){
//				resultStatus = "OK";
//				
//				//Store all the measureTypes into an array
//				List<MeasureDefinition> measureTypeList = measureType.getData();
//				measure_types = new String[measureTypeList.size()]; 
//				for(int i=0; i<measureTypeList.size(); i++){
//					measure_types[i] = measureTypeList.get(i).getMeasureName();
//				}
//			}
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(9, "GET", "/measureTypes", "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall MeasureTypeStore object into the system default output
//			writer.marshallXML(measureType, MeasureTypeStore.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			/*
//			 * R#6 (GET BASE_URL/person/{id}/{measureTypes})
//			 */		
			int mid = 0;
			int storedIdPerson = 0;
			String storedMeasureType = "";
					
			int[] idPerson = new int[2];
//			idPerson[0] = first_person_id;
//			idPerson[1] = last_person_id;
//			
//			for(int h=0; h<idPerson.length; h++){ //Iterate for the first and the last person
//				for(int i=0; i<measure_types.length; i++){	//Iterate for the all measureTypes that each person has		
//					//Get the HealthMeasureHistory
//					response = service.path("person/"+idPerson[h]+"/"+measure_types[i]).request().accept(MediaType.APPLICATION_XML).get();
//					results = response.readEntity(String.class);
//					httpStatus = response.getStatus();
//					
//					//Convert string into inputStream
//					stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//					
//					//Un-marshall HealthMeasureHistory xml String into HealthMeasureStore object
//					healthMeasure = writer.unmarshallXMLHealthMeasureStore(stream);
//					if(healthMeasure.getData().size() > 0){
//						resultStatus = "OK";
//						
//						//Store one mid and the corresponding measureType into variables
//						List<HealthMeasureHistory> hMeasure = healthMeasure.getData();
//						mid = hMeasure.get(0).getIdMeasureHistory();
//						storedMeasureType = measure_types[i];
//						storedIdPerson = idPerson[h];
//					}
//					else
//						resultStatus = "ERROR";
//					
//					printHeader(6, "GET", "/person/"+idPerson[h]+"/"+measure_types[i], "APPLICATION/XML", "APPLICATION/XML");		
//					System.out.println("=> Result: "+ resultStatus);
//					System.out.println("=> HTTP Status: "+ httpStatus);
//					
//					//Marshall HealthMeasureStore object into the system default output
//					writer.marshallXML(healthMeasure, HealthMeasureStore.class);
//					System.out.println("\n");
//					clientApp.clearVariables();
//				}		
//			}	
//			
//			/*
//			 * R#7 (GET BASE_URL/person/{id}/{measureTypes}/{mid})
//			 */	
//			//Get HealthMeasureStore based on provided mid
//			response = service.path("person/"+storedIdPerson+"/"+storedMeasureType+"/"+mid).request().accept(MediaType.APPLICATION_XML).get();
//			results = response.readEntity(String.class);
//			httpStatus = response.getStatus();
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall HealthMeasureHistory xml String into HealthMeasureHistory object
//			healthMeasureHsitory = writer.unmarshallXMLHealthMeasureHistory(stream);
//			if(httpStatus == 200 || httpStatus == 201)
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(7, "GET", "/person/"+storedIdPerson+"/"+storedMeasureType+"/"+mid, "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall HealthMeasureHistory object into the system default output
//			writer.marshallXML(healthMeasureHsitory, HealthMeasureHistory.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			/*
//			 * Step 3.9
//			 */
//			//Get HealthMeasureHistory for the first person
//			response = service.path("person/"+first_person_id+"/"+measure_types[0]).request().accept(MediaType.APPLICATION_XML).get();
//			results = response.readEntity(String.class);
//			httpStatus = response.getStatus();
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall HealthMeasureHistory xml String into HealthMeasureStore object
//			healthMeasure = writer.unmarshallXMLHealthMeasureStore(stream);
//			int countHealthMeasure = healthMeasure.getData().size();
//			
//			if(httpStatus == 200 || httpStatus == 201)
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(6, "GET", "/person/"+first_person_id+"/"+measure_types[0], "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall HealthMeasureStore object into the system default output
//			writer.marshallXML(healthMeasure, HealthMeasureStore.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
//			//Create a new MeasureType for the first person
//			LifeStatus lifeStatus = new LifeStatus();
//			lifeStatus.setValue("72");
//			lifeStatus.setMeasureDefinition(new MeasureDefinition(1));
//			
//			//POST a new HealthProfile and a new archive in HealthMeasureHistory for the first person
//			response = service.path("person/"+first_person_id+"/"+measure_types[0]).request(MediaType.APPLICATION_XML)
//					.post(Entity.entity(lifeStatus, MediaType.APPLICATION_XML), Response.class);
//			httpStatus = response.getStatus();	
//			results = response.readEntity(String.class);
//				
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall LifeStatus xml String into LifeStatus object
//			lifeStatus = writer.unmarshallXMLLifeStatus(stream);
//			if(httpStatus == 200 || httpStatus == 201)
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(8, "POST", "/person/"+first_person_id+"/"+measure_types[0], "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall LifeStatus object into the system default output
//			writer.marshallXML(lifeStatus, LifeStatus.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//					
//			//Get updated HealthMeasureHistory for the first person, for the second time after POST request
//			response = service.path("person/"+first_person_id+"/"+measure_types[0]).request().accept(MediaType.APPLICATION_XML).get();
//			results = response.readEntity(String.class);
//			httpStatus = response.getStatus();
//			
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall HealthMeasureHistory xml String into HealthMeasureStore object
//			healthMeasure = writer.unmarshallXMLHealthMeasureStore(stream);
//			//Check if the HealthMeasureHistory is more than the previous one
//			if(healthMeasure.getData().size() > countHealthMeasure)
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(6, "GET", "/person/"+first_person_id+"/"+measure_types[0], "APPLICATION/XML", "APPLICATION/XML");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall HealthMeasureStore object into the system default output
//			writer.marshallXML(healthMeasure, HealthMeasureStore.class);
//			System.out.println("\n");
//			clientApp.clearVariables();
//			
			//Release all objects before the execution using APPLICATION_JSON 
			person = null;
			people = null;
			first_person_id = 0;
			last_person_id = 0;
			mid = 0;
			storedIdPerson = 0;
			storedMeasureType = "";
			measure_types = null;
						
			/*
			 * Begin all executions using APPLICATION_JSON
			 * 
			 * R#1 (GET BASE_URL/person)
			 */
			//Get list all the people
			response = service.path("person").request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			httpStatus = response.getStatus();
			
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			//Un-marshall people xml String into people object
			personList = writer.unmarshallJSON(stream);
			//Check if the people is more than 2
			if(personList.length > 2){
				resultStatus = "OK";
				
				//Get idPerson for the first and the last person				
				first_person_id = personList[0].getIdPerson();
				last_person_id = personList[personList.length - 1].getIdPerson();
			}
			else
				resultStatus = "ERROR";
			
			printHeader(1, "GET", "/person", "APPLICATION/JSON", "APPLICATION/JSON");		
			System.out.println("=> Result: "+ resultStatus);
			System.out.println("=> HTTP Status: "+ httpStatus);
			
			//Marshall people object into the system default output
			writer.marshallJSON(personList);
			System.out.println("\n");
			clientApp.clearVariables();
			
			/*
			 * R#2 (GET BASE_URL/person/first_person_id)
			 */
			response = service.path("person/"+first_person_id).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			httpStatus = response.getStatus();
			
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			//Un-marshall person xml String into person object
			person = writer.unmarshallJSONPerson(stream);
			//Check if the person is not null
			if(personList != null)
				resultStatus = "OK";		
			else
				resultStatus = "ERROR";
			
			printHeader(2, "GET", "/person/"+first_person_id, "APPLICATION/JSON", "APPLICATION/JSON");		
			System.out.println("=> Result: "+ resultStatus);
			System.out.println("=> HTTP Status: "+ httpStatus);
			
			//Marshall person object into the system default output
			writer.marshallJSON(person);
			System.out.println("\n");
			clientApp.clearVariables();
			
			/*
			 * R#3 (PUT BASE_URL/person/first_person_id)
			 */		
			//Change the first name of the first person to Frank
			person.setName("Frank");
			
			//Update the person with the new first name			
			response = jsonService.path("person/"+first_person_id).request(MediaType.APPLICATION_JSON)
						.put(Entity.entity(person, MediaType.APPLICATION_JSON), Response.class);
			httpStatus = response.getStatus();
			
			//Get the updated person
			results = service.path("person/"+first_person_id).request().accept(MediaType.APPLICATION_JSON).get()
						.readEntity(String.class);
			
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			//Un-marshall person xml String into person object
			person = writer.unmarshallJSONPerson(stream);
			//Check if the first name of the person has been changed to Frank
			if(person.getName().equalsIgnoreCase("Frank"))
				resultStatus = "OK";
			else
				resultStatus = "ERROR";
						
			printHeader(3, "PUT", "/person/"+first_person_id, "APPLICATION/JSON", "APPLICATION/JSON");		
			System.out.println("=> Result: "+ resultStatus);
			System.out.println("=> HTTP Status: "+ httpStatus);
			
			//Marshall person object into the system default output
			writer.marshallJSON(person);
			System.out.println("\n");
			clientApp.clearVariables();
			
			/*
			 * R#4 (POST BASE_URL/person)
			 */	
			//Create a new person object with id {newIdPerson}
			int newIdPerson = 560;
			
			LifeStatus weight = new LifeStatus();
			weight.setMeasureDefinition(new MeasureDefinition(1)); 
			weight.setValue("78.9");			
			
			LifeStatus height = new LifeStatus();
			height.setMeasureDefinition(new MeasureDefinition(2));
			height.setValue("172");
			
			List<LifeStatus> lifeStatusList = new ArrayList<LifeStatus>();
			lifeStatusList.add(weight);
			lifeStatusList.add(height);
			
			Person newPerson = new Person(lifeStatusList);
			newPerson.setIdPerson(newIdPerson);
			newPerson.setName("Chuck");
			newPerson.setLastname("Norris");
			newPerson.setBirthdate("1945-01-01");
					
			//POST a new person 
//			response = jsonService.path("person").request(MediaType.APPLICATION_JSON)
//					.post(Entity.entity(newPerson, MediaType.APPLICATION_JSON), Response.class);
//			httpStatus = response.getStatus();
//			results = response.readEntity(String.class);
//			System.out.println("results: "+results);
//			//Convert string into inputStream
//			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
//			
//			//Un-marshall person xml String into person object
//			person = writer.unmarshallJSONPerson(stream);
//			//Check if the first name of the people has been changed to Matheo
//			if(person.getIdPerson() == newIdPerson)
//				resultStatus = "OK";
//			else
//				resultStatus = "ERROR";
//			
//			printHeader(4, "POST", "/person", "APPLICATION/JSON", "APPLICATION/JSON");		
//			System.out.println("=> Result: "+ resultStatus);
//			System.out.println("=> HTTP Status: "+ httpStatus);
//			
//			//Marshall person object into the system default output
//			writer.marshallJSON(person);
//			System.out.println("\n");
//			clientApp.clearVariables();
			
			/*
			 * R#5 (DELETE BASE_URL/person/newIdPerson)
			 */	
			//Delete the person which has just created with id: newIdPerson
			service.path("person/"+newIdPerson).request().delete();		
			
			//Get the person with id: newIdPerson
			response = service.path("person/"+newIdPerson).request().accept(MediaType.APPLICATION_JSON).get();
			httpStatus = response.getStatus();
			results = response.readEntity(String.class);
			
			if(httpStatus == 404)
				resultStatus = "OK";
			else
				resultStatus = "ERROR";
			
			printHeader(5, "DELETE", "/person/"+newIdPerson, "APPLICATION/JSON", "APPLICATION/JSON");		
			System.out.println("=> Result: "+ resultStatus);
			System.out.println("=> HTTP Status: "+ httpStatus);
			System.out.println(results);
			System.out.println("\n");
			clientApp.clearVariables();
			
			/*
			 * R#9 (GET BASE_URL/measureTypes)
			 */
			//Get all the measureTypes
			response = service.path("measureTypes").request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			httpStatus = response.getStatus();
						
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			//Un-marshall measureTypes xml String into MeasureTypeStore object
			measureTypeList = writer.unmarshallJSONMeasureType(stream);
			//Check if the people is more than 2
			if(measureTypeList.length > 2){
				resultStatus = "OK";
				
				//Store all the measureTypes into an array				
				measure_types = new String[measureTypeList.length]; 
				for(int i=0; i<measureTypeList.length; i++){
					measure_types[i] = measureTypeList[i].getMeasureName();
				}
			}
			else
				resultStatus = "ERROR";
			
			printHeader(9, "GET", "/measureTypes", "APPLICATION/JSON", "APPLICATION/JSON");		
			System.out.println("=> Result: "+ resultStatus);
			System.out.println("=> HTTP Status: "+ httpStatus);
			
			//Marshall MeasureTypeStore object into the system default output
			writer.marshallJSON(measureTypeList);
			System.out.println("\n");
			clientApp.clearVariables();
			
			/*
			 * R#6 (GET BASE_URL/person/{id}/{measureTypes})
			 */					
					
			idPerson = new int[2];
			idPerson[0] = first_person_id;
			idPerson[1] = last_person_id;
			
			for(int h=0; h<idPerson.length; h++){ //Iterate for the first and the last person
				for(int i=0; i<measure_types.length; i++){	//Iterate for the all measureTypes that each person has		
					//Get the HealthMeasureHistory
					response = service.path("person/"+idPerson[h]+"/"+measure_types[i]).request().accept(MediaType.APPLICATION_JSON).get();
					results = response.readEntity(String.class);
					httpStatus = response.getStatus();
					
					//Convert string into inputStream
					stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
					
					//Un-marshall HealthMeasureHistory xml String into HealthMeasureStore object
					healthMeasureList = writer.unmarshallJSONHealthMeasure(stream);
					if(healthMeasureList.length > 0){
						resultStatus = "OK";
						
						//Store one mid and the corresponding measureType into variables						
						mid = healthMeasureList[0].getIdMeasureHistory();
						storedMeasureType = measure_types[i];
						storedIdPerson = idPerson[h];
					}
					else
						resultStatus = "ERROR";
					
					printHeader(6, "GET", "/person/"+idPerson[h]+"/"+measure_types[i], "APPLICATION/JSON", "APPLICATION/JSON");		
					System.out.println("=> Result: "+ resultStatus);
					System.out.println("=> HTTP Status: "+ httpStatus);
					
					//Marshall HealthMeasureStore object into the system default output
					writer.marshallJSON(healthMeasureList);
					System.out.println("\n");
					clientApp.clearVariables();
				}		
			}	
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static URI getBaseURI(String SERVER_URL){
		return UriBuilder.fromUri(SERVER_URL).build();
	}
	
	private static void printHeader(int number, String httpMethod, String url, 
			String acceptType, String contentType){		
		System.out.println("Request #"+number+": "+httpMethod+" "+url+" Accept: "+acceptType+" Content-Type: "+contentType);			
	}
	
	private void clearVariables(){		
		httpStatus = 0;
		results = null;
		resultStatus = null;
		healthMeasureHsitory = null;
		measureType = null;
		healthMeasure = null;
		response = null;
		stream = null;
	}
}
