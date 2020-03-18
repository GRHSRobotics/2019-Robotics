package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.Lift;

@TeleOp(name="DriverControlled", group="Competition")
public class DriverControlled extends LinearOpMode {

    Robot robot = new Robot();

    boolean currentLBumper = false;
    boolean previousLBumper = false;
    boolean foundationClawOpen = true;

    boolean previousY = false;
    boolean intakeOn = false;

    boolean previousRStickY2 = false; //gamepad 2
    boolean previousLStickY2 = false;

    public enum LiftState {
        TANDEM, //Tomas controlling lift normally
        SEPARATED, //fine adjustments to correct the two sides not being equal in height
        ZEROING //automated process to reset the limits of both sidse of lift based on limit switch on bottom of lift

    }

    public LiftState liftState = LiftState.TANDEM; // default to Tomas controlling lift unless second driver or zeroing needed

    public double rightLiftStartPoint = 0; // initial point at beginning of lift height modification
    public double leftLiftStartPoint = 0;

    public double OPTIMAL_INTAKE_POWER = 0.5;

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



            double xPower = -gamepad1.left_stick_x;

            //up on the joystick is negative so it must be inverted to map the way you'd expect
            double yPower = gamepad1.left_stick_y;

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


            //LIFT
            switch(liftState) {

                case TANDEM:
                    if (gamepad1.left_trigger > 0) {
                        robot.lift.setPower(gamepad1.left_trigger);
                    } else if (gamepad1.right_trigger > 0) {
                        robot.lift.setPower(-gamepad1.right_trigger);
                    } else {
                        robot.lift.setPower(0);
                    }
                    break;

                case SEPARATED:

                    if(Math.abs(gamepad2.right_stick_y) > 0.05){
                        if(!previousRStickY2){
                            rightLiftStartPoint = robot.lift.right.getCurrentPosition() / robot.lift.COUNTS_PER_INCH;
                        }

                        robot.lift.right.setPower(gamepad2.right_stick_y);

                    } else if(!(Math.abs(gamepad2.right_stick_y) > 0.05) && previousRStickY2){
                        //driver 2 just stopped using the right trigger, so its time to wrap everything up here

                        double delta = robot.lift.right.getCurrentPosition() / robot.lift.COUNTS_PER_INCH - rightLiftStartPoint;

                        robot.lift.TOP_LIMIT_RIGHT += delta;
                        robot.lift.BOTTOM_LIMIT_RIGHT +=delta;

                    } else if(Math.abs(gamepad2.left_stick_y) > 0.05) {
                        if(!previousLStickY2){
                            leftLiftStartPoint = robot.lift.left.getCurrentPosition() / robot.lift.COUNTS_PER_INCH

                        }
                    }
            }



            previousRStickY2 = Math.abs(gamepad2.right_stick_y) > 0.05;
            previousLStickY2 = Math.abs(gamepad2.left_stick_y) > 0.05;

            //STONE HANDLER
            if(gamepad1.dpad_up){
                robot.stoneHandler.setExtended();
            }
            if(gamepad1.dpad_down){
                robot.stoneHandler.setRetracted();
            }

            if(gamepad1.a || gamepad2.a){
                robot.stoneHandler.setGrabberOpen();
            }
            if(gamepad1.b || gamepad2.b){
                robot.stoneHandler.setGrabberClosed();
            }


            //INTAKE
            if(gamepad1.y && !previousY){
                intakeOn = !intakeOn; //toggle intake state
            }
            previousY = gamepad1.y;

            boolean doFineIntakeControl = (Math.abs(gamepad2.right_trigger) > 0) || (Math.abs(gamepad2.left_trigger) > 0);

            if(intakeOn && !doFineIntakeControl){
                robot.intake.setPower(OPTIMAL_INTAKE_POWER);
            } else if (doFineIntakeControl) {

                if(gamepad2.left_trigger > 0) {
                    robot.intake.setPower(gamepad2.left_trigger);
                } else if(gamepad2.right_trigger > 0){
                    robot.intake.setPower(-gamepad2.right_trigger);
                }

            }
            else {
                robot.intake.setPower(0);
            }







            robot.update();
            telemetry.update();

        }


    }
}
