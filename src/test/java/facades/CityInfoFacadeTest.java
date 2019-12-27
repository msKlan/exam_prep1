/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.CityInfoDTO;
import entities.CityInfo;
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
 * @author jacobfolkehildebrandt
 */
//@Disabled
public class CityInfoFacadeTest {
    private static EntityManagerFactory emf;
    private static CityInfoFacade facade;
    private static CityInfo ci1, ci2, ci3;
    private static CityInfoDTO ci3DTO;

    public CityInfoFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = CityInfoFacade.getCityInfoFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        ci1 = new CityInfo("5700", "Svendborg");
        ci2 = new CityInfo("2300", "Copenhagen S");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(ci1);
            em.persist(ci2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testgetCityInfoByID() {
        assertEquals(new CityInfoDTO(ci1), facade.getCityInfo(ci1.getId()));

    }

    @Test
    public void testAddCityInfo() {
        EntityManager em = emf.createEntityManager();
        ci3 = new CityInfo("2800", "Lyngby");
        ci3DTO = new CityInfoDTO(ci3);
        CityInfoDTO exp = facade.addCityInfo(ci3DTO);
        CityInfoDTO actual = new CityInfoDTO(em.find(CityInfo.class, exp.getId()));
        assertEquals(exp, actual);
    }

    @Test
    public void testgetAllCityInfo() {
        int exp = facade.getAllCityInfo().getAll().size();
        assertEquals(exp, 2);
    }

    @Test
    public void testRemoveCityInfo() {
        int exp = facade.getAllCityInfo().getAll().size();
        facade.removeCityInfo(ci1.getId());
        exp -= 1;
        assertEquals(exp, facade.getAllCityInfo().getAll().size());
    }
    
    @Test
    public void testEditCityInfo(){
        EntityManager em = emf.createEntityManager();
        ci1.setCity("Valby");
        ci3DTO = new CityInfoDTO(ci1);
        CityInfoDTO exp = facade.editCityInfo(ci3DTO);
        CityInfoDTO actual = new CityInfoDTO(em.find(CityInfo.class, ci1.getId()));
        assertEquals(exp, actual);
    }

}
