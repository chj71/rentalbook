package rentalbook;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MyDeliveryRepository extends CrudRepository<Delivery, Long> {
    List<Delivery> findByOrderId(Long orderId);

}