package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="Servo Testing", group="test")
public class ServoTesting extends LinearOpMode {

    Servo servo;

    public void runOpMode(){

        servo = hardwareMap.get(Servo.class, "servo");

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                servo.setPosition(1);
                telemetry.addData("Position: ", "1");
            }

            if(gamepad1.b){
                servo.setPosition(0);
                telemetry.addData("Position: ", "0");
            }

            telemetry.update();


        }


    }
}
