package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="Drivetrain Test", group="test")
public class DrivetrainTest extends LinearOpMode {

    Robot robot = new Robot();

    public enum WheelTested {
        FRONTLEFT,
        FRONTRIGHT,
        BACKLEFT,
        BACKRIGHT
    }

    public WheelTested wheelTested = WheelTested.FRONTLEFT;

    public void runOpMode(){

        robot.drivetrain.initialize(hardwareMap, telemetry, false);

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.dpad_left){
                wheelTested = WheelTested.FRONTLEFT;

                robot.drivetrain.frontLeft.setPower(1);
                robot.drivetrain.frontRight.setPower(0);
                robot.drivetrain.backLeft.setPower(0);
                robot.drivetrain.backRight.setPower(0);

                telemetry.addData("Wheel Tested, ", wheelTested);
                telemetry.addData("Wheel Encoder: ", robot.drivetrain.frontLeft.getCurrentPosition());

            } else if(gamepad1.dpad_down){
                wheelTested = WheelTested.BACKLEFT;

                robot.drivetrain.frontLeft.setPower(0);
                robot.drivetrain.frontRight.setPower(0);
                robot.drivetrain.backLeft.setPower(1);
                robot.drivetrain.backRight.setPower(0);

                telemetry.addData("Wheel Tested, ", wheelTested);
                telemetry.addData("Wheel Encoder: ", robot.drivetrain.backLeft.getCurrentPosition());

            } else if(gamepad1.dpad_right) {
                wheelTested = WheelTested.BACKRIGHT;

                robot.drivetrain.frontLeft.setPower(0);
                robot.drivetrain.frontRight.setPower(0);
                robot.drivetrain.backLeft.setPower(0);
                robot.drivetrain.backRight.setPower(1);

                telemetry.addData("Wheel Tested, ", wheelTested);
                telemetry.addData("Wheel Encoder: ", robot.drivetrain.backRight.getCurrentPosition());

            } else if (gamepad1.dpad_up) {
                wheelTested = WheelTested.FRONTRIGHT;

                robot.drivetrain.frontLeft.setPower(0);
                robot.drivetrain.frontRight.setPower(1);
                robot.drivetrain.backLeft.setPower(0);
                robot.drivetrain.backRight.setPower(0);

                telemetry.addData("Wheel Tested, ", wheelTested);
                telemetry.addData("Wheel Encoder: ", robot.drivetrain.frontRight.getCurrentPosition());

            } else {
                robot.drivetrain.frontLeft.setPower(0);
                robot.drivetrain.frontRight.setPower(0);
                robot.drivetrain.backLeft.setPower(0);
                robot.drivetrain.backRight.setPower(0);

                telemetry.addData("Wheel Tested, ", "None");
            }

            telemetry.update();

        }



    }

}
