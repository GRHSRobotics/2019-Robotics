package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="LiftTest", group="test")
public class LiftTest extends LinearOpMode {
    Robot robot = new Robot();

    public void runOpMode(){
        robot.stoneArm.initialize(hardwareMap, telemetry);
        robot.drivetrain.initialize(hardwareMap, telemetry);

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.dpad_up){
                robot.stoneArm.setIntakePower(-0.5);
            } else if(gamepad1.dpad_down){
                robot.stoneArm.setIntakePower(0.5);
            } else {
                robot.stoneArm.setIntakePower(0);
            }

            if(gamepad1.x){
                robot.stoneArm.setBarHingeDown();
                telemetry.addData("Bar Hings: ", "down");
            }
            if(gamepad1.y){
                robot.stoneArm.setBarHingeUp();
                telemetry.addData("Bar Hinge: ", "up");
            }

            if(gamepad1.a){
                robot.stoneArm.setBlockHingeUp();
                telemetry.addData("Block Hinge: ", "up");
            }
            if(gamepad1.b){
                robot.stoneArm.setBlockHingeDown();
                telemetry.addData("Block Hinge: ", "down");
            }

            //linear lift
            if(gamepad1.right_trigger > 0){
                robot.stoneArm.basicLiftPower(gamepad1.right_trigger);
            } else if(gamepad1.left_trigger > 0){
                robot.stoneArm.basicLiftPower(-gamepad1.left_trigger);
            } else {
                robot.stoneArm.basicLiftPower(0);
            }

            robot.drivetrain.setPower(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

            telemetry.addData("Lift Position: ", robot.stoneArm.linearLift.getCurrentPosition());

            telemetry.update();
        }
    }
}
