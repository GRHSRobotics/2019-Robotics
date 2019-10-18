package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="BasicAutonTest", group="test")
public class BasicAutonTest extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        basicDriveToPosition(0, 10);

        gyroTurn(90, AngleUnit.DEGREES);

    }
}
