/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto_OUT;

import dto_OUT.PersonDTO_OUT;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Klan
 */
public class PersonsDTO_OUT {
        List<PersonDTO_OUT> all = new ArrayList();

    public PersonsDTO_OUT(List<Person> personEntities) {
        for (Person personEntity : personEntities) {
            all.add(new PersonDTO_OUT(personEntity));
        }
    }

    public List<PersonDTO_OUT> getAll() {
        return all;
    }

    public void setAll(List<PersonDTO_OUT> all) {
        this.all = all;
    }
}
