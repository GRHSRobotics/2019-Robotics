package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift implements Subsystem {

    public DcMotor right;
    public DcMotor left;


    public final double COUNTS_PER_ROTATION = 753.2;
    public final double SPOOL_RADIUS_INCHES = 0.8; //spool radius in inches
    public final double COUNTS_PER_INCH = COUNTS_PER_ROTATION / (2 * Math.PI * SPOOL_RADIUS_INCHES);


    public static double TOP_LIMIT_RIGHT = 36; //inches, will determine this one empirically
    public static double BOTTOM_LIMIT_RIGHT = 0;//lift should start at bottom position

    public static double TOP_LIMIT_LEFT = 36; //inches, will determine this one empirically
    public static double BOTTOM_LIMIT_LEFT = 0;

    public final double P_LIFT = 0.08; //determine this one empirically





    public void initialize(HardwareMap hardwareMap, Telemetry telemetry, boolean moveServos){
        right = hardwareMap.get(DcMotor.class, "rightLift");
        left = hardwareMap.get(DcMotor.class, "leftLift");

        right.setDirection(DcMotor.Direction.FORWARD);
        left.setDirection(DcMotor.Direction.REVERSE);

        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPowerBasic(double power){
        right.setPower(power);
        left.setPower(power);
    }

    public void setPower(double power){
        //make this have limits at top and bottom

        double rightError;
        double leftError;

        double appliedError;

        if(power > 0){ //lift is going upward so we have to worry about the upper limit

            //top limit is a bigger number so it is positive to ensure error is positive
            rightError = TOP_LIMIT_RIGHT - right.getCurrentPosition() / COUNTS_PER_INCH;
            leftError = TOP_LIMIT_LEFT - left.getCurrentPosition() / COUNTS_PER_INCH;


        } else { //lift is going downward so we have to worry about the lower limit

            //current position is a bigger number so it is positive to ensure error is positive
            rightError = right.getCurrentPosition() / COUNTS_PER_INCH - BOTTOM_LIMIT_RIGHT;
            leftError = left.getCurrentPosition() / COUNTS_PER_INCH - BOTTOM_LIMIT_LEFT;

        }

        //apply the smaller power to the motor to ensure that one side doesn't over-extend
        appliedError = Math.min(rightError, leftError);

        power = power * Range.clip(P_LIFT * appliedError, -1, 1);

        right.setPower(power);
        left.setPower(power);

    }

}
