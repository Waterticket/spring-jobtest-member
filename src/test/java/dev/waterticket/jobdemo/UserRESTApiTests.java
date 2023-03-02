package dev.waterticket.jobdemo;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
public class UserRESTApiTests {
    private final UserService userService;
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    public UserRESTApiTests(UserService userService) {
        this.userService = userService;
    }

    private final String id = "user_rest_api_tests";
    private final String password = "user_rest_api_tests";

    @BeforeAll
    public void addAdminAccount() {
        User adminUser = User.builder()
                .id(id)
                .password(password)
                .name(id)
                .auth("SYSTEM_ADMIN")
                .build();
        this.userService.insert(adminUser);

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAccountWithNoAuth_assertUnAuthorized() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAccountWithAuth_assertOk() throws Exception {
        mockMvc.perform(get("/api/user")
                .header("Authorization", "Basic " + id + ":" + password))
                .andExpect(status().isOk());
    }

    @Test
    public void getAccountWithWrongID_assertUnAuthorized() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header("Authorization", "Basic " + id + "wrong" + ":" + password))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAccountWithWrongPassword_assertUnAuthorized() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header("Authorization", "Basic " + id + ":" + password + "wrong"))
                .andExpect(status().isUnauthorized());
    }

    @AfterAll
    public void deleteAdminAccount() {
        this.userService.delete(id);
    }
}
