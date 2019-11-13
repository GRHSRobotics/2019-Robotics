package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="RedOneStone", group="Red")
public class RedOneStone extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        driveY(-19, 0.3);
        driveX(20, 1);
        driveX(10, 0.3);
        driveX(-3, 0.3);

        driveY(-5, 0.3);

        //grab first stone
        robot.stoneClaw.setClosed();
        sleep(1000);
        //driveX(-1, 0.2);

        //move back from row
        driveY(9, 0.8);

        //turn and move towards bridge
        gyroTurn(0.8, -90, AngleUnit.DEGREES, 3);
        sleep(2000);
        driveY(-50, 0.6);

        //release stone
        robot.stoneClaw.setOpen();
        sleep(1000);

        driveY(10, 0.5);




    }



}
