package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StoneArm implements Subsystem{

    DcMotor intake;
    DcMotor linearLift;

    Servo intakeTopHinge;
    public static final double hinge_up = 1;
    public static final double hinge_down = 0;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){

        intake = hardwareMap.get(DcMotor.class, "intake");
        linearLift = hardwareMap.get(DcMotor.class, "linearLift");

        //intakeTopHinge = hardwareMap.get(Servo.class, "intakeTopHinge");

        intake.setDirection(DcMotor.Direction.FORWARD);
        linearLift.setDirection(DcMotor.Direction.FORWARD);

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
    }

    public void basicLiftPower(double power){
        linearLift.setPower(power);
    }

    public void setIntakePower(double power){
        intake.setPower(power);
    }

    public void setHingeUp(){
        intakeTopHinge.setPosition(hinge_up);
    }

    public void setHingeDown(){
        intakeTopHinge.setPosition(hinge_down);
    }
}
