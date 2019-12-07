package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="DriverControlled", group="Competition")
public class DriverControlled extends LinearOpMode {

    Robot robot = new Robot();

    boolean currentLBumper = false;
    boolean previousLBumper = false;
    boolean foundationClawOpen = false;

    public void runOpMode(){

        //initialize subsystems
        robot.drivetrain.initialize(hardwareMap, telemetry);
        robot.stoneArm.initialize(hardwareMap, telemetry);
        robot.foundationClaw.initialize(hardwareMap, telemetry);
        robot.stoneClaw.initialize(hardwareMap, telemetry);

        //settings
        robot.drivetrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.drivetrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.foundationClaw.setOpen();

        waitForStart();

        while(opModeIsActive()){

            double xPower = gamepad1.left_stick_x;

            //up on the joystick is negative so it must be inverted to map the way you'd expect
            double yPower = -gamepad1.left_stick_y;

            //right is positive on the gamepad, which is opposite what the power formula uses so
            //it must be flipped
            double rotationPower = -gamepad1.right_stick_x;

            //take magnitude of vector, then square it for greater control at lower speeds
            double power = Math.pow(xPower, 2) + Math.pow(yPower, 2);

            double angle = Math.atan2(yPower, -xPower);

            robot.drivetrain.setPowerPolar(power, angle, rotationPower, AngleUnit.RADIANS);

            //FOUNDATION CLAW
            currentLBumper = gamepad1.left_bumper;

            if(currentLBumper && currentLBumper != previousLBumper){ //checks that bumper has changed state

                //toggles foundation position
                foundationClawOpen = !foundationClawOpen;

                if(foundationClawOpen){
                    robot.foundationClaw.setOpen();
                } else {
                    robot.foundationClaw.setClosed();
                }
            }
            //store this loop's bumper state to compare to next time
            previousLBumper = currentLBumper;


            //STONE CLAW
            if (gamepad1.dpad_right) {
                robot.stoneClaw.setClosed();
                telemetry.addData("StoneClaw:", "Closed");
            }
            if (gamepad1.dpad_left) {
                robot.stoneClaw.setOpen();
                telemetry.addData("StoneClaw:", "Open");
            }


            //INTAKE WHEELS
            if(gamepad1.dpad_up){
                robot.stoneArm.setIntakePower(-0.5);
            } else if(gamepad1.dpad_down){
                robot.stoneArm.setIntakePower(0.5);
            } else {
                robot.stoneArm.setIntakePower(0);
            }


            //linear lift
            if(gamepad1.right_trigger > 0){
                robot.stoneArm.setLiftPower(gamepad1.right_trigger);
            } else if(gamepad1.left_trigger > 0){
                robot.stoneArm.setLiftPower(-gamepad1.left_trigger);
            } else {
                robot.stoneArm.setLiftPower(0);
            }

            //BAR HINGE SERVO
            if(gamepad1.a){
                robot.stoneArm.setBarHingeDown();
                telemetry.addData("Bar Hinge: ", "down");
            }
            if(gamepad1.b){
                robot.stoneArm.setBarHingeUp();
                telemetry.addData("Bar Hinge: ", "up");
            }


            //BLOCK HINGE SERVO
            if(gamepad1.x){
                robot.stoneArm.setBlockHingeDown();
                telemetry.addData("Block Hinge: ", "down");
            }
            if(gamepad1.y){
                robot.stoneArm.setBlockHingeUp();
                telemetry.addData("Block Hinge: ", "up");
            }

            telemetry.update();

        }


    }
}
