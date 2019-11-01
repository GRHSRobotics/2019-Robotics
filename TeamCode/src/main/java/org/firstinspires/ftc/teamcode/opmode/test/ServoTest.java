package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="ServoTest", group="test")
@Disabled
public class ServoTest extends LinearOpMode {

    Robot robot = new Robot();

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        while(opModeIsActive()) {
            //STONE CLAW
            if (gamepad1.dpad_right) {
                robot.stoneClaw.setClosed();
                telemetry.addData("StoneClaw:", "Closed");
            }
            if (gamepad1.dpad_left) {
                robot.stoneClaw.setOpen();
                telemetry.addData("StoneClaw:", "Open");
            }

            //FOUNDATION CLAW
            if (gamepad1.dpad_up) {
                robot.foundationClaw.setOpen();
                telemetry.addData("FoundationClaw:", "Open");
            }
            if (gamepad1.dpad_down) {
                robot.foundationClaw.setClosed();
                telemetry.addData("FoundationClaw:", "Closed");
            }
/*
        //INTAKE HINGE
        if(gamepad1.a){
            robot.stoneArm.setHingeDown();
            telemetry.addData("Intake Hinge:", "Down");
        }
        if(gamepad1.b){
            robot.stoneArm.setHingeUp();
            telemetry.addData("Intake Hinge:", "Up");
        }
*/

            telemetry.update();
        }
    }

}
