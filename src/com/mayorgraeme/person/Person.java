package com.mayorgraeme.person;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.mayorgraeme.Role;

/**
 * Created by graememiller on 09/07/2016.
 */
public class Person {

    private final String name;
    private final Set<Role> roleSet;
    private final Set<PersonDesires> personDesires;
    private final UUID uuid;

    public Person(String name, Set<Role> roleSet, Set<PersonDesires> personDesires) {
        this.roleSet = roleSet;
        this.personDesires = personDesires;
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<PersonDesires> getPersonDesires() {
        return personDesires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return uuid.equals(person.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", roleSet=" + roleSet +
                ", personDesires=" + personDesires +
                ", uuid=" + uuid +
                '}';
    }
}
