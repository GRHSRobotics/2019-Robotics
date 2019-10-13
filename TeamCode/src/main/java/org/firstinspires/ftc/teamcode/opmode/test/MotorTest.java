package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp (name="motorTest", group="test")
public class MotorTest extends LinearOpMode {

    Robot robot;
    ElapsedTime timer = new ElapsedTime();

    public void runOpMode(){

        robot = new Robot(hardwareMap, telemetry);

        waitForStart();
        timer.reset();

        while(opModeIsActive()){
            double forwardPower = 0.25;
            double strafePower = 0;

            robot.drivetrain.setPower(1, 0, 0);

            //robot.drivetrain.setPower(strafePower, forwardPower, 0);


            //use to empirically find formula for mecanum drive
            telemetry.addData("FL: ", robot.drivetrain.frontLeft.getCurrentPosition());
            telemetry.addData("FR: ", robot.drivetrain.frontRight.getCurrentPosition());
            telemetry.addData("BL: ", robot.drivetrain.backLeft.getCurrentPosition());
            telemetry.addData("BR: ", robot.drivetrain.backRight.getCurrentPosition());
            telemetry.update();
        }
    }
}
