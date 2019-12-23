package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.math.AngleHelper;

public class Gyroscope implements Subsystem{

    public BNO055IMU imu;

    public double INITIAL_HEADING_RADIANS = 0;

    //TODO make gyroscope pull from a config/calibration file to make init faster
    public void initialize(HardwareMap hardwareMap, Telemetry telemetry){
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

    public void setInitialHeading(AngleUnit angleUnit, double initialHeading){
        if(angleUnit == AngleUnit.RADIANS){
            INITIAL_HEADING_RADIANS = initialHeading;
        } else {
            INITIAL_HEADING_RADIANS = Math.toRadians(initialHeading);
        }
    }

    /**
     * Gets the current imu reading. Range (-180, 180) or (-pi, pi)
     * @param angleUnit The unit to express the heading in, either radians or degrees
     * @return The heading in the angle unit specified. 0 is forward,
     *          positive is counterclockwise, negative is clockwise
     */
    public double getHeading(AngleUnit angleUnit){
            return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, angleUnit).firstAngle
                    + AngleHelper.expressAngle(AngleUnit.RADIANS, angleUnit, INITIAL_HEADING_RADIANS);
    }

}
