/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa;

import fr.eni_jpa.model.entity.Langue;
import fr.eni_jpa.model.entity.Pays;
import fr.eni_jpa.model.entity.Personne;
import fr.eni_jpa.model.entity.PersonneAdresse;
import fr.eni_jpa.model.entity.PersonneDetail;
import fr.eni_jpa.model.entity.Telephone;
import fr.eni_jpa.model.entity.Ville;
import fr.eni_jpa.model.enumeration.Genre;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.LangueJpaController;
import fr.eni_jpa.model.persistence.controller.PaysJpaController;
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
public class TestRelationManyToMany {
    
        public static void main(String[] args){
          /* 
            // 5 – Cascade sur une relation @ManyToMany/@ManyToMany
          
            // A - Création
            
            PaysJpaController paysJpa = new PaysJpaController();
            // Création d'une entité racine Pays
            Pays pays = new Pays();
            pays.setContinent("Afrique");
            pays.setMonnaie("Franc CFA");
            pays.setNom("Togo");
            pays.setPopulation(13315451L);
            // Récupération de la langue Français appartenant déja au pays France, et création des langues Anglais et Ewé
            Langue anglais = new Langue();
            anglais.setNom("Anglais");
            Langue ewe = new Langue();
            ewe.setNom("Ewé");
            pays.addLangue(anglais);
            pays.addLangue(ewe);
            // Création de l'entité racine Pays et de ses entités filles Langue en cascade.
            paysJpa.create(pays);
            
            
            
            // B - Modification
            PaysJpaController paysJpa = new PaysJpaController();
            LangueJpaController langueJpa = new LangueJpaController();
            // Récupération du pays Togo
            Pays togo = paysJpa.findPaysWithAll(17);
            // On modifie le nombre d'habitant au Togo
            togo.setPopulation(651654354165L);
            // Cette modification est simplement traitée par le merge() dans la méthode edit()
            Langue langue1 = togo.getListLangue().get(0);
            Langue langue2 = langueJpa.findLangueWithAll(12);
            Langue langueNew = new Langue();
            langueNew.setNom("Kyabale");
            // On modifie langue1 : Anglais vers Français
            // Cette modification est simplement traitée par le merge() dans la méthode edit() grâce à la cascade
            langue1.setNom("Français");
            // On ajoute une nouvelle langue à la liste des langues de l'entité Pays
            // Cette modification est simplement traitée par le merge() dans la méthode edit() grâce à la cascade
            togo.addLangue(langueNew);
            // On supprime la liaison de l'entité Pays vers l'entité Langue langue2
            togo.removeLangue(langue2);
            // On utiliser un merge manuel (au travers de la méthode edit) sur l'entité fille langue2 pour prendre en compte la suppression de la relation
            try {
                langueJpa.edit(langue2);
            } catch (Exception ex) {
                Logger.getLogger(TestRelationManyToMany.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Modification de l'entité Pays qui entraine en cascade les 2 modifications (ajout et modification d'une entité Langue)
            // La 3eme modification (suppression d'une entité langue) a été traité manuellement avant car la cascade à partir
            // de l'entité racine Pays ne peut pas porter sur une entité absente de sa liste de Langue.
            try {
                paysJpa.edit(togo);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(TestRelationManyToMany.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TestRelationManyToMany.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            */
            
            
            
            PersistenceFactory.close();

        }
        
        
}
