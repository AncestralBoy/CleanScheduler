package org.project.cleanscheduler.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room", schema = "clean_scheduler_schema")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "is_assigned_this_week")
    private boolean isAssignedThisWeek;

    @Column(name = "score")
    private int score;
}
