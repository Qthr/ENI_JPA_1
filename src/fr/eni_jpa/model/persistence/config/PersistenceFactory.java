/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.persistence.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author martial
 */
public class PersistenceFactory {
    
    private static EntityManagerFactory emf = null;

    private PersistenceFactory() {
    }
    
    public static synchronized EntityManagerFactory getEmf() {
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("ENI_JPA_1PU");
        }
        return emf;
    }
    
    public static synchronized void close(){
        if(emf != null){
            emf.close();
            emf = null;
        }
    }
    
    
}
