package org.firstinspires.ftc.teamcode.opmode.autonomous.newfoundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red New Foundation: Bridge", group="Red")
public class RedBridge extends NewFoundationSide {
    public void runOpMode(){
        runNewFoundationSide(TeamColor.RED, ParkSide.BRIDGE);
    }
}
