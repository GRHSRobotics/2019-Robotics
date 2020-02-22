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

        MecanumDriveREVOptimized drive = new MecanumDriveREVOptimized(hardwareMap);

        FoundationClaw foundationClaw = new FoundationClaw();
        foundationClaw.initialize(hardwareMap, telemetry);

        drive.setPoseEstimate(new Pose2d(-36.0, 63.0, Math.toRadians(-90.0)));

        double colorModifier;

        //written from the BLUE perspective, so blue stays unmodified
        if(teamColor == TeamColor.BLUE){
            colorModifier = 1;
        } else{
            colorModifier = -1;
        }

        Trajectory toFirstStone;

        Trajectory firstStonetoFoundation = drive.trajectoryBuilder()
                .setReversed(true)
                .splineTo(new Pose2d(-10.0, 36.0, Math.toRadians(-180.0)))
                .lineTo(new Vector2d(10.0, 36.0))
                .splineTo(new Pose2d(50.0, 30.0, Math.toRadians(90.0)))
                .build();

        Trajectory moveFoundation = drive.trajectoryBuilder()
                .setReversed(false)
                .splineTo(new Pose2d(30.0, 60.0, Math.toRadians(180.0)))
                .build();

        Trajectory pushFoundation = drive.trajectoryBuilder()
                        .setReversed(true)
                        .lineTo(new Vector2d(42.0, 60.0))
                        .build();

        Trajectory foundationToSecondStone;

        Trajectory secondStoneToFoundation = drive.trajectoryBuilder()
                .setReversed(true)
                .splineTo(new Pose2d(0.0, 36.0, Math.toRadians(180.0)))
                .splineTo(new Pose2d(42.0, 60.0, Math.toRadians(180.0)))
                .build();

        Trajectory foundationToPark = drive.trajectoryBuilder()
                .setReversed(false)
                .splineTo(new Pose2d(0.0, 36.0, Math.toRadians(180.0)))
                .build();


        //INSERT VISION STUFF HERE

        //define trajectory to move to first stone
        switch(stonePattern){
            case A:
                toFirstStone = drive.trajectoryBuilder()
                        .splineTo(new Pose2d(-24.0, 24.0, Math.toRadians(-120.0)))
                        .build();

                foundationToSecondStone = drive.trajectoryBuilder()
                        .setReversed(false)
                        .splineTo(new Pose2d(0.0, 36.0, Math.toRadians(180.0)))
                        .splineTo(new Pose2d(-48.0, 26.0, Math.toRadians(240.0)))
                        .build();
                break;

            case B:

                toFirstStone = drive.trajectoryBuilder()
                        .splineTo(new Pose2d(-32.0, 24.0, Math.toRadians(190.0)))
                        .build();

                foundationToSecondStone = drive.trajectoryBuilder()
                        .setReversed(false)
                        .splineTo(new Pose2d(0.0, 36.0, Math.toRadians(180.0)))
                        .splineTo(new Pose2d(-56.0, 26.0, Math.toRadians(190.0)))
                        .build();

                break;

            case C:

                break;

            default:
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
