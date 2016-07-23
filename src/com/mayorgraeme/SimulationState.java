package com.mayorgraeme;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mayorgraeme.event.Event;
import com.mayorgraeme.event.EventInstance;
import com.mayorgraeme.event.eventrequirement.EventRequirement;
import com.mayorgraeme.person.Person;

import com.google.common.collect.Sets;

/**
 * Created by graememiller on 09/07/2016.
 */
public class SimulationState implements Cloneable {


    private final Set<EventInstance> eventInstanceSet;
    private final Map<Person, Set<EventInstance>> personEventInstanceMap;

    public SimulationState() {
        this(Collections.emptySet(), Collections.emptyMap());
    }

    public static SimulationState copySimulationState(SimulationState simulationState) {
        Set<EventInstance> eventInstanceSet = new HashSet<>(simulationState.getEventInstanceSet().size());
        for (EventInstance eventInstance : simulationState.getEventInstanceSet()) {
            eventInstanceSet.add(new EventInstance(eventInstance));
        }

        Map<Person, Set<EventInstance>> personEventInstanceMapOld = simulationState.getPersonEventInstanceMap();
        Map<Person, Set<EventInstance>> personEventInstanceMapNew = new HashMap<>(personEventInstanceMapOld.size());
        for (Person person : personEventInstanceMapOld.keySet()) {
            Set<EventInstance> eventInstancesFromPersonMap = personEventInstanceMapOld.get(person);
            Set<EventInstance> personEventInstanceSet = new HashSet<>(eventInstancesFromPersonMap.size());

            for (EventInstance eventInstance : eventInstancesFromPersonMap) {
                personEventInstanceSet.add(new EventInstance(eventInstance));
            }
            personEventInstanceMapNew.put(person, personEventInstanceSet);
        }

        return new SimulationState(eventInstanceSet, personEventInstanceMapNew);
    }

    public SimulationState(Set<EventInstance> eventInstanceSet, Map<Person, Set<EventInstance>> personEventInstanceMap) {
        this.eventInstanceSet = new HashSet<>(eventInstanceSet);
        this.personEventInstanceMap = new HashMap<>(personEventInstanceMap);
    }

    public void removeEventInstance(EventInstance eventInstance) {
        eventInstanceSet.remove(eventInstance);
        eventInstance.getAttendees().stream().forEach(person -> personEventInstanceMap.get(person).remove(eventInstance));
    }

    public void updateEventInstsanceTime(EventInstance eventInstance, LocalTime startTime, LocalTime endTime){
        if(!startTime.isBefore(endTime)) {
            System.out.println("Tried to create an event with start before end");
            return;
        }

        eventInstance.setStart(startTime);
        eventInstance.setEnd(endTime);

        checkTimeCollision(eventInstance);
    }

    public void createEventInstance(Event event, LocalTime startTime, LocalTime endTime, Set<Person> people){
        if(people.isEmpty()) {
            throw new IllegalArgumentException("Tried to create event with no people" + event + " start " + startTime + " end " + endTime);
        }

        EventInstance eventInstance = new EventInstance(event, people, startTime, endTime);
        eventInstanceSet.add(eventInstance);

        people.stream().forEach(person -> {
            if(!personEventInstanceMap.containsKey(person))
                personEventInstanceMap.put(person, new HashSet<>());
        });

        checkTimeCollision(eventInstance);
    }

    public void checkTimeCollision(EventInstance eventInstance) {
        Sets.newCopyOnWriteArraySet(eventInstance.getAttendees()).stream().forEach(person -> {
            Set<EventInstance> eventInstancesToKeep = new HashSet<>();
            Set<EventInstance> eventInstancesToRemove = new HashSet<>();

            personEventInstanceMap.get(person).stream().forEach(eventInstanceFromStream -> {
                if (eventInstanceFromStream.getStart().compareTo(eventInstance.getStart()) == 0 ||
                        eventInstanceFromStream.getEnd().compareTo(eventInstance.getEnd()) == 0) {
                    eventInstancesToRemove.add(eventInstanceFromStream);
                } else if (eventInstanceFromStream.getStart().isAfter(eventInstance.getEnd())
                        || eventInstanceFromStream.getEnd().isBefore(eventInstance.getStart())) {
                    eventInstancesToKeep.add(eventInstanceFromStream);
                } else {
                    eventInstancesToRemove.add(eventInstanceFromStream);
                }
            });

            //deal with kept event instances
            eventInstancesToKeep.add(eventInstance);
            personEventInstanceMap.put(person, eventInstancesToKeep);

            //deal with removed event instances
            eventInstancesToRemove.forEach(eventInstanceFromStream -> {
                removePersonFromEventInstance(eventInstanceFromStream, person);
            });
        });
    }

    public void removePersonFromEventInstance(EventInstance eventInstance, Person person) {
        eventInstance.removePerson(person);
        if(eventInstance.getAttendees().size() == 0){
            //no people left, remove event instance
            eventInstanceSet.remove(eventInstance);
        }
        personEventInstanceMap.get(person).remove(eventInstance);
    }

    public void addPersonToEventInstance(EventInstance eventInstance, Person person) {
        eventInstance.addPerson(person);

        if(!personEventInstanceMap.containsKey(person)){
            personEventInstanceMap.put(person, new HashSet<>());
        }
        personEventInstanceMap.get(person).add(eventInstance);
        checkTimeCollision(eventInstance);
    }

    public double getScore(){

        double score = 0;
        for (Person person : personEventInstanceMap.keySet()) {
            for (EventInstance eventInstance : personEventInstanceMap.get(person)) {
                boolean reqsMet = true;
                for (EventRequirement eventRequirement : eventInstance.getEvent().getEventRequirements()) {
                    if(!eventRequirement.requirementMet(eventInstance, this)) {
                        reqsMet = false;
                        break;
                    }
                }

                if(reqsMet) {
                    double thirtyMinsBetween = ChronoUnit.MINUTES.between(eventInstance.getStart(), eventInstance.getEnd()) / 30;
                    score += eventInstance.getEvent().getScorePerThirtyMins() * thirtyMinsBetween;
                }
            }
        }

        return score;
    }

    public Set<EventInstance> getEventInstanceSet() {
        return eventInstanceSet;
    }

    public Map<Person, Set<EventInstance>> getPersonEventInstanceMap() {
        return personEventInstanceMap;
    }

    @Override
    public String toString() {
        return "SimulationState{" +
                "eventInstanceSet=" + eventInstanceSet +
                ", personEventInstanceMap=" + personEventInstanceMap +
                '}';
    }
}
