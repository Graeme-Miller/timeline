package com.mayorgraeme.event.eventrequirement;

import java.util.HashMap;
import java.util.Map;

import com.mayorgraeme.Role;
import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 10/07/2016.
 */
public class MinPeopleOfRoleEventRequirement implements EventRequirement {

    Map<Role, Double> roleToNumber = new HashMap<>();

    public MinPeopleOfRoleEventRequirement(Map<Role, Double> roleToNumber) {
        this.roleToNumber = roleToNumber;
    }

    @Override
    public boolean requirementMet(EventInstance event, SimulationState simulationState) {
        Map<Role, Double> roleToNumberCopy = new HashMap<>(roleToNumber);

        event.getAttendees().stream().forEach(person -> person.getRoleSet().forEach(role -> {
            if(roleToNumberCopy.containsKey(role)){
                Double currentValue = roleToNumberCopy.get(role);
                if(currentValue == 1){
                    roleToNumberCopy.remove(role);
                }else{
                    roleToNumberCopy.put(role, currentValue - 1);
                }
            }
        }));

        if(roleToNumberCopy.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "MinPeopleOfRoleEventRequirement{" +
                "roleToNumber=" + roleToNumber +
                '}';
    }
}
