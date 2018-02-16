package fr.eni_jpa;

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
import org.hibernate.jpa.event.internal.core.JpaAutoFlushEventListener;


/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class TestRelationOneToOne {
    
    public static void main(String[] args) throws IllegalOrphanException{
        /*
        // 2 - Cascade sur la relation OneToOne entre Personne et PersonneDetail
        
        // A – Création en cascade pour une relation @OneToOne
        
        PersonneJpaController persJpa = new PersonneJpaController();
        Personne pers = new Personne();
        pers.setNom("Therre");
        pers.setPrenom("Patrice");
        pers.setDate_naissance(new Date(1966, 2, 28));
        PersonneDetail persDet = new PersonneDetail();
        persDet.setNumSecu("213100351");
        persDet.setSexe(Genre.HOMME);
        pers.addPersonneDetail(persDet);
        // Création de l'entité Personne et de son entité fille PersonneDetail en cascade
        persJpa.create(pers);
        
        
        // B – Modification en cascade pour une relation @OneToOne

        a - 1er cas
        PersonneJpaController persJpa = new PersonneJpaController();
        // Récupère la personne Patrice
        Personne pers = persJpa.findPersonneWithAll(13);
        // Change son prénom dans Personne
        pers.setPrenom("Any");
        // Change son genre dans PersonneDetail
        pers.getPersonneDetail().setSexe(Genre.FEMME);
        // Modification de l'entité Personne et de son entité fille PersonneDetail en cascade
        try {
            persJpa.edit(pers);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOneToOne.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOneToOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // b - 2eme cas
        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
        // Récupère la personne Patrice
        Personne pers = persJpa.findPersonneWithAll(13);
        PersonneDetail persDet = pers.getPersonneDetail();
        // On supprime la relation de Personne vers PersonneDetail
        pers.removePersonneDetail();
        // On est obligé de modifier l'entité fille manuellement car la cascade sur
        // l'entité Personne ne se propage que sur l'entité fille qu'elle possède.
        try {
            persDetJpa.edit(persDet);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Modification de l'entité Personne
        try {
            persJpa.edit(pers);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOneToOne.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOneToOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // C - Suppression en cascade des entités filles orphelines pour une relation @OneToOne
         PersonneJpaController persJpa = new PersonneJpaController();
        // Récupère la personne
        Personne pers = persJpa.findPersonneWithAll(11);
        //On supprime la laisons entre l'entité racine Personne et son entité fille PersonneDetail
        // La suppression de cette relation va entrainer la suppression de l'entité fille PersonneDetail en base lors du edit()
        // car orphanRemoval=true sur la relation @OneToOne de Personne vers PersonneDetail
        pers.removePersonneDetail();
         try {
            persJpa.edit(pers);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // D - Suppression en cascade pour une relation @OneToOne
        
        PersonneJpaController persJpa = new PersonneJpaController();
        // Récupère la personne Any
        Personne pers = persJpa.findPersonneWithAll(12);
        // Suppression de l'entité Personne et de son entité fille PersonneDetail en cascade
        try {
            persJpa.destroy(pers.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOneToOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
       
        */
        PersistenceFactory.close();

        
    }
}
