package bd.edu.seu.reservationservices.controller;
import bd.edu.seu.reservationservices.exception.ResourceAlreadyExistException;
import bd.edu.seu.reservationservices.exception.ResourceDoseNotExistException;
import bd.edu.seu.reservationservices.model.Room;
import bd.edu.seu.reservationservices.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v3/rooms")
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable int id) {

        try {
            Room room = roomService.findById(id);
            return ResponseEntity.ok(room);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            //return studentRepository.findById(id).get();
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("")
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> roomList = roomService.findAll();
        return ResponseEntity.ok(roomList);
    }


    @PostMapping("")
    public ResponseEntity<Room> insertRoom(@RequestBody Room room) {
        try {
            Room insertRoom = roomService.insertRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertRoom);
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<?> saveResource(@RequestBody Room room,
                                          @PathVariable("id") int id) throws ResourceDoseNotExistException {

        Room currentUser = roomService.findById(id);
        if (currentUser == null) {
            return new ResponseEntity(new ResourceDoseNotExistException("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.setNumber(room.getNumber());
        currentUser.setRoomType(room.getRoomType());
        roomService.updateRoom(id,currentUser);
        return new ResponseEntity<Room>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRoom(@PathVariable int id) {
        try {
            boolean deleted = roomService.deleteById(id);
            return ResponseEntity.ok(id);
        } catch (ResourceDoseNotExistException e) {
            return ResponseEntity.notFound().build();

        }

    }
}
