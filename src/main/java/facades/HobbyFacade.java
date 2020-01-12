package facades;

import dto.HobbiesDTO;
import dto.HobbyDTO;
import entities.Hobby;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Klan
 */
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public HobbyDTO getHobby(int id) {
        EntityManager em = emf.createEntityManager();
            Hobby h = em.find(Hobby.class, id);
        try {
            HobbyDTO hobby = new HobbyDTO(h);
            return hobby;
        } finally {
            em.close();
        }
    }

    public HobbyDTO addHubby(HobbyDTO h) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby(h.getName(), h.getDescription());
        try {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }
    
    public HobbiesDTO getAllHobbies() {
        EntityManager em = emf.createEntityManager();
        
        try {
            List<Hobby> list = em.createQuery("SELECT h FROM Hobby h", Hobby.class).getResultList();
            return new HobbiesDTO(list);
        } finally {
            em.close();
        }
    }

    public HobbyDTO removeHobby(int id) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = em.find(Hobby.class, id);
        try {
            em.getTransaction().begin();
            em.remove(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }

    
        public HobbyDTO editHobby(HobbyDTO h) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby(h.getName(),h.getDescription());
        hobby.setId(h.getId());
        try {
            em.getTransaction().begin();
            em.merge(hobby);
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }
        

}
