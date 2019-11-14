package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="AutonTest", group="test")
public class AutonTest extends AutonomousOpMode {

    ElapsedTime timer = new ElapsedTime();

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);
        robot.scanIt.initialize(hardwareMap, telemetry);
        robot.scanIt.activate();
        telemetry.addData("ZeroPowerMode: ", robot.drivetrain.frontRight.getZeroPowerBehavior());
        telemetry.addData("Heading: ", robot.gyroscope.getHeading(AngleUnit.DEGREES));

        telemetry.update();
        waitForStart();

        timer.reset();

        driveXToSkyStone(-0.05);


        //driveX(10, 1);


    }
}
