package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="AutonTest", group="test")
@Disabled
public class AutonTest extends AutonomousOpMode {

    ElapsedTime timer = new ElapsedTime();

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);
        telemetry.addData("ZeroPowerMode: ", robot.drivetrain.frontRight.getZeroPowerBehavior());
        telemetry.addData("Heading: ", robot.gyroscope.getHeading(AngleUnit.DEGREES));

        telemetry.update();
        waitForStart();

        timer.reset();

        gyroTurn(1, -45, AngleUnit.DEGREES, 10);

        while(opModeIsActive() && timer.seconds() < 30){

            robot.drivetrain.setPower(0, 0, 0);
            telemetry.addData("Heading: ", robot.gyroscope.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }


        //driveX(10, 1);


    }
}
