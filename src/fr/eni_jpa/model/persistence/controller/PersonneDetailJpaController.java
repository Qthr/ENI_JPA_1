/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.persistence.controller;

import fr.eni_jpa.model.entity.Personne;
import fr.eni_jpa.model.entity.PersonneDetail;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.exceptions.IllegalOrphanException;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import fr.eni_jpa.model.persistence.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class PersonneDetailJpaController implements Serializable {

    public PersonneDetailJpaController() {
    }

    public EntityManager getEntityManager() {
        return PersistenceFactory.getEmf().createEntityManager();
    }

    public void create(PersonneDetail personneDetail) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(personneDetail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonneDetail(personneDetail.getIdPersonne()) != null) {
                throw new PreexistingEntityException("PersonneDetail " + personneDetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/*
    public void edit(PersonneDetail personneDetail) throws fr.eni_jpa.exceptions.IllegalOrphanException, fr.eni_jpa.exceptions.NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonneDetail persistentPersonneDetail = em.find(PersonneDetail.class, personneDetail.getIdPersonne());
            Personne personneOld = persistentPersonneDetail.getPersonne();
            Personne personneNew = personneDetail.getPersonne();
            List<String> illegalOrphanMessages = null;
            if (personneNew != null && !personneNew.equals(personneOld)) {
                PersonneDetail oldPersonneDetailOfPersonne = personneNew.getPersonneDetail();
                if (oldPersonneDetailOfPersonne != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Personne " + personneNew + " already has an item of type PersonneDetail whose personne column cannot be null. Please make another selection for the personne field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new fr.eni_jpa.exceptions.IllegalOrphanException(illegalOrphanMessages);
            }
            if (personneNew != null) {
                personneNew = em.getReference(personneNew.getClass(), personneNew.getId());
                personneDetail.setPersonne(personneNew);
            }
            if (personneOld != null && !personneOld.equals(personneNew)) {
                personneOld.setPersonneDetail(null);
                personneOld = em.merge(personneOld);
            }
            if (personneNew != null && !personneNew.equals(personneOld)) {
                personneNew.setPersonneDetail(personneDetail);
                personneNew = em.merge(personneNew);
            }
            personneDetail = em.merge(personneDetail);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personneDetail.getIdPersonne();
                if (findPersonneDetail(id) == null) {
                    throw new fr.eni_jpa.exceptions.NonexistentEntityException("The personneDetail with id " + id + " no longer exists.");
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
            PersonneDetail personneDetail;
            try {
                personneDetail = em.getReference(PersonneDetail.class, id);
                personneDetail.getIdPersonne();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personneDetail with id " + id + " no longer exists.", enfe);
            }
            personneDetail.getPersonne().setPersonneDetail(null);
            em.remove(personneDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public List<PersonneDetail> findPersonneDetailEntities() {
        return findPersonneDetailEntities(true, -1, -1);
    }

    public List<PersonneDetail> findPersonneDetailEntities(int maxResults, int firstResult) {
        return findPersonneDetailEntities(false, maxResults, firstResult);
    }

    private List<PersonneDetail> findPersonneDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonneDetail.class));
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

    public PersonneDetail findPersonneDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonneDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonneDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonneDetail> rt = cq.from(PersonneDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
   
}
