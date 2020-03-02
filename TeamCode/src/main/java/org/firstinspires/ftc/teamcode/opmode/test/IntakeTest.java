package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="IntakeTest", group="test")
public class IntakeTest extends LinearOpMode {

    Robot robot = new Robot();

    public void runOpMode(){
        robot.intake.initialize(hardwareMap, telemetry, false);

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.right_trigger > 0){
                robot.intake.setPower(gamepad1.right_trigger);
                telemetry.addData("Intake Power", gamepad1.right_trigger);
            } else if(gamepad1.left_trigger > 0){
                robot.intake.setPower(-gamepad1.left_trigger);
                telemetry.addData("Intake Power", -gamepad1.left_trigger);
            } else {
                robot.intake.setPower(0);
            }

            telemetry.update();

        }

    }
}
