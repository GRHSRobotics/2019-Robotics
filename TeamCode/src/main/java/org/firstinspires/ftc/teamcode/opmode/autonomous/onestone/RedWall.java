package org.firstinspires.ftc.teamcode.opmode.autonomous.onestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red One Stone - Wall", group="Red")
public class RedWall extends OneStone {

    public void runOpMode(){
        runOneStone(TeamColor.RED, ParkSide.WALL);

    }
}
