package com.mayorgraeme.event.eventrequirement;

import com.mayorgraeme.SimulationState;
import com.mayorgraeme.event.EventInstance;

/**
 * Created by graememiller on 09/07/2016.
 */
public interface EventRequirement {

    boolean requirementMet(EventInstance event, SimulationState simulationState);
}
