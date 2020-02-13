package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift implements Subsystem {

    public DcMotor lift;

    public final double TOP_LIMIT = 5000; //will determine this one empirically
    public final double BOTTOM_LIMIT = 0; //lift should start at bottom position
    public final double P_LIFT = 0.008; //determine this one empirically


    //list of positions where the stone will be hovering OVER the previous level
    //in TICKS
    public final int[] hoverPositions = {0, 300, 600, 900};

    //list of positions where the stone will be PLACED on the previous level
    //in TICKS
    public final int[] placedPositions = {100, 400, 700, 1000};


    public void initialize(HardwareMap hardwareMap, Telemetry telemetry, boolean moveServos){
        lift = hardwareMap.get(DcMotor.class, "lift");

        lift.setDirection(DcMotor.Direction.FORWARD);

        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPowerBasic(double power){
        lift.setPower(power);
    }

    public void setPower(double power){
        //make this have limits at top and bottom

        double error;

        if(power > 0){ //lift is going upward so we have to worry about the upper limit

            //top limit is a bigger number so it is positive to ensure error is positive
            error = TOP_LIMIT - lift.getCurrentPosition();

            power = power * Range.clip(P_LIFT * error, -1, 1);

        } else { //lift is going downward so we have to worry about the lower limit

            //current position is a bigger number so it is positive to ensure error is positive
            error = lift.getCurrentPosition() - BOTTOM_LIMIT;

            power = power * Range.clip(P_LIFT * error, -1, 1);

        }

        lift.setPower(power);

    }

    /**
     * Sets the height to a certain number of ticks. Meant to be run in a loop, as there is no loop in this method
     * @param ticks desired height of lift, in ticks
     */
    public void setHeight(int ticks){
        lift.setTargetPosition(ticks);

        setPower(1);

        //only set mode if we need to
        if(lift.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        if(!lift.isBusy()){
            lift.setPower(0);
        }
    }
}
