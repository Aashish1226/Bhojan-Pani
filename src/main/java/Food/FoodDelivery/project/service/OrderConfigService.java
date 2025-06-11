package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.OrderConfigRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.OrderConfigResponseDTO;
import Food.FoodDelivery.project.Entity.OrderConfig;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.OrderConfigMapper;
import Food.FoodDelivery.project.Repository.OrderConfigRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderConfigService {

    private final OrderConfigRepository orderConfigRepository;
    private final OrderConfigMapper orderConfigMapper;

    public OrderConfigService(OrderConfigRepository orderConfigRepository, OrderConfigMapper orderConfigMapper) {
        this.orderConfigRepository = orderConfigRepository;
        this.orderConfigMapper = orderConfigMapper;
    }

    public List<OrderConfigResponseDTO> getOrderConfigs(Boolean isActive) {
        List<OrderConfig> configs = orderConfigRepository.findByIsActive(isActive);
        return configs.stream()
                .map(orderConfigMapper::toDto)
                .toList();
    }

    @Transactional
    public OrderConfigResponseDTO updateOrderConfig(Long id, OrderConfigRequestDTO requestDTO) {
        OrderConfig config = orderConfigRepository.findByIdAndIsActive(id , true)
                .orElseThrow(() -> new CustomEntityNotFoundException("OrderConfig not found with id: " + id + " or is not active"));

        orderConfigMapper.updateOrderConfigFromDto(requestDTO , config);

        OrderConfig updatedConfig = orderConfigRepository.save(config);
        return orderConfigMapper.toDto(updatedConfig);
    }

    @Transactional
    public void softDeleteOrderConfig(Long id) {
        OrderConfig config = orderConfigRepository.findByIdAndIsActive(id , true)
                .orElseThrow(() -> new CustomEntityNotFoundException("OrderConfig not found with id: " + id + " or is not active"));

        config.setIsActive(false);
        orderConfigRepository.save(config);
    }
}
