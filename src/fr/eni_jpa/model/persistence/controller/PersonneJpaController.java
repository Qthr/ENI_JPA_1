/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.eni_jpa.model.persistence.controller;

import fr.eni_jpa.model.entity.Personne;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import fr.eni_jpa.model.entity.Telephone;
import java.util.ArrayList;
import java.util.List;
import fr.eni_jpa.model.entity.PersonneAdresse;
import fr.eni_jpa.model.entity.PersonneDetail;
import fr.eni_jpa.model.persistence.config.PersistenceFactory;
import fr.eni_jpa.model.persistence.controller.exceptions.IllegalOrphanException;
import fr.eni_jpa.model.persistence.controller.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.hibernate.Hibernate;

/**
 *
 * @author Quentin Therre <quentin.therre@novarea-tec.com>
 */
public class PersonneJpaController implements Serializable {

    public PersonneJpaController() {
    }

    public EntityManager getEntityManager() {
        return PersistenceFactory.getEmf().createEntityManager();
    }

    public void create(Personne personne) {
        if (personne.getListTelephones() == null) {
            personne.setListTelephones(new ArrayList<Telephone>());
        }
        if (personne.getListPersonneAdresse() == null) {
            personne.setListPersonneAdresse(new ArrayList<PersonneAdresse>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Telephone> attachedListTelephones = new ArrayList<Telephone>();
            for (Telephone listTelephonesTelephoneToAttach : personne.getListTelephones()) {
                listTelephonesTelephoneToAttach = em.getReference(listTelephonesTelephoneToAttach.getClass(), listTelephonesTelephoneToAttach.getId());
                attachedListTelephones.add(listTelephonesTelephoneToAttach);
            }
            personne.setListTelephones(attachedListTelephones);
            List<PersonneAdresse> attachedListPersonneAdresse = new ArrayList<PersonneAdresse>();
            for (PersonneAdresse listPersonneAdressePersonneAdresseToAttach : personne.getListPersonneAdresse()) {
                listPersonneAdressePersonneAdresseToAttach = em.getReference(listPersonneAdressePersonneAdresseToAttach.getClass(), listPersonneAdressePersonneAdresseToAttach.getPersonneAdressePK());
                attachedListPersonneAdresse.add(listPersonneAdressePersonneAdresseToAttach);
            }
            personne.setListPersonneAdresse(attachedListPersonneAdresse);
            em.persist(personne);
            for (Telephone listTelephonesTelephone : personne.getListTelephones()) {
                Personne oldIdPersonneOfListTelephonesTelephone = listTelephonesTelephone.getIdPersonne();
                listTelephonesTelephone.setIdPersonne(personne);
                listTelephonesTelephone = em.merge(listTelephonesTelephone);
                if (oldIdPersonneOfListTelephonesTelephone != null) {
                    oldIdPersonneOfListTelephonesTelephone.getListTelephones().remove(listTelephonesTelephone);
                    oldIdPersonneOfListTelephonesTelephone = em.merge(oldIdPersonneOfListTelephonesTelephone);
                }
            }
            for (PersonneAdresse listPersonneAdressePersonneAdresse : personne.getListPersonneAdresse()) {
                Personne oldPersonneOfListPersonneAdressePersonneAdresse = listPersonneAdressePersonneAdresse.getPersonne();
                listPersonneAdressePersonneAdresse.setPersonne(personne);
                listPersonneAdressePersonneAdresse = em.merge(listPersonneAdressePersonneAdresse);
                if (oldPersonneOfListPersonneAdressePersonneAdresse != null) {
                    oldPersonneOfListPersonneAdressePersonneAdresse.getListPersonneAdresse().remove(listPersonneAdressePersonneAdresse);
                    oldPersonneOfListPersonneAdressePersonneAdresse = em.merge(oldPersonneOfListPersonneAdressePersonneAdresse);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personne personne) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personne persistentPersonne = em.find(Personne.class, personne.getId());
            List<Telephone> telephoneListOld = persistentPersonne.getListTelephones();
            List<Telephone> telephoneListNew = personne.getListTelephones();
            List<PersonneAdresse> personneAdresseListOld = persistentPersonne.getListPersonneAdresse();
            List<PersonneAdresse> personneAdresseListNew = personne.getListPersonneAdresse();
            List<String> illegalOrphanMessages = null;
            for (PersonneAdresse personneAdresseListOldPersonneAdresse : personneAdresseListOld) {
                if (!personneAdresseListNew.contains(personneAdresseListOldPersonneAdresse)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersonneAdresse " + personneAdresseListOldPersonneAdresse + " since its personne field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Telephone> attachedTelephoneListNew = new ArrayList<Telephone>();
            for (Telephone telephoneListNewTelephoneToAttach : telephoneListNew) {
                telephoneListNewTelephoneToAttach = em.getReference(telephoneListNewTelephoneToAttach.getClass(), telephoneListNewTelephoneToAttach.getId());
                attachedTelephoneListNew.add(telephoneListNewTelephoneToAttach);
            }
            telephoneListNew = attachedTelephoneListNew;
            personne.setListTelephones(telephoneListNew);
            List<PersonneAdresse> attachedPersonneAdresseListNew = new ArrayList<PersonneAdresse>();
            for (PersonneAdresse personneAdresseListNewPersonneAdresseToAttach : personneAdresseListNew) {
                personneAdresseListNewPersonneAdresseToAttach = em.getReference(personneAdresseListNewPersonneAdresseToAttach.getClass(), personneAdresseListNewPersonneAdresseToAttach.getPersonneAdressePK());
                attachedPersonneAdresseListNew.add(personneAdresseListNewPersonneAdresseToAttach);
            }
            personneAdresseListNew = attachedPersonneAdresseListNew;
            personne.setListPersonneAdresse(personneAdresseListNew);
            for (Telephone telephoneListOldTelephone : telephoneListOld) {
                if (!telephoneListNew.contains(telephoneListOldTelephone)) {
                    telephoneListOldTelephone.setIdPersonne(null);
                    telephoneListOldTelephone = em.merge(telephoneListOldTelephone);
                }
            }
            for (Telephone telephoneListNewTelephone : telephoneListNew) {
                if (!telephoneListOld.contains(telephoneListNewTelephone)) {
                    Personne oldIdPersonneOfTelephoneListNewTelephone = telephoneListNewTelephone.getIdPersonne();
                    telephoneListNewTelephone.setIdPersonne(personne);
                    telephoneListNewTelephone = em.merge(telephoneListNewTelephone);
                    if (oldIdPersonneOfTelephoneListNewTelephone != null && !oldIdPersonneOfTelephoneListNewTelephone.equals(personne)) {
                        oldIdPersonneOfTelephoneListNewTelephone.getListTelephones().remove(telephoneListNewTelephone);
                        oldIdPersonneOfTelephoneListNewTelephone = em.merge(oldIdPersonneOfTelephoneListNewTelephone);
                    }
                }
            }
            for (PersonneAdresse personneAdresseListNewPersonneAdresse : personneAdresseListNew) {
                if (!personneAdresseListOld.contains(personneAdresseListNewPersonneAdresse)) {
                    Personne oldPersonneOfPersonneAdresseListNewPersonneAdresse = personneAdresseListNewPersonneAdresse.getPersonne();
                    personneAdresseListNewPersonneAdresse.setPersonne(personne);
                    personneAdresseListNewPersonneAdresse = em.merge(personneAdresseListNewPersonneAdresse);
                    if (oldPersonneOfPersonneAdresseListNewPersonneAdresse != null && !oldPersonneOfPersonneAdresseListNewPersonneAdresse.equals(personne)) {
                        oldPersonneOfPersonneAdresseListNewPersonneAdresse.getListPersonneAdresse().remove(personneAdresseListNewPersonneAdresse);
                        oldPersonneOfPersonneAdresseListNewPersonneAdresse = em.merge(oldPersonneOfPersonneAdresseListNewPersonneAdresse);
                    }
                }
            }
            personne = em.merge(personne);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personne.getId();
                if (findPersonne(id) == null) {
                    throw new NonexistentEntityException("The personne with id " + id + " no longer exists.");
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
            Personne personne;
            try {
                personne = em.getReference(Personne.class, id);
                personne.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personne with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PersonneAdresse> listPersonneAdresseOrphanCheck = personne.getListPersonneAdresse();
            for (PersonneAdresse listPersonneAdresseOrphanCheckPersonneAdresse : listPersonneAdresseOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personne (" + personne + ") cannot be destroyed since the PersonneAdresse " + listPersonneAdresseOrphanCheckPersonneAdresse + " in its listPersonneAdresse field has a non-nullable personne field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Telephone> listTelephones = personne.getListTelephones();
            for (Telephone listTelephonesTelephone : listTelephones) {
                listTelephonesTelephone.setIdPersonne(null);
                listTelephonesTelephone = em.merge(listTelephonesTelephone);
            }
            em.remove(personne);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personne> findPersonneEntities() {
        return findPersonneEntities(true, -1, -1);
    }

    public List<Personne> findPersonneEntities(int maxResults, int firstResult) {
        return findPersonneEntities(false, maxResults, firstResult);
    }

    private List<Personne> findPersonneEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personne.class));
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

    public Personne findPersonne(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personne.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonneCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personne> rt = cq.from(Personne.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Personne findPersonneWithTelephone(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Personne pers = em.find(Personne.class, id);
            Hibernate.initialize(pers.getListTelephones());  
            return pers;
        } finally {
            em.close();
        }
    }
    
    public Personne findPersonneWithTelephone2(Integer id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Personne> q = em.createQuery("SELECT p FROM Personne p JOIN FETCH p.listTelephones where p.id = "+id,Personne.class);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public Personne findPersonneWithPersonneAdresse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Personne pers = em.find(Personne.class, id);
            Hibernate.initialize(pers.getListPersonneAdresse());  
            return pers;
        } finally {
            em.close();
        }
    }
    
    public Personne findPersonneWithAll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Personne pers = em.find(Personne.class, id);
            Hibernate.initialize(pers.getListTelephones());  
            Hibernate.initialize(pers.getListPersonneAdresse());  
            return pers;
        } finally {
            em.close();
        }
    }
    
}
