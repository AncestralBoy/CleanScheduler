package org.project.cleanscheduler.domain.dto.enums;


public enum RoomType {
    BAGNO1("Bagno 1", 1),
    BAGNO2("Bagno 2", 1),
    CORRIDOIO("Corridoio", 1),
    BALCONE("Balcone", 1),
    CUCINA("Cucina", 1);


    private final String displayName;
    private final int score;

    RoomType(String displayName, int score){
        this.displayName = displayName;
        this.score = score;
    }

    public String getDisplayName() { return displayName; }
    public int getScore() { return score; }
}
