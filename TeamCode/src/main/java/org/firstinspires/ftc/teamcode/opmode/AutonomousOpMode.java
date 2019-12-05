package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Robot;

public class AutonomousOpMode extends LinearOpMode {


    private VuforiaLocalizer vuforia;

    public Robot robot = new Robot();

    //keeps track of how far the robot moves to align with the skystone
    public double visionDisplacement = 0;

    //CONSTANTS
    final double POSITION_THRESHOLD = 0.3; //inches
    final double TURN_THRESHOLD_DEGREES = 5; //degrees
    final double P_TURN = 0.018; //power per degree
    final double D_TURN = 0.02;

    final double P_DRIVE_HEADING = 0.010; //P coefficient for heading correction while driving

    public final double P_TURN_COEFF = 0.014; //for sample code
    final double HEADING_THRESHOLD = 1; //for sample code
    final double MIN_DRIVE_POWER = 0;

    final double VISION_POSITION_THRESHOLD = 0.5; //inch

    final double VISION_CENTER_X = 0;


    //will switch to this turn method once it is tested
    public void gyroTurnPD(double desiredAngle, AngleUnit angleUnit) {

        boolean angleReached = false;

        //switch to degrees if input is in radians
        if (angleUnit == AngleUnit.RADIANS) {
            desiredAngle = Math.toDegrees(desiredAngle);

        }

        double error;
        double turnPower;
        double lastTurnPower = 0;
        while (opModeIsActive() && !angleReached) {


            error = desiredAngle - robot.gyroscope.getHeading(AngleUnit.DEGREES);
            while(error > 180) error -= 360;
            while(error <= -180) error += 360;

            if(Math.abs(error) <= TURN_THRESHOLD_DEGREES){
                angleReached = true;
            }

            /*P and D correction
            we are using the previous power as a low effort approximation of speed here, as with the
            RUN_USING_ENCODER mode for the drive motors speed and power should be roughly proportional
             */
            turnPower = P_TURN * error - lastTurnPower * D_TURN;

            robot.drivetrain.setPower(0, 0, turnPower);

            lastTurnPower = turnPower;

            telemetry.addData("Error: ", error);
            telemetry.addData("Heading: ", robot.gyroscope.getHeading(AngleUnit.DEGREES));
            telemetry.update();

        }

        robot.drivetrain.setPower(0, 0, 0);
    }


