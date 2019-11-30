package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="LiftTest", group="test")
public class LiftTest extends LinearOpMode {
    Robot robot = new Robot();

    ElapsedTime downTimer = new ElapsedTime();
    ElapsedTime runTimer = new ElapsedTime();

    boolean intakeChanged = false;
    boolean intakeUp = false;

    public void runOpMode(){
        robot.stoneArm.initialize(hardwareMap, telemetry);
        robot.drivetrain.initialize(hardwareMap, telemetry);


        robot.stoneArm.setBlockHingeDown();


        waitForStart();

        downTimer.reset();
        runTimer.reset();

        while(opModeIsActive()){

            if(gamepad1.dpad_down){
                downTimer.reset();
            }
            if(downTimer.seconds() < 0.25 && runTimer.seconds() > 0.26){
                robot.stoneArm.setIntakePower(0.2);
            } else {
                if(gamepad1.dpad_up && !intakeChanged) {
                    robot.stoneArm.intake.setPower(intakeUp ? -0.3 : 0); //this is a shorthand if/else statement, (condition ? if true : if false)
                    intakeUp = !intakeUp;
                    intakeChanged = true;
                } else if(!gamepad1.dpad_up) {
                    intakeChanged = false;
                }
            }


            if(gamepad1.x){
                robot.stoneArm.setBarHingeDown();
                telemetry.addData("Bar Hings: ", "down");
            }
            if(gamepad1.y){
                robot.stoneArm.setBarHingeUp();
                telemetry.addData("Bar Hinge: ", "up");
            }

            //linear lift
            if(gamepad1.right_trigger > 0){
                robot.stoneArm.basicLiftPower(gamepad1.right_trigger);
            } else if(gamepad1.left_trigger > 0){
                robot.stoneArm.basicLiftPower(-gamepad1.left_trigger);
            } else {
                robot.stoneArm.basicLiftPower(0);
            }

            robot.drivetrain.setPower(gamepad1.left_stick_x, -gamepad1.left_stick_y, -gamepad1.right_stick_x);

            telemetry.addData("Lift Position: ", robot.stoneArm.linearLift.getCurrentPosition());

            telemetry.update();
        }
    }
}
