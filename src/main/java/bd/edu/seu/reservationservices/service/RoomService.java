package bd.edu.seu.reservationservices.service;
import bd.edu.seu.reservationservices.exception.ResourceAlreadyExistException;
import bd.edu.seu.reservationservices.exception.ResourceDoseNotExistException;
import bd.edu.seu.reservationservices.model.Room;
import bd.edu.seu.reservationservices.repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findById(int id) throws ResourceDoseNotExistException {

        Optional<Room> optionalRoom= roomRepository.findById(id);
        if(optionalRoom.isPresent())
        {
            return optionalRoom.get();
        }
        else {
            throw new ResourceDoseNotExistException(id + "");
        }
    }


    public List<Room> findAll() {
        List<Room>roomList = new ArrayList<>();
       roomRepository.findAll().forEach(roomList:: add);
        return roomList;
    }


    public boolean deleteById(int id) throws ResourceDoseNotExistException{

        Optional<Room> optionalRoom = roomRepository.findById(id);
        optionalRoom.ifPresent(room -> roomRepository.deleteById(id));
        optionalRoom.orElseThrow(() -> new ResourceDoseNotExistException(id + ""));
        return true;

    }

    public Room insertRoom(Room room) throws ResourceAlreadyExistException {
        Optional<Room> optionalRoom = roomRepository.findById(room.getId());
        if (optionalRoom.isPresent()) {
            throw  new ResourceAlreadyExistException(room.getId() + "");
        } else {
            return roomRepository.save(room);
        }

    }

    public Room updateRoom(int id ,Room room) throws ResourceDoseNotExistException {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {

         room.setId(id);
            return roomRepository.save(room);

        } else {
            throw  new ResourceDoseNotExistException(id+ "");
        }
    }
}
