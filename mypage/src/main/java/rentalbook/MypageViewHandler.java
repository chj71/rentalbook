package rentalbook;

import rentalbook.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setOrderId(ordered.getId());
                mypage.setItem(ordered.getItem());
                mypage.setStatus(ordered.getStatus());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelled_then_UPDATE_1(@Payload OrderCancelled orderCancelled) {
        try {
            if (orderCancelled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(orderCancelled.getId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(orderCancelled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenRentCanceled_then_UPDATE_2(@Payload RentCanceled rentCanceled) {
        try {
            if (rentCanceled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(rentCanceled.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(rentCanceled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenShipped_then_UPDATE_3(@Payload Shipped shipped) {
        try {
            if (shipped.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(shipped.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(shipped.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCanceled_then_UPDATE_4(@Payload DeliveryCanceled deliveryCanceled) {
        try {
            if (deliveryCanceled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(deliveryCanceled.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(deliveryCanceled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}