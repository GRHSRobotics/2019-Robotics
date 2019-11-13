package org.firstinspires.ftc.teamcode.opmode.autonomous.foundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Foundation Side - Wall", group="Red")
public class RedWall extends FoundationSide {

    public void runOpMode(){
        runFoundationSide(TeamColor.RED, ParkSide.WALL);
    }
}
