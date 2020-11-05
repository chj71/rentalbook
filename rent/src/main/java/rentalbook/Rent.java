package rentalbook;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Rent_table")
public class Rent {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String status;

    @PostUpdate
    public void onPostUpdate(){

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(this.status);
        System.out.println(this.id);
        System.out.println(this.orderId);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        if ("Rent".equals(this.status)) {
            Rented rented = new Rented();
            BeanUtils.copyProperties(this, rented);
            rented.setStatus("Rented");
            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

            rentalbook.external.Delivery delivery = new rentalbook.external.Delivery();
            // mappings goes here
            delivery.setOrderId(rented.getOrderId());
            delivery.setStatus(rented.getStatus());

            RentApplication.applicationContext.getBean(rentalbook.external.DeliveryService.class)
                    .ship(delivery);

            rented.publishAfterCommit();
        }
        else if ("Rent Cancel".equals(this.status)) {

            RentCanceled rentCanceled = new RentCanceled();
            BeanUtils.copyProperties(this, rentCanceled);
            rentCanceled.setStatus("Rent Canceled");
            rentCanceled.publishAfterCommit();

        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
