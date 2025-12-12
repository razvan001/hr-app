package com.razvanpotra.hr_app.profiles.infra.persistence.repository;

import com.razvanpotra.hr_app.profiles.infra.persistence.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, String> {
}
