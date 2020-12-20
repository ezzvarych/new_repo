package ua.kpi.tef.demo_ticket.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kpi.tef.demo_ticket.controller.exception.TripException;
import ua.kpi.tef.demo_ticket.dto.TripDto;
import ua.kpi.tef.demo_ticket.entity.Trip;
import ua.kpi.tef.demo_ticket.mappers.TripMapper;
import ua.kpi.tef.demo_ticket.repository.TripRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TripService {
    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /*public Trip loadTripByTripName(@NonNull String tripName) throws TripNameNotFoundException {
        return tripRepository.findTripByTripName(tripName).orElseThrow(() -> new TripNameNotFoundException("Trip " + tripName + " not found!"));
    }*/
    public List<Trip> getAllTripDto() {

        List<Trip> trips = new ArrayList<>();
        tripRepository.findAll().forEach(trips::add);

        return trips.stream()
                //.map(TripMapper.INSTANCE::tripToTripDto)
                .collect(Collectors.toList());
    }


    public Trip getById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElse(new Trip());
    }

    //public List<Trip> findAll(){ return tripRepository.findAll(); }

    public List<Trip> getSearchedAviaTrip(String whereFrom, String whereTo, LocalDate departureDate){

        return tripRepository.findByFromWhereAndWhereToAndDepartureDate(whereFrom, whereTo, departureDate).stream()
                .filter(p->p.getTripType().toString().equals("AVIA"))
                .collect(Collectors.toList());
                //.orElseThrow(() -> new TripException("no trips:  " + whereFrom + "-" + whereTo));
    }

    public List<Trip> getSearchedAviaTripWithArrivalDate(String whereFrom, String whereTo, LocalDate departureDate, LocalDate arrivalDate){

        return tripRepository.findByFromWhereAndWhereToAndDepartureDateAndArrivalDate(whereFrom, whereTo, departureDate, arrivalDate).stream()
                .filter(p->p.getTripType().toString().equals("AVIA"))
                .collect(Collectors.toList());
        //.orElseThrow(() -> new TripException("no trips:  " + whereFrom + "-" + whereTo));
    }

    public List<Trip> getSearchedRailwayTrip(String whereFrom, String whereTo, LocalDate departureDate){

        return tripRepository.findByFromWhereAndWhereToAndDepartureDate(whereFrom, whereTo, departureDate).stream()
                .filter(p->p.getTripType().toString().equals("RAILWAY"))
                .collect(Collectors.toList());
        //.orElseThrow(() -> new TripException("no trips:  " + whereFrom + "-" + whereTo));
    }

    public List<Trip> getSearchedRailwayTripWithArrivalDate(String whereFrom, String whereTo, LocalDate departureDate, LocalDate arrivalDate){

        return tripRepository.findByFromWhereAndWhereToAndDepartureDateAndArrivalDate(whereFrom, whereTo, departureDate, arrivalDate).stream()
                .filter(p->p.getTripType().toString().equals("RAILWAY"))
                .collect(Collectors.toList());
        //.orElseThrow(() -> new TripException("no trips:  " + whereFrom + "-" + whereTo));
    }

}
