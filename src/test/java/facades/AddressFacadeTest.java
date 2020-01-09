/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.AddressDTO;
import entities.Address;
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
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author Renz
 */
// @Disabled
public class AddressFacadeTest {

    private static EntityManagerFactory emf;
    private static AddressFacade facade;
    private static Address a1, a2, a3;
    private static AddressDTO g1;

    public AddressFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        // emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,
        // Strategy.DROP_AND_CREATE);
        emf = EMF_Creator.createEntityManagerFactory("pu", "jdbc:mysql://localhost3307/exam_prep1_test", "dev", "ax2",
                EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = AddressFacade.getAddressFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        a1 = new Address("Vej 1", "Castle");
        a2 = new Address("Vej 2", "Skyscraper");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.persist(a1);
            em.persist(a2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testgetAddressByID() {
        g1 = new AddressDTO(a1);

        assertEquals(g1, facade.getAddress(a1.getId()));

    }

    @Test
    public void testAddAddress() {
        EntityManager em = emf.createEntityManager();

        a3 = new Address("Vej 3", "Container");
        g1 = new AddressDTO(a3);
        AddressDTO exp = facade.addAddress(g1);
        AddressDTO actual = new AddressDTO(em.find(Address.class, exp.getId()));

        assertEquals(exp, actual);
    }

    @Test
    public void testgetAllAddresses() {

        assertEquals(2, facade.getAllAddress().getAll().size());
    }

    @Test
    public void testRemoveAddress() {

        facade.removeAddress(a1.getId());
        int exp = 1;
        int actual = facade.getAllAddress().getAll().size();
        assertEquals(exp, actual);
    }

    @Test
    public void testEditHobby() {
        EntityManager em = emf.createEntityManager();

        a1.setStreet("Vej 5");
        AddressDTO address = new AddressDTO(a1);

        AddressDTO exp = facade.editAddress(address);
        AddressDTO actual = new AddressDTO(em.find(Address.class, a1.getId()));

        assertEquals(exp, actual);
    }

}
