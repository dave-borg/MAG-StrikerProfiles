package au.com.mag.booking.striker_profiles.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import au.com.mag.booking.striker_profiles.model.User;
import au.com.mag.booking.striker_profiles.repository.UserRepository;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    @SuppressWarnings("null")
    @Test
    void testAddUser() {
        // Arrange
        String name = "Chuck Yeager";
        String email = "chuck@usaf.gov";
        int arn = 123;
        String phoneNumber = "1234567890";

        User user = new User(name, email, arn, phoneNumber);

        when(validator.validate(any(User.class))).thenReturn(Collections.emptySet());
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByArn(arn)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.addUser(name, email, arn, phoneNumber);

        // Assert
        assertEquals(user, savedUser);
    }

    @Test
    void testAddUserWithDuplicateEmail() {
        // Arrange
        String name = "Chuck Yeager";
        String email = "chuck@usaf.gov";
        int arn = 123;
        String phoneNumber = "1234567890";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(name, email, arn, phoneNumber));

        // Assert
        String expectedMessage = "Pilot email already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testAddUserWithDuplicateArn() {
        // Arrange
        String name = "Test Name";
        String email = "test@email.com";
        int arn = 123;
        String phoneNumber = "1234567890";

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByArn(arn)).thenReturn(true);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(name, email, arn, phoneNumber));

        // Assert
        String expectedMessage = "ARN already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }   
}