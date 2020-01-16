package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Testing", group="test")
public class ServoTesting extends LinearOpMode {

    Servo servo;

    public void runOpMode(){
        servo = hardwareMap.get(Servo.class, "servo");

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                servo.setPosition(1);
            }
            if(gamepad1.b){
                servo.setPosition(0);
            }
        }


    }
}
