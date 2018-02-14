/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa;

import fr.eni_jpa.model.entity.Langue;
import fr.eni_jpa.model.entity.Personne;
import fr.eni_jpa.model.entity.PersonneAdresse;
import fr.eni_jpa.model.entity.PersonneDetail;
import fr.eni_jpa.model.entity.Telephone;
import fr.eni_jpa.model.entity.Ville;
import fr.eni_jpa.model.enumeration.Genre;
import fr.eni_jpa.model.persistence.controller.LangueJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneAdresseJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneDetailJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneJpaController;
import fr.eni_jpa.model.persistence.controller.TelephoneJpaController;
import fr.eni_jpa.model.persistence.controller.VilleJpaController;
import fr.eni_jpa.model.persistence.controller.exceptions.IllegalOrphanException;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class TestSuppression {
    
        public static void main(String[] args){
            /*
            // 1 - Suppression d’une entité : gérer les relations @OneToOne/@OneToOne entre une entité racine et une entité fille
            PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
            PersonneDetail persDet = persDetJpa.findPersonneDetail(6);
            // ...
            try {
                persDetJpa.destroy(persDet.getIdPersonne());
            } catch (NonexistentEntityException ex) {
                System.out.println("L'entité que vous souhaitez supprimer n'existe pas en base :"+ex.getMessage());
            }
          
            
            // 2 - Suppression d’une entité : gérer les relations @OneToOne/@OneToOne(optional=false) entre une entité racine et une entité fille
            PersonneJpaController persJpa = new PersonneJpaController();            
            Personne pers = persJpa.findPersonne(7);
            // ...
            PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
            try {
                persDetJpa.destroy(pers.getPersonneDetail().getIdPersonne());
                persJpa.destroy(pers.getId());
            } catch (NonexistentEntityException ex) {
                System.out.println("L'entité que vous souhaitez supprimer n'existe pas en base :"+ex.getMessage());
            } catch (IllegalOrphanException ex) {
                System.out.println("Vous ne pouvez pas supprimer cette entité Personne, une entité deviendrait orpheline :"+ex.getMessage());
            }
            
            
            // 3 - Suppression d’une entité : gérer les relations @ManyToOne/@OneToMany entre une entité racine et une entité fille
            
            VilleJpaController villeJpa = new VilleJpaController();
            Ville ville = villeJpa.findVille(1);
            // ...
            try {
                villeJpa.destroy(ville.getId());
            } catch (NonexistentEntityException ex) {
                System.out.println("L'entité que vous souhaitez supprimer n'existe pas en base :"+ex.getMessage());
            }
            
            // 4 - Suppression d’une entité : gérer les relations @OneToMany/@ManyToOne entre une entité racine et une entité fille
            PersonneJpaController persJpa = new PersonneJpaController();            
            Personne pers = persJpa.findPersonneWithTelephone(5);
            // ...
            TelephoneJpaController telJpa = new TelephoneJpaController();
            try {
                for(Telephone tel : pers.getListTelephones()){
                    telJpa.destroy(tel.getId());
                }
                persJpa.destroy(pers.getId());
            } catch (NonexistentEntityException ex) {
                System.out.println("L'entité que vous souhaitez supprimer n'existe pas en base :"+ex.getMessage());
            } catch (IllegalOrphanException ex) {
                System.out.println("Vous ne pouvez pas supprimer cette entité Personne, une entité deviendrait orpheline :"+ex.getMessage());
            }
            
            // 5 - Suppression d’une entité : gérer les relations @OneToMany/@ManyToOne(optional = false) entre une entité racine et une entité fille
            PersonneJpaController persJpa = new PersonneJpaController();            
            Personne pers = persJpa.findPersonneWithPersonneAdresse(3);
            // ...
            PersonneAdresseJpaController persAdrJpa = new PersonneAdresseJpaController();
            try {
                for(PersonneAdresse persAdr : pers.getListPersonneAdresse()){
                    persAdrJpa.destroy(persAdr.getPersonneAdressePK());
                }
                persJpa.destroy(pers.getId());
            } catch (NonexistentEntityException ex) {
                System.out.println("L'entité que vous souhaitez supprimer n'existe pas en base :"+ex.getMessage());
            } catch (IllegalOrphanException ex) {
                System.out.println("Vous ne pouvez pas supprimer cette entité Personne, une entité deviendrait orpheline :"+ex.getMessage());
            }
            
            
            // 6 - Suppression d’une entité : gérer les relations @ManyToMany/@ManyToMany entre une entité racine et une entité fille
            LangueJpaController langueJpa = new LangueJpaController();            
            Langue langue = langueJpa.findLangue(4);
            try {
                // ...
                langueJpa.destroy(langue.getId());
            } catch (NonexistentEntityException ex) {
                System.out.println("L'entité que vous souhaitez supprimer n'existe pas en base :"+ex.getMessage());
            }
            */
        }
}
