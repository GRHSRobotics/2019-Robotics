package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

public class AutonomousOpMode extends LinearOpMode {

    Robot robot;


    public void initialize(){
        robot = new Robot(hardwareMap, telemetry);
    }

    //TODO write all of these methods

    public void gyroTurn(double angle, AngleUnit angleUnit){

    }

    public void driveToPosition(double xInches, double yInches){
        robot.drivetrain.setOrigin();

        boolean targetReached = false;

        while(opModeIsActive() && !targetReached){

            robot.drivetrain.setPower();
        }
    }
    


    //never ran but is necessary to not have error messages
    public void runOpMode(){}
}
