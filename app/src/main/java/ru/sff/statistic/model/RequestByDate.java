package ru.sff.statistic.model;

import java.io.Serializable;

public class RequestByDate implements Serializable {

    private RequestDateType requestDateType;
    private AmPm amPm;
    private int day;
    private int month;
    private int dayOfWeek;
    private int dayNumber;
    private int monthNumber;
    private String startDay;
    private String endDay;

    public RequestDateType getRequestDateType() {
        return requestDateType;
    }

    public void setRequestDateType( RequestDateType requestDateType ) {
        this.requestDateType = requestDateType;
    }

    public AmPm getAmPm() {
        return amPm;
    }

    public void setAmPm( AmPm amPm ) {
        this.amPm = amPm;
    }

    public int getDay() {
        return day;
    }

    public void setDay( int day ) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth( int month ) {
        this.month = month;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek( int dayOfWeek ) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber( int dayNumber ) {
        this.dayNumber = dayNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber( int monthNumber ) {
        this.monthNumber = monthNumber;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay( String startDay ) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay( String endDay ) {
        this.endDay = endDay;
    }
}
