package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="DriverControlled - Reverse", group="Competition")
public class DriverControlledRev extends LinearOpMode {

    Robot robot = new Robot();

    boolean currentLBumper = false;
    boolean previousLBumper = false;
    boolean foundationClawOpen = true;

    public void runOpMode(){

        //initialize subsystems
        robot.drivetrain.initialize(hardwareMap, telemetry, false);
        robot.foundationClaw.initialize(hardwareMap, telemetry, false);

        //settings
        robot.drivetrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.drivetrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.foundationClaw.setOpen();

        waitForStart();

        while(opModeIsActive()){

            robot.update();

            double xPower = gamepad1.left_stick_x;

            //up on the joystick is negative so it must be inverted to map the way you'd expect
            double yPower = -gamepad1.left_stick_y;

            //right is positive on the gamepad, which is opposite what the power formula uses so
            //it must be flipped
            double rotationPower = -gamepad1.right_stick_x;

            //take magnitude of vector, then square it for greater control at lower speeds
            double power = Math.pow(xPower, 2) + Math.pow(yPower, 2);

            double angle = Math.atan2(yPower, xPower);

            //Minecraft-style "sneak" feature
            if(gamepad1.right_bumper){
                power /= 4;
                rotationPower /= 4;
            }

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




            telemetry.update();

        }


    }
}
