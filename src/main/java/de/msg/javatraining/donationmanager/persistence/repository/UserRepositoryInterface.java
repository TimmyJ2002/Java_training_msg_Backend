package de.msg.javatraining.donationmanager.persistence.repository;

import de.msg.javatraining.donationmanager.persistence.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryInterface extends JpaRepository<User, Long> {
  User findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Transactional
  @Modifying // It means it's not a select statement
  @Query(value = "UPDATE user set password = :password", nativeQuery = true)
  void changeUserPassword(@Param("password") String password);
}
