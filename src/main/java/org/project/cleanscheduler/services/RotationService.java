package org.project.cleanscheduler.services;

import lombok.RequiredArgsConstructor;
import org.project.cleanscheduler.database.entity.Assignment;
import org.project.cleanscheduler.database.entity.Room;
import org.project.cleanscheduler.database.entity.Roommate;
import org.project.cleanscheduler.database.repository.RoomRepository;
import org.project.cleanscheduler.database.services.interface_.AssignmentService;
import org.project.cleanscheduler.domain.dto.enums.RoomType;
import org.project.cleanscheduler.utils.AssignmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RotationService {

    private final AssignmentService assignmentService;
    private final RoomRepository roomRepository;

    private static final int HOW_OFTEN_SHOULD_CLEAN_BALCONY = 2;

    public void generateWeeklyAssignments (List<Roommate> activeRoommates, LocalDate weekStart){
        List<Room> availableRooms = getAvailableRooms(weekStart);
        List<Assignment> assignmentsToAbandon = new ArrayList<>();

        List<Roommate> sortedRoommates = activeRoommates.stream()
                .sorted(Comparator.comparingLong(Roommate::getId))
                .collect(Collectors.toList());

        int numRooms = availableRooms.size();
        long weeksSinceEpoch = ChronoUnit.WEEKS.between(LocalDate.EPOCH, weekStart);
        int offset = (int) (weeksSinceEpoch % numRooms);

        for (int i = 0; i < sortedRoommates.size(); i++) {
            Roommate roommate = sortedRoommates.get(i);

            assignmentService.findByRoommateAndStatus(roommate, AssignmentStatus.PENDING)
                    .ifPresent(a -> {
                        a.setStatus(AssignmentStatus.ABANDONED);
                        assignmentsToAbandon.add(a);
                    });

            if (i >= numRooms) {
                continue;
            }
            Room assignedRoom = availableRooms.get((i + offset) % numRooms);

            Assignment assignment = new Assignment();
            assignment.setStatus(AssignmentStatus.PENDING);
            assignment.setWeekStart(weekStart);
            assignment.setRoom(assignedRoom);
            assignment.setRoommate(roommate);

            assignmentService.createAssignment(assignment);
        }

        if (!assignmentsToAbandon.isEmpty()) {
            assignmentService.updateAssignments(assignmentsToAbandon);
        }
    }

    private List<Room> getAvailableRooms(LocalDate weekStart) {
        Map<String, Room> roomMap = roomRepository.findAll().stream()
                .collect(Collectors.toMap(Room::getName, r -> r));

        List<Room> rooms = new ArrayList<>(Arrays.asList(
                RoomType.BAGNO1, RoomType.BAGNO2, RoomType.CORRIDOIO, RoomType.CUCINA
        )).stream()
                .map(type -> roomMap.get(type.getDisplayName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Room balcony = roomMap.get(RoomType.BALCONE.getDisplayName());
        if (balcony != null && shouldIncludeBalcony(weekStart, balcony)) {
            // Replace a standard room with the balcony, cycling through which one gets replaced
            long weeksSinceEpoch = ChronoUnit.WEEKS.between(LocalDate.EPOCH, weekStart);
            int replaceIdx = (int) ((weeksSinceEpoch / HOW_OFTEN_SHOULD_CLEAN_BALCONY) % rooms.size());
            rooms.set(replaceIdx, balcony);
        }

        return rooms;
    }

    private boolean shouldIncludeBalcony(LocalDate weekStart, Room balcony){
        Optional<Assignment> lastBalcony = assignmentService
                .findTopByRoomAndStatusOrderByWeekStartDesc(balcony, AssignmentStatus.COMPLETED);


        if (lastBalcony.isEmpty()) return true;

        long weeksBetween = ChronoUnit.WEEKS.between(
                lastBalcony.get().getWeekStart(), weekStart
        );
        return weeksBetween >= HOW_OFTEN_SHOULD_CLEAN_BALCONY;
    }

}
