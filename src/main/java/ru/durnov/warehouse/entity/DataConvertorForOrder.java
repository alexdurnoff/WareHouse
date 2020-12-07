package ru.durnov.warehouse.entity;

public class DataConvertorForOrder {
    private DateStringComparator dateStringComparator;
    private String date;

    public DataConvertorForOrder(String date){
        this.date = date;
        this.dateStringComparator = new DateStringComparator();
    }

    private String getMonth(){
        int mounthNumber = this.dateStringComparator.getMonth(this.date);
        switch (mounthNumber){
            case 1:
                return "января";
            case 2:
                return "февраля";
            case 3:
                return "марта";
            case 4:
                return "апреля";
            case 5:
                return "мая";
            case 6:
                return "июня";
            case 7:
                return "июля";
            case 8:
                return "августа";
            case 9:
                return "сентября";
            case 10:
                return "октября";
            case 11:
                return "ноября";
            case 12:
                return "декабря";
        }
        return "";
    }

    public String convertDate(){
        return this.dateStringComparator.getDay(this.date) + " " + getMonth() + " "
                + this.dateStringComparator.getYear(this.date) + " г.";
    }

}
