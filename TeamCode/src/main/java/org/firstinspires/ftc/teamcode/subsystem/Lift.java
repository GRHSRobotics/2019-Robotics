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


    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){
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

    public void setHeight(int ticks){
        lift.setTargetPosition(ticks);

        //only set mode if we need to
        if(lift.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
}
