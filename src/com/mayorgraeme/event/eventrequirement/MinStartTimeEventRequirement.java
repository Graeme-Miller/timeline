package com.mayorgraeme.event.eventrequirement;

import java.time.LocalTime;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 10/07/2016.
 */
public class MinStartTimeEventRequirement implements EventRequirement{

    private LocalTime minStartTime;

    public MinStartTimeEventRequirement(LocalTime minStartTime) {
        this.minStartTime = minStartTime;
    }

    @Override
    public boolean requirementMet(EventInstance event, SimulationState simulationState) {
        if (event.getStart().isBefore(minStartTime)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "MinStartTimeEventRequirement{" +
                "minStartTime=" + minStartTime +
                '}';
    }
}
