package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StoneArm implements Subsystem {

    public DcMotor intake;
    public DcMotor linearLift;

    public Servo intakeBarHinge;
    public Servo blockHinge;

    public static final double barHinge_up = 0.5;
    public static final double barHinge_down = 0.18;

    public static final double blockHinge_up = 0;
    public static final double blockHinge_down = 0.65;

    public final double P_LIFT = 0.002;
    public final double BOTTOM_LIMIT = 300; //in encoder ticks
    public final double TOP_LIMIT = 15500;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){

        intake = hardwareMap.get(DcMotor.class, "intake");
        linearLift = hardwareMap.get(DcMotor.class, "linearLift");

        intakeBarHinge = hardwareMap.get(Servo.class, "intakeTopHinge");
        blockHinge = hardwareMap.get(Servo.class, "blockHinge");

        intake.setDirection(DcMotor.Direction.FORWARD);
        linearLift.setDirection(DcMotor.Direction.REVERSE);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    enum liftLevel {
        GROUND,
        EMPTY_FOUNDATION,
        ONE,
        TWO,
        THREE,
        FOUR
    }

    public void setHeight(liftLevel level){
        //use P or PD looping to get this real good
        //feedforward maybe??
    }

    public void setLiftPower(double power){
        //make this have limits at top and bottom

        double error;

        if(power > 0){ //lift is going upward so we have to worry about the upper limit

            //top limit is a bigger number so it is positive to ensure error is positive
            error = TOP_LIMIT - linearLift.getCurrentPosition();

            power = power * Range.clip(P_LIFT * error, -1, 1);

        } else { //lift is going downward so we have to worry about the lower limit

            //current position is a bigger number so it is positive to ensure error is positive
            error = linearLift.getCurrentPosition() - BOTTOM_LIMIT;

            power = power * Range.clip(P_LIFT * error, -1, 1);

        }

        linearLift.setPower(power);


    }

    public void basicLiftPower(double power){
        linearLift.setPower(power);
    }

    public void setIntakePower(double power){
        intake.setPower(power);
    }

    public void setBarHingeUp(){
        intakeBarHinge.setPosition(barHinge_up);
    }

    public void setBarHingeDown(){
        intakeBarHinge.setPosition(barHinge_down);
    }

    public void setBlockHingeUp(){
        blockHinge.setPosition(blockHinge_up);
    }

    public void setBlockHingeDown(){
        blockHinge.setPosition(blockHinge_down);
    }
}
