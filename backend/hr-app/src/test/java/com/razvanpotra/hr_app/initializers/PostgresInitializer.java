package com.razvanpotra.hr_app.initializers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:latest")
          .withDatabaseName("hrdb")
          .withUsername("hruser")
          .withPassword("hrpass");

  static {
    postgres.start();
  }

  @Override
  public void initialize(ConfigurableApplicationContext context) {
    TestPropertyValues.of(
        "spring.datasource.url=" + postgres.getJdbcUrl(),
        "spring.datasource.username=" + postgres.getUsername(),
        "spring.datasource.password=" + postgres.getPassword(),
        "spring.datasource.driver-class-name=org.postgresql.Driver"
    ).applyTo(context.getEnvironment());
  }
}