package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StoneHandler implements Subsystem {

    public Servo grabber;
    public Servo extender;

    //CONSTANTS
    public final double GRABBER_CLOSED = 0;
    public final double GRABBER_OPEN = 1;

    public final double EXTENDED = 1;
    public final double RETRACTED = 0;

    public void initialize(HardwareMap hardwareMap, Telemetry telemery, boolean moveServos){

        grabber = hardwareMap.get(Servo.class, "stoneGrabber");
        extender = hardwareMap.get(Servo.class, "stoneExtender");

    }

    public void setGrabberOpen(){
        grabber.setPosition(GRABBER_OPEN);
    }

    public void setGrabberClosed(){
        grabber.setPosition(GRABBER_CLOSED);
    }

    public void setExtended(){
        extender.setPosition(EXTENDED);
    }

    public void setRetracted(){
        extender.setPosition(RETRACTED);
    }
}
