package com.mayorgraeme.event;

import java.util.Set;
import java.util.UUID;

import com.mayorgraeme.event.eventrequirement.EventRequirement;

/**
 * Created by graememiller on 09/07/2016.
 */
public class Event {

    private final String name;
    private final UUID uuid;
    private final Set<EventRequirement> eventRequirements;
    private final double score;

    public Event(Set<EventRequirement> eventRequirements, String name, double score) {
        this.eventRequirements = eventRequirements;
        this.name = name;
        uuid = UUID.randomUUID();
        this.score = score;
    }

    public Set<EventRequirement> getEventRequirements() {
        return eventRequirements;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getScorePerThirtyMins() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return uuid.equals(event.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventRequirements=" + eventRequirements +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                ", score=" + score +
                '}';
    }
}
