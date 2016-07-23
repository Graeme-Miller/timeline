package com.mayorgraeme;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mayorgraeme.event.EventInstance;
import com.mayorgraeme.person.Person;

import com.google.common.base.Strings;

/**
 * Created by graememiller on 23/07/2016.
 */
public class Utils {

    public static void printSimulationState(SimulationState simulationState) {
        System.out.println("---------EVENTS---------");
        System.out.print("|-----Name -----|");
        System.out.print("|-----00:00-----|");
        printUpTo(LocalTime.MIDNIGHT.plusMinutes(30), LocalTime.MIDNIGHT);
        System.out.println();

        for (Person person : simulationState.getPersonEventInstanceMap().keySet()) {
            System.out.print("|-");
            System.out.print(Strings.padEnd(person.getName(), 14, '-'));
            System.out.print("|");

            ArrayList<EventInstance> eventInstancesPerson = new ArrayList(simulationState.getPersonEventInstanceMap().get(person));

            Collections.sort(eventInstancesPerson, new Comparator<EventInstance>() {
                @Override
                public int compare(EventInstance o1, EventInstance o2) {
                    return o1.getStart().compareTo(o2.getEnd());
                }
            });

            LocalTime lastPrintedTime = LocalTime.MIDNIGHT;
            for (EventInstance eventInstance : eventInstancesPerson) {
                LocalTime start = eventInstance.getStart();
                LocalTime end = eventInstance.getEnd();

                if(lastPrintedTime.compareTo(start) != 0) {
                    printStringUpTo(lastPrintedTime, start, "*************");
                    lastPrintedTime = start;
                }

                printStringUpTo(lastPrintedTime, end.plusMinutes(30), eventInstance.toString());
                lastPrintedTime = end.plusMinutes(30);
            }

            if(lastPrintedTime.compareTo(LocalTime.MIDNIGHT) != 0) {
                printStringUpTo(lastPrintedTime, LocalTime.MIDNIGHT, "*************");
            }

            System.out.println();
        }
    }

    private static void printUpTo(LocalTime start, LocalTime end) {
        LocalTime current = start;
        while(current.compareTo(end) != 0) {
            System.out.print("|-----"+current+"-----|");
            current = current.plusMinutes(30);
        }
    }

    private static void printStringUpTo(LocalTime start, LocalTime end, String string) {
        LocalTime current = start;
        while(current.compareTo(end) != 0) {
            System.out.print("|-"+string+"-|");
            current = current.plusMinutes(30);
        }
    }
}
