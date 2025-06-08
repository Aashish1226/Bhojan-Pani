package Food.FoodDelivery.project.Repository;

import Food.FoodDelivery.project.Entity.Country;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findByRegionId(Long regionId);

    boolean existsByIso2(String iso2);

    boolean existsByName(String name);

    boolean existsByPhoneCode(String phoneCode);

    @Query("""
    SELECT cn FROM City c
    JOIN c.state s
    JOIN s.country cn
    WHERE c.id = :cityId AND s.id = :stateId AND cn.id = :countryId
""")
    Optional<Country> isValidCityStateCountry(@Param("cityId") Long cityId,
                                              @Param("stateId") Long stateId,
                                              @Param("countryId") Long countryId);

    List<Country> findByNameInIgnoreCase(List<String> names);



    @Query("SELECT c FROM Country c WHERE LOWER(c.name) NOT IN :names ORDER BY c.name")
    List<Country> findAllExcludingNames(@Param("names") List<String> lowerNames);

}
