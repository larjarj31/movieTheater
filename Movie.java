package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;
    private static int MOVIE_MONTHLY_DISCOUNT_DAY = 7;
    private static int MOVIE_AFTERNOON_DISCOUNT_START = 11; // ISO Hour
    private static int MOVIE_AFTERNOON_DISCOUNT_END = 16; // ISO Hour


    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing);
    }

    private double getDiscount(Showing showing) {
        double sequenceDiscount = getSequenceDiscount(showing);
        double specialDiscount = getSpecialDiscount(showing);
        double afternoonDiscount = getAfternoonDiscount(showing);
        double monthlyDiscount = getMonthlyDiscount(showing);
        sequenceDiscount = Math.max(Math.max(sequenceDiscount,specialDiscount),
                                    Math.max(afternoonDiscount,monthlyDiscount));
        return sequenceDiscount;
    }

    private double getSpecialDiscount(Showing showing){
        double ticketPrice = showing.getMovie().getTicketPrice();
        return (MOVIE_CODE_SPECIAL == specialCode) ? ticketPrice * .20 : 0.00;
    }
    private double getSequenceDiscount(Showing showing){
        double sequenceDiscount = 0.00;
        int currShowSequence = showing.getSequenceOfTheDay();
        switch (currShowSequence){
            case 1:
                sequenceDiscount = 3;
                break;

            case 2:
                sequenceDiscount = 2;
                break;
            default:
                // No Sequence Discount Applied
        }
        return sequenceDiscount;
    }
    private double getAfternoonDiscount(Showing showing){
        int currentHour = showing.getStartTime().getHour();
        int currentMin = showing.getStartTime().getMinute();
        double ticketPrice = showing.getMovie().getTicketPrice();
        if (currentHour >= MOVIE_AFTERNOON_DISCOUNT_START && currentHour <= MOVIE_AFTERNOON_DISCOUNT_END){
                    // time range is 11AM - 4PM show we can only see 4:00 PM shows as latest start time
            if (currentHour == MOVIE_AFTERNOON_DISCOUNT_END && currentMin > 0){ // edge case listed above
                return 0.00;
            }
                return ticketPrice * .25;
        }
        return 0.00;
    }

    private double getMonthlyDiscount(Showing showing){
        int currDayOfMonth = showing.getStartTime().getDayOfMonth();
        return currDayOfMonth == MOVIE_MONTHLY_DISCOUNT_DAY ? 1.00 : 0.00;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}