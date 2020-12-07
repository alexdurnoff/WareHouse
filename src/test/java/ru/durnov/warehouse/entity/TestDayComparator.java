package ru.durnov.warehouse.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestDayComparator {
    private ArrayList<String> dates;

    @Before
    public void setUp(){
        dates = new ArrayList<>();
        String date1 = "2021-09-16";
        String date2 = "2020-08-24";
        String date3 = "2020-07-15";
        String date4 = "2020-05-17";
        String date5 = "2018-12-31";
        String date6 = "2020-11-08";
        String date7 = "2020-05-16";
        String date8 = "2020-05-12";
        dates.add(date1); dates.add(date2); dates.add(date3); dates.add(date4);dates.add(date5); dates.add(date6);
        dates.add(date7); dates.add(date8);
        dates.sort(new DateStringComparator());
    }

    @Test
    public void testDateComparator(){
        for (int i = 0; i < dates.size(); i++){
            System.out.println(dates.get(i));
        }
    }

    @Test
    public void testDayCompaiyring(){
        DateStringComparator dateStringComparator = new DateStringComparator();
        String date1 = dates.get(0);
        String date2 = dates.get(1);
        System.out.println(dateStringComparator.getDay(date1));
        System.out.println(dateStringComparator.getMonth(date1));
        System.out.println(dateStringComparator.getDay(date1));
        System.out.println(dateStringComparator.getDay(date2));
        System.out.println(dateStringComparator.getMonth(date2));
        System.out.println(dateStringComparator.getDay(date2));


    }
}
