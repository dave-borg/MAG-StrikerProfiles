package au.com.mag.booking.striker_profiles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.mag.booking.striker_profiles.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByArn(int arn);
    
}
