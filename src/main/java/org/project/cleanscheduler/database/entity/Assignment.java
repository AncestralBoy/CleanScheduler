package org.project.cleanscheduler.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.project.cleanscheduler.utils.AssignmentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "assignment", schema = "clean_scheduler_schema")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roommate_id")
    private Roommate roommate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "week_start")
    private LocalDate weekStart;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;
}
