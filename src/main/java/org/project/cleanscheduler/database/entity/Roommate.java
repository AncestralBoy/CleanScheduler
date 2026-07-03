package org.project.cleanscheduler.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roommate", schema = "clean_scheduler_schema")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Roommate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "surname", nullable = false, length = 20)
    private String surname;

    @Column(name = "nickname", length = 20, unique = true)
    private String nickname;

    @Column(name = "clean_points")
    private int cleanPoints;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
