/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PhoneDTO;
import dto.PhonesDTO;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Klan
 */
public class PhoneFacade {

    private static PhoneFacade instance;
    private static EntityManagerFactory emf;

    private PhoneFacade() {
    }

    public static PhoneFacade getPhoneFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PhoneDTO getPhoneID(int id) {
        EntityManager em = emf.createEntityManager();
        Phone p = em.find(Phone.class, id);
        try {
            PhoneDTO pdto = new PhoneDTO(p);
            return pdto;
        } finally {
            em.close();
        }
    }

    public PhoneDTO addPhone(PhoneDTO p) {
        EntityManager em = emf.createEntityManager();
        Phone phone = new Phone(p.getNumber(), p.getDescription());
        try {
            em.getTransaction().begin();
            em.persist(phone);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        PhoneDTO pdto = new PhoneDTO(phone);
        return pdto;
    }

    /**
     *
     * @return
     */
    public PhonesDTO getAllPhones() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Phone> list = em.createQuery("SELECT p FROM Phone p", Phone.class).getResultList();
            return new PhonesDTO(list);
        } finally {
            em.close();
        }
    }

    public PhoneDTO removePhone(int id) {
        EntityManager em = emf.createEntityManager();
        Phone phone = em.find(Phone.class, id);
        try {
            em.getTransaction().begin();
            em.remove(phone);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PhoneDTO(phone);
    }

    public PhoneDTO editPhone(PhoneDTO p) {
        EntityManager em = emf.createEntityManager();
        Phone phone = new Phone(p.getNumber(), p.getDescription());
        phone.setId(p.getId());
        try {
            em.getTransaction().begin();
            em.merge(phone);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return new PhoneDTO(phone);
    }

}
