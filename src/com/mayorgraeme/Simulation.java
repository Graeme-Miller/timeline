package com.mayorgraeme;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import com.mayorgraeme.event.Event;
import com.mayorgraeme.event.EventInstance;
import com.mayorgraeme.person.Person;

import com.google.common.collect.Sets;

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

        double temp = 1500;
        double alpha = 0.85;


        SimulationState simulationStateNew;
        while(temp > 1) {
            simulationStateNew = SimulationState.copySimulationState(simulationState);
            makeRandomChange(simulationStateNew);
            double acceptanceProbability = acceptanceProbability(random, simulationState, simulationStateNew, temp);

            if(acceptanceProbability < random.nextDouble()) {
                simulationState = simulationStateNew;
            }

            temp = temp * alpha;
        }
    }


    public SimulationState getSimulationState() {
        return simulationState;
    }

    private void makeRandomChange(SimulationState simulationState) {
        int choice = random.nextInt(5);

        switch (choice) {
            case 0: addRandomPerson(simulationState); break;
            case 1: removeRandomPerson(simulationState); break;
            case 2: addRandomEvent(simulationState); break;
            case 3: removeRandomEvent(simulationState); break;
            case 4: moveRandomEvent(simulationState); break;
            default: throw new IllegalStateException("Wrong random choice!");
        }
    }

    private void addRandomPerson(SimulationState simulationState){
        EventInstance randomEventInstance = getRandomFromCollection(simulationState.getEventInstanceSet());

        if(randomEventInstance.getAttendees().size() == people.size()) {
            System.out.println("NOT ADDING PERSON FULL");
            return;
        }

        Sets.SetView<Person> allPeopleNotAtRandomEvent = Sets.difference(people, randomEventInstance.getAttendees());
        simulationState.addPersonToEventInstance(randomEventInstance, getRandomFromCollection(allPeopleNotAtRandomEvent));
    }
    private void removeRandomPerson(SimulationState simulationState){
        EventInstance randomEventInstance = getRandomFromCollection(simulationState.getEventInstanceSet());
        Set<Person> attendees = randomEventInstance.getAttendees();

        if(attendees.size() == 1) {
            simulationState.removeEventInstance(randomEventInstance);
        } else {
            Person randomPerson = getRandomFromCollection(randomEventInstance.getAttendees());
            simulationState.removePersonFromEventInstance(randomEventInstance, randomPerson);
        }
    }
    private void addRandomEvent(SimulationState simulationState){
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
    private void removeRandomEvent(SimulationState simulationState){
        simulationState.removeEventInstance(getRandomFromCollection(simulationState.getEventInstanceSet()));
    }


    private void moveRandomEvent(SimulationState simulationState){
        EventInstance randomEventInstance = getRandomFromCollection(simulationState.getEventInstanceSet());

        int change = random.nextInt(4);
        switch (change) {
            case 0: simulationState.updateEventInstsanceTime(randomEventInstance, randomEventInstance.getStart().minus(30, ChronoUnit.MINUTES), randomEventInstance.getEnd());
            case 1: simulationState.updateEventInstsanceTime(randomEventInstance, randomEventInstance.getStart().plus(30, ChronoUnit.MINUTES), randomEventInstance.getEnd());
            case 2: simulationState.updateEventInstsanceTime(randomEventInstance, randomEventInstance.getStart(), randomEventInstance.getEnd().minus(30, ChronoUnit.MINUTES));
            case 3: simulationState.updateEventInstsanceTime(randomEventInstance, randomEventInstance.getStart(), randomEventInstance.getEnd().plus(30, ChronoUnit.MINUTES));
        }
    }

    private SimulationState makeRandomStart(SimulationState simulationState) {
        final int minEventsMultiplyer = 1;
        final int maxEventsMultiplyer = 6;

        final int maxPeoplePerEvent = 5;

        final int numberOfEventsMultiplyer = random.nextInt(maxEventsMultiplyer + 1) + minEventsMultiplyer;
        final int numberOfEvents = numberOfEventsMultiplyer * events.size();

        for (int i = 0; i < numberOfEvents; i++) {
            addRandomEvent(simulationState);
        }

        return simulationState;
    }

    private <T> T getRandomFromCollection(Collection<T> collection){
        int index = random.nextInt(collection.size());

        T[] array = (T[])collection.toArray();

        return array[index];
    }


    public static double acceptanceProbability(Random random, SimulationState simulationStateOld, SimulationState simulationStateNew, double temperature){
        double oldScore = getScore(simulationStateOld, random);
        double newScore = getScore(simulationStateNew, random);

        if(newScore > oldScore) {
            return 1.0;
        }

        return Math.exp((oldScore - newScore)/temperature);
    }

    public static double getScore(SimulationState simulationState, Random random) {
        return random.nextInt(100);
    }


}
