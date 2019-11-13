package org.firstinspires.ftc.teamcode.opmode.autonomous.foundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Blue Foundation Side - Bridge", group="Blue")
public class BlueBridge extends FoundationSide {

    public void runOpMode(){
        runFoundationSide(TeamColor.BLUE, ParkSide.BRIDGE);
    }
}
