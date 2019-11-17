package org.firstinspires.ftc.teamcode.opmode.autonomous.visionstone;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

public class VisionStone extends AutonomousOpMode {

    public StonePattern detectedStonePattern;

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


        driveY(-8, 0.5);

        //gyroTurn(0.7, 5, AngleUnit.DEGREES, 3);
        sleep(2000);
        detectedStonePattern = detectSkyStonePosition(teamColor, 3);
        telemetry.addData("Stone Pattern: ", detectedStonePattern);
        telemetry.addData("Stone Position: ", robot.scanIt.getX());
        telemetry.update();
        sleep(3000000);



    }
}
