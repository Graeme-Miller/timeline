package com.mayorgraeme.event.eventrequirement;

import java.time.LocalTime;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 10/07/2016.
 */
public class MinEndTimeEventRequirement implements EventRequirement{

    private LocalTime minEndTime;

    public MinEndTimeEventRequirement(LocalTime minEndTime) {
        this.minEndTime = minEndTime;
    }

    @Override
    public boolean requirementMet(EventInstance event, SimulationState simulationState) {
        if (event.getEnd().isBefore(minEndTime)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "MinEndTimeEventRequirement{" +
                "minEndTime=" + minEndTime +
                '}';
    }
}
