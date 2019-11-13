package org.firstinspires.ftc.teamcode.opmode.autonomous.foundationside;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

@Autonomous(name="Blue Foundation Side - Wall", group="Blue")
public class BlueWall extends FoundationSide{

    public void runOpMode(){
        runFoundationSide(AutonomousOpMode.TeamColor.BLUE, AutonomousOpMode.ParkSide.WALL);
    }
}
