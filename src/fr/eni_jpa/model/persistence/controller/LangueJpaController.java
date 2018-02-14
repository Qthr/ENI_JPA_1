/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.persistence.controller;

import fr.eni_jpa.model.entity.Langue;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fr.eni_jpa.model.entity.Pays;
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
public class LangueJpaController implements Serializable {

    public LangueJpaController() {
    }

    public EntityManager getEntityManager() {
        return PersistenceFactory.getEmf().createEntityManager();
    }

    public void create(Langue langue) {
        if (langue.getListPays() == null) {
            langue.setListPays(new ArrayList<Pays>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pays> attachedListPays = new ArrayList<Pays>();
            for (Pays listPaysPaysToAttach : langue.getListPays()) {
                listPaysPaysToAttach = em.getReference(listPaysPaysToAttach.getClass(), listPaysPaysToAttach.getId());
                attachedListPays.add(listPaysPaysToAttach);
            }
            langue.setListPays(attachedListPays);
            em.persist(langue);
            for (Pays listPaysPays : langue.getListPays()) {
                listPaysPays.getListLangue().add(langue);
                listPaysPays = em.merge(listPaysPays);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Langue langue) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Langue persistentLangue = em.find(Langue.class, langue.getId());
            List<Pays> listPaysOld = persistentLangue.getListPays();
            List<Pays> listPaysNew = langue.getListPays();
            List<Pays> attachedListPaysNew = new ArrayList<Pays>();
            for (Pays listPaysNewPaysToAttach : listPaysNew) {
                listPaysNewPaysToAttach = em.getReference(listPaysNewPaysToAttach.getClass(), listPaysNewPaysToAttach.getId());
                attachedListPaysNew.add(listPaysNewPaysToAttach);
            }
            listPaysNew = attachedListPaysNew;
            langue.setListPays(listPaysNew);
            for (Pays listPaysOldPays : listPaysOld) {
                if (!listPaysNew.contains(listPaysOldPays)) {
                    listPaysOldPays.getListLangue().remove(langue);
                    listPaysOldPays = em.merge(listPaysOldPays);
                }
            }
            for (Pays listPaysNewPays : listPaysNew) {
                if (!listPaysOld.contains(listPaysNewPays)) {
                    listPaysNewPays.getListLangue().add(langue);
                    listPaysNewPays = em.merge(listPaysNewPays);
                }
            }
            langue = em.merge(langue);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = langue.getId();
                if (findLangue(id) == null) {
                    throw new NonexistentEntityException("The langue with id " + id + " no longer exists.");
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
            Langue langue;
            try {
                langue = em.getReference(Langue.class, id);
                langue.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The langue with id " + id + " no longer exists.", enfe);
            }
            List<Pays> listPays = langue.getListPays();
            for (Pays listPaysPays : listPays) {
                listPaysPays.getListLangue().remove(langue);
                listPaysPays = em.merge(listPaysPays);
            }
            em.remove(langue);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Langue> findLangueEntities() {
        return findLangueEntities(true, -1, -1);
    }

    public List<Langue> findLangueEntities(int maxResults, int firstResult) {
        return findLangueEntities(false, maxResults, firstResult);
    }

    private List<Langue> findLangueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Langue.class));
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

    public Langue findLangue(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Langue.class, id);
        } finally {
            em.close();
        }
    }
    

    public int getLangueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Langue> rt = cq.from(Langue.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Langue> findLangueByIdPays(Pays pays) {
        EntityManager em = getEntityManager();
        try {
            Query tq = em.createQuery("SELECT l FROM Langue l WHERE :pa MEMBER OF l.listPays ", Langue.class);
            return  tq.setParameter("pa", pays).getResultList();
        } finally {
            em.close();
        }
     }
    
     public Langue findLangueWithAll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Langue langue = em.find(Langue.class, id);
            Hibernate.initialize(langue.getListPays());  
            return langue;
        } finally {
            em.close();
        }
    }
    
}
