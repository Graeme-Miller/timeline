package com.mayorgraeme;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.mayorgraeme.event.Event;
import com.mayorgraeme.event.EventInstance;
import com.mayorgraeme.person.Person;

/**
 * Created by graememiller on 09/07/2016.
 */
public class SimulationStateTest {

    SimulationState simulationState;
    Event eventOne;
    Event eventTwo;

    Person personOne = new Person("Alice", Collections.emptySet(), Collections.emptySet());
    Person personTwo = new Person("Bob", Collections.emptySet(), Collections.emptySet());

    @Before
    public void setup(){
        simulationState = new SimulationState();
        eventOne = new Event(Collections.emptySet(), "Party", 1);
        eventTwo = new Event(Collections.emptySet(), "Work", 1);
    }

    private void checkEvent(Event event, EventInstance createdInstance, LocalTime start, LocalTime end) {
        checkEvent(event, createdInstance, Collections.emptySet(), start, end);
    }

    private void checkEvent(Event event, EventInstance createdInstance, Set<Person> people, LocalTime start, LocalTime end) {
        assertEquals(createdInstance.getAttendees(), people);
        assertEquals(createdInstance.getStart(), start);
        assertEquals(createdInstance.getEnd(), end);
        assertEquals(createdInstance.getEvent(), event);
        assertNotNull(createdInstance.getUuid());
    }

