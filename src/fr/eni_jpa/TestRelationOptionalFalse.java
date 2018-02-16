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
        
       // 1  – Exemple : la relation @OneToOne/@OneToOne(optional=false) entre Personne et PersonneDetail

       // A - Modification
        
        // a - 1ere solution 
        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
        // Récupère une entité Personne
        Personne pers = persJpa.findPersonneWithAll(11);
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
        
        
        PersistenceFactory.close();

        
    }
}
