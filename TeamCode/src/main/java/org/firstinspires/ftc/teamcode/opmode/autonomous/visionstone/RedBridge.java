package org.firstinspires.ftc.teamcode.opmode.autonomous.visionstone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Vision Stone - Bridge", group="Red")
public class RedBridge extends VisionStone {

    public void runOpMode(){
        runVisionStone(TeamColor.RED, ParkSide.BRIDGE);
    }
}
