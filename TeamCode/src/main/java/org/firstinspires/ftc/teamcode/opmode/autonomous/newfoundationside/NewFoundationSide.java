package org.firstinspires.ftc.teamcode.opmode.autonomous.newfoundationside;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

public class NewFoundationSide extends AutonomousOpMode {
    public void runNewFoundationSide(TeamColor teamColor, ParkSide parkSide){

        double colorModifier;

        //written from BLUE perspective
        if(teamColor == TeamColor.BLUE){
            colorModifier = 1;
        } else {
            colorModifier = -1;
        }

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        driveY(-2, 0.2);

        driveX(12 * colorModifier, 0.3);

        driveY(-15, 0.4);
        driveY(-7, 0.2);

        //grab foundation
        robot.foundationClaw.setClosed();
        sleep(1000);

        driveY(15, 0.3);

        gyroTurn(0.7, 20 * colorModifier, AngleUnit.DEGREES, 5);

        driveY(6, 0.3);

        gyroTurn(0.7, 90 * colorModifier, AngleUnit.DEGREES, 5);

        driveY(-15, 0.2);


        robot.foundationClaw.setOpen();
        sleep(1000);

        gyroTurn(0.7, 90 * colorModifier, AngleUnit.DEGREES, 5);

        if(parkSide == ParkSide.WALL){
            driveX(7.3 * colorModifier, 0.3);
        } else {
            driveX(-8.5 * colorModifier, 0.3);
        }

        driveY(30, 0.3);



    }

}

