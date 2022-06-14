package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    /*
    @Test
    void specialMovieWith50PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        assertEquals(10, spiderMan.calculateTicketPrice(showing));

        System.out.println(Duration.ofMinutes(90));
    }
    */



    @Test
    void movieSpecialCodeDiscount(){
        //special code case - 20% discount
        Movie batman = new Movie("Batman Begins",Duration.ofMinutes(90) , 15.00, 1);
        Showing showing = new Showing(batman,4,LocalDateTime.of(LocalDate.of(2022,12,25),LocalTime.of(9,30)));
        assertEquals(12.00,batman.calculateTicketPrice(showing));


        //non special code case - no 20% discount (Assuming no sequence, afternoon, monthly discounts will be applied)
        Movie batman2 = new Movie("Batman Begins",Duration.ofMinutes(90),15.00,2);
        Showing showing2 = new Showing(batman,4,LocalDateTime.of(LocalDate.of(2022,12,25),LocalTime.of(9,30)));
        assertEquals(12.00,batman2.calculateTicketPrice(showing2));



    }

    @Test
    void movieSequenceDiscount(){
        //sequence 1 case - $3 Discount
        Movie ironman = new Movie("Iron Man",Duration.ofMinutes(90),12,3);
        Showing showing = new Showing(ironman,1,LocalDateTime.of(LocalDate.of(2022,12,25),LocalTime.of(9,30)));
        assertEquals(9.00,ironman.calculateTicketPrice(showing));

        // sequence 2 case - $2 Discount
        Movie ironman2 = new Movie("Iron Man",Duration.ofMinutes(90),12,3);
        Showing showing2 = new Showing(ironman2,2,LocalDateTime.of(LocalDate.of(2022,12,25),LocalTime.of(9,30)));
        assertEquals(10.00,ironman2.calculateTicketPrice(showing2));


        //sequence (any other) case - No Discount ((Assuming no special code, afternoon, monthly discounts will be applied)
        Movie ironman3 = new Movie("Iron Man",Duration.ofMinutes(90),12.00,3);
        Showing showing3 = new Showing(ironman3,5,LocalDateTime.of(LocalDate.of(2022,12,25),LocalTime.of(9,30)));
        assertEquals(12.00,ironman3.calculateTicketPrice(showing3));


    }

    @Test
    void movieAfternoonDiscount(){
        //afternoonDiscount - (11 AM On the Dot) -> 25 % discount
        Movie superman = new Movie("Man Of Steel",Duration.ofMinutes(90),10.00,3);
        Showing showing = new Showing(superman,4,LocalDateTime.of(LocalDate.now(),LocalTime.of(11,0)));
        assertEquals(7.50,superman.calculateTicketPrice(showing));

        // afternoon Discount (11 AM - 4 PM (1:30 PM) -> 25% discount
        Movie superman2 = new Movie("Man Of Steel",Duration.ofMinutes(90),10.00,3);
        Showing showing2 = new Showing(superman2,4,LocalDateTime.of(LocalDate.now(),LocalTime.of(13,30)));
        assertEquals(7.50,superman2.calculateTicketPrice(showing2));

        // afternoon Discount ( 4PM on the Dot (latest start time is 4:00PM) ) -> 25% discount
        Movie superman3 = new Movie("Man Of Steel",Duration.ofMinutes(90),10.00,3);
        Showing showing3 = new Showing(superman3,4,LocalDateTime.of(LocalDate.now(),LocalTime.of(16,0)));
        assertEquals(7.50,superman3.calculateTicketPrice(showing3));


        // afterrnon Discount (before 11 AM) -> No Discount
        Movie superman4 = new Movie("Man Of Steel",Duration.ofMinutes(90),10.00,3);
        Showing showing4 = new Showing(superman4,4,LocalDateTime.of(LocalDate.now(),LocalTime.of(10,59)));
        assertEquals(10.00,superman4.calculateTicketPrice(showing4));

        // afternoon Discount (after 4 PM) -> No Discount
        Movie superman5 = new Movie("Man Of Steel",Duration.ofMinutes(90),10.00,3);
        Showing showing5 = new Showing(superman5,4,LocalDateTime.of(LocalDate.now(),LocalTime.of(17,30)));
        assertEquals(10.00,superman5.calculateTicketPrice(showing5));


    }

    @Test
    void movieMonthlyDiscount(){
        //monthly discount -> Day of Month = 7 -> $1 discount
        Movie captainAmerica = new Movie("Captain America",Duration.ofMinutes(90),10.00,3);
        Showing showing = new Showing(captainAmerica,4,LocalDateTime.of(LocalDate.of(2022,12,7),LocalTime.of(12,30)));
        assertEquals(9,captainAmerica.calculateTicketPrice(showing));

        //monthly discount -> Day of Month = 30 -> $0 discount
        Movie captainAmerica2 = new Movie("Captain America",Duration.ofMinutes(90),10.00,3);
        Showing showing2 = new Showing(captainAmerica2,4,LocalDateTime.of(LocalDate.of(2022,12,30),LocalTime.of(12,30)));
        assertEquals(10,captainAmerica.calculateTicketPrice(showing2));


    }

    @Test
    void applyMaximumDiscount(){

        // case 1 -  ( Sequence = 1, startTime = 11: 15 AM, Day of Month = 7, special code = 1) -> ticket price = $ 12 -> expected is $3 Discount (Maximum)
        Movie batmanVSuperman = new Movie("Batman vs Superman",Duration.ofMinutes(90),10.00,1);
        Showing showing = new Showing(batmanVSuperman,4,LocalDateTime.of(LocalDate.of(2022,12,7),LocalTime.of(11,15)));
        assertEquals(7,batmanVSuperman.calculateTicketPrice(showing));


        // case 2 ( Sequence = 1, startTime = 11:15 AM, Day of Month = 7, special code = 1) -> ticket price = $ 15 -> expected is $3.75 Discount (25% off afternnon discount) (Maximum)

        Movie batmanVSuperman2 = new Movie("Batman vs Superman",Duration.ofMinutes(90),15.00,1);
        Showing showing2 = new Showing(batmanVSuperman2,4,LocalDateTime.of(LocalDate.of(2022,12,7),LocalTime.of(11,15)));
        assertEquals(11.25,batmanVSuperman2.calculateTicketPrice(showing2));


        // case 3 (Sequence = 2, startTime = 10:15 AM, Day of Month = 10, special code = 1) -> ticket price = $ 15 -> $3.00 Discount Special Code
        Movie batmanVSuperman3 = new Movie("Batman vs Superman",Duration.ofMinutes(90),15.00,4);
        Showing showing3 = new Showing(batmanVSuperman3,4,LocalDateTime.of(LocalDate.of(2022,12,10),LocalTime.of(10,15)));
        assertEquals(12,batmanVSuperman3.calculateTicketPrice(showing3));







    }


}
