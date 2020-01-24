package org.firstinspires.ftc.teamcode.drive.opmode.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name="IMUTest")
public class IMUTest extends LinearOpMode {

    BNO055IMU imu;

    public void runOpMode(){

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        telemetry.addData("IMU is good to go", "");
        telemetry.update();

        waitForStart();


        while(opModeIsActive()) {
            telemetry.addData("Heading", imu.getAngularOrientation().firstAngle);
            telemetry.update();
        }

    }
}
