package org.firstinspires.ftc.teamcode.opmode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="BlueStoneSide", group="Blue")
@Disabled
public class BlueStoneSide extends AutonomousOpMode {

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        driveX(-20, 1);
        driveX(-10, 0.3);
        driveX(1, 0.3);

        driveY(-19, 1);
        driveY(-5, 0.3);

        //grab first stone
        robot.stoneClaw.setClosed();
        sleep(1000);

        //move back from row
        driveY(7, 0.8);

        //turn and move towards bridge
        gyroTurn(0.8, 90, AngleUnit.DEGREES, 3);
        sleep(2000);
        driveY(-40, 0.6);

        //release stone
        robot.stoneClaw.setOpen();
        sleep(1000);

        //move to second stone
        driveY(27, 0.5);
        gyroTurn(0.7, 0, AngleUnit.DEGREES, 5);

        //nudge forward towards second stone and grab
        driveY(-5, 1);
        driveY(-5, 0.3);
        driveX(-1, 0.2);
        robot.stoneClaw.setClosed();
        sleep(1000);

        //move away from row with second stone
        driveY(8, 0.8);

        //move under bridge and release stone 2
        gyroTurn(0.7, 90, AngleUnit.DEGREES, 5);
        driveY(-30, 1);
        robot.stoneClaw.setOpen();
        sleep(1000);

        //drive towards last stone
        driveY(15, 0.7);
        gyroTurn(0.7, 0, AngleUnit.DEGREES, 3);
        driveY(-4, 1);
        driveY(-7.5, 0.3);
        driveX(-1, 0.2);

        //grab and bring third stone
        robot.stoneClaw.setClosed();
        sleep(1000);
        driveX(-0.5, 0.2);
        driveY(8, 1);
        gyroTurn(0.8, 90, AngleUnit.DEGREES, 3);
        driveY(-25, 0.6);

        //release last stone
        robot.stoneClaw.setOpen();
        sleep(1000);
        driveY(5, 0.3);



    }
}
