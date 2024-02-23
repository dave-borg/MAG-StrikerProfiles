package au.com.mag.booking.striker_profiles.service;

import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import au.com.mag.booking.striker_profiles.model.User;
import au.com.mag.booking.striker_profiles.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

/**
 * This class is where the magic happens, dig? Like that scene in Airplane! where everyone's tryna 
 * land that big ol' bird without a scratch. Here, we're handling all the dope operations related to users - 
 * adding 'em, finding 'em, deleting 'em, and updating their groovy details. It's the cool cat in the alley 
 * of user management, backed by a UserRepository to keep things tight and a Validator to keep it on the 
 * straight and narrow.
 */
@Service
public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final Validator validator;

    /**
     * Constructs a new UserService, ready to jive with user data. It's like picking your best dance partner;
     * you need one that knows the steps (Validator) and one that knows all the cool folks (UserRepository).
     *
     * @param validator the rhythm keeper ensuring all moves are slick and legal.
     * @param userRepository the DJ spinning the records of all the cats in the system.
     */
    public UserService(Validator validator, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    /**
     * Adds a new user to the disco floor, making sure they got the moves (valid details) and ain't stepping
     * on anyone's toes (duplicate email or ARN). Throws down a beat of validation before letting them join the party.
     *
     * @param name The groover's name.
     * @param email Their handle in the digital sphere, gotta be unique.
     * @param arn Their access record number, like a VIP pass.
     * @param phoneNumber Their digits for a late-night call.
     * @return The new cat on the dance floor, all shiny and checked in.
     */
    public User addUser(String name, String email, int arn, String phoneNumber) {
        log.info(String.format("Adding new user {name: %s, email: %s, ARN: %d, Phone: %s}", name, email, arn, phoneNumber));

        User user = new User(name, email, arn, phoneNumber);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            log.error(String.format("Error adding user: %s", violations));
            throw new ConstraintViolationException(violations);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            log.debug("Error adding user: Email already exists");
            throw new IllegalArgumentException("Pilot email already exists");
        }
        if (userRepository.existsByArn(user.getArn())) {
            log.debug("Error adding user: ARN already exists");
            throw new IllegalArgumentException("ARN already exists");
        }

        log.debug("Validation passed, saving user");
        return userRepository.save(user);
    }

    /**
     * Gets a user by their email, like finding where they're grooving in the discotheque. No email, no find, 
     * that's the rule of the dance floor.
     *
     * @param email The digital invite to their dance space.
     * @return An optional boogie wonderland participant, might be there, might not.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Gets a user by their ARN, like having a secret handshake. No correct shake, no entry.
     *
     * @param arn The special code to their clubhouse.
     * @return An optional member of the secret groove society.
     */
    public Optional<User> findByArn(int arn) {
        return userRepository.findByArn(arn);
    }

    /**
     * Makes a user disappear from the record, like they never busted a move. If they're not on the list, 
     * they're already ghosting us.
     *
     * @param email The digital footprint to erase.
     * @return true if the ghost was successfully busted, false if they were an apparition.
     */
    @SuppressWarnings("null")
    public boolean deleteByEmail(@Valid String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates the user's groove in the system. Checks if their new moves aren't stepping on anyones style 
     * (duplicates) and keeps them looking sharp (validation). It's like a costume change for their next dance number.
     *
     * @param existingEmail The old-school email, their previous dance move.
     * @param newEmail The new groove, gotta be fresh and not copied.
     * @param newName Their new stage name, shining under the disco ball.
     * @param newArn Their new secret club pass.
     * @param phoneNumber Their updated digits, for the after-party.
     * @return true if they're back on the dance floor with a new look, false if they missed the beat.
     */
    public boolean updateUser(@Valid String existingEmail, @Valid String newEmail, @Valid String newName, @Valid int newArn, @Valid String phoneNumber) {
        Optional<User> user = userRepository.findByEmail(existingEmail);
        if (user.isPresent()) {
            User existingUser = user.get();

            // Check if the new email is duplicated
            if (!existingUser.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
                throw new ConstraintViolationException("Email is duplicated", null);
            }

            // Check if the new ARN is duplicated
            if (existingUser.getArn() != newArn && userRepository.existsByArn(newArn)) {
                throw new ConstraintViolationException("ARN is duplicated", null);
            }

            existingUser.setName(newName);
            existingUser.setEmail(newEmail);
            existingUser.setArn(newArn);
            existingUser.setPhoneNumber(phoneNumber);

            // Validate the updated user
            Set<ConstraintViolation<User>> violations = validator.validate(existingUser);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            userRepository.save(existingUser);
            return true;
        }
        return false;
    }
}