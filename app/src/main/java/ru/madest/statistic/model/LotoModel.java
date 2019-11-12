package ru.madest.statistic.model;

import java.io.Serializable;

public class LotoModel implements Serializable {

    private Integer id;
    private String drawDate;
    private Integer day;
    private String month;
    private Integer year;
    private String dayOfWeek;
    private Integer numOfWeekInMonth;
    private Integer dayOfYear;
    private String amPm;
    private Integer draw;
    private Integer slotOne;
    private Integer slotTwo;
    private Integer slotThree;
    private Integer slotFour;
    private Integer slotFive;
    private Integer slotSix;
    private Integer sum;
    private Integer digitOne;
    private Integer digitTwo;
    private Integer digitThree;
    private Integer digitFour;
    private Integer digitFive;
    private Integer digitSix;
    private Integer combination;
    private Integer evenCount;
    private Integer oddCount;
    private String superPrize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate( String drawDate ) {
        this.drawDate = drawDate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getNumOfWeekInMonth() {
        return numOfWeekInMonth;
    }

    public void setNumOfWeekInMonth(Integer numOfWeekInMonth) {
        this.numOfWeekInMonth = numOfWeekInMonth;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getSlotOne() {
        return slotOne;
    }

    public void setSlotOne(Integer slotOne) {
        this.slotOne = slotOne;
    }

    public Integer getSlotTwo() {
        return slotTwo;
    }

    public void setSlotTwo(Integer slotTwo) {
        this.slotTwo = slotTwo;
    }

    public Integer getSlotThree() {
        return slotThree;
    }

    public void setSlotThree(Integer slotThree) {
        this.slotThree = slotThree;
    }

    public Integer getSlotFour() {
        return slotFour;
    }

    public void setSlotFour(Integer slotFour) {
        this.slotFour = slotFour;
    }

    public Integer getSlotFive() {
        return slotFive;
    }

    public void setSlotFive(Integer slotFive) {
        this.slotFive = slotFive;
    }

    public Integer getSlotSix() {
        return slotSix;
    }

    public void setSlotSix(Integer slotSix) {
        this.slotSix = slotSix;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getDigitOne() {
        return digitOne;
    }

    public void setDigitOne(Integer digitOne) {
        this.digitOne = digitOne;
    }

    public Integer getDigitTwo() {
        return digitTwo;
    }

    public void setDigitTwo(Integer digitTwo) {
        this.digitTwo = digitTwo;
    }

    public Integer getDigitThree() {
        return digitThree;
    }

    public void setDigitThree(Integer digitThree) {
        this.digitThree = digitThree;
    }

    public Integer getDigitFour() {
        return digitFour;
    }

    public void setDigitFour(Integer digitFour) {
        this.digitFour = digitFour;
    }

    public Integer getDigitFive() {
        return digitFive;
    }

    public void setDigitFive(Integer digitFive) {
        this.digitFive = digitFive;
    }

    public Integer getDigitSix() {
        return digitSix;
    }

    public void setDigitSix(Integer digitSix) {
        this.digitSix = digitSix;
    }

    public Integer getCombination() {
        return combination;
    }

    public void setCombination(Integer combination) {
        this.combination = combination;
    }

    public Integer getEvenCount() {
        return evenCount;
    }

    public void setEvenCount(Integer evenCount) {
        this.evenCount = evenCount;
    }

    public Integer getOddCount() {
        return oddCount;
    }

    public void setOddCount(Integer oddCount) {
        this.oddCount = oddCount;
    }

    public String getSuperPrize() {
        return superPrize;
    }

    public void setSuperPrize(String superPrize) {
        this.superPrize = superPrize;
    }
}
