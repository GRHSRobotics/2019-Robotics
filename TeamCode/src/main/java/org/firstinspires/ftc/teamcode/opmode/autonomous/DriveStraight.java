package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="Drive Straight 10in", group="")
public class DriveStraight extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        driveY(-10, 0.5);


    }
}
