package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="Servo Testing", group="test")
public class ServoTesting extends LinearOpMode {

    Robot robot = new Robot();

    public void runOpMode(){
        robot.foundationClaw.initialize(hardwareMap, telemetry, false);



        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                robot.foundationClaw.setOpen();
                telemetry.addData("Foundation Claws: ", "open");
            }

            if(gamepad1.b){
                robot.foundationClaw.setClosed();
                telemetry.addData("Foundation Claws: ", "closed");
            }

            telemetry.update();


        }


    }
}
