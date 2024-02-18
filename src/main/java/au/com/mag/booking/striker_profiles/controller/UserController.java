package au.com.mag.booking.striker_profiles.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.mag.booking.striker_profiles.model.User;
import au.com.mag.booking.striker_profiles.service.UserService;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/striker_profile/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestParam String name, @RequestParam String email, @RequestParam int arn,
            @RequestParam String pwdHash) {
        User newUser = userService.addUser(new User(name, email, arn, pwdHash));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(newUser);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<User> getUser(@RequestParam String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findByArn")
    public String getUser(@RequestParam int arn) {
        return new String();
    }

    @PutMapping("/update")
    public String updateUser(@RequestParam String email, @RequestParam String name, @RequestParam String pwdHash) {
        return new String();
    }

    @DeleteMapping("/deleteByEmail")
    public String deleteUser(@RequestParam String email) {
        return new String();
    }

    @DeleteMapping("/deleteByArn")
    public String deleteUser(@RequestParam int arn) {
        return new String();
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String pwdHash) {
        return new String();
    }

    @PostMapping("/logout")
    public String logoutUser(@RequestParam String email) {
        return new String();
    }

    @GetMapping("/request_method_name")
    public String requestMethodName(@RequestParam String param) {
        return new String();
    }
}
