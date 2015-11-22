package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import introsde.rest.ehealth.dao.LifePlayerDao;

@Entity
@Table(name="HealthMeasureHistory")
@NamedQueries({
	@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h"),
	@NamedQuery(name="HealthMeasureHistory.getMeasureByPersonId", query="SELECT h FROM HealthMeasureHistory h "
			+ "where h.person.idPerson = :idPerson and h.measureDefinition.idMeasureDef = :idMeasureDef"),
	@NamedQuery(name="HealthMeasureHistory.getMeasureByMid",query="SELECT h FROM HealthMeasureHistory h "
			+ "where h.person.idPerson = :idPerson and h.measureDefinition.idMeasureDef = :idMeasureDef and "
			+ "h.idMeasureHistory = :mid")
})
@XmlRootElement(name="measure")
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mhistory")
	@TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthMeasureHistory", allocationSize = 1)
	@Column(name="idMeasureHistory")
	@XmlElement(name="mid")
	private int idMeasureHistory;

	@Column(name="value")
	private String value;
	
	@Column(name="timestamp")
	@XmlElement(name="created")
	private String timestamp;

	@ManyToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef")
	@XmlTransient
	private MeasureDefinition measureDefinition;

	// notice that we haven't included a reference to the history in Person
	// this means that we don't have to make this attribute XmlTransient
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
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
        return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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

	// database operations
	public static HealthMeasureHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifePlayerDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthMeasureHistory> getAll() {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class)
	    		.getResultList();
	    LifePlayerDao.instance.closeConnections(em);
	    return list;
	}
	
	public static List<HealthMeasureHistory> getMeasureByPersonId(int personId, int idMeasureDef) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
	    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.getMeasureByPersonId", HealthMeasureHistory.class)
	    		.setParameter("idPerson", personId)
	    		.setParameter("idMeasureDef", idMeasureDef)
	    		.getResultList();
	    LifePlayerDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthMeasureHistory getMeasureByMid(int personId, int idMeasureDef, int mid) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
	    HealthMeasureHistory list = em.createNamedQuery("HealthMeasureHistory.getMeasureByMid", HealthMeasureHistory.class)
	    		.setParameter("idPerson", personId)
	    		.setParameter("idMeasureDef", idMeasureDef)
	    		.setParameter("mid", mid)
	    		.getSingleResult();
	    LifePlayerDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifePlayerDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifePlayerDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifePlayerDao.instance.closeConnections(em);
	}
}
