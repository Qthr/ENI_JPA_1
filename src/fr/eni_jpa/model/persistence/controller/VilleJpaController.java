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
        if (ville.getAdresseList() == null) {
            ville.setAdresseList(new ArrayList<Adresse>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pays pays = ville.getPays();
            if (pays != null) {
                pays = em.getReference(pays.getClass(), pays.getId());
                ville.setPays(pays);
            }
            List<Adresse> attachedAdresseList = new ArrayList<Adresse>();
            for (Adresse adresseListAdresseToAttach : ville.getAdresseList()) {
                adresseListAdresseToAttach = em.getReference(adresseListAdresseToAttach.getClass(), adresseListAdresseToAttach.getId());
                attachedAdresseList.add(adresseListAdresseToAttach);
            }
            ville.setAdresseList(attachedAdresseList);
            em.persist(ville);
            if (pays != null) {
                pays.getListVille().add(ville);
                pays = em.merge(pays);
            }
            for (Adresse adresseListAdresse : ville.getAdresseList()) {
                Ville oldVilleOfAdresseListAdresse = adresseListAdresse.getVille();
                adresseListAdresse.setVille(ville);
                adresseListAdresse = em.merge(adresseListAdresse);
                if (oldVilleOfAdresseListAdresse != null) {
                    oldVilleOfAdresseListAdresse.getAdresseList().remove(adresseListAdresse);
                    oldVilleOfAdresseListAdresse = em.merge(oldVilleOfAdresseListAdresse);
                }
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
            List<Adresse> adresseListOld = persistentVille.getAdresseList();
            List<Adresse> adresseListNew = ville.getAdresseList();
            if (paysNew != null) {
                paysNew = em.getReference(paysNew.getClass(), paysNew.getId());
                ville.setPays(paysNew);
            }
            List<Adresse> attachedAdresseListNew = new ArrayList<Adresse>();
            for (Adresse adresseListNewAdresseToAttach : adresseListNew) {
                adresseListNewAdresseToAttach = em.getReference(adresseListNewAdresseToAttach.getClass(), adresseListNewAdresseToAttach.getId());
                attachedAdresseListNew.add(adresseListNewAdresseToAttach);
            }
            adresseListNew = attachedAdresseListNew;
            ville.setAdresseList(adresseListNew);
            if (paysOld != null && !paysOld.equals(paysNew)) {
                paysOld.getListVille().remove(ville);
                paysOld = em.merge(paysOld);
            }
            if (paysNew != null && !paysNew.equals(paysOld)) {
                paysNew.getListVille().add(ville);
                paysNew = em.merge(paysNew);
            }
            System.out.println("Adresses new : toutes - trappes : "+adresseListNew);       
            System.out.println("Adresse old : toutes :"+adresseListOld);

            for (Adresse adresseListOldAdresse : adresseListOld) {
                if (!adresseListNew.contains(adresseListOldAdresse)) {
                    adresseListOldAdresse.setVille(null);
                    System.out.println("NOM ADRESSE :"+adresseListOldAdresse.getLibelle()+"/ SA VILLE :"+adresseListOldAdresse.getVille());
                    adresseListOldAdresse = em.merge(adresseListOldAdresse);
                }
            }
            for (Adresse adresseListNewAdresse : adresseListNew) {
                if (!adresseListOld.contains(adresseListNewAdresse)) {
                    Ville oldVilleOfAdresseListNewAdresse = adresseListNewAdresse.getVille();
                    adresseListNewAdresse.setVille(ville);
                    adresseListNewAdresse = em.merge(adresseListNewAdresse);
                    if (oldVilleOfAdresseListNewAdresse != null && !oldVilleOfAdresseListNewAdresse.equals(ville)) {
                        oldVilleOfAdresseListNewAdresse.getAdresseList().remove(adresseListNewAdresse);
                        oldVilleOfAdresseListNewAdresse = em.merge(oldVilleOfAdresseListNewAdresse);
                    }
                }
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
            List<Adresse> adresseList = ville.getAdresseList();
            for (Adresse adresseListAdresse : adresseList) {
                adresseListAdresse.setVille(null);
                adresseListAdresse = em.merge(adresseListAdresse);
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
