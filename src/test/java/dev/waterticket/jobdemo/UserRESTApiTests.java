package dev.waterticket.jobdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.dto.UserAddRequest;
import dev.waterticket.jobdemo.user.dto.UserUpdateNameRequest;
import dev.waterticket.jobdemo.user.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Autowired
    private ObjectMapper objectMapper;

    public UserRESTApiTests(UserService userService) {
        this.userService = userService;
    }

    private final String id = "user_rest_api_tests";
    private final String password = "user_rest_api_tests";
    private final String invalid_id = "user_rest_api_tests_invalid";

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

    @Test
    public void insertAccount_assertCreated() throws Exception {
        UserAddRequest userAddRequest = UserAddRequest.builder()
                .id("user_rest_api_tests_insert")
                .name("user_rest_api_tests_insert")
                .auth("USER")
                .build();
        String content = objectMapper.writeValueAsString(userAddRequest);

        mockMvc.perform(post("/api/user")
                        .header("Authorization", "Basic " + id + ":" + password)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        this.userService.delete("user_rest_api_tests_insert");
    }

    @Test
    public void insertAccountDuplicate_assertConflict() throws Exception {
        UserAddRequest userAddRequest = UserAddRequest.builder()
                .id(id)
                .name("user_rest_api_tests_2")
                .auth("USER")
                .build();
        String content = objectMapper.writeValueAsString(userAddRequest);

        mockMvc.perform(post("/api/user")
                        .header("Authorization", "Basic " + id + ":" + password)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void updateAccountName_assertOk() throws Exception {
        UserUpdateNameRequest userUpdateNameRequest = UserUpdateNameRequest.builder()
                .id(id)
                .name(id + "_updated")
                .build();
        String content = objectMapper.writeValueAsString(userUpdateNameRequest);

        mockMvc.perform(put("/api/user")
                        .header("Authorization", "Basic " + id + ":" + password)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateAccountNameInvalidUser_assertNotFound() throws Exception {
        UserUpdateNameRequest userUpdateNameRequest = UserUpdateNameRequest.builder()
                .id(invalid_id)
                .name(invalid_id + "_updated")
                .build();
        String content = objectMapper.writeValueAsString(userUpdateNameRequest);

        mockMvc.perform(put("/api/user")
                        .header("Authorization", "Basic " + id + ":" + password)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @AfterAll
    public void deleteAdminAccount() {
        this.userService.delete(id);
    }
}
