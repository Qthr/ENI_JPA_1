/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
@Entity
@Table(name = "personne_adresse")
public class PersonneAdresse implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @EmbeddedId
    protected PersonneAdressePK personneAdressePK;
	
    @Basic(optional = false)
    @Column(name = "debut")
    @Temporal(TemporalType.DATE)
    private Date debut;
    
    @Column(name = "fin")
    @Temporal(TemporalType.DATE)
    private Date fin;
    
    @Basic(optional = false)
    @Column(name = "principale")
    private boolean principale;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_adresse", referencedColumnName = "id",
                insertable = false, updatable = false)
    private Adresse adresse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_personne", referencedColumnName = "id",
		insertable = false, updatable = false)
    private Personne personne;

    
    public PersonneAdressePK getPersonneAdressePK() {
        return personneAdressePK;
    }

    public void setPersonneAdressePK(PersonneAdressePK personneAdressePK) {
        this.personneAdressePK = personneAdressePK;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public boolean isPrincipale() {
        return principale;
    }

    public void setPrincipale(boolean principale) {
        this.principale = principale;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }


   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personneAdressePK != null ? personneAdressePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonneAdresse)) {
            return false;
        }
        PersonneAdresse other = (PersonneAdresse) object;
        if ((this.personneAdressePK == null && other.personneAdressePK != null) || (this.personneAdressePK != null && !this.personneAdressePK.equals(other.personneAdressePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.eni_jpa.model.entity.PersonneAdresse[ id=" + personneAdressePK + " ]";
    }
    
}
