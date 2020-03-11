package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxModule;
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

import java.util.List;

//this is the main hardware class
public class Robot {

    List<LynxModule> hubs;

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
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry, boolean moveServos){

        hubs = hardwareMap.getAll(LynxModule.class);
        for(LynxModule hub : hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        drivetrain.initialize(hardwareMap, telemetry, moveServos);
        gyroscope.initialize(hardwareMap, telemetry, moveServos);
        foundationClaw.initialize(hardwareMap, telemetry, moveServos);
        //scanIt.initialize(hardwareMap,telemetry);
        rangeSensor.initialize(hardwareMap, telemetry, moveServos);
        lift.initialize(hardwareMap, telemetry, moveServos);
        intake.initialize(hardwareMap, telemetry, moveServos);
        stoneHandler.initialize(hardwareMap, telemetry, moveServos);
    }

    /**
     * Must be called with every loop or else robot state data will not be updated.
     */
    public void update(){

        stoneHandler.update();
        //lift.update();
    }

}
