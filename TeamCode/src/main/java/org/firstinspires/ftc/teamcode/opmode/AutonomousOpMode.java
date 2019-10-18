package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

public class AutonomousOpMode extends LinearOpMode {

    public Robot robot = new Robot();

    //CONSTANTS
    double POSITION_THRESHOLD = 0.3; //inches
    double TURN_THRESHOLD_DEGREES = 5; //degrees
    double P_TURN = 0.2; //power per degree

    //TODO write all of these methods

    public void gyroTurn(double desiredAngle, AngleUnit angleUnit){

        double currentAngle = robot.gyroscope.getHeading(AngleUnit.DEGREES);

        //switch to degrees if input is in radians
        if(angleUnit == AngleUnit.RADIANS){
            desiredAngle = Math.toDegrees(desiredAngle);

        }

        //change angle interval to something more favorable if projected arc is more than 180 degrees
        //so that we can always be making the shorter turn
        if(Math.abs(desiredAngle - currentAngle) > 180){

            //only one of these should be below 0 in this case so its okay to run this loop on both
            while(desiredAngle < 0){
                desiredAngle += 360;
            }
            while(currentAngle < 0){
                currentAngle += 360;
            }
        }

        double error;
        double turnPower;
        while (Math.abs(desiredAngle - currentAngle) > TURN_THRESHOLD_DEGREES){

            currentAngle = robot.gyroscope.getHeading(AngleUnit.DEGREES);

            error = desiredAngle - currentAngle;


            //simple P correction
            turnPower = P_TURN * error;

            robot.drivetrain.setPower(0, 0, turnPower);


        }
    }

    public void basicDriveToPosition(double xInches, double yInches){

        robot.drivetrain.setOrigin();

        boolean targetReached = false;
        boolean yReached = false;
        boolean xReached = false;

        while(opModeIsActive() && !targetReached){

            //use funky formula to assign motor power
            //TODO look into PID or better formula for this (maybe based on error instead of total displacement?)
            double xPower = xInches / (Math.abs(xInches) + Math.abs(yInches));
            double yPower = yInches / (Math.abs(xInches) + Math.abs(yInches));

            //set motor powers
            robot.drivetrain.setPower(xPower, yPower, 0);

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
