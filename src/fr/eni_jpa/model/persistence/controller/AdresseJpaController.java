/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.persistence.controller;

import fr.eni_jpa.model.entity.Adresse;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fr.eni_jpa.model.entity.Ville;
import fr.eni_jpa.model.entity.PersonneAdresse;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.exceptions.IllegalOrphanException;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class AdresseJpaController implements Serializable {

    public AdresseJpaController() {
    }

    public EntityManager getEntityManager() {
        return PersistenceFactory.getEmf().createEntityManager();
    }

    public void create(Adresse adresse) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ville ville = adresse.getVille();
            if (ville != null) {
                ville = em.getReference(ville.getClass(), ville.getId());
                adresse.setVille(ville);
            }
            em.persist(adresse);
            if (ville != null) {
                ville.getAdresseList().add(adresse);
                ville = em.merge(ville);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adresse adresse) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adresse persistentAdresse = em.find(Adresse.class, adresse.getId());
            Ville villeOld = persistentAdresse.getVille();
            Ville villeNew = adresse.getVille();
            if (villeNew != null) {
                villeNew = em.getReference(villeNew.getClass(), villeNew.getId());
                adresse.setVille(villeNew);
            }
            adresse = em.merge(adresse);
            if (villeOld != null && !villeOld.equals(villeNew)) {
                villeOld.getAdresseList().remove(adresse);
                villeOld = em.merge(villeOld);
            }
            if (villeNew != null && !villeNew.equals(villeOld)) {
                villeNew.getAdresseList().add(adresse);
                villeNew = em.merge(villeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adresse.getId();
                if (findAdresse(id) == null) {
                    throw new NonexistentEntityException("The adresse with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adresse adresse;
            try {
                adresse = em.getReference(Adresse.class, id);
                adresse.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adresse with id " + id + " no longer exists.", enfe);
            }
            Ville ville = adresse.getVille();
            if (ville != null) {
                ville.getAdresseList().remove(adresse);
                ville = em.merge(ville);
            }
            em.remove(adresse);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Adresse> findAdresseEntities() {
        return findAdresseEntities(true, -1, -1);
    }

    public List<Adresse> findAdresseEntities(int maxResults, int firstResult) {
        return findAdresseEntities(false, maxResults, firstResult);
    }

    private List<Adresse> findAdresseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adresse.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Adresse findAdresse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adresse.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdresseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adresse> rt = cq.from(Adresse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Adresse findAdresseWithAll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Adresse adr = em.find(Adresse.class, id);
            Hibernate.initialize(adr.getListPersonneAdresse());  
            return adr;
        } finally {
            em.close();
        }
    }
    
}
