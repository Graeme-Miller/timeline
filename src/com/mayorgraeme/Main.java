package com.mayorgraeme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.mayorgraeme.event.Event;
import com.mayorgraeme.event.EventInstance;
import com.mayorgraeme.person.Person;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Event chopWood = new Event(Collections.emptySet(), "Chop Wood");
        Event sleep = new Event(Collections.emptySet(), "Sleep");
        Event moot = new Event(Collections.emptySet(), "Moot");
        Event eat = new Event(Collections.emptySet(), "Eat");
        Event fight = new Event(Collections.emptySet(), "Fight");

        Set<Event> events = new HashSet<>();
        events.add(chopWood);
        events.add(sleep);
        events.add(moot);
        events.add(eat);
        events.add(fight);

        Person person = new Person("Graeme", Collections.emptySet(), Collections.emptySet());

        Simulation simulation = new Simulation(events, Collections.singleton(person));
        simulation.go();
        SimulationState simulationState = simulation.getSimulationState();

        ArrayList<EventInstance> eventInstances = new ArrayList(simulationState.getEventInstanceSet());
        Collections.sort(eventInstances, new Comparator<EventInstance>() {
            @Override
            public int compare(EventInstance o1, EventInstance o2) {
                return o1.getStart().compareTo(o2.getEnd());
            }
        });
        System.out.println("---------EVENTS---------");
        for (EventInstance eventInstance : eventInstances) {
            System.out.println("Name "+eventInstance.getEvent().getName() + " start " +eventInstance.getStart() + " end "+ eventInstance.getEnd() );
        }

    }
}
