package org.firstinspires.ftc.teamcode.opmode.autonomous.foundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Red Foundation Side - Bridge", group="Red")
public class RedBridge extends FoundationSide {

    public void runOpMode(){
        runFoundationSide(TeamColor.RED, ParkSide.BRIDGE);
    }
}
