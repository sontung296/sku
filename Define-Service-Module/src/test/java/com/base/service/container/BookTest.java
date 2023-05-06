package com.base.service.container;

import com.base.service.BaseTest;
import com.base.service.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class BookTest extends BaseTest {
    @Autowired
    private BookRepository bookRepository;
    @Container
    private static final MySQLContainer<?> container = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("password")
            // Load script to Container
            .withInitScript("test_container_DB.sql")
            .withReuse(true);

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        Startables.deepStart( container).join();
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }
    @Test
    void testDB() {
        assertThat(bookRepository.findAll()).hasSize(8);
    }

}

