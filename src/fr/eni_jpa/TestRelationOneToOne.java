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
        // 1 - Relation OneToOne entre Personne et PersonneDetail
        
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
        
        
        // C - Suppression en cascade pour une relation @OneToOne
        
        PersonneJpaController persJpa = new PersonneJpaController();
        // Récupère la personne Any
        Personne pers = persJpa.findPersonneWithAll(12);
        // Suppression de l'entité Personne et de son entité fille PersonneDetail en cascade
        try {
            persJpa.destroy(pers.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestRelationOneToOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // D - Suppression en cascade des entités filles orphelines 
        PersonneJpaController persJpa = new PersonneJpaController();
        // Récupère la personne
        Personne pers = persJpa.findPersonneWithAll(11);
        // Suppression de la liaison entre l'entité Personne et son entité fille PersonneDetail
        // qui entraine en cascade la suppression de l'entité fille PersonneDetail devenue orpheline
        pers.removePersonneDetail();
        */
        
        /*
        // 3 – Création d’une entité : gérer les relations @ManyToMany - liste d’entités filles avec table de jointure
        
        PaysJpaController paysJpa = new PaysJpaController();
        LangueJpaController langueJpa = new LangueJpaController();
        // Création de deux entités Pays
        Pays p1 = new Pays();
        p1.setNom("Espagne");
        p1.setContinent("Europe");
        p1.setMonnaie("Euro");
        p1.setPopulation(46560000L);
        Pays p2 = new Pays();
        p2.setNom("Mexique");
        p2.setContinent("Amérique");
        p2.setMonnaie("Mexican peso");
        p2.setPopulation(127500000L);
        // Persistance de l'entité Pays
        paysJpa.create(p1);
        paysJpa.create(p2);
        // Création des entités Langue
        Langue l1 = new Langue();
        l1.setNom("Espagnol");
        Langue l2 = new Langue();
        l2.setNom("Catalan");
        Langue l3 = new Langue();
        l3.setNom("Nahuatl");
        // Ajout des entités Pays persistées, à chaque entité Langue : on ajoute un pays dans une langue à chaque fois que ce pays parle cette langue.
        ArrayList<Pays> paysParlantEspagnol = new ArrayList<>();
        paysParlantEspagnol.add(p1);
        paysParlantEspagnol.add(p2);
        l1.setListPays(paysParlantEspagnol);
        ArrayList<Pays> paysParlantCatalan = new ArrayList<>();
        paysParlantCatalan.add(p1);
        l2.setListPays(paysParlantCatalan);
        ArrayList<Pays> paysParlantNahutl = new ArrayList<>();
        paysParlantNahutl.add(p2);
        l3.setListPays(paysParlantNahutl);
        // Peristsance des entités Langue avec établissement des liaisons entre les différents Pays/Langue dans la table de jointure pays_langue.
        langueJpa.create(l1);       // Persistance de la langue "Espagnol" et liaison avec les pays "Espagne" et "Mexique"
        langueJpa.create(l2);       // Persistance de la langue "Catalan" et liaison avec le pays "Espagne"
        langueJpa.create(l3);       // Persistance de la langue "Nahuatl" et laision avec le pays "Mexique"
        
       
        // 4 – Création d’une entité : gérer le cas des entités avec clé primaire composée
        
        PersonneAdresseJpaController persAdrJpa = new PersonneAdresseJpaController();
        PersonneJpaController persJpa = new PersonneJpaController();
        AdresseJpaController adrJpa = new AdresseJpaController();
        // Création des entités Personne et Adresse
        Personne pers = new Personne();
        pers.setNom("Therre");
        pers.setPrenom("Quentin");
        pers.setDate_naissance(new Date(1993, 4, 29));
        Adresse adr1 = new Adresse();
        adr1.setLibelle("1 rue de Trappes");
        Adresse adr2 = new Adresse();
        adr2.setLibelle("16 rue du lyon");
        // Persistance des entités Personne et Adresse qui permet également de récupérer les id auto-générés (utiles pour persister PersonneAdresse)
        persJpa.create(pers);
        adrJpa.create(adr1);
        adrJpa.create(adr2);
        // Création de deux entités PersonneAdresse pour pers:Quentin / adr:1 rue de trappes et pers:Quentin / adr:16 rue du lyon
        PersonneAdresse persAdr1 = new PersonneAdresse();
        persAdr1.setPersonneAdressePK(new PersonneAdressePK(pers.getId(), adr1.getId()));   // on crée la clé primaire composée à partir des id-autogénérés de Personne et Adresse
        persAdr1.setDebut(new Date(2000, 1, 1));
        persAdr1.setPrincipale(true);
        persAdr1.setPersonne(pers);
        persAdr1.setAdresse(adr1);
        PersonneAdresse persAdr2 = new PersonneAdresse();
        persAdr2.setPersonneAdressePK(new PersonneAdressePK(pers.getId(), adr2.getId()));
        persAdr2.setDebut(new Date(2017, 9, 1));
        persAdr2.setFin(new Date(2018, 3, 1));
        persAdr2.setPrincipale(false);
        persAdr2.setPersonne(pers);
        persAdr2.setAdresse(adr2);
        // Persistance des entités PersonneAdresse liés par leur clé primaire composé aux couples Personne/Adresse
        try {
            persAdrJpa.create(persAdr1);
            persAdrJpa.create(persAdr2);
        } catch (Exception ex) {
            System.out.println("Erreur lors de l'ajout d'une entité PersonneAdresse en base : "+ex.getMessage());
        }
        
    
        
        // 5 - Création d’une entité : relation @OneToOne avec identifiant partagé - entité fille possédant la même clé primaire que l’entité racine

        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
        // Création de l'entités Personne
        Personne pers = new Personne();
        pers.setNom("Therre");
        pers.setPrenom("Germain");
        pers.setDate_naissance(new Date(1994, 6, 18)); 
        // Persistance de l'entité Personne qui permet également de récupérer l'id auto-générés (utiles pour persister PersonneDetail)
        persJpa.create(pers);
        // Création de l'entité PersonneDetail
        PersonneDetail persDet = new PersonneDetail();
        persDet.setIdPersonne(pers.getId());
        persDet.setNumSecu("65130316561");
        persDet.setSexe(Genre.HOMME);
        persDet.setPersonne(pers);
        // Ajout manuel de l'entité PersonneDetail créée à l'entité Personne
        pers.setPersonneDetail(persDet);
        try {
            // Persistance de l'entité PersonneDetail lié par sa clé primaire à l'entité Personne
            persDetJpa.create(persDet);
        } catch (Exception ex) {
            System.out.println("Erreur lors de l'ajout d'une entité PersonneDetail en base : "+ex.getMessage());
        }
        
       
       /*
        // 3 – Création d’une entité : gérer les relations @ManyToMany - liste d’entités filles avec table de jointure
        
        PaysJpaController paysJpa = new PaysJpaController();
        LangueJpaController langueJpa = new LangueJpaController();
        // Création de deux entités Pays
        Pays p1 = new Pays();
        p1.setNom("Espagne");
        p1.setContinent("Europe");
        p1.setMonnaie("Euro");
        p1.setPopulation(46560000L);
        Pays p2 = new Pays();
        p2.setNom("Mexique");
        p2.setContinent("Amérique");
        p2.setMonnaie("Mexican peso");
        p2.setPopulation(127500000L);
        // Persistance de l'entité Pays
        paysJpa.create(p1);
        paysJpa.create(p2);
        // Création des entités Langue
        Langue l1 = new Langue();
        l1.setNom("Espagnol");
        Langue l2 = new Langue();
        l2.setNom("Catalan");
        Langue l3 = new Langue();
        l3.setNom("Nahuatl");
        // Ajout des entités Pays persistées, à chaque entité Langue : on ajoute un pays dans une langue à chaque fois que ce pays parle cette langue.
        ArrayList<Pays> paysParlantEspagnol = new ArrayList<>();
        paysParlantEspagnol.add(p1);
        paysParlantEspagnol.add(p2);
        l1.setListPays(paysParlantEspagnol);
        ArrayList<Pays> paysParlantCatalan = new ArrayList<>();
        paysParlantCatalan.add(p1);
        l2.setListPays(paysParlantCatalan);
        ArrayList<Pays> paysParlantNahutl = new ArrayList<>();
        paysParlantNahutl.add(p2);
        l3.setListPays(paysParlantNahutl);
        // Peristsance des entités Langue avec établissement des liaisons entre les différents Pays/Langue dans la table de jointure pays_langue.
        langueJpa.create(l1);       // Persistance de la langue "Espagnol" et liaison avec les pays "Espagne" et "Mexique"
        langueJpa.create(l2);       // Persistance de la langue "Catalan" et liaison avec le pays "Espagne"
        langueJpa.create(l3);       // Persistance de la langue "Nahuatl" et laision avec le pays "Mexique"
        
       
        // 4 – Création d’une entité : gérer le cas des entités avec clé primaire composée
        
        PersonneAdresseJpaController persAdrJpa = new PersonneAdresseJpaController();
        PersonneJpaController persJpa = new PersonneJpaController();
        AdresseJpaController adrJpa = new AdresseJpaController();
        // Création des entités Personne et Adresse
        Personne pers = new Personne();
        pers.setNom("Therre");
        pers.setPrenom("Quentin");
        pers.setDate_naissance(new Date(1993, 4, 29));
        Adresse adr1 = new Adresse();
        adr1.setLibelle("1 rue de Trappes");
        Adresse adr2 = new Adresse();
        adr2.setLibelle("16 rue du lyon");
        // Persistance des entités Personne et Adresse qui permet également de récupérer les id auto-générés (utiles pour persister PersonneAdresse)
        persJpa.create(pers);
        adrJpa.create(adr1);
        adrJpa.create(adr2);
        // Création de deux entités PersonneAdresse pour pers:Quentin / adr:1 rue de trappes et pers:Quentin / adr:16 rue du lyon
        PersonneAdresse persAdr1 = new PersonneAdresse();
        persAdr1.setPersonneAdressePK(new PersonneAdressePK(pers.getId(), adr1.getId()));   // on crée la clé primaire composée à partir des id-autogénérés de Personne et Adresse
        persAdr1.setDebut(new Date(2000, 1, 1));
        persAdr1.setPrincipale(true);
        persAdr1.setPersonne(pers);
        persAdr1.setAdresse(adr1);
        PersonneAdresse persAdr2 = new PersonneAdresse();
        persAdr2.setPersonneAdressePK(new PersonneAdressePK(pers.getId(), adr2.getId()));
        persAdr2.setDebut(new Date(2017, 9, 1));
        persAdr2.setFin(new Date(2018, 3, 1));
        persAdr2.setPrincipale(false);
        persAdr2.setPersonne(pers);
        persAdr2.setAdresse(adr2);
        // Persistance des entités PersonneAdresse liés par leur clé primaire composé aux couples Personne/Adresse
        try {
            persAdrJpa.create(persAdr1);
            persAdrJpa.create(persAdr2);
        } catch (Exception ex) {
            System.out.println("Erreur lors de l'ajout d'une entité PersonneAdresse en base : "+ex.getMessage());
        }
        
    
        
        // 5 - Création d’une entité : relation @OneToOne avec identifiant partagé - entité fille possédant la même clé primaire que l’entité racine

        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
        // Création de l'entités Personne
        Personne pers = new Personne();
        pers.setNom("Therre");
        pers.setPrenom("Germain");
        pers.setDate_naissance(new Date(1994, 6, 18)); 
        // Persistance de l'entité Personne qui permet également de récupérer l'id auto-générés (utiles pour persister PersonneDetail)
        persJpa.create(pers);
        // Création de l'entité PersonneDetail
        PersonneDetail persDet = new PersonneDetail();
        persDet.setIdPersonne(pers.getId());
        persDet.setNumSecu("65130316561");
        persDet.setSexe(Genre.HOMME);
        persDet.setPersonne(pers);
        // Ajout manuel de l'entité PersonneDetail créée à l'entité Personne
        pers.setPersonneDetail(persDet);
        try {
            // Persistance de l'entité PersonneDetail lié par sa clé primaire à l'entité Personne
            persDetJpa.create(persDet);
        } catch (Exception ex) {
            System.out.println("Erreur lors de l'ajout d'une entité PersonneDetail en base : "+ex.getMessage());
        }
        
       
       */
        
        PersistenceFactory.close();

        
    }
}
