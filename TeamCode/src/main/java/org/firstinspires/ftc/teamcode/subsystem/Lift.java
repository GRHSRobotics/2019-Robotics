package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift implements Subsystem {

    public DcMotor right;
    public DcMotor left;


    public final double COUNTS_PER_ROTATION = 753.2;
    public final double SPOOL_RADIUS_INCHES = 0.944; //spool radius in inches
    public final double COUNTS_PER_INCH = COUNTS_PER_ROTATION / (2 * Math.PI * SPOOL_RADIUS_INCHES);

    public final double TOP_LIMIT = 36; //inches, will determine this one empirically
    public final double BOTTOM_LIMIT = 0; //lift should start at bottom position
    public final double P_LIFT = 0.008; //determine this one empirically


    //list of positions where the stone will be hovering OVER the previous level
    //in INCHES
    public final int[] hoverPositions = {2, 10, 18, 26};

    //list of positions where the stone will be PLACED on the previous level
    //in INCHES
    public final int[] placedPositions = {1, 9, 17, 25};


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

        double error;

        if(power > 0){ //lift is going upward so we have to worry about the upper limit

            //top limit is a bigger number so it is positive to ensure error is positive
            error = TOP_LIMIT - right.getCurrentPosition();

            power = power * Range.clip(P_LIFT * error, -1, 1);

        } else { //lift is going downward so we have to worry about the lower limit

            //current position is a bigger number so it is positive to ensure error is positive
            error = right.getCurrentPosition() - BOTTOM_LIMIT;

            power = power * Range.clip(P_LIFT * error, -1, 1);

        }

        right.setPower(power);
        left.setPower(power);

    }

    /**
     * Sets the height to a certain number of ticks. Meant to be run in a loop, as there is no loop in this method
     * @param inches desired height of lift, in inches
     */
    public void setHeight(double inches){
        right.setTargetPosition((int)(inches * COUNTS_PER_INCH));
        left.setTargetPosition((int)(inches * COUNTS_PER_INCH));

        setPower(1);

        //only set mode if we need to
        if(right.getMode() != DcMotor.RunMode.RUN_TO_POSITION || left.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void setLevelHover(int levelHover){
        setHeight(hoverPositions[levelHover]);
    }

    public void setLevelPlaced(int levelPlaced){
        setHeight(placedPositions[levelPlaced]);
    }

    /**
     * Checks lift position so that we are only setting power when it still has a distance to travel
     */
    public void update(){
        if(!right.isBusy() || !left.isBusy()){
            right.setPower(0);
            left.setPower(0);
        }
    }
}
