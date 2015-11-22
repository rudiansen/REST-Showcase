package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.LifeStatus;
import introsde.rest.ehealth.model.MeasureDefinition;
import introsde.rest.ehealth.model.Person;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }
    
    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person getPerson() {
        Person person = this.getPersonById(id);             
        if (person == null){
        	System.out.println("Get: Person with " + id + " not found");    
        	throw new NotFoundException();
        }        
        return person;
    }
    
    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Person getPersonHTML() {
        Person person = this.getPersonById(id);        
        if (person == null){
        	System.out.println("Get: Person with " + id + " not found");    
        	throw new NotFoundException();
        }            
        System.out.println("Returning person... " + person.getIdPerson());
        return person;
    }

    //Assignment: Request #3
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        System.out.println("--> Updating Person... " +this.id);
        Response res;
        Person existing = getPersonById(this.id);

        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            person.setIdPerson(this.id);
            
            int idMeasureDef = 0;
        	
            //Preserve current LifeStatus. Prevent duplicate entry with different idMeasure
            List<LifeStatus> list = person.getLifeStatus();
            for(LifeStatus lifeStatus: list){
            	lifeStatus.setPerson(person);
            	
            	MeasureDefinition measureDef = MeasureDefinition.getIdMeasureDef(lifeStatus.getMeasureDefinition().getMeasureName());
            	idMeasureDef = measureDef.getIdMeasureDef();
            	            	
            	LifeStatus currentLifeStatusInDn = LifeStatus.getByMeasureDef(this.id, idMeasureDef);
            	lifeStatus.setIdMeasure(currentLifeStatusInDn.getIdMeasure());            	            	
            }            
            Person.updatePerson(person);
        }
        return res;
    }

    //Assignment: Request #5
    @DELETE
    public void deletePerson() {
    	System.out.println("DELETE Person with id: "+ id);
    	
        Person c = getPersonById(id);
        if (c == null)
            throw new NotFoundException("Delete: Person with " + id
                    + " not found");
        Person.removePerson(c);
    }

    public Person getPersonById(int personId) {
        System.out.println("Reading person from DB with id: "+personId);
        
        Person person = Person.getPersonById(personId);
        if(person != null)        	     	    
        	System.out.println("Person: "+person.toString());
        
        return person;
    }
    
}