package org.firstinspires.ftc.teamcode.opmode.autonomous.noarm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;


/**
 * Starting point: front of robot touching wall, left number plate in line with first tile border
 * to the left of skybridge
 */
@Autonomous(name="BlueFoundationSide", group="Blue")
public class BlueFoundationSide extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        //line robot up with
        driveX(12, 0.6);

        driveY(-18, 0.6);
        driveY(-5, 0.3);

        //grab foundation
        robot.foundationClaw.setClosed();
        sleep(1000);

        driveY(35, 0.5);
        driveY(5, 0.2);

        robot.foundationClaw.setOpen();
        sleep(1000);

        driveX(-35, 0.6);



    }
}
