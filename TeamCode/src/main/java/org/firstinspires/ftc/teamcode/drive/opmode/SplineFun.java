package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.MecanumDriveREVOptimized;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class SplineFun extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDriveREVOptimized drive = new MecanumDriveREVOptimized(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectorySync(
                drive.trajectoryBuilder()
                        .splineTo(new Pose2d(15, 30, 45))
                        .splineTo(new Pose2d(30, 60, 0))
                        .build()
        );

        sleep(2000);

        drive.followTrajectorySync(
                drive.trajectoryBuilder()
                        .reverse()
                        .splineTo(new Pose2d(0, 0, 0))
                        .build()
        );
    }
}
