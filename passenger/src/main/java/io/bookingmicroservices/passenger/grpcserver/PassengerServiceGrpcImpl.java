package io.bookingmicroservices.passenger.grpcserver;

import buildingblocks.mediator.abstractions.IMediator;
import io.bookingmicroservices.passenger.passengers.dtos.PassengerDto;
import io.bookingmicroservices.passenger.passengers.features.getpassengerbyid.GetPassengerByIdQuery;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import passenger.Passenger;
import passenger.PassengerServiceGrpc;
import java.util.UUID;
import static io.bookingmicroservices.passenger.passengers.features.Mappings.toPassengerResponseDtoGrpc;

@Service
public class PassengerServiceGrpcImpl extends PassengerServiceGrpc.PassengerServiceImplBase {

    private final IMediator mediator;

    public PassengerServiceGrpcImpl(IMediator mediator) {
        this.mediator = mediator;
    }


    @Override
    public void getById(Passenger.PassengerRequestDto request, StreamObserver<Passenger.PassengerResponseDto> responseObserver) {

        PassengerDto result = mediator.send(new GetPassengerByIdQuery(UUID.fromString(request.getId())));
        Passenger.PassengerResponseDto passengerResponseDtoGrpc = toPassengerResponseDtoGrpc(result);

        responseObserver.onNext(passengerResponseDtoGrpc);
        responseObserver.onCompleted();
    }
}