    public void gyroTurn (  double speed, double angle, AngleUnit angleUnit, double maxTimeS) {
        ElapsedTime timer = new ElapsedTime();
        if(angleUnit == AngleUnit.RADIANS){
            angle = Math.toDegrees(angle);
        }
        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF) && timer.seconds() < maxTimeS) {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
        }
        sleep(500);

    }

    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double rotationSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            rotationSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rotationSpeed = speed * steer;

            //give the motor power a minimum to prevent stalling
            if(Math.abs(rotationSpeed) < MIN_DRIVE_POWER){
                rotationSpeed = MIN_DRIVE_POWER;
            }
        }

        robot.drivetrain.setPower(0, 0, rotationSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", rotationSpeed);

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - robot.gyroscope.getHeading(AngleUnit.DEGREES);
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  +ve = steer left
     * @param error   Error angle in robot relative degrees
     * @param PCoeff  Proportional Gain Coefficient
     * @return
     */
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void driveX(double xInches, double power){
        robot.drivetrain.setOrigin();

        int distance = (int)(xInches * Math.sqrt(2) * robot.drivetrain.COUNTS_PER_INCH);

        robot.drivetrain.frontLeft.setTargetPosition(robot.drivetrain.frontLeft.getCurrentPosition() + distance);
        robot.drivetrain.frontRight.setTargetPosition(robot.drivetrain.frontRight.getCurrentPosition() - distance);
        robot.drivetrain.backLeft.setTargetPosition(robot.drivetrain.backLeft.getCurrentPosition() - distance);
        robot.drivetrain.backRight.setTargetPosition(robot.drivetrain.backRight.getCurrentPosition() + distance);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.drivetrain.setPower(power, 0, 0);

        while(opModeIsActive() && robot.drivetrain.isBusy()){
            telemetry.addData("X Target: ", xInches);
            telemetry.addData("X Position: ", robot.drivetrain.getXInches());
            telemetry.update();
        }

        robot.drivetrain.setPower(0, 0, 0);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void driveXGyro(double xInches, double power){
        robot.drivetrain.setOrigin();

        double targetHeading = robot.gyroscope.getHeading(AngleUnit.DEGREES);

        double error = targetHeading - robot.gyroscope.getHeading(AngleUnit.DEGREES);

        double rotationPower = P_DRIVE_HEADING * error;

        int distance = (int)(xInches * Math.sqrt(2) * robot.drivetrain.COUNTS_PER_INCH);

        robot.drivetrain.frontLeft.setTargetPosition(robot.drivetrain.frontLeft.getCurrentPosition() + distance);
        robot.drivetrain.frontRight.setTargetPosition(robot.drivetrain.frontRight.getCurrentPosition() - distance);
        robot.drivetrain.backLeft.setTargetPosition(robot.drivetrain.backLeft.getCurrentPosition() - distance);
        robot.drivetrain.backRight.setTargetPosition(robot.drivetrain.backRight.getCurrentPosition() + distance);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.drivetrain.setPower(power, 0, rotationPower);

        while(opModeIsActive() && robot.drivetrain.isBusy()){

            error = targetHeading - robot.gyroscope.getHeading(AngleUnit.DEGREES);

            rotationPower = P_DRIVE_HEADING * error;

            robot.drivetrain.setPower(power, 0, rotationPower);

            telemetry.addData("X Target: ", xInches);
            telemetry.addData("X Position: ", robot.drivetrain.getYInches());
            telemetry.update();
        }

        robot.drivetrain.setPower(0, 0, 0);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void driveY(double yInches, double power){
        robot.drivetrain.setOrigin();

        int distance = (int)(yInches * Math.sqrt(2) * robot.drivetrain.COUNTS_PER_INCH);

        robot.drivetrain.frontLeft.setTargetPosition(robot.drivetrain.frontLeft.getCurrentPosition() + distance);
        robot.drivetrain.frontRight.setTargetPosition(robot.drivetrain.frontRight.getCurrentPosition() + distance);
        robot.drivetrain.backLeft.setTargetPosition(robot.drivetrain.backLeft.getCurrentPosition() + distance);
        robot.drivetrain.backRight.setTargetPosition(robot.drivetrain.backRight.getCurrentPosition() + distance);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.drivetrain.setPower(0, power, 0);

        while(opModeIsActive() && robot.drivetrain.isBusy()){
            telemetry.addData("Y Target: ", yInches);
            telemetry.addData("Y Position: ", robot.drivetrain.getYInches());
            telemetry.update();
        }

        robot.drivetrain.setPower(0, 0, 0);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void driveYGyro(double yInches, double power){
        robot.drivetrain.setOrigin();

        double targetHeading = robot.gyroscope.getHeading(AngleUnit.DEGREES);

        double error = targetHeading - robot.gyroscope.getHeading(AngleUnit.DEGREES);

        double rotationPower = P_DRIVE_HEADING * error;

        int distance = (int)(yInches * Math.sqrt(2) * robot.drivetrain.COUNTS_PER_INCH);

        robot.drivetrain.frontLeft.setTargetPosition(robot.drivetrain.frontLeft.getCurrentPosition() + distance);
        robot.drivetrain.frontRight.setTargetPosition(robot.drivetrain.frontRight.getCurrentPosition() + distance);
        robot.drivetrain.backLeft.setTargetPosition(robot.drivetrain.backLeft.getCurrentPosition() + distance);
        robot.drivetrain.backRight.setTargetPosition(robot.drivetrain.backRight.getCurrentPosition() + distance);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.drivetrain.setPower(0, power, rotationPower);

        while(opModeIsActive() && robot.drivetrain.isBusy()){

            error = targetHeading - robot.gyroscope.getHeading(AngleUnit.DEGREES);

            rotationPower = P_DRIVE_HEADING * error;

            robot.drivetrain.setPower(0, power, rotationPower);

            telemetry.addData("Y Target: ", yInches);
            telemetry.addData("Y Position: ", robot.drivetrain.getYInches());
            telemetry.update();
        }

        robot.drivetrain.setPower(0, 0, 0);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    /** Drives left or right until centered with a stone. assumes that stone is in frame
     *
     */
    public void driveXToSkyStone(){

        robot.drivetrain.setOrigin();

        ElapsedTime scanTime = new ElapsedTime();
        while(scanTime.seconds() < 3 && !robot.scanIt.targetVisible) {
            robot.scanIt.scanitonce();
        }


        if(robot.scanIt.targetVisible) {

            while (opModeIsActive() && Math.abs(robot.scanIt.getX() + 1) >= VISION_POSITION_THRESHOLD) {

                robot.scanIt.scanitonce();


                if (robot.scanIt.getX() < 0) {
                    robot.drivetrain.setPower(0.05, 0, 0);
                } else {
                    robot.drivetrain.setPower(-0.05, 0, 0);
                }

                telemetry.addData("Camera X:", robot.scanIt.getX());
                telemetry.update();
            }
            robot.drivetrain.setPower(0, 0, 0);

            robot.scanIt.deactivate();

            visionDisplacement += robot.drivetrain.getXInches();
        }
    }

    public StonePattern detectSkyStonePosition(TeamColor teamColor, double maxTimeS){
        ElapsedTime stoneTimer = new ElapsedTime();

        robot.scanIt.activate();

        while(opModeIsActive() && stoneTimer.seconds() < maxTimeS){
            robot.scanIt.scanitonce();
        }
         if(!robot.scanIt.targetVisible){
             return StonePattern.A;
         } else {
            if(teamColor == TeamColor.RED){
                if(robot.scanIt.getX() < VISION_CENTER_X){
                    return StonePattern.B;
                } else {
                    return StonePattern.C;
                }
            } else {
                if(robot.scanIt.getX() > VISION_CENTER_X){
                    return StonePattern.B;
                } else {
                    return StonePattern.C;
                }
            }
         }
    }


    //never ran but is necessary to not have error messages
     public void runOpMode(){}

     public enum TeamColor {
        RED,
        BLUE
     }

     public enum ParkSide {
        WALL,
        BRIDGE
     }

     public enum StonePattern {
        A,
        B,
        C

     }
}
    


