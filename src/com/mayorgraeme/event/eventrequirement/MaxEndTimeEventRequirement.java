package com.mayorgraeme.event.eventrequirement;

import java.time.LocalTime;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 10/07/2016.
 */
public class MaxEndTimeEventRequirement implements EventRequirement{

    private LocalTime maxEndTime;

    public MaxEndTimeEventRequirement(LocalTime maxEndTime) {
        this.maxEndTime = maxEndTime;
    }

    @Override
    public boolean requirementMet(EventInstance event, SimulationState simulationState) {
        if (event.getEnd().isAfter(maxEndTime)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "MaxEndTimeEventRequirement{" +
                "maxEndTime=" + maxEndTime +
                '}';
    }
}
