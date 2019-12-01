package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.autonomous.visionstone.VisionStone;

@Autonomous(name="Vision Init Test", group="test")
public class VisionInitTest extends VisionStone {

    public void runOpMode(){

        testVisionStone(TeamColor.BLUE, ParkSide.WALL);

    }
}
