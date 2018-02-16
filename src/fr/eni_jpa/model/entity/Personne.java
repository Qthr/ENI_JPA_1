/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
@Entity
@Table(name = "personne")
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance")
    private Date date_naissance;
    
    @Basic
    @Column(name = "nom")
    private String nom;
    
    @Basic
    @Column(name = "prenom")
    private String prenom;
    
    @OneToOne(mappedBy = "personne", cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonneDetail personneDetail;
    
    @OneToMany(mappedBy = "idPersonne", cascade = CascadeType.ALL)
    private List<Telephone> listTelephones = new ArrayList<>();
    
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonneAdresse> listPersonneAdresse = new ArrayList<>();

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public PersonneDetail getPersonneDetail() {
        return personneDetail;
    }

    public void setPersonneDetail(PersonneDetail personneDetail) {
        this.personneDetail = personneDetail;
    }

    public void setListTelephones(List<Telephone> listTelephones) {
        this.listTelephones = listTelephones;
    }

    public List<Telephone> getListTelephones() {
        return listTelephones;
    }

    public List<PersonneAdresse> getListPersonneAdresse() {
        return listPersonneAdresse;
    }

    public void setListPersonneAdresse(List<PersonneAdresse> listPersonneAdresse) {
        this.listPersonneAdresse = listPersonneAdresse;
    }
    
     public void addPersonneDetail(PersonneDetail persDet) {
        this.personneDetail = persDet;
        persDet.setPersonne(this);
    }
 
    public void removePersonneDetail() {
        if (this.personneDetail != null) {
            this.personneDetail.setPersonne(null);
        }
        this.personneDetail = null;
    }
    
    public void addTelephone(Telephone tel) {
         this.getListTelephones().add(tel);
         tel.setIdPersonne(this);
    }
 
    public void removeTelephone(Telephone tel) {
        tel.setIdPersonne(null);
        this.getListTelephones().remove(tel);
    }
    
     public void removeTelephones(){
         for(Telephone tel : new ArrayList<>(this.listTelephones)) {
            this.removeTelephone(tel);
        }
    }
     
     public void addPersonneAdresse(PersonneAdresse persAdr){
         this.getListPersonneAdresse().add(persAdr);
         persAdr.setPersonne(this);
     }
     
     public void removePersonneAdresse(PersonneAdresse persAdr){
         persAdr.setPersonne(null);
         this.getListPersonneAdresse().remove(persAdr);
     }
    
    public void removePersonneAdresses(){
        for(PersonneAdresse persAdr : new ArrayList<>(this.listPersonneAdresse)){
            this.removePersonneAdresse(persAdr);
        }
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
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.eni_jpa.model.entity.Personne[ id=" + id + " ]";
    }
    
}
