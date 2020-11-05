package rentalbook;

import rentalbook.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    MyDeliveryRepository myDeliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentCanceled_ShipCancel(@Payload RentCanceled rentCanceled){

        if(rentCanceled.isMe()){
            System.out.println("##### listener ShipCancel : " + rentCanceled.toJson());

            /*
            // view 객체 조회
            List<Delivery> deliveryList = deliveryRepository.findByOrderId(rentCanceled.getOrderId());
            for(Delivery delivery : deliveryList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                delivery.setStatus(rentCanceled.getStatus());
                // view 레파지 토리에 save
                deliveryRepository.save(delivery);
            }
            */
            List<Delivery> myDeliveryList = myDeliveryRepository.findByOrderId(rentCanceled.getOrderId());
            for(Delivery myDelivery : myDeliveryList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                myDelivery.setStatus("Ship Canceled");
                // view 레파지 토리에 save
                myDeliveryRepository.save(myDelivery);
            }
        }
    }

}
