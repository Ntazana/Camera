package com.develogical.camera;

public class Camera implements WriteCompleteListener {
    boolean power = false;
    boolean iswriting = false;
    boolean shouldpowerdownafterwrite = false;
    Sensor sensor;
    MemoryCard memcard;

    public Camera(Sensor sensor) {
        this.sensor = sensor;
        //this.memcard = memcard;
    }

    public Camera(Sensor sensor, MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memcard = memoryCard;
    }

    public void pressShutter() {
        if ( !power ) return;
        iswriting = true;
        memcard.write( sensor.readData(), this);
    }

    public void powerOn() {
        sensor.powerUp();
        power = true;
    }

    public void powerOff() {
        if (iswriting)
        {
            shouldpowerdownafterwrite = true;
            return;
        }
        sensor.powerDown();
        power = false;
    }

    public void writeComplete()
    {
        iswriting = false;
        if ( shouldpowerdownafterwrite )
        {
            shouldpowerdownafterwrite = false;
            powerOff();
        }
    }
}

