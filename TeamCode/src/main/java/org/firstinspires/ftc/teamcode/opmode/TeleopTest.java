package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.math.AngleHelper;

@TeleOp (name="TeleopTest", group="test")
public class TeleopTest extends LinearOpMode {

    Robot robot;

    public void runOpMode(){

        robot = new Robot(hardwareMap, telemetry);

        waitForStart();

        while(opModeIsActive()){

            double xPower = gamepad1.left_stick_x;

            //up on the joystick is negative so it must be inverted to map the way you'd expect
            double yPower = -gamepad1.left_stick_y;

            //right is positive on the gamepad, which is opposite what the power formula uses so
            //it must be flipped
            double rotationPower = -gamepad1.right_stick_x;
            /*
            double power = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2 ) + Math.pow(gamepad1.left_stick_y, 2));

            double angle = AngleHelper.fieldToRobotCentric(AngleUnit.RADIANS, Math.atan2(yPower, xPower), robot.gyroscope.getHeading(AngleUnit.RADIANS));
            //double angle = Math.atan2(yPower, xPower) + Math.PI/2;
            //field centric version
            robot.drivetrain.setPowerPolar(power, angle, rotationPower, AngleUnit.RADIANS);
            */
            robot.drivetrain.setPower(xPower, yPower, rotationPower);

        }

    }
}
