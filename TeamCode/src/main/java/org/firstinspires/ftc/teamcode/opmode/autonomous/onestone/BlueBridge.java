package org.firstinspires.ftc.teamcode.opmode.autonomous.onestone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue One Stone - Bridge", group="Blue")
public class BlueBridge extends OneStone {

    public void runOpMode(){
        runOneStone(TeamColor.BLUE, ParkSide.BRIDGE);
    }
}
