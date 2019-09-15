package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class Gyroscope {

    public BNO055IMU imu;

    //TODO make gyroscope pull from a config/calibration file to make init faster
    public Gyroscope(HardwareMap hardwareMap, Telemetry telemetry){
        //DEFINE REV HUB IMU
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        /* SET IMU PARAMETERS */
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        /* makes it so that the only part of the device that activates is the IMU
        and not the accelerometer or any of that other stuff */
        parameters.mode = BNO055IMU.SensorMode.IMU;

        //INIT IMU
        telemetry.addData("Mode", "Calibrating Gyro");
        telemetry.update();

        this.imu.initialize(parameters);

        telemetry.addData("Gyro: ", "initiaslized");

        //display whether the IMU calibrated successfully
        telemetry.addData("IMU calib status", imu.getCalibrationStatus().toString());
        telemetry.update();
    }

    public double getAngle(AngleUnit angleUnit){
        if(angleUnit == AngleUnit.RADIANS){
            return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
        } else {
            return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        }
    }
}
