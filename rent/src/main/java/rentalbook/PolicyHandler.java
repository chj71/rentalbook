package rentalbook;

import rentalbook.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    RentRepository rentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_RentOrder(@Payload Ordered ordered){

        if(ordered.isMe()){
            System.out.println("##### listener RentOrder : " + ordered.toJson());
            Rent rent = new Rent();
            rent.setOrderId(ordered.getId());
            rent.setStatus(ordered.getStatus());

            rentRepository.save(rent);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_RentCancel(@Payload OrderCancelled orderCancelled){

        if(orderCancelled.isMe()){
            System.out.println("##### listener RentCancel : " + orderCancelled.toJson());
            Optional<Rent> rentOptional = rentRepository.findById(orderCancelled.getId());
            Rent rent = rentOptional.get();
            rent.setStatus(orderCancelled.getStatus());

            rentRepository.save(rent);
        }
    }

}
