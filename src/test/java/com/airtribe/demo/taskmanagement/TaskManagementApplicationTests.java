package com.airtribe.demo.taskmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb_test",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class TaskManagementApplicationTests {

    @Test
    void contextLoads() {

    }
}