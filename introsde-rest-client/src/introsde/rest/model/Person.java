package introsde.rest.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import introsde.rest.utils.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlAttribute(name="id")
    private int idPerson;    
    @XmlElement(name="firstname")
    private String name;        
    private String lastname;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date birthdate; 
    @XmlTransient
    private String username;
    @XmlTransient
    private String email;
    @XmlElementWrapper(name="healthProfile")
    @XmlElement(name="measureType")
    private List<LifeStatus> lifeStatus;
    
    public List<LifeStatus> getLifeStatus() {
        return lifeStatus;
    }
    public void setLifeStatus(LifeStatus lifeStatus){
    	this.lifeStatus.add(lifeStatus);
    }      
    
    public Person(){    	
    }
    
    public Person(List<LifeStatus> lifeStatus){
    	this.lifeStatus = lifeStatus;
    }
    
    // getters
    public int getIdPerson(){
        return idPerson;
    }

    public String getLastname(){
        return lastname;
    }
    public String getName(){
        return name;
    }
    public String getUsername(){
        return username;
    }
    public String getBirthdate(){
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(birthdate);
    }
    public String getEmail(){
        return email;
    }
    
    // setters
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setBirthdate(String birthdate) throws ParseException{
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    	Date date = format.parse(birthdate);
        this.birthdate = date;
    }
    public void setEmail(String email){
        this.email = email;
    }            
}