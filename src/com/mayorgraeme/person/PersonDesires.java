package com.mayorgraeme.person;

import com.mayorgraeme.event.EventInstance;
import com.sun.tools.javac.util.List;

/**
 * Created by graememiller on 09/07/2016.
 */
public interface PersonDesires {

    double score(List<EventInstance> eventInstances);
}
