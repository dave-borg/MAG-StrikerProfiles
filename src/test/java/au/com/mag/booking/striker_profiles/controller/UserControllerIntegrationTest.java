package au.com.mag.booking.striker_profiles.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import au.com.mag.booking.striker_profiles.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        userService.addUser("Ted Striker", "striker@example.com", 123, "123-456-7890");
        userService.addUser("Elaine Dickinson", "elaine@example.com", 456, "456-789-0123");
        userService.addUser("Dr. Rumack", "rumack@example.com", 789, "789-012-3456");
    }

    @AfterEach
    public void tearDown() {
        userService.deleteByEmail("striker@example.com");
        userService.deleteByEmail("elaine@example.com");
        userService.deleteByEmail("rumack@example.com");
    }

    @Test
    void testAddAndGetUserByEmail() throws Exception {
        String email = "ted@striker.com";
        String name = "Ted Striker";
        int arn = 12345;
        String phoneNumber = "123-456-7890";

        // Add a user
        mockMvc.perform(MockMvcRequestBuilders.post("/striker_profile/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"arn\":" + arn + ",\"phoneNumber\":\"" + phoneNumber + "\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // Get the user by email
        mockMvc.perform(MockMvcRequestBuilders.get("/striker_profile/user/findByEmail")
                .param("email", email)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        String email = "striker@example.com";

        mockMvc.perform(MockMvcRequestBuilders.get("/striker_profile/user/findByEmail")
                .param("email", email)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void testUpdateUser() throws Exception {
        String existingEmail = "striker@example.com";
        String newEmail = "tedstriker@example.com";
        String newName = "Ted Stryker";
        int newArn = 124;
        String newPhoneNumber = "124-567-8901";

        mockMvc.perform(MockMvcRequestBuilders.put("/striker_profile/user/update")
                .param("existingEmail", existingEmail)
                .param("newEmail", newEmail)
                .param("newName", newName)
                .param("newArn", String.valueOf(newArn))
                .param("newPhoneNumber", newPhoneNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User with email " + newEmail + " updated successfully"));
    }
}