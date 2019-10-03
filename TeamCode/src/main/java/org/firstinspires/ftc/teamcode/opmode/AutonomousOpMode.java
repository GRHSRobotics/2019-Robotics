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

    public void basicDriveToPosition(double xInches, double yInches){

        double POSITION_THRESHOLD = 0.3; //inches

        robot.drivetrain.setOrigin();

        boolean targetReached = false;

        while(opModeIsActive() && !targetReached){

            //use funky formula to assign motor power
            //TODO look into PID or better formula for this
            double xPower = xInches / (Math.abs(xInches) + Math.abs(yInches));
            double yPower = yInches / (Math.abs(xInches) + Math.abs(yInches));

            //set motor powers
            robot.drivetrain.setPower(xPower, yPower, 0);

            boolean yReached = false;
            boolean xReached = false;

            //conditions for ending movement
            //TODO this is basic and doesn't account for overshoots and doesn't help if one direction is
            // fulfilled properly but not the other
            if(Math.abs(robot.drivetrain.getXInches() - xInches) > POSITION_THRESHOLD){
                xReached = true;

            }
            if(Math.abs(robot.drivetrain.getYInches() - yInches) > POSITION_THRESHOLD){
                yReached = true;

            }
            if(xReached && yReached){
                targetReached = true;
            }
        }
    }
    


    //never ran but is necessary to not have error messages
    public void runOpMode(){}
}
