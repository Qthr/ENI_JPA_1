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
import fr.eni_jpa.model.entity.Ville;
import java.util.ArrayList;
import java.util.List;
import fr.eni_jpa.model.entity.Langue;
import fr.eni_jpa.model.entity.Pays;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.exceptions.IllegalOrphanException;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Hibernate;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class PaysJpaController implements Serializable {

    public PaysJpaController() {
    }

    public EntityManager getEntityManager() {
        return PersistenceFactory.getEmf().createEntityManager();
    }

    public void create(Pays pays) {
        if (pays.getListVille() == null) {
            pays.setListVille(new ArrayList<Ville>());
        }
        if (pays.getListLangue() == null) {
            pays.setListLangue(new ArrayList<Langue>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ville> attachedListVille = new ArrayList<Ville>();
            for (Ville listVilleVilleToAttach : pays.getListVille()) {
                listVilleVilleToAttach = em.getReference(listVilleVilleToAttach.getClass(), listVilleVilleToAttach.getId());
                attachedListVille.add(listVilleVilleToAttach);
            }
            pays.setListVille(attachedListVille);
            List<Langue> attachedListLangue = new ArrayList<Langue>();
            for (Langue listLangueLangueToAttach : pays.getListLangue()) {
                listLangueLangueToAttach = em.getReference(listLangueLangueToAttach.getClass(), listLangueLangueToAttach.getId());
                attachedListLangue.add(listLangueLangueToAttach);
            }
            pays.setListLangue(attachedListLangue);
            em.persist(pays);
            for (Ville listVilleVille : pays.getListVille()) {
                Pays oldPaysOfListVilleVille = listVilleVille.getPays();
                listVilleVille.setPays(pays);
                listVilleVille = em.merge(listVilleVille);
                if (oldPaysOfListVilleVille != null) {
                    oldPaysOfListVilleVille.getListVille().remove(listVilleVille);
                    oldPaysOfListVilleVille = em.merge(oldPaysOfListVilleVille);
                }
            }
            for (Langue listLangueLangue : pays.getListLangue()) {
                listLangueLangue.getListPays().add(pays);
                listLangueLangue = em.merge(listLangueLangue);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pays pays) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pays persistentPays = em.find(Pays.class, pays.getId());
            List<Ville> listVilleOld = persistentPays.getListVille();
            List<Ville> listVilleNew = pays.getListVille();
            List<Langue> listLangueOld = persistentPays.getListLangue();
            List<Langue> listLangueNew = pays.getListLangue();
            List<String> illegalOrphanMessages = null;
            for (Ville listVilleOldVille : listVilleOld) {
                if (!listVilleNew.contains(listVilleOldVille)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ville " + listVilleOldVille + " since its pays field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ville> attachedListVilleNew = new ArrayList<Ville>();
            for (Ville listVilleNewVilleToAttach : listVilleNew) {
                listVilleNewVilleToAttach = em.getReference(listVilleNewVilleToAttach.getClass(), listVilleNewVilleToAttach.getId());
                attachedListVilleNew.add(listVilleNewVilleToAttach);
            }
            listVilleNew = attachedListVilleNew;
            pays.setListVille(listVilleNew);
            List<Langue> attachedListLangueNew = new ArrayList<Langue>();
            for (Langue listLangueNewLangueToAttach : listLangueNew) {
                listLangueNewLangueToAttach = em.getReference(listLangueNewLangueToAttach.getClass(), listLangueNewLangueToAttach.getId());
                attachedListLangueNew.add(listLangueNewLangueToAttach);
            }
            listLangueNew = attachedListLangueNew;
            pays.setListLangue(listLangueNew);
            for (Ville listVilleNewVille : listVilleNew) {
                if (!listVilleOld.contains(listVilleNewVille)) {
                    Pays oldPaysOfListVilleNewVille = listVilleNewVille.getPays();
                    listVilleNewVille.setPays(pays);
                    listVilleNewVille = em.merge(listVilleNewVille);
                    if (oldPaysOfListVilleNewVille != null && !oldPaysOfListVilleNewVille.equals(pays)) {
                        oldPaysOfListVilleNewVille.getListVille().remove(listVilleNewVille);
                        oldPaysOfListVilleNewVille = em.merge(oldPaysOfListVilleNewVille);
                    }
                }
            }
            for (Langue listLangueOldLangue : listLangueOld) {
                if (!listLangueNew.contains(listLangueOldLangue)) {
                    listLangueOldLangue.getListPays().remove(pays);
                    listLangueOldLangue = em.merge(listLangueOldLangue);
                }
            }
            for (Langue listLangueNewLangue : listLangueNew) {
                if (!listLangueOld.contains(listLangueNewLangue)) {
                    listLangueNewLangue.getListPays().add(pays);
                    listLangueNewLangue = em.merge(listLangueNewLangue);
                }
            }
            pays = em.merge(pays);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pays.getId();
                if (findPays(id) == null) {
                    throw new NonexistentEntityException("The pays with id " + id + " no longer exists.");
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
            Pays pays;
            try {
                pays = em.getReference(Pays.class, id);
                pays.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pays with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ville> listVilleOrphanCheck = pays.getListVille();
            for (Ville listVilleOrphanCheckVille : listVilleOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pays (" + pays + ") cannot be destroyed since the Ville " + listVilleOrphanCheckVille + " in its listVille field has a non-nullable pays field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Langue> listLangue = pays.getListLangue();
            for (Langue listLangueLangue : listLangue) {
                listLangueLangue.getListPays().remove(pays);
                listLangueLangue = em.merge(listLangueLangue);
            }
            em.remove(pays);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pays> findPaysEntities() {
        return findPaysEntities(true, -1, -1);
    }

    public List<Pays> findPaysEntities(int maxResults, int firstResult) {
        return findPaysEntities(false, maxResults, firstResult);
    }

    private List<Pays> findPaysEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pays.class));
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

    public Pays findPays(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pays.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaysCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pays> rt = cq.from(Pays.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    public Pays findPaysWithAll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Pays pays = em.find(Pays.class, id);
            Hibernate.initialize(pays.getListVille());  
            Hibernate.initialize(pays.getListLangue());
            return pays;
        } finally {
            em.close();
        }
    }
    
}
