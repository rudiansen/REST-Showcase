package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.ehealth.dao.LifePlayerDao;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted 
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person", allocationSize = 1)
    @Column(name="idPerson")
    @XmlAttribute(name="id")
    private int idPerson;
    
    @XmlElement(name="firstname")
    @Column(name="name")
    private String name;
    
    @Column(name="lastname")
    private String lastname;
    
    @Column(name="birthdate")
    private String birthdate; 
    
    @XmlTransient
    @Column(name="username")
    private String username;
    
    @XmlTransient
    @Column(name="email")
    private String email;
    
    // mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @XmlElementWrapper(name = "healthProfile")
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
    
    // getters
    public int getIdPerson(){
        return this.idPerson;
    }
    public String getLastname(){
        return this.lastname;
    }
    public String getName(){
        return this.name;
    }
    public String getUsername(){
        return this.username;
    }
    public String getBirthdate(){    	
        return this.birthdate;
    }
    public String getEmail(){
        return this.email;
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
    public void setBirthdate(String birthdate){    	    	
        this.birthdate = birthdate;
    }
    public void setEmail(String email){
        this.email = email;
    }        
    
    public static Person getPersonById(int personId) {
        EntityManager em = LifePlayerDao.instance.createEntityManager();
        
        //Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
        Person p = em.find(Person.class, personId);
        LifePlayerDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifePlayerDao.instance.createEntityManager();
        
        //Refresh the entity manager
        em.getEntityManagerFactory().getCache().evictAll();
        
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
                .getResultList();
        LifePlayerDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = LifePlayerDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifePlayerDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = LifePlayerDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifePlayerDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifePlayerDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifePlayerDao.instance.closeConnections(em);
    }
    
}