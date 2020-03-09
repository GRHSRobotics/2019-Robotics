package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystem.StoneHandler;

@TeleOp(name="Stone Handler Test", group="test")
public class StoneHandlerTest extends LinearOpMode {

    public Robot robot = new Robot();

    public void runOpMode(){

        robot.stoneHandler.initialize(hardwareMap, telemetry, false);

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.dpad_up){
                robot.stoneHandler.setExtended();

            } else if(gamepad1.dpad_down){
                robot.stoneHandler.setRetracted();

            } else {
                robot.stoneHandler.extensionState = StoneHandler.ExtensionState.STOPPED;

            }

            if(gamepad1.a){
                robot.stoneHandler.setGrabberClosed();
            } else if(gamepad1.b){
                robot.stoneHandler.setGrabberOpen();
            }

            robot.stoneHandler.update();
        }



    }

}
