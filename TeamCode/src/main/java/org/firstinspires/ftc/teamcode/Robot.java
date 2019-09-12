package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystem.MecanumDrivetrain;

//this is the main hardware class
public class Robot {
    //TODO create subsystems and add them here

    public MecanumDrivetrain drivetrain;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry){
        drivetrain = new MecanumDrivetrain(hardwareMap);
    }

}
