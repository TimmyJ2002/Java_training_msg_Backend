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


  @Modifying
  @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
  void changeUserPassword(@Param("userId") Long userId, @Param("newPassword") int newPassword);
  @Modifying
  @Query("UPDATE User u SET u.loginCount = :newLoginCount WHERE u.id = :userId")
  void updateLoginCount(@Param("userId") Long userId, @Param("newLoginCount") int newLoginCount);
  boolean existsByMobileNumber(String mobileNumber);


  boolean existsByEmailAndIdNot(String email, Long id);

  boolean existsByMobileNumberAndIdNot(String mobileNumber, Long id);

}
