package com.mayorgraeme.event.eventrequirement;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.Event;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 24/07/2016.
 */
public class MaxNumberOfEventRequirement implements EventRequirement{

    private int numberOfEvents;

    public MaxNumberOfEventRequirement(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    @Override
    public boolean requirementMet(EventInstance eventInstance, SimulationState simulationState) {
        Event event = eventInstance.getEvent();

        int countOfEvents = 0;
        for (EventInstance instance : simulationState.getEventInstanceSet()) {
            if(instance.getEvent().equals(event)) {
                countOfEvents++;

                if(countOfEvents > numberOfEvents) {
                    return false;
                }
            }
        }

        return true;
    }
}
