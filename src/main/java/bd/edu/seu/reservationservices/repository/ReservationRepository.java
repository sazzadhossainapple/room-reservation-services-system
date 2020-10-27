package bd.edu.seu.reservationservices.repository;
import bd.edu.seu.reservationservices.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    public List<Reservation> findReservationByFromDateBetween(LocalDate fromDate,
                                                              LocalDate toDate);
}