    @Test
    public void testCreateEventOnePerson(){
        simulationState.createEventInstance(eventOne, LocalTime.NOON, LocalTime.MIDNIGHT, Collections.singleton(personOne));

        Set<EventInstance> eventInstanceList = simulationState.getEventInstanceSet();
        assertEquals(eventInstanceList.size(), 1);

        EventInstance createdInstance = eventInstanceList.iterator().next();
        checkEvent(eventOne, createdInstance, Collections.singleton(personOne), LocalTime.NOON, LocalTime.MIDNIGHT);

        assertEquals(simulationState.getPersonEventInstanceMap().size(), 1);
        Set<EventInstance> eventInstancesFromPersonMap = simulationState.getPersonEventInstanceMap().get(personOne);
        assertEquals(eventInstancesFromPersonMap.size(), 1);
        EventInstance eventInstanceFromPersonMap = eventInstancesFromPersonMap.iterator().next();
        assertEquals(eventInstanceFromPersonMap, createdInstance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEventNoPeople(){
        simulationState.createEventInstance(eventOne, LocalTime.NOON, LocalTime.MIDNIGHT, Collections.emptySet());
    }

    @Test
    public void testOverlappingEventOnePersonEnd(){
        simulationState.createEventInstance(eventOne, LocalTime.of(6, 0), LocalTime.of(12, 0), Collections.singleton(personOne));
        simulationState.createEventInstance(eventTwo, LocalTime.of(3, 0), LocalTime.of(6, 0), Collections.singleton(personOne));

        Set<EventInstance> eventInstanceList = simulationState.getEventInstanceSet();
        assertEquals(eventInstanceList.size(), 1);

        EventInstance createdInstance = eventInstanceList.iterator().next();
        checkEvent(eventTwo, createdInstance, Collections.singleton(personOne), LocalTime.of(3, 0), LocalTime.of(6, 0));

        assertEquals(simulationState.getPersonEventInstanceMap().size(), 1);
        Set<EventInstance> eventInstancesFromPersonMap = simulationState.getPersonEventInstanceMap().get(personOne);
        assertEquals(eventInstancesFromPersonMap.size(), 1);
        EventInstance eventInstanceFromPersonMap = eventInstancesFromPersonMap.iterator().next();
        assertEquals(eventInstanceFromPersonMap, createdInstance);
    }

    @Test
    public void testOverlappingEventOnePersonStart(){
        simulationState.createEventInstance(eventOne, LocalTime.of(6, 0), LocalTime.of(12, 0), Collections.singleton(personOne));
        simulationState.createEventInstance(eventTwo, LocalTime.of(9, 0), LocalTime.of(15, 0), Collections.singleton(personOne));

        Set<EventInstance> eventInstanceList = simulationState.getEventInstanceSet();
        assertEquals(eventInstanceList.size(), 1);

        EventInstance createdInstance = eventInstanceList.iterator().next();
        checkEvent(eventTwo, createdInstance, Collections.singleton(personOne), LocalTime.of(9, 0), LocalTime.of(15, 0));

        assertEquals(simulationState.getPersonEventInstanceMap().size(), 1);
        Set<EventInstance> eventInstancesFromPersonMap = simulationState.getPersonEventInstanceMap().get(personOne);
        assertEquals(eventInstancesFromPersonMap.size(), 1);
        EventInstance eventInstanceFromPersonMap = eventInstancesFromPersonMap.iterator().next();
        assertEquals(eventInstanceFromPersonMap, createdInstance);
    }

    @Test
    public void testOverlappingEventOnePersonFull(){
        simulationState.createEventInstance(eventOne, LocalTime.of(6, 0), LocalTime.of(12, 0), Collections.singleton(personOne));
        simulationState.createEventInstance(eventTwo, LocalTime.of(1, 0), LocalTime.of(15, 0), Collections.singleton(personOne));

        Set<EventInstance> eventInstanceList = simulationState.getEventInstanceSet();
        assertEquals(eventInstanceList.size(), 1);

        EventInstance createdInstance = eventInstanceList.iterator().next();
        checkEvent(eventTwo, createdInstance, Collections.singleton(personOne), LocalTime.of(1, 0), LocalTime.of(15, 0));

        assertEquals(simulationState.getPersonEventInstanceMap().size(), 1);
        Set<EventInstance> eventInstancesFromPersonMap = simulationState.getPersonEventInstanceMap().get(personOne);
        assertEquals(eventInstancesFromPersonMap.size(), 1);
        EventInstance eventInstanceFromPersonMap = eventInstancesFromPersonMap.iterator().next();
        assertEquals(eventInstanceFromPersonMap, createdInstance);
    }

    @Test
    public void testNonOverlappingEventOnePerson(){
        simulationState.createEventInstance(eventOne, LocalTime.of(3, 0), LocalTime.of(4, 0), Collections.singleton(personOne));
        simulationState.createEventInstance(eventTwo, LocalTime.of(1, 0), LocalTime.of(2, 0), Collections.singleton(personOne));

        Set<EventInstance> eventInstanceList = simulationState.getEventInstanceSet();
        assertEquals(eventInstanceList.size(), 2);

        Set<EventInstance> eventInstancesFromPersonOneMap = simulationState.getPersonEventInstanceMap().get(personOne);
        assertEquals(eventInstancesFromPersonOneMap.size(), 2);
    }


    @Test
    public void testOverlappingEventTwoPerson(){
        simulationState.createEventInstance(eventOne, LocalTime.of(6, 0), LocalTime.of(12, 0), Collections.singleton(personOne));
        simulationState.createEventInstance(eventTwo, LocalTime.of(7, 0), LocalTime.of(11, 0), Collections.singleton(personTwo));

        Set<EventInstance> eventInstanceList = simulationState.getEventInstanceSet();
        assertEquals(eventInstanceList.size(), 2);

        assertEquals(simulationState.getPersonEventInstanceMap().size(), 2);
        Set<EventInstance> eventInstancesFromPersonOneMap = simulationState.getPersonEventInstanceMap().get(personOne);
        assertEquals(eventInstancesFromPersonOneMap.size(), 1);
        EventInstance eventInstanceFromPersonOneMap = eventInstancesFromPersonOneMap.iterator().next();
        checkEvent(eventOne, eventInstanceFromPersonOneMap, Collections.singleton(personOne), LocalTime.of(6, 0), LocalTime.of(12, 0));

        assertEquals(simulationState.getPersonEventInstanceMap().size(), 2);
        Set<EventInstance> eventInstancesFromPersonTwoMap = simulationState.getPersonEventInstanceMap().get(personTwo);
        assertEquals(eventInstancesFromPersonTwoMap.size(), 1);
        EventInstance eventInstanceFromPersonTwoMap = eventInstancesFromPersonTwoMap.iterator().next();
        checkEvent(eventTwo, eventInstanceFromPersonTwoMap, Collections.singleton(personTwo), LocalTime.of(7, 0), LocalTime.of(11, 0));
    }


}
