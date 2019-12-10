package org.firstinspires.ftc.teamcode.opmode.autonomous.foundationside;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

public class FoundationSide extends AutonomousOpMode {

    /** Sequence of translations, turns, etc for foundation side autonomous.
     *
     * @param teamColor the color that this autonomous is running on
     * @param parkSide the position that the robot is to park in
     */
    public void runFoundationSide(TeamColor teamColor, ParkSide parkSide){
        //this method is written from the perspective of the BLUE side, so RED version has
        // x translations, as well as rotations reversed.

        double colorModifier;
        if(teamColor == TeamColor.BLUE){
            colorModifier = 1;
        } else {
            colorModifier = -1; //in order to reverse certain things on the RED side
        }

        robot.initialize(hardwareMap, telemetry);

        waitForStart();

        //line robot up with foundation
        driveY(-3, 0.5);
        driveX(12 * colorModifier, 0.6);

        gyroTurn(0.8, 0, AngleUnit.RADIANS, 5);
        driveY(-13, 0.6);
        driveY(-9, 0.2);

        //grab foundation
        robot.foundationClaw.setClosed();
        sleep(1000);

        driveY(23, 0.5);
        driveY(20, 0.2);
        gyroTurn(1, 0 * colorModifier, AngleUnit.DEGREES, 5);

        robot.foundationClaw.setOpen();
        sleep(1000);
        driveY(-2, 0.3);
        gyroTurn(1, 0 * colorModifier, AngleUnit.DEGREES, 3);

        //make sure foundation is in position
        driveX(-25 * colorModifier, 0.6);
        driveY(-10, 0.5);
        driveX(20 * colorModifier, 0.7);

        //navigate
        if(parkSide == ParkSide.BRIDGE){

            driveY(-2, 0.5);
            driveX(-20 * colorModifier, 0.5);
        } else {
            driveY(20, 0.4);
            driveY(-3, 0.4);
            driveX(-20 * colorModifier, 0.5);
        }


    }

}
