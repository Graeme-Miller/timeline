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

        Person graeme = new Person("Graeme", Collections.emptySet(), Collections.emptySet());
        Person lizzie = new Person("Lizzie", Collections.emptySet(), Collections.emptySet());

        Set<Person> people = new HashSet<>();
        people.add(graeme);
        people.add(lizzie);

        Simulation simulation = new Simulation(events, people);
        simulation.go();
        SimulationState simulationState = simulation.getSimulationState();

        System.out.println("---------EVENTS---------");
        for (Person person : simulationState.getPersonEventInstanceMap().keySet()) {
            System.out.println("Person: "+person);

            ArrayList<EventInstance> eventInstances = new ArrayList(simulationState.getPersonEventInstanceMap().get(person));

            Collections.sort(eventInstances, new Comparator<EventInstance>() {
                @Override
                public int compare(EventInstance o1, EventInstance o2) {
                    return o1.getStart().compareTo(o2.getEnd());
                }
            });

            for (EventInstance eventInstance : eventInstances) {
                if(eventInstance.getAttendees().size() > 1) {
                    System.out.println("Name " + eventInstance.getEvent().getName() + " start " + eventInstance.getStart() + " end " + eventInstance.getEnd() + "UUID " + eventInstance.getUuid());
                }
            }
        }

    }
}
