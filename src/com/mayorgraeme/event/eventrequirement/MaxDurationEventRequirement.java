package com.mayorgraeme.event.eventrequirement;

import java.time.Duration;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 10/07/2016.
 */
public class MaxDurationEventRequirement implements EventRequirement{

    private final Duration duration;

    public MaxDurationEventRequirement(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean requirementMet(EventInstance event, SimulationState simulationState) {

        if(Duration.between(event.getStart(), event.getEnd()).compareTo(duration) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "MaxDurationEventRequirement{" +
                "duration=" + duration +
                '}';
    }
}
