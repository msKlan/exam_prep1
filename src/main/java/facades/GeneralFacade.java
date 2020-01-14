/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import dto_OUT.HobbyDTO_OUT;
import dto_OUT.PersonDTO_OUT;
import dto_OUT.PersonsDTO_OUT;

import entities.Address;

import entities.Hobby;
import entities.Person;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * @author Klan
 */
public class GeneralFacade {

    private static GeneralFacade instance;
    private static EntityManagerFactory emf;

    public static GeneralFacade getGeneralFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GeneralFacade();
        }
        return instance;
    }

    public PersonsDTO_OUT getAllPersonsByPhone(String phonenumber) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p JOIN WHERE p.number = :phoneNumber",
                    Person.class);

            List<Person> list = query.setParameter("phoneNumber", phonenumber).getResultList();

            return new PersonsDTO_OUT(list);
        } finally {
            em.close();
        }
    }

    public PersonsDTO_OUT getAllPersonsByAge(String age) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p WHERE p.age = :age",
                    Person.class);

            List<Person> list = query.setParameter("age", age).getResultList();

            return new PersonsDTO_OUT(list);
        } finally {
            em.close();
        }
    }

    public PersonsDTO getAllPersonsByHobby(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em
                    .createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobbyName", Person.class);

            List<Person> list = query.setParameter("hobbyName", name).getResultList();

            return new PersonsDTO(list);
        } finally {
            em.close();
        }
    }

    public PersonsDTO getAllPersonsByCity(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery(
                    "SELECT p FROM Person p JOIN p.address a JOIN a.cityInfo c WHERE c.city = :cityName", Person.class);

            List<Person> list = query.setParameter("cityName", name).getResultList();

            return new PersonsDTO(list);
        } finally {
            em.close();
        }
    }

    public List<String> getAllZipCodes() {
        EntityManager em = emf.createEntityManager();
        try {
            List<String> list = em.createQuery("SELECT c.zipCode FROM CityInfo c", String.class).getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    public long getCountPersonByHobby(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            // Query query = em.createQuery("SELECT COUNT(p) FROM Hobby h JOIN Person p
            // WHERE h.name = :hobbyName");
            Hobby hobby = (Hobby) em.createQuery("SELECT h FROM Hobby h WHERE h.name = :hobbyName")
                    .setParameter("hobbyName", name).getSingleResult();
            long count = hobby.getPersons().size();

            return count;
        } finally {
            em.close();
        }
    }

    public PersonDTO_OUT addPerson(PersonDTO_OUT person) {
        EntityManager em = emf.createEntityManager();
        Person p = new Person(person.getfName(), person.getlName(), person.getEmail(), person.getPhone(),
                person.getAge());
        System.out.println("Person P: " + p);

        Address a = new Address(person.getAddress().getStreet(), person.getAddress().getAdditionalInfo());

        System.out.println("Address A: " + a.getStreet() + a.getAdditionalInfo());
        a.setPerson(p);

        for (HobbyDTO_OUT h : person.getHobbies()) {
            String name = h.getName();
            String pDesc = h.getDescription();
            Hobby hobby = new Hobby(name, pDesc);
            System.out.println("Hobby: " + hobby);
            p.setHobby(hobby);
        }

        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO_OUT(p);
    }

    public static void main(String[] args) {
        // EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
        // "pu",
        // "jdbc:mysql://localhost:3307/exam_prep1",
        // "dev",
        // "ax2",
        // EMF_Creator.Strategy.CREATE);
        // GeneralFacade af = getGeneralFacade(EMF);
        // System.out.println(af.getCountPersonByHobby("Football"));
        // System.out.println(af.getAllPersonsByPhone("80808080"));
        //
        // CityInfo c1 = new CityInfo("8210", "Aarhus");
        // Address a1 = new Address("Aarhus 69", "Apartment");
        // a1.setCityInfo(c1);
        //
        // Phone ph1 = new Phone("45454545", "Private");
        // Phone ph2 = new Phone("80808080", "Work");
        // Hobby h1 = new Hobby("Soccer", "Sport");
        // Hobby h2 = new Hobby("Swimming", "Sport");
        //
        // Person p1 = new Person("Peter", "Petersen", "peter@petersen.com");
        // h1.setPerson(p1);
        // h2.setPerson(p1);
        //
        // p1.setHobby(h1);
        // p1.setHobby(h2);
        //
        // p1.setAddress(a1);
        // p1.setPhone(ph1);
        // p1.setPhone(ph2);
        //
        // PersonDTO_OUT pDTO = new PersonDTO_OUT(p1);
        //
        // System.out.println(af.addPerson(pDTO));

    }

}
