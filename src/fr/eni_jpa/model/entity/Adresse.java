/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.entity;

import com.sun.istack.internal.Nullable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
@Entity
@Table(name = "adresse")
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic
    @Column(name = "libelle")
    private String libelle;
    
    @ManyToOne
    @JoinColumn(name = "id_ville", referencedColumnName = "id")
    private Ville ville;
    
    @OneToMany(mappedBy = "adresse")
    private List<PersonneAdresse> listPersonneAdresse;

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public List<PersonneAdresse> getListPersonneAdresse() {
        return listPersonneAdresse;
    }

    public void setListPersonneAdresse(List<PersonneAdresse> listPersonneAdresse) {
        this.listPersonneAdresse = listPersonneAdresse;
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
        if (!(object instanceof Adresse)) {
            return false;
        }
        Adresse other = (Adresse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.eni_jpa.model.entity.Adresse[ id=" + id + " ]";
    }
    
}
