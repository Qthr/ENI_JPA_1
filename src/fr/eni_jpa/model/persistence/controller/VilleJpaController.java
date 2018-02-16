/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.persistence.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fr.eni_jpa.model.entity.Pays;
import fr.eni_jpa.model.entity.Adresse;
import fr.eni_jpa.model.entity.Ville;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
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
public class VilleJpaController implements Serializable {

    public VilleJpaController() {
    }

    public EntityManager getEntityManager() {
        return PersistenceFactory.getEmf().createEntityManager();
    }

    public void create(Ville ville) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pays pays = ville.getPays();
            if (pays != null) {
                pays = em.getReference(pays.getClass(), pays.getId());
                ville.setPays(pays);
            }
            em.persist(ville);
            if (pays != null) {
                pays.getListVille().add(ville);
                pays = em.merge(pays);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ville ville) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ville persistentVille = em.find(Ville.class, ville.getId());
            Pays paysOld = persistentVille.getPays();
            Pays paysNew = ville.getPays();
            if (paysNew != null) {
                paysNew = em.getReference(paysNew.getClass(), paysNew.getId());
                ville.setPays(paysNew);
            }
            if (paysOld != null && !paysOld.equals(paysNew)) {
                paysOld.getListVille().remove(ville);
                paysOld = em.merge(paysOld);
            }
            if (paysNew != null && !paysNew.equals(paysOld)) {
                paysNew.getListVille().add(ville);
                paysNew = em.merge(paysNew);
            }
            ville = em.merge(ville);   
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ville.getId();
                if (findVille(id) == null) {
                    throw new NonexistentEntityException("The ville with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ville ville;
            try {
                ville = em.getReference(Ville.class, id);
                ville.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ville with id " + id + " no longer exists.", enfe);
            }
            Pays pays = ville.getPays();
            if (pays != null) {
                pays.getListVille().remove(ville);
                pays = em.merge(pays);
            }
            em.remove(ville);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ville> findVilleEntities() {
        return findVilleEntities(true, -1, -1);
    }

    public List<Ville> findVilleEntities(int maxResults, int firstResult) {
        return findVilleEntities(false, maxResults, firstResult);
    }

    private List<Ville> findVilleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ville.class));
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

    public Ville findVille(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ville.class, id);
        } finally {
            em.close();
        }
    }

    public int getVilleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ville> rt = cq.from(Ville.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Ville findVilleWithAll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Ville ville = em.find(Ville.class, id);
            Hibernate.initialize(ville.getAdresseList());  
            return ville;
        } finally {
            em.close();
        }
    }
    
}
