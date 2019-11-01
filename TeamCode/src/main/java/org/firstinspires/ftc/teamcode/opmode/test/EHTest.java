package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="EHTest", group="Test")
@Disabled
public class EHTest extends LinearOpMode {

    Robot robot = new Robot();

    ElapsedTime intakeTImer = new ElapsedTime();
    public final double INTAKE_TIME = 0.5;

    public void runOpMode(){

        robot.stoneArm.initialize(hardwareMap, telemetry);

        waitForStart();

        while(opModeIsActive()){

            //linear lift
            if(gamepad1.right_trigger > 0){
                robot.stoneArm.basicLiftPower(gamepad1.right_trigger);
            }
            if(gamepad1.left_trigger > 0){
                robot.stoneArm.basicLiftPower(-gamepad1.left_trigger);
            } else {
                robot.stoneArm.basicLiftPower(0);
            }

            if(intakeTImer.seconds() <= INTAKE_TIME){
                robot.stoneArm.setIntakePower(-0.5);
            } else{
                robot.stoneArm.setIntakePower(0);
            }
            if(gamepad1.dpad_up){
                intakeTImer.reset();
            }


        }


    }
}
