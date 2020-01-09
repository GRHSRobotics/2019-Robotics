package org.firstinspires.ftc.teamcode.opmode.autonomous.newfoundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue New Foundation: Wall", group="Blue")
public class BlueWall extends NewFoundationSide {
    public void runOpMode(){
        runNewFoundationSide(TeamColor.BLUE, ParkSide.WALL);
    }
}
