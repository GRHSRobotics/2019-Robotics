package org.firstinspires.ftc.teamcode.opmode.autonomous.newfoundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue New Foundation: Bridge", group="Blue")
public class BlueBridge extends NewFoundationSide {
    public void runOpMode(){
        runNewFoundationSide(TeamColor.BLUE, ParkSide.BRIDGE);
    }
}
