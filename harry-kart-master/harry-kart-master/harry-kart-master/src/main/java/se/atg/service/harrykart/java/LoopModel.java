package se.atg.service.harrykart.java;

public class LoopModel {
    private int lane1;
    private int lane2;
    private int lane3;
    private int lane4;

    public LoopModel(int lane1, int lane2, int lane3, int lane4) {
        this.lane1 = lane1;
        this.lane2 = lane2;
        this.lane3 = lane3;
        this.lane4 = lane4;
    }

    public int getLane1() {
        return lane1;
    }

    public void setLane1(int lane1) {
        this.lane1 = lane1;
    }

    public int getLane2() {
        return lane2;
    }

    public void setLane2(int lane2) {
        this.lane2 = lane2;
    }

    public int getLane3() {
        return lane3;
    }

    public void setLane3(int lane3) {
        this.lane3 = lane3;
    }

    public int getLane4() {
        return lane4;
    }

    public void setLane4(int lane4) {
        this.lane4 = lane4;
    }
}
