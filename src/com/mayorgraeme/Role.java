package com.mayorgraeme;

import java.util.UUID;

/**
 * Created by graememiller on 09/07/2016.
 */
public class Role {

    private final String name;
    private final UUID uuid;

    public Role(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return uuid != null ? uuid.equals(role.uuid) : role.uuid == null;

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
