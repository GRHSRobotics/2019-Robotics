package org.firstinspires.ftc.teamcode.opmode.autonomous.visionstone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="Blue Vision Stone - Bridge", group="Blue")
@Disabled
public class BlueBridge extends VisionStone {

    public void runOpMode(){
        runVisionStone(TeamColor.BLUE, ParkSide.BRIDGE);
    }
}
