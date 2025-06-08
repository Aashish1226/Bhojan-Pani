package Food.FoodDelivery.project.Mapper;

import Food.FoodDelivery.project.DTO.RequestDTO.PaymentRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.PaymentResponseDTO;
import Food.FoodDelivery.project.Entity.Payments;
import org.mapstruct.*;

@Mapper(componentModel = "spring" , uses = {OrderMapper.class})
public interface PaymentMapper {

    @Mapping(target = "orders", ignore = true)
    Payments toEntity(PaymentRequestDTO dto);

    @Mapping(source = "orders.id" , target = "orderId")
    PaymentResponseDTO toResponseDTO(Payments payments);

}
