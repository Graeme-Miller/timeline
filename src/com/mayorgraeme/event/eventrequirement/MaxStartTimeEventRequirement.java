package com.mayorgraeme.event.eventrequirement;

import java.time.LocalTime;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 10/07/2016.
 */
public class MaxStartTimeEventRequirement implements EventRequirement{

    private LocalTime maxStartTime;

    public MaxStartTimeEventRequirement(LocalTime maxStartTime) {
        this.maxStartTime = maxStartTime;
    }

    @Override
    public boolean requirementMet(EventInstance event, SimulationState simulationState) {
        if (event.getStart().isAfter(maxStartTime)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "MaxStartTimeEventRequirement{" +
                "maxStartTime=" + maxStartTime +
                '}';
    }
}
