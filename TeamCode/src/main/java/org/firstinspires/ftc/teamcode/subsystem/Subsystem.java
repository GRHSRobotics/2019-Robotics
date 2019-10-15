package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Contains the methods that all subsystem classes must implement
 */
public interface Subsystem {

    /**
     * all subsystems must be able to get and configure hardware when this is called.
     */
    void initialize(HardwareMap hardwareMap, Telemetry telemetry);
}
