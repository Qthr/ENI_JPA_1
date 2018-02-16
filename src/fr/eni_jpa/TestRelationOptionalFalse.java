/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import fr.eni_jpa.model.entity.Adresse;
import fr.eni_jpa.model.entity.Langue;
import fr.eni_jpa.model.entity.Pays;
import fr.eni_jpa.model.entity.Personne;
import fr.eni_jpa.model.entity.PersonneAdresse;
import fr.eni_jpa.model.entity.PersonneAdressePK;
import fr.eni_jpa.model.entity.PersonneDetail;
import fr.eni_jpa.model.entity.Telephone;
import fr.eni_jpa.model.entity.Ville;
import fr.eni_jpa.model.enumeration.Genre;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.AdresseJpaController;
import fr.eni_jpa.model.persistence.controller.LangueJpaController;
import fr.eni_jpa.model.persistence.controller.PaysJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneAdresseJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneDetailJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneJpaController;
import fr.eni_jpa.model.persistence.controller.TelephoneJpaController;
import fr.eni_jpa.model.persistence.controller.VilleJpaController;
import fr.eni_jpa.model.persistence.controller.exceptions.IllegalOrphanException;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class TestRelationOptionalFalse {
        
    public static void main(String[] args){
        
        // 4 – Cascade sur les relations @OneToOne / @OneToOne(optional=false) et @OneToMany / @ManyToOne(optional=false)
        
        //On prend l’exemple d’une relation @OneToMany/@ManyToOne(optional=false), le principe est le même pour @OneToOne/@OneToOne(optional=false).

      /*
        // A - Création
        PaysJpaController paysJpa = new PaysJpaController();
        // Création d'une entité racine Pays
        Pays pays = new Pays();
        pays.setContinent("Europe");
        pays.setMonnaie("Livre");
        pays.setNom("Angleterre");
        pays.setPopulation(133165131L);
        // Création de ses entités filles Ville
        Ville ville1 = new Ville();
        ville1.setCapital(true);
        ville1.setNom("Londres");
        ville1.setCodePostal("365455");
        Ville ville2 = new Ville();
        ville2.setCapital(false);
        ville2.setNom("Slough");
        ville2.setCodePostal("395213");
        Ville ville3 = new Ville();
        ville3.setCapital(false);
        ville3.setNom("Bournemouth");
        ville3.setCodePostal("135420");
        pays.addVille(ville1);
        pays.addVille(ville2);
        pays.addVille(ville3);
        // Création en base de l'entité Pays et de ses entités filles Ville en cascade
        paysJpa.create(pays);
       
      
      
       // B - Modification
      
       PaysJpaController paysJpa = new PaysJpaController();
       // Récupération du pays Angleterre
       Pays pays = paysJpa.findPaysWithAll(10);
       // On modifie le nombre d'habitant en Angleterre
       pays.setPopulation(132145631L);
       // Cette modification est simplement traitée par le merge() dans la méthode edit()
       Ville ville1 = pays.getListVille().get(0);
       Ville ville2 = pays.getListVille().get(1);
       Ville ville3 = pays.getListVille().get(2);
       Ville villeNew = new Ville();
       villeNew.setCapital(false);
       villeNew.setNom("Manchester");
       villeNew.setCodePostal("135220");
       // On modifie la ville1 : Londres vers Liverpool
       // Cette modification est simplement traitée par le merge() dans la méthode edit() grâce à la cascade
       ville1.setCapital(false);
       ville1.setNom("Liverpool");
       ville1.setCodePostal("254613");
       // On ajoute une nouvelle Ville à la liste des villes de l'entité Pays - simple
       // Cette modification est simplement traitée par le merge() dans la méthode edit() grâce à la cascade
       pays.addVille(villeNew);
       // On supprime la liaison de l'entité Pays vers deux villes - complexe
       // Cette modification entraîne la suppression des deux villes devenues orphelines
       // Elle est traitée grâce à la propriété orphanRemoval = true sur la relation @OneToMany de l'entité Pays vers Ville.
       pays.removeVille(ville2);
       pays.removeVille(ville3);
       // Modification de l'entité Pays qui entraine en cascade les 3 modifications (modif d'une ville, ajout d'une ville, suppression de 2 villes)
       try {
            paysJpa.edit(pays);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOptionalFalse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOptionalFalse.class.getName()).log(Level.SEVERE, null, ex);
        }
       */
       
       // C - Suppression
        PaysJpaController paysJpa = new PaysJpaController();
        // Récupération d'une entité racine Pays
        Pays pays = paysJpa.findPaysWithAll(12);
        // Suppression de l'entité Pays et de ses entités filles Ville en cascade
        try {
            paysJpa.destroy(pays.getId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TestRelationOptionalFalse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOptionalFalse.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        
        PersistenceFactory.close();

        
    }
}
