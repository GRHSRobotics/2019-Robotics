package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="RedFoundation: with arm", group="Blue")
@Disabled
public class RedFoundation extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        //line robot up with
        driveX(-13, 1);

        driveY(-18, 1);
        driveY(-5, 0.2);

        //grab foundation
        robot.foundationClaw.setClosed();
        sleep(1000);

        driveY(27, 0.5);
        driveY(5, 0.2);

        robot.foundationClaw.setOpen();
        sleep(1000);

        driveX(38, 1);



    }
}
