package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.CartItemRequestDTO;
import Food.FoodDelivery.project.DTO.ResponseDTO.CartResponseDTO;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.CartMapper;
import Food.FoodDelivery.project.Repository.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final FoodVariantRepository foodVariantRepository;
    private final CartItemRepository cartItemRepository;
    private final EntityManager entityManager;

    @Transactional
    public CartResponseDTO incrementItem(CartItemRequestDTO requestDTO, String userUUID) {
        Users user = validateUser(userUUID);

        Cart cart = cartRepository.findByUserAndIsActive(user, true)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        Long foodId = requestDTO.getFoodId();
        Long variantId = requestDTO.getFoodVariantId();

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getFood().getId().equals(foodId)
                        && Objects.equals(
                        item.getFoodVariant() != null ? item.getFoodVariant().getId() : null,
                        variantId)
                        && item.getIsActive())
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepository.save(existingItem);
        } else {
            Food food = foodRepository.findByIdAndIsAvailableAndIsActive(foodId, true, true)
                    .orElseThrow(() -> new CustomEntityNotFoundException("Food not found or unavailable"));

            CartItem newItem = new CartItem();
            newItem.setFood(food);
            newItem.setQuantity(1);
            newItem.setCart(cart);
            newItem.setIsActive(true);

            if (variantId != null) {
                FoodVariant variant = foodVariantRepository.findByIdAndIsActiveAndIsAvailable(variantId, true, true)
                        .orElseThrow(() -> new CustomEntityNotFoundException("Food variant not found or unavailable"));

                if (!variant.getFood().getId().equals(foodId)) {
                    throw new IllegalArgumentException("Variant does not belong to the specified food");
                }

                newItem.setFoodVariant(variant);
            }

            CartItem savedItem = cartItemRepository.save(newItem);
            cart.getItems().add(savedItem);
        }

        Cart savedCart = cartRepository.save(cart);

        entityManager.flush();

        return cartMapper.toDto(savedCart);
    }

    @Transactional
    public CartResponseDTO decrementItem(CartItemRequestDTO requestDTO, String userUUID) {
        Users user = validateUser(userUUID);

        Cart cart = cartRepository.findByUserAndIsActive(user, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active cart not found"));

        Long foodId = requestDTO.getFoodId();
        Long variantId = requestDTO.getFoodVariantId();

        CartItem item = cart.getItems().stream()
                .filter(i ->
                        i.getFood().getId().equals(foodId) &&
                                Objects.equals(
                                        i.getFoodVariant() != null ? i.getFoodVariant().getId() : null,
                                        variantId)
                )
                .findFirst()
                .orElseThrow(() -> new CustomEntityNotFoundException("Cart item not found"));

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        } else {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        }

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDto(savedCart);
    }

    @Transactional
    public CartResponseDTO getCart(String userUUID) {
        Users user = validateUser(userUUID);

        Cart cart = cartRepository.findByUserAndIsActive(user, true)
                .orElse(null);

        if (cart == null || cart.getItems().isEmpty()) {
            CartResponseDTO emptyCart = new CartResponseDTO();
            emptyCart.setUserId(user.getId());
            emptyCart.setItems(List.of());
            return emptyCart;
        }
        return cartMapper.toDto(cart);
    }

    @Transactional
    public void deleteCart(String userUUID) {
        Users user = validateUser(userUUID);

        Cart cart = cartRepository.findByUserAndIsActive(user, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active cart not found"));

        cart.setIsActive(false);

        if (cart.getItems() != null) {
            cart.getItems().forEach(item -> item.setIsActive(false));
        }

        cartRepository.save(cart);
    }

    private Users validateUser(String userUUID) {
        return userRepository.findByUserIdAndIsActiveTrue(userUUID)
                .orElseThrow(() -> new CustomEntityNotFoundException("User not found for UUID: " + userUUID));
    }

    @Transactional
    public void deleteCartItem(Long cartItemId, String userUUID) {
        Users user = validateUser(userUUID);

        Cart cart = cartRepository.findByUserAndIsActive(user, true)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new CustomEntityNotFoundException("Cart item not found for ID: " + cartItemId));

        cart.getItems().remove(item);

        cartRepository.save(cart);
    }

    public Page<CartResponseDTO> getCartHistory(String userUUID, int page, int size, String sortDirection) {
        Users user = userRepository.findByUserIdAndIsActiveTrue(userUUID)
                .orElseThrow(() -> new CustomEntityNotFoundException("User not found."));

        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createDate"));

        Page<Cart> pagedHistory = cartRepository.findByUserIdAndIsActive(user.getId(), false, pageable);

        return pagedHistory.map(cartMapper::toDto);
    }
}
