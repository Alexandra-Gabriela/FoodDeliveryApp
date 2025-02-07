package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Users.RestaurantAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantAdminRepository extends JpaRepository<RestaurantAdmin, Long> {
}
