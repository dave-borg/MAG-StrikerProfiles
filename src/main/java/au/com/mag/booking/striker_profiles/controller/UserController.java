package au.com.mag.booking.striker_profiles.controller;

import java.net.URI;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.mag.booking.striker_profiles.model.User;
import au.com.mag.booking.striker_profiles.service.UserService;
import jakarta.validation.Valid;

/**
 * This is the UserController, and don't call it Shirley! It's responsible for
 * handling HTTP requests related to User entities.
 * It uses the UserService to perform operations on User entities and returns
 * HTTP responses.
 * 
 * @RestController Indicates that this class is a controller and the return
 *                 value of the methods should be bound to the web response
 *                 body.
 *                 @RequestMapping("/striker_profile/user") Maps all HTTP
 *                 operations by default to /striker_profile/user.
 */
@RestController
@RequestMapping("/striker_profile/user")
public class UserController {
    private static final Logger log = LogManager.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Adds a new user. If the user can't be added, it returns an error message.
     * "Looks like I picked the wrong week to quit sniffing glue." (not really) - if
     * the user
     * can't be added.
     *
     * @param userParams The parameters of the user to be added. This must be a
     *                   valid User object.
     * @param result     The result of the validation of the userParams.
     *
     * @return ResponseEntity<?> If the user is added successfully, it returns the
     *         user and the location of the user in the header. If the user can't be
     *         added, it returns an error message.
     *
     *         @PostMapping("/add") Maps the following method to the HTTP POST
     *         method on /striker_profile/user/add.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Validated @RequestBody User userParams, BindingResult result) {
        if (result.hasErrors()) {
            if (log.isWarnEnabled())
                log.warn(String.format("Result had errors: %s", result.getAllErrors().get(0).getDefaultMessage()));
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }

        if (log.isInfoEnabled())
            log.info(String.format("addUser(%s)", userParams.toString()));

        if (log.isDebugEnabled()) {
            log.debug(String.format("Name: %s", userParams.getName()));
            log.debug(String.format("Email: %s", userParams.getEmail()));
            log.debug(String.format("ARN: %s", userParams.getArn()));
            log.debug(String.format("Phone: %s", userParams.getPhoneNumber()));
        }

        User newUser;

        try {
            newUser = userService.addUser(userParams.getName(), userParams.getEmail(), userParams.getArn(),
                    userParams.getPhoneNumber());
            if (log.isDebugEnabled()) {
                log.debug(String.format("Added user: %s", newUser));
            }
        } catch (Exception e) {
            if (log.isWarnEnabled())
                log.warn(String.format("Error adding user: %s", e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Error adding user: %s", e.getMessage()));
        }

        if (newUser == null) {
            log.error("User should have been created but is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(newUser);
    }

    /**
     * Finds a user by their email. It's an entirely different kind of flying,
     * altogether.
     * "It's an entirely different kind of flying."
     *
     * @param email The email of the user to be found. This must be a valid email
     *              address.
     *
     * @return ResponseEntity<User> If the user is found, it returns the user
     *         wrapped in a ResponseEntity with an OK status. If the user is not
     *         found, it returns a ResponseEntity with a NOT_FOUND status.
     *
     *         @GetMapping("/findByEmail") Maps the following method to the HTTP GET
     *         method on /striker_profile/user/findByEmail.
     */
    @GetMapping("/findByEmail")
    public ResponseEntity<User> getUser(@Valid @RequestParam String email) {
        if (log.isInfoEnabled())
            log.info(String.format("findByEmail(%s)", email));

        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Finds a user by their ARN. 
     *
     * @param arn The ARN of the user to be found. This must be a valid ARN.
     *
     * @return ResponseEntity<User> If the user is found, it returns the user
     *         wrapped in a ResponseEntity with an OK status. If the user is not
     *         found, it returns a ResponseEntity with a NOT_FOUND status.
     *
     *         @GetMapping("/findByArn") Maps the following method to the HTTP GET
     *         method on /striker_profile/user/findByArn.
     */
    @GetMapping("/findByArn")
    public ResponseEntity<User> getUser(@RequestParam int arn) {
        if (log.isInfoEnabled())
            log.info(String.format("findByArn(%s)", arn));

        Optional<User> user = userService.findByArn(arn);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates the details of an existing user. 
     *
     * @param existingEmail The email of the user to be updated. This must be a
     *                      valid email address.
     * @param newEmail      The new email to be set for the user. This must be a
     *                      valid email address and not already in use.
     * @param newName       The new name to be set for the user. This must be a
     *                      valid name.
     * @param newArn        The new ARN to be set for the user. This must be a valid
     *                      ARN and not already in use.
     * @param newPhoneNumber The new phone number to be set for the user. This must be a valid phone number.
     *
     * @return ResponseEntity<String> If the user is found and updated successfully,
     *         it returns a ResponseEntity with an OK status. If the user is not
     *         found, it returns a ResponseEntity with a NOT_FOUND status.
     *
     *         @PutMapping("/update") Maps the following method to the HTTP PUT
     *         method on /striker_profile/user/update.
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestParam String existingEmail,
            @Valid @RequestParam String newEmail, @Valid @RequestParam String newName, @Valid @RequestParam int newArn,
            @Valid @RequestParam String newPhoneNumber) {
        boolean isUpdated = userService.updateUser(existingEmail, newEmail, newName, newArn, newPhoneNumber);
        if (isUpdated) {
            return ResponseEntity.ok("User with email " + newEmail + " updated successfully");
        } else {
            log.info(String.format("User with email %s not found", existingEmail));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + existingEmail + " not found");
        }
    }

    /**
     * Deletes a user by their email. 
     *
     * @param email The email of the user to be deleted. This must be a valid email
     *              address.
     *
     * @return ResponseEntity<String> If the user is found and deleted successfully,
     *         it returns a ResponseEntity with an OK status. If the user is not
     *         found, it returns a ResponseEntity with a NOT_FOUND status.
     *
     *         @DeleteMapping("/deleteByEmail") Maps the following method to the
     *         HTTP DELETE method on /striker_profile/user/deleteByEmail.
     */
    @DeleteMapping("/deleteByEmail")
    public ResponseEntity<String> deleteUser(@Valid @RequestParam String email) {
        boolean isDeleted = userService.deleteByEmail(email);
        if (isDeleted)
            return ResponseEntity.ok("User with email " + email + " deleted successfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
    }
}