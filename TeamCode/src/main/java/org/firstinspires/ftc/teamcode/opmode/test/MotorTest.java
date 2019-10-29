package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp (name="motorTest", group="test")
public class MotorTest extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    ElapsedTime timer = new ElapsedTime();

    public void runOpMode(){

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        timer.reset();

        while(opModeIsActive()){
            double forwardPower = 0.25;
            double strafePower = 0;

            //frontLeft.setPower(forwardPower);
            //frontRight.setPower(forwardPower);
            //backLeft.setPower(forwardPower);
            backRight.setPower(forwardPower);

            //robot.drivetrain.setPower(strafePower, forwardPower, 0);


            //use to empirically find formula for mecanum drive
            telemetry.addData("FL: ", frontLeft.getCurrentPosition());
            telemetry.addData("FR: ", frontRight.getCurrentPosition());
            telemetry.addData("BL: ", backLeft.getCurrentPosition());
            telemetry.addData("BR: ", backRight.getCurrentPosition());
            telemetry.update();
        }
    }
}
