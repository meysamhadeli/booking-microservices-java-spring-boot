package io.bookingmicroservices.passenger.passengers.features.getpassengerbyid;

import buildingblocks.mediator.abstractions.queries.IQueryHandler;
import io.bookingmicroservices.passenger.data.mongo.documents.PassengerDocument;
import io.bookingmicroservices.passenger.data.mongo.repositories.PassengerReadRepository;
import io.bookingmicroservices.passenger.passengers.dtos.PassengerDto;
import io.bookingmicroservices.passenger.passengers.features.Mappings;
import org.springframework.stereotype.Service;

@Service
public class GetPassengerByIdQueryHandler implements IQueryHandler<GetPassengerByIdQuery, PassengerDto> {
    private final PassengerReadRepository passengerReadRepository;

    public GetPassengerByIdQueryHandler(PassengerReadRepository passengerReadRepository) {
        this.passengerReadRepository = passengerReadRepository;
    }

    @Override
    public PassengerDto handle(GetPassengerByIdQuery query) {
        PassengerDocument passenger = passengerReadRepository.findPassengerByPassengerIdAndIsDeletedFalse(query.id());
        return Mappings.toPassengerDto(passenger);
    }
}
