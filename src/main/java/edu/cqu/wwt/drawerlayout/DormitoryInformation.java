package edu.cqu.wwt.drawerlayout;

/**
 * Created by 吴文韬 on 2017/3/22.
 */

public class DormitoryInformation {

    private String roomName;
    private String roomCommander;
    private String roomUmpires;


    public DormitoryInformation(String dormitory,String roomCommander,String roomUmpires)
    {
        this.roomName=dormitory;
        this.roomCommander=roomCommander;
        this.roomUmpires=roomUmpires;
    }

    public String getDormitory() {
        return roomName;
    }

    public void setDormitory(String dormitory) {
        this.roomName = dormitory;
    }

    public String getRoomCommander() {
        return roomCommander;
    }

    public void setRoomCommander(String roomCommander) {
        this.roomCommander = roomCommander;
    }

    public void setRoomUmpires(String roomUmpires) {
        this.roomUmpires = roomUmpires;
    }

    public String getRoomUmpires() {
        return roomUmpires;
    }
}
