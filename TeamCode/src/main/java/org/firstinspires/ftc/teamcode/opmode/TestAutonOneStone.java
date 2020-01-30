package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystem.FoundationClaw;
import org.firstinspires.ftc.teamcode.subsystem.rrdrive.MecanumDriveREVOptimized;

@Autonomous(group="autontest")
public class TestAutonOneStone extends LinearOpMode {


    @Override
    public void runOpMode(){
        MecanumDriveREVOptimized drive = new MecanumDriveREVOptimized(hardwareMap);

        FoundationClaw foundationClaw = new FoundationClaw();
        foundationClaw.initialize(hardwareMap, telemetry);

        waitForStart();

        drive.setPoseEstimate(new Pose2d(-36.0, 63.0, Math.toRadians(-90.0)));

        drive.followTrajectorySync(drive.trajectoryBuilder()
                .splineTo(new Pose2d(-28.0, 24.0, Math.toRadians(-120.0)))
                .build()
        );
        sleep(1000);

        //move to foundation
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .setReversed(true)
                .splineTo(new Pose2d(-10.0, 36.0, Math.toRadians(-180.0)))
                .lineTo(new Vector2d(10.0, 36.0))
                .splineTo(new Pose2d(50.0, 30.0, Math.toRadians(90.0)))
                .build()
        );
        foundationClaw.setClosed();
        sleep(1000);

        //drop stone and pull foundation
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .setReversed(false)
                .splineTo(new Pose2d(30.0, 60.0, Math.toRadians(180.0)))
                .build()
        );

        //push foundation into build zone
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .setReversed(true)
                .lineTo(new Vector2d(42.0, 60.0))
                .build()
        );
        foundationClaw.setOpen();
        sleep(1000);

        //park under bridge near center of field
        drive.followTrajectorySync(drive.trajectoryBuilder()
                .setReversed(false)
                .splineTo(new Pose2d(0.0, 36.0, Math.toRadians(180.0)))
                .build()
        );

    }
}
