package com.razvanpotra.hr_app.profiles.infra.persistence.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {

  @Id
  @Column(name = "id", nullable = false, length = 255)
  private String id;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "email", nullable = false, length = 255)
  private String email;

  @Column(name = "position", nullable = false, length = 255)
  private String position;

  @Column(name = "department", nullable = false, length = 255)
  private String department;

  @Column(name = "salary")
  private Double salary;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 50)
  private UserType type;

  @Column(name = "username", nullable = false, length = 255, unique = true)
  private String userName;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FeedbackEntity> feedbacks;
}