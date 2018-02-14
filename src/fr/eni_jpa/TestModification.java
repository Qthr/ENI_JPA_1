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
public class TestModification {
        
    public static void main(String[] args){
        /*
        // 1 - Modification d’une entité : gérer les relations @OneToOne(optional=false)/@OneToOne entre une entité racine et une entité fille
        
        PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneDetail persDet = persDetJpa.findPersonneDetail(8);
        
        // pers1 est une entité Personne qui ne possède pas d'entité PersonneDetail
        // -> la modification de persDet n'entrainera pas d'IllegalOrphanException
        Personne pers1 = persJpa.findPersonneWithAll(6);
        persDet.setPersonne(pers1);
        try {
            persDetJpa.edit(persDet);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // pers2 est une entité Personne qui possède une entité PersonneDetail
        // -> la modification de persDet entrainera une IllegalOrphanException (encapsulée dans Exception ex)
        Personne pers2 = persJpa.findPersonne(9);
        persDet.setPersonne(pers2);
        try {
            persDetJpa.edit(persDet);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        // 2 - Suppression d’une entité : gérer les relations @OneToOne/@OneToOne entre une entité racine et une entité fille
        
        PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneDetail persDet = persDetJpa.findPersonneDetail(8);
        
        // pers1 est une entité Personne qui ne possède pas d'entité PersonneDetail
        // -> la modification de persDet n'entrainera pas d'IllegalOrphanException
        Personne pers1 = persJpa.findPersonneWithAll(6);
        persDet.setPersonne(pers1);
        try {
            persDetJpa.edit(persDet);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // pers2 est une entité Personne qui possède une entité PersonneDetail
        // -> la modification de persDet n'entrainera pas non plus IllegalOrphanException
        // L’ancienne entité PersonneDetail de pers2 aura simplement une référence vers Personne à null 
        Personne pers2 = persJpa.findPersonne(9);
        persDet.setPersonne(pers2);
        try {
            persDetJpa.edit(persDet);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        // 3 - Modification d’une entité : gérer les relations @OneToOne/@OneToOne(optional=false) entre une entité racine et une entité fille
       
        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneDetailJpaController persDetJpa = new PersonneDetailJpaController();
        Personne persAModifier = persJpa.findPersonneWithAll(8);
        Personne persSansDetail = persJpa.findPersonneWithAll(6);
        // Détache l'ancien PersonneDetail de persAModifier
        persAModifier.getPersonneDetail().setPersonne(persSansDetail);
        try {
            persDetJpa.edit(persAModifier.getPersonneDetail());
        } catch (fr.eni_jpa.exceptions.NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Modifie persAModifier en lui ajoutant un nouveau PersonneDetail
        PersonneDetail nouveauPersDet = persDetJpa.findPersonneDetail(9);
        persAModifier.setPersonneDetail(nouveauPersDet);
        try {
            persJpa.edit(persAModifier);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // pers1 est une entité Personne qui ne possède pas d'entité PersonneDetail
        // -> la modification de persDet n'entrainera pas d'IllegalOrphanException
        Personne pers1 = persJpa.findPersonneWithAll(6);
        persDet.setPersonne(pers1);
        

        
        // 4 - Modification d’une entité : gérer les relations @ManyToOne/@OneToMany entre une entité racine et une entité fille
        
        VilleJpaController villeJpa = new VilleJpaController();
        PaysJpaController paysJpa = new PaysJpaController();
        Pays france = paysJpa.findPaysWithAll(2);      // -> liste des villes de france : Paris, Chateaufort, Madrid
        Pays espagne = paysJpa.findPaysWithAll(5);     // -> liste des villes d'espagne : Barcelone, Seville
        // On veut récupérer la ville Madrid pour lui donner le bon pays, à savoir l'Espagne et enregistrer cette modification dans la bdd.
        // Cette modification doit avoir un impacte sur :
        //      - l'entité Ville Madrid 
        //      - la liste de l'entité Pays france (paysOld sans VilleJpaController)
        //      - la liste de l'entié Pays espagne (paysNew dans VilleJpaController)
        Ville madrid = villeJpa.findVilleWithAll(4);
        madrid.setPays(espagne);
        try {
            villeJpa.edit(madrid);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Villes de France:"+france.getListVille());  // -> liste des villes de france : Paris, Chateaufort, Madrid
        System.out.println("Villes d'Espagne:"+espagne.getListVille()); // -> liste des villes d'espagne : Barcelone, Seville
        // On remarque que les entités france et espagne n'ont pas été actualisées lors de l'appel à villeJpa.edit(madrid).
        // Il est nécessaire de les récupérer à nouveau en base pour les actualiser
        france = paysJpa.findPaysWithAll(2);      // -> liste des villes de france : Paris, Chateaufort
        espagne = paysJpa.findPaysWithAll(5);     // -> liste des villes d'espagne : Barcelone, Seville, Madrid
        
        
        //5 - Modification d’une entité : gérer les relations @OneToMany/@ManyToOne entre une entité racine et une entité fille
        
        // Exemple 1 : Entre l'entité racine Personne et les entités filles Telephones
        
        PersonneJpaController persJpa = new PersonneJpaController();
        TelephoneJpaController telJpa = new TelephoneJpaController();
        // On récupère une entité Personne qui possède les téléphones suivants : "Maison", "Portable, "Bureau"
        Personne pers = persJpa.findPersonneWithAll(6);
        // On va modifier les Téléphones de la personne
        // On récupère le téléphone bureau pour l'enlever de la liste des Telephones de l'entité Personne (ademettons que la personne n'a plus de travail)
        Telephone tel = telJpa.findTelephone(5);
        pers.getListTelephones().remove(tel);
        // On ajoute également à la liste un nouveau Telephone, celui de la maison de campagne de la Personne.
        Telephone maisonCampagne = new Telephone();
        maisonCampagne.setLibelle("Maison Campagne");
        maisonCampagne.setNumero("0435698525");
        telJpa.create(maisonCampagne);
        pers.getListTelephones().add(maisonCampagne);
        pers.setNom("Copin");
        // On modifie l'entité Personne pour mettre à jour sa nouvelle liste de Telephones
        try {
            persJpa.edit(pers);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        // Exemple 2 : Entre l'entité racine Ville et les entités filles Adresse
        
        VilleJpaController villeJpa = new VilleJpaController();
        AdresseJpaController adrJpa = new AdresseJpaController();
        
        Ville v = villeJpa.findVilleWithAll(2);
        Adresse adr = adrJpa.findAdresseWithAll(7);
        v.getAdresseList().remove(adr);
        Adresse adr2 = new Adresse();
        adr2.setLibelle("ALOLAOLOA");
        adrJpa.create(adr2);
        v.getAdresseList().add(adr2);
        try {
            villeJpa.edit(v);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // 6 – Modification d’une entité : gérer les relations @OneToMany/@ManyToOne(optional = false) entre une entité racine et une entité fille
        
        PersonneJpaController persJpa = new PersonneJpaController();
        PersonneAdresseJpaController persAdrJpa = new PersonneAdresseJpaController();
        AdresseJpaController adrJpa = new AdresseJpaController();
        
        // On récupère une entité Personne Germain qui possède les PersonneAdresse suivantes : 1 rue de trappes/Germain (principale) et rue de la madonne/Germain (secondaire).
        Personne pers = persJpa.findPersonneWithAll(8);
        // On récupère la personneAdresse rue de la madonne/Germain (secondaire) pour la supprimer à la liste des PersonneAdresse de l'entité Personne
        PersonneAdresse persAdr = persAdrJpa.findPersonneAdresse(new PersonneAdressePK(7,8));
        pers.getListPersonneAdresse().remove(persAdr);
        // On crée une nouvelle PersonneAdresse pour l'ajouter à la liste des PersonneAdresse de l'entité Personne
        PersonneAdresse persAdr2 = new PersonneAdresse();
        Adresse adr = adrJpa.findAdresse(8);
        persAdr2.setAdresse(adr);
        persAdr2.setPersonne(pers);
        persAdr2.setDebut(new Date(2017, 2, 5));   
        persAdr2.setFin(new Date(2025, 2, 5));
        persAdr2.setPrincipale(false);
        persAdr2.setPersonneAdressePK(new PersonneAdressePK(adr.getId(), pers.getId()));
        try {
            persAdrJpa.create(persAdr2);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        pers.getListPersonneAdresse().add(persAdr2);
        // On tente de persister la modification apportée à Personne
        // -> Lève une IllegalOrphanException car on tente de supprimer un lien obligatoire de la PersonneAdresse rue de la madonne/Germain (principale) avec l'entité Personne.
        // Pour éviter cette erreur, il aurait d'abord fallu donner une autre Personne à l'entité PersonneAdresse, ou supprimer carrement la PersonneAdresse.
        try {
            persJpa.edit(pers);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        
        // 7 - Modification d’une entité : gérer les relations @ManyToMany/@ManyToMany entre une entité racine et une entité fille
        
        LangueJpaController langueJpa = new LangueJpaController();
        PaysJpaController paysJpa = new PaysJpaController();
        
        // Récupération de la langue "Espagnol" qui possède 3 entités Pays : Espagne, Mexique et France
        Langue espagnol = langueJpa.findLangueWithAll(4);
        espagnol.getListPays().stream().forEach(p -> System.out.print(p.getNom()+" / "));
        
         // Récupération de la langue "Français" qui possède 2 entités Pays : France, Sénégal (témoin)
        Langue français = langueJpa.findLangueWithAll(7);
        français.getListPays().stream().forEach(p -> System.out.print(p.getNom()+" / "));
      
        // On va supprimer l'entité Pays France de la liste des pays de la langue Espagnol
        Pays france = paysJpa.findPaysWithAll(2);
        espagnol.getListPays().remove(france);
        
        // On va ajouter le pays Argentine à la liste des pays de la langue Espagnol
        Pays argentine = new Pays();
        argentine.setNom("Argentine");
        argentine.setContinent("Amérique");
        argentine.setMonnaie("Peso argentin");
        argentine.setPopulation(1651323145L);
        paysJpa.create(argentine);
        espagnol.getListPays().add(argentine);
        
        try {
            // On modifie la langue Espagnol
            langueJpa.edit(espagnol);
        } catch (Exception ex) {
            Logger.getLogger(TestModification.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // A la fin les pays de la langue Espagnol sont : Espagne, Mexique, Argentine
        espagnol = langueJpa.findLangueWithAll(4);
        espagnol.getListPays().stream().forEach(p -> System.out.print(p.getNom()+" / "));
        
        // Et ceux de la langue français n'ont pas changé : France, Sénégal
        français = langueJpa.findLangueWithAll(7);
        français.getListPays().stream().forEach(p -> System.out.print(p.getNom()+" / "));
        */
        
        PersistenceFactory.close();

        
    }
}
