package org.firstinspires.ftc.teamcode.opmode.autonomous.onestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red One Stone - Bridge", group="Red")
public class RedBridge extends OneStone {

    public void runOpMode(){
        runOneStone(TeamColor.RED, ParkSide.BRIDGE);

    }
}
