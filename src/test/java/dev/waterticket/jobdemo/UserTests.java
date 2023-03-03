package dev.waterticket.jobdemo;

import dev.waterticket.jobdemo.user.domain.User;
import dev.waterticket.jobdemo.user.exception.SameIDExistsException;
import dev.waterticket.jobdemo.user.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
public class UserTests {
    private final UserService userService;

    public UserTests(UserService userService) {
        this.userService = userService;
    }

    private User user1;
    private User user2;

    @BeforeAll
    public void addUserAccount() {
        user1 = User.builder()
                .id("user1")
                .password("user1_pw")
                .name("user1")
                .auth("SYSTEM_ADMIN")
                .build();
        user1 = this.userService.insert(user1);

        user2 = User.builder()
                .id("user2")
                .password("user2_pw")
                .name("user2")
                .auth("USER")
                .build();
        user2 = this.userService.insert(user2);
    }

    @Test
    public void tryInsertSameId_assertException() {
        assertThrows(SameIDExistsException.class, () -> {
            this.userService.insert(user1);
        });
    }

    @Test
    public void UpdateUserName() {
        this.userService.updateName(user1.getId(), "user1_new");

        String newName = this.userService.getUserById(user1.getId()).getName();
        assertEquals("user1_new", newName);
    }

    // CleanUp Code
//    @AfterAll
//    public void deleteUserAccount() {
//        this.userService.delete(user1.getId());
//        this.userService.delete(user2.getId());
//    }
}
