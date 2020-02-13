package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.math.AngleHelper;

/**
 * A messy playground for trying out new ideas for driver controlled movement.
 */
@TeleOp (name="TeleopTest", group="test")
public class TeleopTest extends LinearOpMode {

    Robot robot = new Robot();

    double lastTime = 0;
    ElapsedTime loopTimer = new ElapsedTime();

    ElapsedTime intakeTImer = new ElapsedTime();
    public final double INTAKE_TIME = 0.5;

    boolean useMarkMethod = false;

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry, false);
        robot.drivetrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.drivetrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        waitForStart();

        loopTimer.reset();
        intakeTImer.reset();

        while(opModeIsActive()){

            double xPower = gamepad1.left_stick_x;

            //up on the joystick is negative so it must be inverted to map the way you'd expect
            double yPower = -gamepad1.left_stick_y;

            //right is positive on the gamepad, which is opposite what the power formula uses so
            //it must be flipped
            double rotationPower = -gamepad1.right_stick_x;

            double power = Math.hypot(xPower, yPower);

            double angle = Math.atan2(yPower, -xPower);

            robot.drivetrain.setPowerPolar(power, angle, rotationPower, AngleUnit.RADIANS);



            //intake
            /*
            if(intakeTImer.seconds() <= INTAKE_TIME){
                robot.stoneArm.setIntakePower(-0.5);
            } else{
                robot.stoneArm.setIntakePower(0);
            }
            */



                      //LOOP TIMER TEST
            //intended to see how long each teleop loop takes
            double currentTime = loopTimer.milliseconds();
            double loopTime = currentTime - lastTime;

            /*
            telemetry.addData("Loop time: ", loopTime);
            telemetry.addData("Loops per second:", 1000 / loopTime);
            telemetry.update();
            lastTime = currentTime;
            */
/*
            telemetry.addData("frontLeft:", robot.drivetrain.frontLeft.getCurrentPosition());
            telemetry.addData("frontRight:", robot.drivetrain.frontRight.getCurrentPosition());
            telemetry.addData("backLeft:", robot.drivetrain.backLeft.getCurrentPosition());
            telemetry.addData("backRight:", robot.drivetrain.backRight.getCurrentPosition());
            */

           // telemetry.addData("xInches: ", robot.drivetrain.getXInches());
            //telemetry.addData("yInches: ", robot.drivetrain.getYInches());
            //telemetry.addData("Heading: ", robot.gyroscope.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }

    }
}
