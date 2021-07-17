package se.atg.service.harrykart.java;

import org.jetbrains.annotations.NotNull;

public class ParticipantModel implements Comparable<ParticipantModel>{
    private Integer lane;
    private String name;
    private Integer baseSpeed;


    public ParticipantModel(Integer lane, String name, Integer baseSpeed) {
        this.lane = lane;
        this.name = name;
        this.baseSpeed = baseSpeed;
    }

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(Integer baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    @Override
    public int compareTo(@NotNull ParticipantModel o) {
        return this.getBaseSpeed().compareTo(o.getBaseSpeed());
    }
}
