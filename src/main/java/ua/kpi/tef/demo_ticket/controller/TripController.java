package ua.kpi.tef.demo_ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.demo_ticket.controller.exception.TripException;
import ua.kpi.tef.demo_ticket.dto.TripDto;
import ua.kpi.tef.demo_ticket.entity.Trip;
import ua.kpi.tef.demo_ticket.entity.User;
import ua.kpi.tef.demo_ticket.service.TripService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /*@RequestMapping("/avia")
    public String getAviaPage(Model model){
        List<Trip> trips = tripService.findAll();
        model.addAttribute("trips", trips);
        return "avia_trip.html";
    }*/

    /*@RequestMapping("/railway")
    public String getRailwayPage(Model model){
        List<Trip> trips = tripService.findAll();
        model.addAttribute("trips", trips);
        return "railway_trip.html";
    }*/
    /*@InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("departureDate");

    }*/

    @GetMapping("/")
    public String createTripView(Model model) {
        List<Trip> tripDto = tripService.getAllTripDto();
        List<String> tripsFrom = tripDto.stream()
                .map(Trip::getFromWhere)
                .collect(Collectors.toList());

        List<String> tripsTo = tripDto.stream()
                .map(Trip::getWhereTo)
                .collect(Collectors.toList());
        List<LocalDate> departureDate = tripDto.stream()
                .map(Trip::getDepartureDate)
                .collect(Collectors.toList());
        List<LocalDate> arrivalDate = tripDto.stream()
                .map(Trip::getArrivalDate)
                .collect(Collectors.toList());
        //model.addAttribute("newOrder", OrderDto.builder().build());
        //model.addAttribute("types", orderTypeService.getAllOrderTypeDto());
        model.addAttribute("tripsFrom", tripsFrom);
        model.addAttribute("tripsTo", tripsTo);
        model.addAttribute("departureDate", departureDate);
        model.addAttribute("arrivalDate", arrivalDate);
        return "index";
    }

    /*@GetMapping("/avia_trip/{tripsFrom}/{tripsTo}")
    public String findTripPage(@PathVariable String tripsFrom, @PathVariable String tripsTo) {
        try {
            tripService.getSearchedTrip(tripsFrom, tripsTo);
        } catch (TripException e) {
            e.printStackTrace();
        }
        //if (bindingResult.hasErrors()){
        //  return "/";
        //}
        //tripService.getAllTripDto();
        //orderService.createOrder(newOrder, user);
        return "avia_trip";
    }*/

    /*@GetMapping("/find_railway_trips")
    public String findRailwayTripPage(){
        return "railway_trip";
    }*/

    @PostMapping("/find_avia_trips")
    public String findAviaTrip(Model model, @RequestParam("tripsFrom") String  tripsFrom,
                               @RequestParam("tripsTo") String tripsTo,
                               @RequestParam("departureDate") String departureDate,
                                @RequestParam(value = "arrivalDate",required = false) String arrivalDate) {

        LocalDate departure = LocalDate.parse(departureDate);
        List<Trip> trips;
        if(!arrivalDate.isEmpty()) {
            LocalDate arrival = LocalDate.parse(arrivalDate);
            trips = tripService.getSearchedAviaTripWithArrivalDate(tripsFrom, tripsTo, departure, arrival);
        }else {
            trips = tripService.getSearchedAviaTrip(tripsFrom, tripsTo, departure);
        }
        model.addAttribute("trips", trips);
        return "avia_trip";
    }

    @PostMapping("/find_railway_trips")
    public String postFindRailwayTrip(Model model, @RequestParam("tripsFrom") String  tripsFrom,
                                  @RequestParam("tripsTo") String tripsTo,
                                  @RequestParam("departureDate") String departureDate,
                                      @RequestParam(required = false) String arrivalDate) {
        LocalDate departure = LocalDate.parse(departureDate);
        List<Trip> trips;
        if(!arrivalDate.isEmpty()){
            LocalDate arrival = LocalDate.parse(arrivalDate);
            trips = tripService.getSearchedRailwayTripWithArrivalDate(tripsFrom, tripsTo, departure, arrival);
        } else {
            trips = tripService.getSearchedRailwayTrip(tripsFrom, tripsTo, departure);
        }
        if(trips.size() == 0){
            System.out.println("List is empty!");
            model.addAttribute("trips", "No data");
        }
        model.addAttribute("trips", trips);
        return "railway_trip";
    }
}
