package bd.edu.seu.reservationservices.controller;
import bd.edu.seu.reservationservices.exception.ResourceAlreadyExistException;
import bd.edu.seu.reservationservices.exception.ResourceDoseNotExistException;
import bd.edu.seu.reservationservices.model.Reservation;
import bd.edu.seu.reservationservices.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v3/reservations")
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getStudent(@PathVariable int id) {

        try {
            Reservation reservation = reservationService.findById(id);
            return ResponseEntity.ok(reservation);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservationList = reservationService.findAll();
        return ResponseEntity.ok(reservationList);
    }



    @PostMapping("")
    public ResponseEntity<Reservation> insertReservation(@RequestBody Reservation reservation) {
        try {
            Reservation insertReservation = reservationService.insertReservation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertReservation);
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }
    @PutMapping("/reservations/{id}")
    public ResponseEntity<?> saveResource(@RequestBody Reservation reservation,
                                          @PathVariable("id") int id) throws ResourceDoseNotExistException {

        Reservation currentUser = reservationService.findById(id);
        if (currentUser == null) {
            return new ResponseEntity(new ResourceDoseNotExistException("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.setName(reservation.getName());
        currentUser.setFromDate(reservation.getFromDate());
        currentUser.setToDate(reservation.getToDate());

       reservationService.updateReservation(id,currentUser);
        return new ResponseEntity<Reservation>(currentUser, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteReservation(@PathVariable int id) {
        try {
            boolean deleted = reservationService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();

        }

    }
}
