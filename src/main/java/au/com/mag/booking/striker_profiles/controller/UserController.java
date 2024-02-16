package au.com.mag.booking.striker_profiles.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import au.com.mag.booking.striker_profiles.model.User;
import au.com.mag.booking.striker_profiles.service.UserService;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/striker_profile/user")
public class UserController {

    private final UserService userService;

    @Autowired
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

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getUser(@RequestParam int arn) {
        return new String();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String updateUser(@RequestParam String email, @RequestParam String name, @RequestParam String pwdHash) {
        return new String();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam String email) {
        return new String();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam int arn) {
        return new String();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@RequestParam String email, @RequestParam String pwdHash) {
        return new String();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutUser(@RequestParam String email) {
        return new String();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutUser(@RequestParam int arn) {
        return new String();
    }

    @RequestMapping(value = "/request_method_name", method = RequestMethod.GET)
    public String requestMethodName(@RequestParam String param) {
        return new String();
    }
}
