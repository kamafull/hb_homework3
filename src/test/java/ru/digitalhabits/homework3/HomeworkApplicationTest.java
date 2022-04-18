package ru.digitalhabits.homework3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
class HomeworkApplicationTest {

    @Autowired
    private HomeworkApplication application;

    @Test
    void smokeTest() {
        assertThat(application).isNotNull();
    }
}
