package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystem.FoundationClaw;
import org.firstinspires.ftc.teamcode.subsystem.Gyroscope;
import org.firstinspires.ftc.teamcode.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.subsystem.StoneArm;
import org.firstinspires.ftc.teamcode.subsystem.StoneClaw;
import org.firstinspires.ftc.teamcode.subsystem.Subsystem;

//this is the main hardware class
public class Robot {
    //TODO create subsystems and add them here

    public MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    public Gyroscope gyroscope = new Gyroscope();
    public StoneClaw stoneClaw = new StoneClaw();
    public StoneArm stoneArm = new StoneArm();
    public FoundationClaw foundationClaw = new FoundationClaw();


    /**
     * Called when we want to initialize all subsystems on the robot. Otherwise it is best to
     * manually initialize the desired subsystems individually
     * @param hardwareMap
     * @param telemetry
     */
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){
        drivetrain.initialize(hardwareMap, telemetry);
        gyroscope.initialize(hardwareMap, telemetry);
        stoneClaw.initialize(hardwareMap, telemetry);
        stoneArm.initialize(hardwareMap, telemetry);
        foundationClaw.initialize(hardwareMap, telemetry);
    }

}
