package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class MRRangeSensor implements Subsystem {

    ModernRoboticsI2cRangeSensor rangeSensor;

    public void initialize(HardwareMap hardwareMap, Telemetry telemetry, boolean moveServos){

        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "rangeSensor");
    }

    public double getDistance(DistanceUnit distanceUnit){
        return rangeSensor.getDistance(distanceUnit);
    }
}
