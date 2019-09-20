package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp (name="test", group="test")
public class TestOpMode extends LinearOpMode {


    public void runOpMode(){
        telemetry.addData("This works", "");
        telemetry.addData("this is fun", "");
        telemetry.update();

        waitForStart();
    }
}
