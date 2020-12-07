package ru.durnov.warehouse.entity;

import java.util.Comparator;

/**
 * Этот компаратор располагает вначале самую последнюю дату.
 */
public class DateStringComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (getYear(o1) != getYear(o2)) {
            return Integer.compare(getYear(o1), getYear(o2)) * (-1);
        } else if (getMonth(o1) != getMonth(o2)) {
            return Integer.compare(getMonth(o1), getMonth(o2)) * (-1);
        }
        return Integer.compare(getDay(o1), getDay(o2)) * (-1);
    }

    public int getYear(String date){
        int firstNumber = date.indexOf('-');
        return Integer.parseInt(date.substring(0, firstNumber));
    }

    public int getMonth(String date){
        int firstNumber = date.indexOf('-');
        int lastNumber = date.lastIndexOf('-');
        return Integer.parseInt(date.substring(firstNumber + 1, lastNumber));
    }

    public int getDay(String date){
        int lastNumber = date.lastIndexOf('-');
        return Integer.parseInt(date.substring(lastNumber + 1));
    }
}
