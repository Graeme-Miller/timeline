package com.mayorgraeme;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import com.mayorgraeme.event.Event;
import com.mayorgraeme.person.Person;

/**
 * Created by graememiller on 09/07/2016.
 */
public class Simulation {

    private final Set<Event> events;
    private final Set<Person> people;
    private SimulationState simulationState;
    private final Random random;

    public Simulation(Set<Event> events, Set<Person> people) {
        this.events = events;
        this.people = people;
        simulationState= new SimulationState();
        random = new Random();
    }

    public void go(){
        simulationState = makeRandomStart(simulationState);
    }

    public SimulationState getSimulationState() {
        return simulationState;
    }

    private SimulationState makeRandomStart(SimulationState simulationState) {
        final int minEventsMultiplyer = 1;
        final int maxEventsMultiplyer = 6;

        final int maxPeoplePerEvent = 5;

        final int numberOfEventsMultiplyer = random.nextInt(maxEventsMultiplyer + 1) + minEventsMultiplyer;
        final int numberOfEvents = numberOfEventsMultiplyer * events.size();

        for (int i = 0; i < numberOfEvents; i++) {
            Event randomEventFromCollection = getRandomFromCollection(events);

            int startHour = random.nextInt(23);
            int startMin = random.nextInt(2) == 1?0:30;
            LocalTime startTime = LocalTime.of(startHour, startMin);

            int endHour = random.nextInt(4) + startHour + 1;
            int endMin = endHour==23||random.nextInt(2) == 1?0:30; //zero if 24, or 50% chance
            endHour = Math.min(23, endHour);

            System.out.println("start hour "+ startHour + " start min "+ startMin + " / end hour " +endHour+ " end min "+endMin);
            LocalTime endTime = LocalTime.of(endHour, endMin);

            simulationState.createEventInstance(randomEventFromCollection, startTime, endTime, Collections.singleton(getRandomFromCollection(people)));
        }

        return simulationState;
    }

    private <T> T getRandomFromCollection(Collection<T> collection){
        int index = random.nextInt(collection.size());

        T[] array = (T[])collection.toArray();

        return array[index];
    }

    //Make Random Start
    //Make Random adjustments, accept smaller and smaller changes
    //profit



}
