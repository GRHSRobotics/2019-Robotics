package org.firstinspires.ftc.teamcode.opmode.autonomous.visionstone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Vision Stone - Wall", group="Red")
public class RedWall extends VisionStone {

    public void runOpMode(){
        runVisionStone(TeamColor.RED, ParkSide.WALL);
    }
}
