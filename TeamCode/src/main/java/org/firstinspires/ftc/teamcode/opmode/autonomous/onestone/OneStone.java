package org.firstinspires.ftc.teamcode.opmode.autonomous.onestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

public class OneStone extends AutonomousOpMode {


    public void runOneStone(TeamColor teamColor, ParkSide parkSide){
        //written from RED perspective

        robot.initialize(hardwareMap, telemetry, true);

        waitForStart();

        double colorModifier;
        if(teamColor == TeamColor.BLUE){
            colorModifier = -1;
        } else {
            colorModifier = 1; //in order to reverse certain things on the RED side
        }

        driveY(-10, 0.3);
        driveX(20 * colorModifier, 1);
        driveX(10 * colorModifier, 0.3);
        driveX(-3 * colorModifier, 0.3);

        driveY(-12, 0.3);

        //grab first stone
        robot.stoneClaw.setClosed();
        sleep(1000);
        //driveX(-1, 0.2);

        if(parkSide == ParkSide.BRIDGE){
            //move back from row
            driveY(7, 0.8);
        } else {
            //drive back to wall
            driveY(18, 0.6);
            driveY(15, 0.3);
            driveY(-3, 0.3);
        }

        //turn and move towards bridge
        gyroTurn(0.8, -85 * colorModifier, AngleUnit.DEGREES, 3);
        sleep(2000);
        driveY(-50, 0.6);

        //release stone
        robot.stoneClaw.setOpen();
        sleep(1000);

        driveY(10, 0.5);

    }
}


