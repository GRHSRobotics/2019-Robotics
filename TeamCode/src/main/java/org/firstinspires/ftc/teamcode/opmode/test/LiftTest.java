package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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
        robot.lift.initialize(hardwareMap, telemetry, false);



        waitForStart();

        downTimer.reset();
        runTimer.reset();

        while(opModeIsActive()){


            if(gamepad1.left_trigger > 0){
                robot.lift.setPowerBasic(-1* gamepad1.left_trigger);
            } else if(gamepad1.right_trigger > 0){
                robot.lift.setPowerBasic(1 * gamepad1.right_trigger);
            } else if(Math.abs(gamepad1.left_stick_y) > 0.1){
                robot.lift.left.setPower(-gamepad1.left_stick_y);

            } else if(Math.abs(gamepad1.right_stick_y) > 0.1){
                robot.lift.right.setPower(-gamepad1.right_stick_y);
            } else {
                robot.lift.setPowerBasic(0);
            }

            telemetry.update();
        }
    }
}