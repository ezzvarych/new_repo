package ua.kpi.tef.demo_ticket.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.kpi.tef.demo_ticket.dto.TripDto;
import ua.kpi.tef.demo_ticket.entity.Trip;

@Mapper(componentModel = "spring")
public interface  TripMapper {

    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    TripDto tripToTripDto(Trip trip);
}
