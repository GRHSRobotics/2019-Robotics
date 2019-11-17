package org.firstinspires.ftc.teamcode.opmode.autonomous.onestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue One Stone - Wall", group="Blue")
public class BlueWall extends OneStone {
    public void runOpMode(){
        runOneStone(TeamColor.BLUE, ParkSide.WALL);
    }
}
