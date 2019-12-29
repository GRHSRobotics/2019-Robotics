package org.firstinspires.ftc.teamcode.subsystem;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//2 motor Compliant wheel intake
public class Intake implements Subsystem {

    public DcMotor left;
    public DcMotor right;

    public final double STANDARD_POWER = 0.8;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){
        left = hardwareMap.get(DcMotor.class, "leftIntake");
        right = hardwareMap.get(DcMotor.class, "rightIntake");

        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.REVERSE);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPower(double power){
        left.setPower(power);
        right.setPower(power);
    }

    public void setPowerStandard(){
        setPower(STANDARD_POWER);
    }

}
