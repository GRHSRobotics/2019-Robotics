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

        robot.initialize(hardwareMap, telemetry);
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

            double power = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2 ) + Math.pow(gamepad1.left_stick_y, 2));

            double angle = AngleHelper.fieldToRobotCentric(AngleUnit.RADIANS, Math.atan2(yPower, xPower), robot.gyroscope.getHeading(AngleUnit.RADIANS));
            telemetry.addData("robot centric angle", angle);
            //double angle = Math.atan2(yPower, xPower) + Math.PI/2;
            //field centric version
            //robot.drivetrain.setPowerPolar(power, angle, rotationPower, AngleUnit.RADIANS);

            //robot.drivetrain.setPower(xPower, yPower, rotationPower);

            if(gamepad1.a) useMarkMethod = true;
            if(gamepad1.b) useMarkMethod = false;

            if(useMarkMethod) {
                // Mark Vadeika's Technique
                robot.drivetrain.frontLeft.setPower(Range.clip(-gamepad1.left_stick_y - gamepad1.left_stick_x, -1, 1));
                robot.drivetrain.frontRight.setPower(Range.clip(-gamepad1.right_stick_y + gamepad1.right_stick_x, -1, 1));
                robot.drivetrain.backLeft.setPower(Range.clip(-gamepad1.left_stick_y + gamepad1.left_stick_x, -1, 1));
                robot.drivetrain.backRight.setPower(Range.clip(-gamepad1.right_stick_y - gamepad1.right_stick_x, -1, 1));

                telemetry.addData("Drive Scheme: ", "Marquus");
            } else {
                robot.drivetrain.setPower(xPower, yPower, rotationPower);

                telemetry.addData("Drive Scheme: ", "Colin");
            }

            //linear lift
            if(gamepad1.right_trigger > 0){
                robot.stoneArm.basicLiftPower(gamepad1.right_trigger);
            }
            if(gamepad1.left_trigger > 0){
                robot.stoneArm.basicLiftPower(-gamepad1.left_trigger);
            } else {
                robot.stoneArm.basicLiftPower(0);
            }

            //intake
            /*
            if(gamepad1.dpad_up){
                robot.stoneArm.setIntakePower(0.25);
            }
            if(gamepad1.dpad_down){
                robot.stoneArm.setIntakePower(-0.25);
            }
            if(gamepad1.dpad_right){
                robot.stoneArm.setIntakePower(0);
            }
            */
            if(intakeTImer.seconds() <= INTAKE_TIME){
                robot.stoneArm.setIntakePower(-0.5);
            } else{
                robot.stoneArm.setIntakePower(0);
            }
            if(gamepad1.dpad_up){
                intakeTImer.reset();
            }

            //FOUNDATION CLAW
            if (gamepad1.x) {
                robot.foundationClaw.setOpen();
                telemetry.addData("FoundationClaw:", "Open");
            }
            if (gamepad1.y) {
                robot.foundationClaw.setClosed();
                telemetry.addData("FoundationClaw:", "Closed");
            }

            //STONE CLAW
            if (gamepad1.dpad_right) {
                robot.stoneClaw.setClosed();
                telemetry.addData("StoneClaw:", "Closed");
            }
            if (gamepad1.dpad_left) {
                robot.stoneClaw.setOpen();
                telemetry.addData("StoneClaw:", "Open");
            }

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

            telemetry.addData("frontLeft:", robot.drivetrain.frontLeft.getCurrentPosition());
            telemetry.addData("frontRight:", robot.drivetrain.frontRight.getCurrentPosition());
            telemetry.addData("backLeft:", robot.drivetrain.backLeft.getCurrentPosition());
            telemetry.addData("backRight:", robot.drivetrain.backRight.getCurrentPosition());
            telemetry.update();
        }

    }
}