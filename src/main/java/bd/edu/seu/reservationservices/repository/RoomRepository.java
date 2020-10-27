package bd.edu.seu.reservationservices.repository;
import bd.edu.seu.reservationservices.model.Room;
import bd.edu.seu.reservationservices.model.RoomType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    public Optional<Room> findRoomByNumber(int number);
    public List<Room> findRoomByRoomType(RoomType roomType);
}
