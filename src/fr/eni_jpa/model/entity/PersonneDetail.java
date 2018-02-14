/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.entity;

import fr.eni_jpa.model.enumeration.Genre;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
@Entity
@Table(name = "personne_detail")
public class PersonneDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id_personne")
    private Integer idPersonne;
    
    @Basic
    @Column(name = "num_secu")
    private String numSecu;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "sexe")
    private Genre sexe;
    
    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn(name="id_personne", referencedColumnName="id" )
    private Personne personne;

    @PrePersist
    public void avantSauvegarde(){
       this.idPersonne = this.personne.getId();
    }
 
    public Integer getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Integer idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNumSecu() {
        return numSecu;
    }

    public void setNumSecu(String numSecu) {
        this.numSecu = numSecu;
    }

    public Genre getSexe() {
        return sexe;
    }

    public void setSexe(Genre sexe) {
        this.sexe = sexe;
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
        hash += (idPersonne != null ? idPersonne.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonneDetail)) {
            return false;
        }
        PersonneDetail other = (PersonneDetail) object;
        if ((this.idPersonne == null && other.idPersonne != null) || (this.idPersonne != null && !this.idPersonne.equals(other.idPersonne))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.eni_jpa.model.entity.PersonneDetail[ idPersonne=" + idPersonne + " ]";
    }
    
}
