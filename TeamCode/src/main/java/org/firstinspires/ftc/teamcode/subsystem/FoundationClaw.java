package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class FoundationClaw implements Subsystem{


    //left and right are defined here as relative to the forward direction of the robot
    public Servo leftClaw;
    public Servo rightClaw;

    public static final double leftOpen = 1;
    public static final double leftClosed = 0;

    public static final double rightOpen = 0;
    public static final double rightClosed = 0.95;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){
        leftClaw = hardwareMap.get(Servo.class, "leftFoundationClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightFoundationClaw");

    }

    public void setClosed(){
        leftClaw.setPosition(leftClosed);
        rightClaw.setPosition(rightClosed);
    }

    public void setOpen(){
        leftClaw.setPosition(leftOpen);
        rightClaw.setPosition(rightOpen);
    }
}
