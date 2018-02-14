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
import fr.eni_jpa.model.persistence.controller.AdresseJpaController;
import fr.eni_jpa.model.persistence.controller.LangueJpaController;
import fr.eni_jpa.model.persistence.controller.PaysJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneAdresseJpaController;
import fr.eni_jpa.model.persistence.controller.PersonneJpaController;
import fr.eni_jpa.model.persistence.controller.TelephoneJpaController;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class TestRecupération {
    
    public static void main(String[] args){
       
       /* 
        // 1 – Récupération d’une entité avec une clé primaire simple
       
        AdresseJpaController adrJpa = new AdresseJpaController();
        Adresse adr = adrJpa.findAdresse(3);
        System.out.println(adr);
    
      
        // 2 – Récupération d’une liste d’entités
        
        AdresseJpaController adrJpa = new AdresseJpaController();

        // Toutes les adresses
        ArrayList<Adresse> allAdresses = (ArrayList<Adresse>) adrJpa.findAdresseEntities();
        allAdresses.stream().forEach(System.out::println);
       
        // N adresses à partir de l’adresse n°X. Avec N = 2 et X = 1
        ArrayList<Adresse> someAdresses = (ArrayList<Adresse>) adrJpa.findAdresseEntities(2, 1);
        someAdresses.stream().forEach(System.out::println);
        
        
        // 3 – Récupération d’une entité avec une clé primaire composée
        PersonneAdresseJpaController persAdrJpa = new PersonneAdresseJpaController();
        PersonneAdresse persAdr = persAdrJpa.findPersonneAdresse(new PersonneAdressePK(7, 3));
        System.out.println(persAdr);
           
        
        // 4 – Récupération d’une entité depuis une entité déjà chargée
        
        PersonneJpaController persJpa = new PersonneJpaController();
        Personne pers = persJpa.findPersonneWithTelephone(5);
        // A - Accès à une entité simple de l’entité récupérée : FetchType.EAGER
        System.out.println(pers.getPrenom());
        // B - Accès à une liste d’entités de l’entité récupérée : FetchType.LAZY
        List<Telephone> telephones = pers.getListTelephones();
        telephones.stream().forEach(System.out::println);
       
       // 5 – Récupération d’une entité depuis une requête spécifique
       
       LangueJpaController langueJpa = new LangueJpaController();
       // On récupère le pays pour lequel on veut connaître les langues parlées.
       PaysJpaController paysJpa = new PaysJpaController();
       Pays mexique = paysJpa.findPays(6);
       // On utilise la nouvelle méthode de recherche ajoutée au contrôleur LangueJpaController        
       ArrayList<Langue> languesParleesMexique = (ArrayList<Langue>) langueJpa.findLangueByIdPays(mexique);
       // On affiche les langues parlées au Mexique
       languesParleesMexique.stream().forEach(System.out::println);*/
        
        
    }
}
