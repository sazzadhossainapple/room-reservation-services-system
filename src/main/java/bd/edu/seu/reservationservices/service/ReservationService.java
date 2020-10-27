package bd.edu.seu.reservationservices.service;
import bd.edu.seu.reservationservices.exception.ResourceAlreadyExistException;
import bd.edu.seu.reservationservices.exception.ResourceDoseNotExistException;
import bd.edu.seu.reservationservices.model.Reservation;
import bd.edu.seu.reservationservices.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation findById(int id) throws ResourceDoseNotExistException{

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isPresent())
        {
            return optionalReservation.get();
        }
        else {
            throw new ResourceDoseNotExistException(id + "");
        }
    }


    public List<Reservation> findAll() {
        List<Reservation>reservationList = new ArrayList<>();
        reservationRepository.findAll().forEach(reservationList :: add);
        return reservationList;
    }


    public boolean deleteById(int id) throws ResourceDoseNotExistException {

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        optionalReservation.ifPresent(reservation -> reservationRepository.deleteById(id));
        optionalReservation.orElseThrow(() -> new ResourceDoseNotExistException(id + ""));
        return true;

    }

    public Reservation insertReservation(Reservation reservation) throws ResourceAlreadyExistException {
        Optional<Reservation> optionalReservation= reservationRepository.findById(reservation.getId());
        if (optionalReservation.isPresent()) {
            throw  new ResourceAlreadyExistException(reservation.getId() + "");
        } else {
            return reservationRepository.save(reservation);
        }

    }

    public Reservation updateReservation (int id ,Reservation reservation) throws ResourceDoseNotExistException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {

            reservation.setId(id);
            return reservationRepository.save(reservation);

        } else {
            throw  new ResourceDoseNotExistException(id+ "");
        }
    }



}
