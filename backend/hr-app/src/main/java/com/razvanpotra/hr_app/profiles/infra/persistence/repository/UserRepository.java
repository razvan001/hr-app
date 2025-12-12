package com.razvanpotra.hr_app.profiles.infra.persistence.repository;

import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
  UserEntity save(UserEntity userEntity);
  Optional<UserEntity> findByUserName(String userName);
  Optional<UserEntity> findById(String id);
}