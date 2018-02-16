/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
@Entity
@Table(name = "pays")
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    
    @Basic(optional = false)
    @Column(name = "monnai")
    private String monnaie;
    
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    
    @Basic
    @Column(name = "population")
    private Long population;
    
    @Basic
    @Column(name = "continent")        
    private String continent;
    
    @OneToMany(mappedBy = "pays", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ville> listVille = new ArrayList<>();
    
    @ManyToMany(mappedBy = "listPays", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<Langue> listLangue = new ArrayList<>();
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonnaie() {
        return monnaie;
    }

    public void setMonnaie(String monnaie) {
        this.monnaie = monnaie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
    
    public List<Ville> getListVille() {
        return listVille;
    }

    public void setListVille(List<Ville> listVille) {
        this.listVille = listVille;
    }

    public List<Langue> getListLangue() {
        return listLangue;
    }

    public void setListLangue(List<Langue> listLangue) {
        this.listLangue = listLangue;
    }
    
    public void addVille(Ville ville) {
         this.getListVille().add(ville);
         ville.setPays(this);
    }
 
    public void removeVille(Ville ville) {
        ville.setPays(null);
        this.getListVille().remove(ville);
    }
    
    public void removeVilles(){
         for(Ville ville : new ArrayList<>(this.listVille)) {
            this.removeVille(ville);
        }
    }
    
     public void addLangue(Langue langue) {
         this.getListLangue().add(langue);
         langue.getListPays().add(this);
    }
 
    public void removeLangue(Langue langue) {
        this.getListLangue().remove(langue);
        langue.getListPays().remove(this);
    }
    
    public void removeLangues(){
         for(Langue langue : new ArrayList<>(this.listLangue)) {
            this.removeLangue(langue);
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
        if (!(object instanceof Pays)) {
            return false;
        }
        Pays other = (Pays) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.eni_jpa.model.entity.Pays[ id=" + id + " ]";
    }
    
}
