package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.ehealth.dao.LifePlayerDao;

@Entity
@Table(name="MeasureDefinition")
@NamedQueries({
	@NamedQuery(name="MeasureDefinition.findAll", query="SELECT m FROM MeasureDefinition m"),
	@NamedQuery(name="MeasureDefinition.getIdMeasureDef", query="SELECT m FROM MeasureDefinition m "
			+ "where lower(m.measureName) = :measureName")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class MeasureDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measuredef")
	@TableGenerator(name="sqlite_measuredef", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="MeasureDefinition", allocationSize = 1)
	@Column(name="idMeasureDef")
	@XmlElement(name="id")
	private int idMeasureDef;
	@XmlElement(name="measure")
	@Column(name="measureName")
	private String measureName;
	@Column(name="measureType")
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

	// database operations
	public static MeasureDefinition getMeasureDefinitionById(int personId) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		MeasureDefinition p = em.find(MeasureDefinition.class, personId);
		LifePlayerDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<MeasureDefinition> getAll() {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
	    List<MeasureDefinition> list = em.createNamedQuery("MeasureDefinition.findAll", MeasureDefinition.class)
	    		.getResultList();
	    LifePlayerDao.instance.closeConnections(em);
	    return list;
	}
	
	public static MeasureDefinition getIdMeasureDef(String measureName) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		MeasureDefinition measureDef = em.createNamedQuery("MeasureDefinition.getIdMeasureDef", MeasureDefinition.class)
	    		.setParameter("measureName", measureName)
	    		.getSingleResult();
	    LifePlayerDao.instance.closeConnections(em);
	    return measureDef;
	}
	
	public static MeasureDefinition saveMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifePlayerDao.instance.closeConnections(em);
	    return p;
	}
	
	public static MeasureDefinition updateMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifePlayerDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeMeasureDefinition(MeasureDefinition p) {
		EntityManager em = LifePlayerDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifePlayerDao.instance.closeConnections(em);
	}
}
