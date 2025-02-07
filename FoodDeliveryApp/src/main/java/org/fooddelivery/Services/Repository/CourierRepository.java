package org.fooddelivery.Services.Repository;

import org.fooddelivery.DomainModel.Users.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

}
