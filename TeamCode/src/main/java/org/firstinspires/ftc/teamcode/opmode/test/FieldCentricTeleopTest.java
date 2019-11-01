package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.math.AngleHelper;

@TeleOp(name="Field Centric Teleop Test", group="test")
@Disabled
public class FieldCentricTeleopTest extends LinearOpMode {

    Robot robot = new Robot();

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        while(opModeIsActive()){

            double fieldCentricAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) + Math.PI/2;

            double robotCentricAngle = AngleHelper.fieldToRobotCentric(AngleUnit.RADIANS, fieldCentricAngle,
                    robot.gyroscope.getHeading(AngleUnit.RADIANS));

            double magnitude = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);

            double xPower = magnitude * Math.cos(robotCentricAngle);
            double yPower = magnitude * Math.sin(robotCentricAngle);

            //this value is negative because left has to be positive to conform to standard angle reference frame
            double rotationPower = -gamepad1.right_stick_x;
/*
            if(Math.abs(gamepad1.right_stick_x) > 0){
                rotationPower = -gamepad1.right_stick_x;
            } else {
                //limit rotation

            }
*/
            robot.drivetrain.setPower(xPower, yPower, rotationPower);


        }
    }
}
