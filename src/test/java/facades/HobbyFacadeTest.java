package facades;

import dto.HobbyDTO;
import entities.Hobby;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;
    private static Hobby h1, h2, h3;
    private static HobbyDTO g1, g2, g3;

    public HobbyFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        // emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,
        // Strategy.DROP_AND_CREATE);
        emf = EMF_Creator.createEntityManagerFactory("pu", "jdbc:mysql://localhost3307/exam_prep1_test", "dev", "ax2",
                EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = HobbyFacade.getHobbyFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        h1 = new Hobby("Football", "Sport");
        h2 = new Hobby("Coins", "Collection");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.persist(h1);
            em.persist(h2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testgetHobbyByID() {

        g1 = new HobbyDTO(h1);
        assertEquals(g1, facade.getHobby(h1.getId()));

    }

    @Test
    public void testAddHobby() {
        EntityManager em = emf.createEntityManager();

        h3 = new Hobby("Gaming", "IT");
        g3 = new HobbyDTO(h3);
        HobbyDTO exp = facade.addHubby(g3);

        HobbyDTO actual = new HobbyDTO(em.find(Hobby.class, exp.getId()));

        assertEquals(exp, actual);
    }

    @Test
    public void testgetAllHobbies() {

        assertEquals(2, facade.getAllHobbies().getAll().size());
    }

    @Test
    public void testRemoveHobby() {

        facade.removeHobby(h1.getId());
        int exp = 1;
        int actual = facade.getAllHobbies().getAll().size();
        assertEquals(exp, actual);
    }

    @Test
    public void testEditHobby() {
        EntityManager em = emf.createEntityManager();

        h1.setName("American Football");
        HobbyDTO hobby = new HobbyDTO(h1);

        HobbyDTO exp = facade.editHobby(hobby);
        HobbyDTO actual = new HobbyDTO(em.find(Hobby.class, h1.getId()));

        assertEquals(exp, actual);
    }

}
