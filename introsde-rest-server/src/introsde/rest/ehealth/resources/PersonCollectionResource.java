package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.HealthMeasureHistory;
import introsde.rest.ehealth.model.LifeStatus;
import introsde.rest.ehealth.model.MeasureDefinition;
import introsde.rest.ehealth.model.Person;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;

    //Assignment: Request #1
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<Person> getPersonsBrowser(@Context final HttpServletResponse response) {
        System.out.println("Getting list of people...");
        List<Person> people = Person.getAll();
        return people;
    }

    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        System.out.println("Getting count...");
        List<Person> people = Person.getAll();
        int count = people.size();
        return String.valueOf(count);
    }
    
    //Assignment: Request #2
    @Path("{personId}")
    public PersonResource getPerson(@PathParam("personId") int id) {
        return new PersonResource(uriInfo, request, id);
    }

    //Assignment: Request #4
    @POST
    @Produces({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    public Person newPerson(Person person) throws IOException {
        System.out.println("Creating new person...");   
        
        //Set idPerson to the LifeStatus
        List<LifeStatus> lifeStatus = person.getLifeStatus();        
        for(LifeStatus life: lifeStatus)
        	life.setPerson(person);
        	
        return Person.savePerson(person);
    }
    
    //Assignment: Request #6
    @GET
    @Path("{personId}/{measureType}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<HealthMeasureHistory> getHealthMeasureHistoryByPersonIdAndMeasureType(@PathParam("personId") int id, @PathParam("measureType") String measureType) {
    	System.out.println("Reading HealthMeasureHistory from DB with id: "+id+" and measureType: "+ measureType);

    	int idMeasureDef = 0;
    	
    	MeasureDefinition measureDef = MeasureDefinition.getIdMeasureDef(measureType);
    	idMeasureDef = measureDef.getIdMeasureDef();
    		
    	List<HealthMeasureHistory> healthMeasureHistory = HealthMeasureHistory.getMeasureByPersonId(id, idMeasureDef);
        return healthMeasureHistory;
    }
    
    //Assignment: Request #7
    @GET
    @Path("{personId}/{measureType}/{mid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public HealthMeasureHistory getHealthMeasureHistoryById(@PathParam("personId") int id, @PathParam("measureType") String measureType, 
    		@PathParam("mid") int mid){    	
    	System.out.println("Reading HealthMeasureHistory from DB with id: "+id+", measureType: "+ measureType + " and mid: " + mid);
    		
    	int idMeasureDef = 0;
    	
    	MeasureDefinition measureDef = MeasureDefinition.getIdMeasureDef(measureType);
    	idMeasureDef = measureDef.getIdMeasureDef();
    	
    	HealthMeasureHistory healthMeasureHistory = HealthMeasureHistory.getMeasureByMid(id, idMeasureDef, mid);
        System.out.println("HealthMeasureHistory: "+ healthMeasureHistory.toString());
        
        return healthMeasureHistory;
    }
    
    //Assignment: Request #8
    @POST
    @Path("{personId}/{measureType}")
    @Produces({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
    public LifeStatus updateMeasure(LifeStatus lifeStatus, @PathParam("personId") int id, @PathParam("measureType") String measureType){    	
    	
    	int idMeasureDef = 0;
    	
    	MeasureDefinition measureDef = MeasureDefinition.getIdMeasureDef(measureType);
    	idMeasureDef = measureDef.getIdMeasureDef();
    	    	
    	//Get a Person by given idPerson
    	Person person = Person.getPersonById(id);
    	//Get a LifeStatus by given idPerson and MeasureType
    	LifeStatus currentLifeStatus = LifeStatus.getByMeasureDef(id, idMeasureDef);
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    	Date today = Calendar.getInstance().getTime();
    	
    	//Save currentLifeStatus into HealthMeasureHistory Table
    	HealthMeasureHistory newHealthMeasureHistory = new HealthMeasureHistory();
    	newHealthMeasureHistory.setPerson(person);
    	newHealthMeasureHistory.setMeasureDefinition(new MeasureDefinition(idMeasureDef));
    	newHealthMeasureHistory.setTimestamp(df.format(today));
    	newHealthMeasureHistory.setValue(currentLifeStatus.getValue());
    	HealthMeasureHistory.saveHealthMeasureHistory(newHealthMeasureHistory);
    	
    	//Delete currentLifeStatus from LifeStatus Table    	
    	LifeStatus.removeLifeStatus(currentLifeStatus);
    	
    	//Create a new LifeStatus for the corresponding person
    	lifeStatus.setPerson(person);
    	LifeStatus.saveLifeStatus(lifeStatus);
    	    	
    	return lifeStatus;
    }
    
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{personId}/{measureType}/{mid}")
    public Response  updateHealthMeasure(HealthMeasureHistory healthMeasure, @PathParam("personId") int id, 
    	@PathParam("measureType") String measureType, @PathParam("mid") int mid){
    	Response res;
    	int idMeasureDef = 0;
    	
    	Person person = Person.getPersonById(id);
    	if(person == null)
    		throw new NotFoundException();

    	MeasureDefinition measureDef = MeasureDefinition.getIdMeasureDef(measureType);
    	idMeasureDef = measureDef.getIdMeasureDef();
    	
    	HealthMeasureHistory exisiting = HealthMeasureHistory.getMeasureByMid(id, idMeasureDef, mid);
    	
    	if(exisiting != null){
    		healthMeasure.setPerson(person);
    		healthMeasure.setMeasureDefinition(new MeasureDefinition(idMeasureDef));
    		HealthMeasureHistory.updateHealthMeasureHistory(healthMeasure);
    		res = Response.created(uriInfo.getAbsolutePath()).build();    		
    	}
    	else
    		throw new NotFoundException();
    	
    	return res; 	    	
    }

}