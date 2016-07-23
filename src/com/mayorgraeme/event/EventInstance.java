package com.mayorgraeme.event;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.eventrequirement.EventRequirement;
import com.mayorgraeme.person.Person;

import com.google.common.base.Strings;

/**
 * Created by graememiller on 09/07/2016.
 */
public class EventInstance {

    private final UUID uuid;
    private final Event event;
    private LocalTime start;
    private LocalTime end;
    private final Set<Person> attendees;


    public EventInstance(EventInstance eventInstance){
        this(eventInstance.getEvent(), eventInstance.getAttendees(), eventInstance.getStart(), eventInstance.getEnd(), eventInstance.getUuid());
    }

    public EventInstance(Event event, LocalTime start, LocalTime end) {
        this(event, Collections.emptySet(), start, end);
    }

    public EventInstance(Event event, Set<Person> attendees, LocalTime start, LocalTime end, UUID uuid) {
        this.attendees = new HashSet<Person>(attendees);
        this.end = end;
        this.start = start;
        this.uuid = uuid;
        this.event = event;
    }

    public EventInstance(Event event, Set<Person> attendees, LocalTime start, LocalTime end) {
        this.attendees = new HashSet<Person>(attendees);
        this.end = end;
        this.start = start;
        this.uuid = UUID.randomUUID();
        this.event = event;
    }

    public boolean areReqsMet(SimulationState simulationState) {
        for (EventRequirement eventRequirement : getEvent().getEventRequirements()) {
            if(!eventRequirement.requirementMet(this, simulationState)) {
                return false;
            }
        }
        return true;
    }

    public Event getEvent() {
        return event;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<Person> getAttendees() {
        return attendees;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void removePerson(Person person) {
        this.attendees.remove(person);
    }

    public void addPerson(Person person) {
        this.attendees.add(person);
    }

    @Override
    public String toString() {
        String name = "EI-"
                        +Strings.padEnd(event.getName().substring(0, Math.min(event.getName().length(), 5)), 5, '-')
                        +"-"+uuid.toString().substring(33);

        return Strings.padEnd(name, 13, '-');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventInstance that = (EventInstance) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
