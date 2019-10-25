package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StoneClaw implements Subsystem{

    public Servo claw;

    public double open = 0;
    public double closed = 0.50;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){

        claw = hardwareMap.get(Servo.class, "stoneClaw");


    }

    public void setOpen(){
        claw.setPosition(open);
    }

    public void setClosed(){
        claw.setPosition(closed);
    }


}
