/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa;

import fr.eni_jpa.model.entity.Adresse;
import fr.eni_jpa.model.entity.Langue;
import fr.eni_jpa.model.entity.Pays;
import fr.eni_jpa.model.entity.Personne;
import fr.eni_jpa.model.entity.PersonneAdresse;
import fr.eni_jpa.model.entity.PersonneAdressePK;
import fr.eni_jpa.model.entity.Telephone;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.AdresseJpaController;
import fr.eni_jpa.model.persistence.controller.LangueJpaController;
import fr.eni_jpa.model.persistence.controller.PaysJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneAdresseJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneJpaController;
import fr.eni_jpa.model.persistence.controller.TelephoneJpaController;
import fr.eni_jpa.model.persistence.controller.exceptions.IllegalOrphanException;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class TestRelationOneToMany {
    
    public static void main(String[] args) throws NonexistentEntityException{
       
      /*
        // 2 - Cascade sur la relation OneToMany entre Personne et PersonneAdresse
       
        // A – Création en cascade pour une relation @OneToMany
        
        PersonneJpaController persJpa = new PersonneJpaController();
        // Création d'une entité racine Personne
        Personne pers = new Personne();
        pers.setNom("Picaud");
        pers.setPrenom("Robin");
        pers.setDate_naissance(new Date (1993, 11, 8));
        // Création de ses entités filles Telephone
        Telephone tel1 = new Telephone();
        tel1.setNumero("0139561174");
        tel1.setLibelle("Maison");
        Telephone tel2 = new Telephone();
        tel2.setNumero("0684125695");
        tel2.setLibelle("Portable");
        pers.addTelephone(tel1);
        pers.addTelephone(tel2);
        // Création en base de l'entité Personne et de ses entités filles Telephone en cascade
        persJpa.create(pers);
        
        
        // B – Modification en cascade pour une relation @OneToMany
        
        PersonneJpaController persJpa = new PersonneJpaController();
        TelephoneJpaController telJpa = new TelephoneJpaController();
        // Récupération d'une entité racine Personne
        Personne pers = persJpa.findPersonneWithAll(16);
        // Modification de l'entité racine Personne
        pers.setPrenom("Chloé");
        pers.setDate_naissance(new Date (1991, 5, 3));
        // Modification de ses entités filles Telephone
        Telephone tel1 = pers.getListTelephones().get(0);
        Telephone tel2 = pers.getListTelephones().get(1);
        Telephone tel3 = new Telephone();
        tel3.setLibelle("Maison Campagne");
        tel3.setNumero("0265896561");
        // On modifie le numéro de tel1 possédé par Personne
        tel1.setNumero("0315832156");
        // On supprime tel2 de la liste des telephones possédés par Personne
        pers.removeTelephone(tel2);
        // On est obligé de modifier l'entité fille manuellement car la cascade sur 
        // l'entité Personne ne se propage que sur les entités filles présentes dans sa liste
        try {
            telJpa.edit(tel2);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        }
        // On ajoute tel3 à la liste des téléphones possédés par Personne
        pers.addTelephone(tel3);
        // Modification en base de l'entité Personne et de ses entités filles Telephone en cascade
        try {
            persJpa.edit(pers);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        }

        // C - Suppression en cascade des entités filles orphelines pour une relation @OneToMany
        
        PersonneJpaController persJpa = new PersonneJpaController();
        TelephoneJpaController telJpa = new TelephoneJpaController();
        // Récupération d'une entité racine Personne
        Personne pers = persJpa.findPersonneWithAll(16);
        // Modification de l'entité racine Personne
        pers.setPrenom("Chloé");
        pers.setDate_naissance(new Date (1991, 5, 3));
        // Modification de ses entités filles Telephone
        Telephone tel1 = pers.getListTelephones().get(0);
        Telephone tel2 = pers.getListTelephones().get(1);
        Telephone tel3 = new Telephone();
        tel3.setLibelle("Maison Campagne");
        tel3.setNumero("0265896561");
        // On modifie le numéro de tel1 possédé par Personne
        tel1.setNumero("0315832156");
        // On supprime tel2 de la liste des telephones possédés par Personne
        // La suppression de cette relation va entrainer la suppression de tel2 en base lors du edit()
        // car orphanRemoval=true sur la relation @OneToMany de Personne vers Telephone
        pers.removeTelephone(tel2);
        // On ajoute tel3 à la liste des téléphones possédés par Personne
        pers.addTelephone(tel3);
        // Modification en base de l'entité Personne et de ses entités filles Telephone en cascade
        try {
            persJpa.edit(pers);     // -> tel2 est supprimée de la base 
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
      /*
        // D - Suppression en cascade pour une relation @OneToMany
        
        PersonneJpaController persJpa = new PersonneJpaController();
        // Récupération d'une entité racine Personne
        Personne pers = persJpa.findPersonneWithAll(15);
        // Suppression de l'entité Personne et de ses entités filles Telephone en cascade
        try {
            persJpa.destroy(pers.getId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOneToMany.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
        
        */
        
        PersistenceFactory.close();

        
    }
}
