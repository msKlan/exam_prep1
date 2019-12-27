/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PhoneDTO;
import entities.Person;
import entities.Phone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Klan
 */
//@Disabled
public class PhoneFacadeTest {
    private static EntityManagerFactory emf;
    private static PhoneFacade facade;
    private static PhoneDTO pdto;
    private static Phone p1, p2, p3;
 //   private static Person person;

    public PhoneFacadeTest() {
    }


    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = PhoneFacade.getPhoneFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
//        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
//        facade = PhoneFacade.getPhoneFacade(emf);
 
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
    EntityManager em = emf.createEntityManager();
        
        p1 = new Phone("123456", "Test1");
        p2 = new Phone("654321", "Test2");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }             
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testgetPhoneByID() {
        pdto = new PhoneDTO(p1);
        assertEquals(pdto, facade.getPhoneID(p1.getId()));
    }
   
    @Test
    public void testAddPhone() {
        EntityManager em = emf.createEntityManager();

        p3 = new Phone("111111", "Me");
        pdto = new PhoneDTO(p3);
        PhoneDTO exp = facade.addPhone(pdto);
        PhoneDTO act = new PhoneDTO(em.find(Phone.class, exp.getId()));
        assertEquals(exp, act);
    }

    @Test
    public void testgetAllPhones() {
        assertEquals(2, facade.getAllPhones().getAll().size());
    }

    @Test
    public void testRemovePhone() {
        facade.removePhone(p2.getId());
        assertEquals(1, facade.getAllPhones().getAll().size());
    }
    
    @Test
    public void testEditPhone(){
        EntityManager em = emf.createEntityManager();

        p1.setDescription("Me2");
        PhoneDTO p = new PhoneDTO(p1);
        PhoneDTO exp = facade.editPhone(p);
        PhoneDTO act = new PhoneDTO(em.find(Phone.class, p1.getId()));

        assertEquals(exp, act);
    }

}

