package org.firstinspires.ftc.teamcode.opmode.TwoStoneAndFoundation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.subsystem.FoundationClaw;
import org.firstinspires.ftc.teamcode.subsystem.rrdrive.MecanumDriveREVOptimized;

public class TwoStoneAndFoundation extends AutonomousOpMode {


    public void runTwoStoneAndFoundation(TeamColor teamColor, StonePattern stonePattern) {

        double colorModifier;
        //written from the BLUE perspective, so blue stays unmodified
        if(teamColor == TeamColor.BLUE){
            colorModifier = 1;
        } else{
            colorModifier = -1;
        }


        MecanumDriveREVOptimized drive = new MecanumDriveREVOptimized(hardwareMap);

        FoundationClaw foundationClaw = new FoundationClaw();
        foundationClaw.initialize(hardwareMap, telemetry);

        drive.setPoseEstimate(new Pose2d(-36.0, 63.0 * colorModifier, Math.toRadians(-90.0) * colorModifier));

        Trajectory toFirstStone;

        Trajectory firstStonetoFoundation = drive.trajectoryBuilder()
                .setReversed(true)
                .splineTo(new Pose2d(-10.0, 36.0 * colorModifier, Math.toRadians(-180.0) * colorModifier))
                .lineTo(new Vector2d(10.0, 36.0 * colorModifier))
                .splineTo(new Pose2d(50.0, 30.0 * colorModifier, Math.toRadians(90.0) * colorModifier))
                .build();

        Trajectory moveFoundation = drive.trajectoryBuilder()
                .setReversed(false)
                .splineTo(new Pose2d(30.0, 60.0 * colorModifier, Math.toRadians(180.0) * colorModifier))
                .build();

        Trajectory pushFoundation = drive.trajectoryBuilder()
                        .setReversed(true)
                        .lineTo(new Vector2d(42.0, 60.0 * colorModifier))
                        .build();

        Trajectory foundationToSecondStone;

        Trajectory secondStoneToFoundation = drive.trajectoryBuilder()
                .setReversed(true)
                .splineTo(new Pose2d(0.0, 36.0 * colorModifier, Math.toRadians(180.0) * colorModifier))
                .splineTo(new Pose2d(42.0, 60.0 * colorModifier, Math.toRadians(180.0) * colorModifier))
                .build();

        Trajectory foundationToPark = drive.trajectoryBuilder()
                .setReversed(false)
                .splineTo(new Pose2d(0.0, 36.0 * colorModifier, Math.toRadians(180.0) * colorModifier))
                .build();


        //INSERT VISION STUFF HERE

        //define trajectory for stone-specific movements
        switch(stonePattern){
            case A:
                toFirstStone = drive.trajectoryBuilder()
                        .splineTo(new Pose2d(-24.0, 24.0 * colorModifier, Math.toRadians(-120.0) * colorModifier))
                        .build();

                foundationToSecondStone = drive.trajectoryBuilder()
                        .setReversed(false)
                        .splineTo(new Pose2d(0.0, 36.0 * colorModifier, Math.toRadians(180.0) * colorModifier))
                        .splineTo(new Pose2d(-48.0, 26.0 * colorModifier, Math.toRadians(240.0) * colorModifier))
                        .build();
                break;

            case B:

                toFirstStone = drive.trajectoryBuilder()
                        .splineTo(new Pose2d(-32.0, 24.0 * colorModifier, Math.toRadians(190.0) * colorModifier))
                        .build();

                foundationToSecondStone = drive.trajectoryBuilder()
                        .setReversed(false)
                        .splineTo(new Pose2d(0.0, 36.0 * colorModifier, Math.toRadians(180.0) * colorModifier))
                        .splineTo(new Pose2d(-56.0, 26.0 * colorModifier, Math.toRadians(190.0) * colorModifier))
                        .build();

                break;

                //use case C as the default case if vision messes up
            default:
                toFirstStone = drive.trajectoryBuilder()
                        .splineTo(new Pose2d(-40.0, 24.0 * colorModifier, Math.toRadians(190.0) * colorModifier))
                        .build();

                foundationToSecondStone = drive.trajectoryBuilder()
                        .setReversed(false)
                        .splineTo(new Pose2d(0.0, 36.0 * colorModifier, Math.toRadians(180.0) * colorModifier))
                        .splineTo(new Pose2d(-62.0, 26.0 * colorModifier, Math.toRadians(190.0) * colorModifier))
                        .build();

                break;
        }

        waitForStart();

        drive.followTrajectorySync(toFirstStone);

        drive.followTrajectorySync(firstStonetoFoundation);

        drive.followTrajectorySync(moveFoundation);

        drive.followTrajectorySync(pushFoundation);

        drive.followTrajectorySync(foundationToSecondStone);

        drive.followTrajectorySync(secondStoneToFoundation);

        drive.followTrajectorySync(foundationToPark);

    }

    @Override
    public void runOpMode(){}

}
