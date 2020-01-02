package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumDrivetrain implements Subsystem{

    //TODO look into reasons to use/not use DcMotorEx (setVelocity method, etc)

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public static final double WHEEL_RADIUS_INCHES = 50 / 25.4; //100mm mecanum wheels
    public static final double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS_INCHES;
    public static final double COUNTS_PER_ROTATION = 537.6  ; //gobilda 19.2:1 planetary motor
    public final double COUNTS_PER_INCH = COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;

    //used to "zero" the robot's position for ease of calculating displacement
    public double initialFLTicks = 0;
    public double initialFRTicks = 0;
    public double initialBLTicks = 0;
    public double initialBRTicks = 0;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        //reverse left side motors so that applying a positive power makes them all go forwards
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Assigns power to the motors (rectangular version)
     * @param xPower The desired side to side power (positive is rightward)
     * @param yPower The desired forward/backward power (positive is forward)
     * @param rotationPower The desired rotation power (positive is counterclockwise)
     */
    public void setPower(double xPower, double yPower, double rotationPower){

        //take magnitude of vector, then square it for greater control at lower speeds
        double power = Math.hypot(xPower, yPower);

        double angle = Math.atan2(yPower, -xPower);

        setPowerPolar(power, angle, rotationPower, AngleUnit.RADIANS);

    }

    /**
     * Sets the current robot position as the new origin for translation calculations
     */
    public void setOrigin(){
        initialFLTicks = frontLeft.getCurrentPosition();
        initialFRTicks = frontRight.getCurrentPosition();
        initialBLTicks = backLeft.getCurrentPosition();
        initialBRTicks = backRight.getCurrentPosition();
    }


    /**
     * Gets the x (left/right) translation of the robot since the last call of setOrigin().
     * This algorithm only works with no rotation.
     * @return The x (left/right) translation of the robot in inches. Positive is right and negative is left.
     */
    public double getXInches(){
        //https://ftcforum.usfirst.org/forum/ftc-technology/50373-mecanum-encoder-algorithm
        return ((frontLeft.getCurrentPosition() - initialFLTicks + backRight.getCurrentPosition() - initialBRTicks) -
                (frontRight.getCurrentPosition() - initialFRTicks + backLeft.getCurrentPosition() - initialBLTicks))
                / (4 * Math.sqrt(2) * COUNTS_PER_INCH);
        //TODO check empirically whether 4 or 1/sqrt(2) is the right coefficient to use here
    }

    /**
     * Gets the y (forward/backward) translation of the robot since the last call of setOrigin().
     * This algorithm only works with no rotation.
     * @return The y (forward/backward) translation of the robot in inches. Positive is forward and negative is backward.
     */
    public double getYInches(){
        return (frontLeft.getCurrentPosition() - initialFLTicks + frontRight.getCurrentPosition() - initialFRTicks
        + backLeft.getCurrentPosition() - initialBLTicks + backRight.getCurrentPosition() - initialBRTicks)
                / (4 * COUNTS_PER_INCH);
    }

    /**
     * Adds the x and y position vectors to get the total translation of the robot since the last call of setOrigin().
     * @return The magnitude of total translation of the robot in inches.
     */
    public double getTotalInches(){
        return Math.hypot(getXInches(), getYInches());
    }

    public void setMode(DcMotor.RunMode runMode){
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        frontLeft.setZeroPowerBehavior(zeroPowerBehavior);
        frontRight.setZeroPowerBehavior(zeroPowerBehavior);
        backLeft.setZeroPowerBehavior(zeroPowerBehavior);
        backRight.setZeroPowerBehavior(zeroPowerBehavior);
    }

    /**
     *
     * @param speed The desired speed of robot movement in the range [-1, 1]
     * @param direction The desired direction of robot travel in the range [0, 2pi] where 0 is RIGHT
     * @param rotationSpeed The desired rate of change of robot direction [-1, 1]
     * @param angleUnit The unit of the inputted angle, either in degrees or radians
     */
    public void setPowerPolar(double speed, double direction, double rotationSpeed, AngleUnit angleUnit){

        //clamping isnt really necessary but it's good for preventing worst case scenario errors maybe

        double angle;
        double clampedSpeed;
        double clampedRotationSpeed;

        //convert to radians if necessary
        if(angleUnit == AngleUnit.DEGREES){
            angle = Math.toRadians(direction);
        } else {
            angle = direction;
        }

        //clamp robot speed if necessary
        if(speed > 1){
            clampedSpeed = 1;
        } else if(speed < -1){
            clampedSpeed = -1;
        } else {
            clampedSpeed = speed;
        }

        //clamp direction change speed if necessary
        if(rotationSpeed > 1){
            clampedRotationSpeed = 1;
        } else if(rotationSpeed < -1){
            clampedRotationSpeed = -1;
        } else{
            clampedRotationSpeed = rotationSpeed;
        }

        //calculate raw motor powers, see team resources for math explanation
        double rawPowerFL = clampedSpeed * Math.sin(angle + Math.PI/4) - clampedRotationSpeed;
        double rawPowerFR = clampedSpeed * Math.cos(angle + Math.PI/4) + clampedRotationSpeed;
        double rawPowerBL = clampedSpeed * Math.cos(angle + Math.PI/4) - clampedRotationSpeed;
        double rawPowerBR = clampedSpeed * Math.sin(angle + Math.PI/4) + clampedRotationSpeed;

        double powerFL = Range.clip(rawPowerFL, -1, 1);
        double powerFR = Range.clip(rawPowerFR, -1, 1);
        double powerBL = Range.clip(rawPowerBL, -1, 1);
        double powerBR = Range.clip(rawPowerBR, -1, 1);



        //set final motor powers
        frontLeft.setPower(powerFL);
        frontRight.setPower(powerFR);
        backLeft.setPower(powerBL);
        backRight.setPower(powerBR);

    }

    public boolean isBusy(){
        if(frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()){
            return true;
        } else{
            return false;
        }
    }


}
