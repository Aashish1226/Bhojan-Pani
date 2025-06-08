package Food.FoodDelivery.project.Controller;

import Food.FoodDelivery.project.DTO.RequestDTO.AddressesRequestDto;
import Food.FoodDelivery.project.DTO.ResponseDTO.*;
import Food.FoodDelivery.project.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("addresses")
@RequiredArgsConstructor
public class AddressesController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressesResponseDto> addAddress(@RequestAttribute("userUUID") String uuid,@Valid @RequestBody AddressesRequestDto requestDto) {
        AddressesResponseDto response = addressService.addAddressByUuid(uuid, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressesResponseDto>> getAllAddressesByUserUuid(@RequestAttribute("userUUID") String uuid) {
        List<AddressesResponseDto> addresses = addressService.getAllAddressesByUserUuid(uuid);
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressesResponseDto> updateAddress(@RequestAttribute("userUUID") String uuid,@PathVariable Long addressId,@Valid @RequestBody AddressesRequestDto dto) {
        AddressesResponseDto updatedAddress = addressService.updateAddressByUuid(uuid, addressId, dto);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressesResponseDto> deleteAddress(@RequestAttribute("userUUID") String uuid,@PathVariable Long id) {
        AddressesResponseDto response = addressService.softDeleteAddress(uuid, id);
        return ResponseEntity.ok(response);
    }

}
