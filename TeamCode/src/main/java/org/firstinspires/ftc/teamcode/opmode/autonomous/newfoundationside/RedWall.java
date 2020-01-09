package org.firstinspires.ftc.teamcode.opmode.autonomous.newfoundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red New Foundation: Wall", group="Red")
public class RedWall extends NewFoundationSide {
    public void runOpMode(){
        runNewFoundationSide(TeamColor.RED, ParkSide.WALL);
    }
}
