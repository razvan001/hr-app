package com.razvanpotra.hr_app.profiles.infra.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackEntity {

  @Id
  @Column(name = "id", nullable = false, length = 255)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_feedback_user"))
  private UserEntity user;

  @Column(name = "author", nullable = false, length = 255)
  private String author;

  @Column(name = "text", nullable = false, columnDefinition = "text")
  private String text;

  @Column(name = "date", nullable = false)
  private LocalDateTime date;
}
