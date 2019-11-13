package org.firstinspires.ftc.teamcode.opmode.autonomous.onestone;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

public class OneStone extends AutonomousOpMode {


    public void runOneStone(TeamColor teamColor, ParkSide parkSide){
        //written from RED perspective

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        double colorModifier;
        if(teamColor == TeamColor.BLUE){
            colorModifier = -1;
        } else {
            colorModifier = 1; //in order to reverse certain things on the RED side
        }

        driveY(-19, 0.3);
        driveX(20 * colorModifier, 1);
        driveX(10 * colorModifier, 0.3);
        driveX(-3 * colorModifier, 0.3);

        driveY(-5, 0.3);

        //grab first stone
        robot.stoneClaw.setClosed();
        sleep(1000);
        //driveX(-1, 0.2);

        //move back from row
        driveY(9, 0.8);

        //turn and move towards bridge
        gyroTurn(0.8, -90 * colorModifier, AngleUnit.DEGREES, 3);
        sleep(2000);
        driveY(-50, 0.6);

        //release stone
        robot.stoneClaw.setOpen();
        sleep(1000);

        driveY(10, 0.5);

    }
}
