package com.mayorgraeme.event.eventrequirement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mayorgraeme.Role;
import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;
import com.mayorgraeme.person.Person;

/**
 * Created by graememiller on 10/07/2016.
 */
public class MaxPeopleOfRoleEventRequirement implements EventRequirement {

    Map<Role, Double> roleToNumber = new HashMap<>();

    public MaxPeopleOfRoleEventRequirement(Map<Role, Double> roleToNumber) {
        this.roleToNumber = roleToNumber;
    }

    @Override
    public boolean requirementMet(EventInstance event, SimulationState simulationState) {
        Map<Role, Double> roleToNumberCopy = new HashMap<>(roleToNumber);

        for (Person person : event.getAttendees()) {
            for (Role role : person.getRoleSet()) {
                if(roleToNumberCopy.containsKey(role)){
                    Double currentValue = roleToNumberCopy.get(role);
                    if(currentValue == 0){
                        return false;
                    }else{
                        roleToNumberCopy.put(role, currentValue - 1);
                    }
                }
            }

        }

        return true;
    }

    @Override
    public String toString() {
        return "MaxPeopleOfRoleEventRequirement{" +
                "roleToNumber=" + roleToNumber +
                '}';
    }
}
