package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp (name="test", group="test")
public class TestOpMode extends LinearOpMode {

    Robot robot;
    ElapsedTime timer = new ElapsedTime();

    public void runOpMode(){

        robot = new Robot(hardwareMap, telemetry);

        waitForStart();
        timer.reset();

        while(opModeIsActive() && timer.seconds() < 5){
            double forwardPower = 1;
            double strafePower = 0;

            robot.drivetrain.setPower(strafePower, forwardPower, 0);


            //use to empirically find formula for mecanum drive
            telemetry.addData("FL: ", robot.drivetrain.frontLeft.getCurrentPosition());
            telemetry.addData("FR: ", robot.drivetrain.frontRight.getCurrentPosition());
            telemetry.addData("BL: ", robot.drivetrain.backLeft.getCurrentPosition());
            telemetry.addData("BR: ", robot.drivetrain.backRight.getCurrentPosition());
            telemetry.update();
        }
    }
}
