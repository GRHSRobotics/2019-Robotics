package org.firstinspires.ftc.teamcode.opmode.autonomous.visionstone;

import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

public class VisionStone extends AutonomousOpMode {

    public void runVisionStone(TeamColor teamColor, ParkSide parkSide){
        //written from the perspective of BLUE

        robot.initialize(hardwareMap, telemetry);
        robot.scanIt.initialize(hardwareMap, telemetry);
        waitForStart();

        double colorModifier;
        if(teamColor == TeamColor.BLUE){
            colorModifier = 1;
        } else {
            colorModifier = -1;
        }




    }
}
