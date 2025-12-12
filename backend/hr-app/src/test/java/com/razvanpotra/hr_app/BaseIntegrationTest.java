package com.razvanpotra.hr_app;

import com.razvanpotra.hr_app.initializers.PostgresInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {HrAppApplication.class}, initializers = {PostgresInitializer.class})
public abstract class BaseIntegrationTest {

}
