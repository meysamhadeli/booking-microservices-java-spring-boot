package io.bookingmicroservices.booking.bookings.features.createbooking;

import buildingblocks.mediator.abstractions.commands.ICommandHandler;
import buildingblocks.mediator.abstractions.requests.Unit;
import io.bookingmicroservices.booking.bookings.exceptions.BookingAlreadyExistException;
import io.bookingmicroservices.booking.bookings.features.Mappings;
import io.bookingmicroservices.booking.data.mongo.documents.BookingDocument;
import io.bookingmicroservices.booking.data.mongo.repositories.BookingReadRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBookingMongoCommandHandler implements ICommandHandler<CreateBookingMongoCommand, Unit> {
    private final BookingReadRepository bookingReadRepository;

    public CreateBookingMongoCommandHandler(BookingReadRepository bookingReadRepository) {
        this.bookingReadRepository = bookingReadRepository;
    }

    @Override
    public Unit handle(CreateBookingMongoCommand command) {

        BookingDocument existBooking = bookingReadRepository.findBookingByBookingIdAndIsDeletedFalse(command.id());
        if (existBooking != null) {
            throw new BookingAlreadyExistException();
        }

        BookingDocument bookingDocument = Mappings.toBookingDocument(command);

        bookingReadRepository.save(bookingDocument);

        return Unit.VALUE;
    }
}
