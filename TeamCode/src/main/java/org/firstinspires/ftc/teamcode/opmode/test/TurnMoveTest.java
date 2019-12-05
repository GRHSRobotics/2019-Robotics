package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.opmode.AutonomousOpMode;

public class TurnMoveTest extends AutonomousOpMode {

    double TURN_ANGLE_RADIANS = Math.toRadians(90);
    double X_DRIVE_POWER = 0.5;
    double Y_DRIVE_POWER = 0;
    double TURN_POWER = 0.3;

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);
        waitForStart();

        double currentAngle = robot.gyroscope.getHeading(AngleUnit.RADIANS);
        double error = TURN_ANGLE_RADIANS - currentAngle;

        while(Math.abs(error) > 5 && opModeIsActive()){
            currentAngle = robot.gyroscope.getHeading(AngleUnit.RADIANS);
            error = TURN_ANGLE_RADIANS - currentAngle;

            double xPower = X_DRIVE_POWER * Math.cos(error + Math.PI/2);
            double yPower = X_DRIVE_POWER * Math.sin(error + Math.PI/2);
            double rotationPower = Range.clip(P_TURN_COEFF * error, -1, 1);

            robot.drivetrain.setPower(xPower, yPower, rotationPower);
        }

        robot.drivetrain.setPower(0, 0, 0);

    }

}
