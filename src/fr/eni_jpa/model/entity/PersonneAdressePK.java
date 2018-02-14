/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
@Embeddable
public class PersonneAdressePK implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "id_adresse")
    private Integer idAdresse;
    
    @Basic(optional = false)
    @Column(name = "id_personne")
    private Integer idPersonne;
    
    public PersonneAdressePK(){
    
    }
    
    public PersonneAdressePK(Integer idAdresse, Integer idPersonne){
        this.idAdresse = idAdresse;
        this.idPersonne = idPersonne;
    }

    public Integer getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Integer idAdresse) {
        this.idAdresse = idAdresse;
    }

    public Integer getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Integer idPersonne) {
        this.idPersonne = idPersonne;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAdresse;
        hash += (int) idPersonne;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonneAdressePK)) {
            return false;
        }
        PersonneAdressePK other = (PersonneAdressePK) object;
        if (this.idAdresse != other.idAdresse) {
            return false;
        }
        if (this.idPersonne != other.idPersonne) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.eni.jpa.entity.PersonneAdressePK[ idAdresse=" + idAdresse + ", idPersonne=" + idPersonne + " ]";
    }
    
}
