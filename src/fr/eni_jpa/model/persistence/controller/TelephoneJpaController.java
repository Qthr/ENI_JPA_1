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
import fr.eni_jpa.model.entity.Personne;
import fr.eni_jpa.model.entity.Telephone;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class TelephoneJpaController implements Serializable {

    public TelephoneJpaController() {
    }

    public EntityManager getEntityManager() {
        return PersistenceFactory.getEmf().createEntityManager();
    }

    public void create(Telephone telephone) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personne idPersonne = telephone.getIdPersonne();
            if (idPersonne != null) {
                idPersonne = em.getReference(idPersonne.getClass(), idPersonne.getId());
                telephone.setIdPersonne(idPersonne);
            }
            em.persist(telephone);
            if (idPersonne != null) {
                idPersonne.getListTelephones().add(telephone);
                idPersonne = em.merge(idPersonne);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telephone telephone) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telephone persistentTelephone = em.find(Telephone.class, telephone.getId());
            Personne idPersonneOld = persistentTelephone.getIdPersonne();
            Personne idPersonneNew = telephone.getIdPersonne();
            if (idPersonneNew != null) {
                idPersonneNew = em.getReference(idPersonneNew.getClass(), idPersonneNew.getId());
                telephone.setIdPersonne(idPersonneNew);
            }
            telephone = em.merge(telephone);
            if (idPersonneOld != null && !idPersonneOld.equals(idPersonneNew)) {
                idPersonneOld.getListTelephones().remove(telephone);
                idPersonneOld = em.merge(idPersonneOld);
            }
            if (idPersonneNew != null && !idPersonneNew.equals(idPersonneOld)) {
                idPersonneNew.getListTelephones().add(telephone);
                idPersonneNew = em.merge(idPersonneNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telephone.getId();
                if (findTelephone(id) == null) {
                    throw new NonexistentEntityException("The telephone with id " + id + " no longer exists.");
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
            Telephone telephone;
            try {
                telephone = em.getReference(Telephone.class, id);
                telephone.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telephone with id " + id + " no longer exists.", enfe);
            }
            Personne idPersonne = telephone.getIdPersonne();
            if (idPersonne != null) {
                idPersonne.getListTelephones().remove(telephone);
                idPersonne = em.merge(idPersonne);
            }
            em.remove(telephone);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telephone> findTelephoneEntities() {
        return findTelephoneEntities(true, -1, -1);
    }

    public List<Telephone> findTelephoneEntities(int maxResults, int firstResult) {
        return findTelephoneEntities(false, maxResults, firstResult);
    }

    private List<Telephone> findTelephoneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telephone.class));
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

    public Telephone findTelephone(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telephone.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelephoneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telephone> rt = cq.from(Telephone.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
