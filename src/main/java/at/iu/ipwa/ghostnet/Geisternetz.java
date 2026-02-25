/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package at.iu.ipwa.ghostnet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author ehabz
 */
@Entity
@Table(name ="geisternetz")
public class Geisternetz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "breitengrad", nullable = false)
    private Double breitengrad;
    
    @Column(name = "laengengrad", nullable = false)
    private Double laengengrad;
    
    @Column(name = "groesse")
    private Integer groesse; 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable=false)
    private Status status = Status.GEMELDET;
    
    @Column(name = "anonym_gemeldet", nullable=false)
    private boolean anonymGemeldet;
    
    @ManyToOne
    @JoinColumn(name="bergende_person_id")
    private Person bergendePerson;
    
    public Geisternetz(Long id, Double breitengrad, Double laengengrad, Integer groesse) {
        this.id = id;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
        this.groesse = groesse;
    }

    public Geisternetz(Long id, Double breitengrad, Double laengengrad, Integer groesse, boolean anonymGemeldet, Person bergendePerson) {
        this.id = id;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
        this.groesse = groesse;
        this.anonymGemeldet = anonymGemeldet;
        this.bergendePerson = bergendePerson;
    }
    
    
    
    public Geisternetz(){
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBreitengrad() {
        return breitengrad;
    }

    public void setBreitengrad(Double breitengrad) {
        this.breitengrad = breitengrad;
    }

    public Double getLaengengrad() {
        return laengengrad;
    }

    public void setLaengengrad(Double laengengrad) {
        this.laengengrad = laengengrad;
    }

    public Integer getGroesse() {
        return groesse;
    }

    public void setGroesse(Integer groesse) {
        this.groesse = groesse;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isAnonymGemeldet() {
        return anonymGemeldet;
    }

    public void setAnonymGemeldet(boolean anonymGemeldet) {
        this.anonymGemeldet = anonymGemeldet;
    }

    public Person getBergendePerson() {
        return bergendePerson;
    }

    public void setBergendePerson(Person bergendePerson) {
        this.bergendePerson = bergendePerson;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geisternetz)) {
            return false;
        }
        Geisternetz other = (Geisternetz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "at.iu.ipwa.ghostnet.Geisternetz[ id=" + id + " ]";
    }
    
}
