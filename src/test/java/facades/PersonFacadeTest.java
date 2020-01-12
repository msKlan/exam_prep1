package facades;

import dto.PersonDTO;
import utils.EMF_Creator;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;
    private static PersonDTO p3DTO;

    public PersonFacadeTest() {
    }

    /*
     * **** HINT **** A better way to handle configuration values, compared to the
     * UNUSED example above, is to store those values ONE COMMON place accessible
     * from anywhere. The file config.properties and the corresponding helper class
     * utils.Settings is added just to do that. See below for how to use these
     * files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2() {
         emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,
         Strategy.DROP_AND_CREATE);
 //       emf = EMF_Creator.createEntityManagerFactory("pu", "jdbc:mysql://localhost3307/exam_prep1_test", "dev", "ax2",
  //              EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        // Clean up database after test is done or use a persistence unit with
        // drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    // TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Hans", "Hansen", "hans@hansen.com","11111111");
        p2 = new Person("Bo", "Boesen", "bo@boesen.com","22222222");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        // Remove any data after each test was run
    }

    @Test
    public void testgetPersonByID() {
        PersonDTO exp = new PersonDTO(p1);
        assertEquals(exp, facade.getPerson(p1.getId()));

    }

    @Test
    public void testgetAllPersons() {
        int exp = facade.getAllPersons().getAll().size();
        exp = 2;

        assertEquals(exp, facade.getAllPersons().getAll().size());
    }

    @Test
    public void testAddPerson() {
        EntityManager em = emf.createEntityManager();
        p3 = new Person("Lars", "Larsen", "lars@larsen.com","33333333");
        p3DTO = new PersonDTO(p3);

        PersonDTO exp = facade.addPerson(p3DTO);

        PersonDTO actual = new PersonDTO(em.find(Person.class, exp.getId()));
        assertEquals(exp, actual);
    }

    @Test
    public void testDeletePerson() {
        int exp = facade.getAllPersons().getAll().size();
        facade.deletePerson(p1.getId());
        exp -= 1;
        assertEquals(exp, facade.getAllPersons().getAll().size());
    }

    @Test
    public void testEditPerson() {
        EntityManager em = emf.createEntityManager();
        p1.setFirstName("Peter");
        PersonDTO pDTO = new PersonDTO(p1);
        PersonDTO exp = facade.editPerson(pDTO);
        PersonDTO actual = new PersonDTO(em.find(Person.class, p1.getId()));

        assertEquals(exp, actual);
    }

}
