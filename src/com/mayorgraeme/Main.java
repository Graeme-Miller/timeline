package com.mayorgraeme;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mayorgraeme.event.Event;
import com.mayorgraeme.event.eventrequirement.MaxDurationEventRequirement;
import com.mayorgraeme.event.eventrequirement.MaxPeopleOfRoleEventRequirement;
import com.mayorgraeme.event.eventrequirement.MinPeopleOfRoleEventRequirement;
import com.mayorgraeme.person.Person;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class Main {

    public static void main(String[] args) {
	// write your code here

        MaxDurationEventRequirement minHalfHour = new MaxDurationEventRequirement(Duration.ofMinutes(30));
        MaxDurationEventRequirement minTwoHour = new MaxDurationEventRequirement(Duration.ofHours(2));

        MaxDurationEventRequirement maxHalfHour = new MaxDurationEventRequirement(Duration.ofMinutes(30));
        MaxDurationEventRequirement maxOneHour = new MaxDurationEventRequirement(Duration.ofHours(1));
        MaxDurationEventRequirement maxThreeHour = new MaxDurationEventRequirement(Duration.ofHours(3));

        Role villager = new Role("Villager");
        Role villageElder = new Role("Village Elder");

        Map<Role, Double> minMootRole = new HashMap<>();
        minMootRole.put(villageElder, 1d);
        minMootRole.put(villager, 2d);

        MinPeopleOfRoleEventRequirement minMootReq = new MinPeopleOfRoleEventRequirement(minMootRole);
        MinPeopleOfRoleEventRequirement minFightReq = new MinPeopleOfRoleEventRequirement(ImmutableMap.of(villager, 2d));
        MaxPeopleOfRoleEventRequirement maxFightReq = new MaxPeopleOfRoleEventRequirement(ImmutableMap.of(villager, 2d));

        Event chopWood = new Event(ImmutableSet.of(minHalfHour), "Chop Wood", 4);
        Event sleep = new Event(ImmutableSet.of(minHalfHour), "Sleep", 4);
        Event moot = new Event(ImmutableSet.of(minMootReq, minTwoHour, maxThreeHour), "Moot", 5);
        Event eat = new Event(ImmutableSet.of(maxOneHour), "Eat", 4);
        Event fight = new Event(ImmutableSet.of(maxHalfHour, minFightReq, maxFightReq), "Fight", 3);

        Set<Event> events = new HashSet<>();
        events.add(chopWood);
        events.add(sleep);
        events.add(moot);
        events.add(eat);
        events.add(fight);

        Person graeme = new Person("Graeme", ImmutableSet.of(villageElder, villager), ImmutableSet.of());
        Person lizzie = new Person("Lizzie", ImmutableSet.of(villageElder, villager), ImmutableSet.of());
        Person edward = new Person("edward", ImmutableSet.of(villager), ImmutableSet.of());
        Person john = new Person("john", ImmutableSet.of(villager), ImmutableSet.of());
        Person alfred = new Person("alfred", ImmutableSet.of(villager), ImmutableSet.of());
        Person egbert = new Person("egbert", ImmutableSet.of(villager), ImmutableSet.of());

        Set<Person> people = ImmutableSet.of(graeme, lizzie, edward, john, alfred, egbert);

        Simulation simulation = new Simulation(events, people);
        simulation.go();
        SimulationState simulationState = simulation.getSimulationState();

        Utils.printSimulationState(simulationState);

    }
}
