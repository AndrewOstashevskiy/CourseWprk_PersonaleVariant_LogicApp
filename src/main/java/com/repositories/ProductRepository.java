package com.repositories;

import com.domain.ItemStatus;
import com.domain.Product;
import com.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    List<Product> findByDomainAndOwnerAndSoldFalse(String domain, User user);

    List<Product> findByOwnerAndStatus(User user, ItemStatus status);

    List<Product> findByOldOwnerAndSoldTrue(UUID old);

    @Query("SELECT p FROM Product p "
            + "WHERE p.placed = true")
    List<Product> findProductsAvailableToBuy(User owner, boolean sold);

    @Query("SELECT p FROM Product p "
            + "WHERE p.status = 'ACTIVE' or p.status = 'SOLD'")
    List<Product> selectAllExceptPending();

    @Query("SELECT p FROM Product p "
            + "WHERE p.owner = ?1 and p.status = 'ACTIVE' and p.placed = true")
    List<Product> getAllInSelling(User user);
}
