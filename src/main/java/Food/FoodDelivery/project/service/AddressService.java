package Food.FoodDelivery.project.service;

import Food.FoodDelivery.project.DTO.RequestDTO.AddressesRequestDto;
import Food.FoodDelivery.project.DTO.ResponseDTO.AddressesResponseDto;
import Food.FoodDelivery.project.Entity.*;
import Food.FoodDelivery.project.Enum.AddressType;
import Food.FoodDelivery.project.Exceptions.CustomEntityNotFoundException;
import Food.FoodDelivery.project.Mapper.AddressesMapper;
import Food.FoodDelivery.project.Repository.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressesMapper addressesMapper;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final EntityManager entityManager;

    public AddressesResponseDto addAddressByUuid(String uuid, AddressesRequestDto dto) {
        Users user = validateUser(uuid);
        Country country = countryRepository.isValidCityStateCountry(dto.getCityId(), dto.getStateId(), dto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid City-State-Country combination"));

        AddressType addressType = AddressType.valueOf(dto.getAddressType());

        boolean exists = addressRepository.existsByUserAndAddressType(user.getId(),addressType);
        if (exists) {
            throw new IllegalArgumentException("User already has an address of type: " + dto.getAddressType());
        }

        Addresses address = addressesMapper.toEntity(dto);
        address.setUser(user);
        address.setCountry(country);
        address.setState(entityManager.getReference(State.class, dto.getStateId()));
        address.setCity(entityManager.getReference(City.class, dto.getCityId()));

        Addresses saved = addressRepository.save(address);
        return addressesMapper.toResponse(saved);
    }

    public List<AddressesResponseDto> getAllAddressesByUserUuid(String uuid) {
        Users user = userRepository.findByUserIdAndIsActiveTrue(uuid)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active user not found with UUID: " + uuid));

        List<Addresses> addresses = addressRepository.findByUserIdAndIsActive(user.getId(), true);

       return addressesMapper.toResponseList(addresses);
    }

    @Transactional
    public AddressesResponseDto softDeleteAddress(String uuid, Long addressId) {
        Users user = validateUser(uuid);
        Addresses address = addressRepository.findByIdAndIsActive(addressId, true);

        if (address == null) {
            throw new CustomEntityNotFoundException("Active address not found with ID: " + addressId);
        }

        if (!address.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Address does not belong to the user");
        }
        address.setIsActive(false);
        Addresses deletedAddress = addressRepository.save(address);
        return addressesMapper.toResponse(deletedAddress);
    }

    @Transactional
    public AddressesResponseDto updateAddressByUuid(String uuid, Long addressId, AddressesRequestDto dto) {
        Users user = validateUser(uuid);
        Addresses address = addressRepository.findByIdAndIsActive(addressId, true);
        if (address == null) {
            throw new CustomEntityNotFoundException("Active address not found with ID: " + addressId);
        }

        if (!address.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Address does not belong to the user");
        }

        Country country = countryRepository.isValidCityStateCountry(dto.getCityId(), dto.getStateId(), dto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid City-State-Country combination"));

        address.setCountry(country);
        address.setState(entityManager.getReference(State.class, dto.getStateId()));
        address.setCity(entityManager.getReference(City.class, dto.getCityId()));

        addressesMapper.updateEntityFromDto(dto, address);

        Addresses updatedAddress = addressRepository.save(address);

        return addressesMapper.toResponse(updatedAddress);
    }

    private Users validateUser(String uuid) {
        return userRepository.findByUserIdAndIsActiveTrue(uuid)
                .orElseThrow(() -> new CustomEntityNotFoundException("Active user not found with UUID: " + uuid));
    }

}
