package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystem.FoundationClaw;
import org.firstinspires.ftc.teamcode.subsystem.Gyroscope;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.Lift;
import org.firstinspires.ftc.teamcode.subsystem.MRRangeSensor;
import org.firstinspires.ftc.teamcode.subsystem.MecanumDrivetrain;
import org.firstinspires.ftc.teamcode.subsystem.ScanIt;
import org.firstinspires.ftc.teamcode.subsystem.StoneHandler;

//this is the main hardware class
public class Robot {
    //TODO create subsystems and add them here

    public MecanumDrivetrain drivetrain = new MecanumDrivetrain();
    public Gyroscope gyroscope = new Gyroscope();
    public FoundationClaw foundationClaw = new FoundationClaw();
    public ScanIt scanIt = new ScanIt();
    public MRRangeSensor rangeSensor = new MRRangeSensor();
    public Lift lift = new Lift();
    public Intake intake = new Intake();
    public StoneHandler stoneHandler = new StoneHandler();



    /**
     * Called when we want to initialize all subsystems on the robot. Otherwise it is best to
     * manually initialize the desired subsystems individually
     * @param hardwareMap
     * @param telemetry
     */
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){
        drivetrain.initialize(hardwareMap, telemetry);
        gyroscope.initialize(hardwareMap, telemetry);
        foundationClaw.initialize(hardwareMap, telemetry);
        //scanIt.initialize(hardwareMap,telemetry);
        rangeSensor.initialize(hardwareMap, telemetry);
        lift.initialize(hardwareMap, telemetry);
        intake.initialize(hardwareMap, telemetry);
        stoneHandler.initialize(hardwareMap, telemetry);
    }

}
