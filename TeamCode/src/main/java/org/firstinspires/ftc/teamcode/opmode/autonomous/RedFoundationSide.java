package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="RedFoundationSide", group="Red")
@Disabled
public class RedFoundationSide extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry, true);

        waitForStart();

        //line robot up with foundation
        driveY(-3, 0.5);
        driveX(-12, 0.6);

        driveY(-15, 0.6);
        driveY(-9, 0.2);

        //grab foundation
        robot.foundationClaw.setClosed();
        sleep(1000);

        driveY(25, 0.5);
        driveY(20, 0.2);
        gyroTurn(1, 0, AngleUnit.DEGREES, 5);

        robot.foundationClaw.setOpen();
        sleep(1000);
        driveY(-3, 0.3);
        gyroTurn(1, 0, AngleUnit.DEGREES, 3);

        //make sure foundation is in position
        driveX(25, 0.6);
        driveY(-10, 0.5);
        driveX(-20, 0.7);

        //navigate
        driveY(-5, 0.5);
        driveX(20, 0.5);



    }

}
