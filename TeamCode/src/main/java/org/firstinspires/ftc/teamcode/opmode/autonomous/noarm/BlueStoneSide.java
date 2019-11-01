package org.firstinspires.ftc.teamcode.opmode.autonomous.noarm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="BlueStoneSide", group="Blue")
public class BlueStoneSide extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        driveX(20, 1);

        driveY(-35, 1);
        driveY(-3, 0.2);
    }
}
