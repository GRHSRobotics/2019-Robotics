package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrivetrain {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public double WHEEL_RADIUS; //get value once drivetrain is built
    public double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS;
    public double COUNTS_PER_ROTATION; //get value once drivetrain is built
    public double COUNTS_PER_INCH = COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;

    //used to "zero" the robot's position for ease of calculating displacement
    public double initialFLTicks = 0;
    public double initialFRTicks = 0;
    public double initialBLTicks = 0;
    public double initialBRTicks = 0;

    public MecanumDrivetrain(HardwareMap hardwareMap){

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        //reverse left side motors so that applying a positive power makes them all go forwards
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Assigns power to the motor (rectangular version)
     * @param xPower The desired side to side power
     * @param yPower The desired forward power
     * @param rotationPower The desired rotation power
     */
    public void setPower(double xPower, double yPower, double rotationPower){
        //TODO double check formula to do this
        //the signs on the xPower might be messed up

        double frontLeftPower = yPower - xPower - rotationPower;
        double frontRightPower = yPower + xPower + rotationPower;
        double backLeftPower = yPower + xPower - rotationPower;
        double backRightPower = yPower - xPower + rotationPower;

        double max = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

        //brings all powers to within the range [-1, 1] while keeping their scaling relative to each other
        if(max > 1){
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower /= max;
            backRightPower /= max;
        }

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

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


    public double getXInches(){
        //https://ftcforum.usfirst.org/forum/ftc-technology/50373-mecanum-encoder-algorithm
        return ((frontLeft.getCurrentPosition() - initialFLTicks + backRight.getCurrentPosition() - initialBRTicks) -
                (frontRight.getCurrentPosition() - initialFRTicks + backLeft.getCurrentPosition() - initialBLTicks))
                / (4 * COUNTS_PER_INCH);
    }

    public double getYInches(){
        return (frontLeft.getCurrentPosition() - initialFLTicks + frontRight.getCurrentPosition() - initialFRTicks
        + backLeft.getCurrentPosition() - initialBLTicks + backRight.getCurrentPosition() - initialBRTicks)
                / (4 * COUNTS_PER_INCH);
    }

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


}