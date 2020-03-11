package org.firstinspires.ftc.teamcode.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.SkystoneDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="Sample Skystone Detection OpMode")
public class SkystoneTest extends LinearOpMode {

    OpenCvCamera camera;
    SkystoneDetector pipeline;
    @Override
    public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        camera.openCameraDevice();

        pipeline = new SkystoneDetector(10, 30, 50, 50);

        camera.setPipeline(pipeline);
        camera.openCameraDevice();
        camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);


        while(!isStopRequested() && !opModeIsActive()) {
            telemetry.addData("Skystone Position", pipeline.getSkystonePosition());
            telemetry.update();
        }

        waitForStart();

        // Assuming threaded. It hopefully found the skystone at the end of init.
        SkystoneDetector.SkystonePosition position = pipeline.getSkystonePosition();

        switch (position) {
            case LEFT_STONE:
                break;
            case CENTER_STONE:
                break;
            case RIGHT_STONE:
                break;
            default:
                break;
        }
        telemetry.addData("Skystone Position", position);
        telemetry.update();

        camera.stopStreaming();
        camera.closeCameraDevice();
    }

}

