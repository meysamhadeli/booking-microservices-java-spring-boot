package io.bookingmicroservices.flight.grpcserver;

import buildingblocks.mediator.abstractions.IMediator;
import flight.Flight;
import flight.FlightServiceGrpc;
import io.bookingmicroservices.flight.flights.dtos.FlightDto;
import io.bookingmicroservices.flight.flights.features.getflightbyid.GetFlightByIdQuery;
import io.bookingmicroservices.flight.seats.dtos.SeatDto;
import io.bookingmicroservices.flight.seats.features.Mappings;
import io.bookingmicroservices.flight.seats.features.getavailableseats.GetAvailableSeatsQuery;
import io.bookingmicroservices.flight.seats.features.reserveseat.ReserveSeatCommand;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.List;
import java.util.UUID;
import static io.bookingmicroservices.flight.flights.features.Mappings.toFlightResponseDtoGrpc;
import static io.bookingmicroservices.flight.seats.features.Mappings.toSeatResponseDtoGrpc;


@GrpcService
public class FlightServiceGrpcImpl extends FlightServiceGrpc.FlightServiceImplBase {
  private final IMediator mediator;

  public FlightServiceGrpcImpl(IMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void getById(Flight.GetByIdRequestDto request, StreamObserver<Flight.FlightResponseDto> responseObserver) {

    FlightDto result = mediator.send(new GetFlightByIdQuery(UUID.fromString(request.getId())));
    Flight.FlightResponseDto flightResponseDtoGrpc = toFlightResponseDtoGrpc(result);

    responseObserver.onNext(flightResponseDtoGrpc);
    responseObserver.onCompleted();
  }

  @Override
  public void getAvailableSeats(Flight.GetAvailableSeatsRequestDto request, StreamObserver<Flight.GetAvailableSeatsResponseDto> responseObserver) {
    List<SeatDto> result = mediator.send(new GetAvailableSeatsQuery(UUID.fromString(request.getFlightId())));
    List<Flight.SeatResponseDto> seatsResponseDtoGrpc = result.stream().map(Mappings::toSeatResponseDtoGrpc).toList();

    responseObserver.onNext(Flight.GetAvailableSeatsResponseDto.newBuilder().addAllSeatsDto(seatsResponseDtoGrpc).build());
    responseObserver.onCompleted();
  }

  @Override
  public void reserveSeat(Flight.ReserveSeatRequestDto request, StreamObserver<Flight.SeatResponseDto> responseObserver) {

    SeatDto result = mediator.send(new ReserveSeatCommand(request.getSeatNumber(), UUID.fromString(request.getFlightId())));
    Flight.SeatResponseDto seatResponseDtoGrpc = toSeatResponseDtoGrpc(result);

    responseObserver.onNext(seatResponseDtoGrpc);
    responseObserver.onCompleted();
  }
}
