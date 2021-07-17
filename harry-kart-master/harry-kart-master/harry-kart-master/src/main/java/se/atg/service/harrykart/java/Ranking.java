package se.atg.service.harrykart.java;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonRootName("ranking")

public class Ranking {
    private int position;
    private String horse;

    public int getPosition() {
        return position;
    }

    public Ranking(int position, String horse) {
        this.position = position;
        this.horse = horse;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getHorse() {
        return horse;
    }

    public void setHorse(String horse) {
        this.horse = horse;
    }
}
