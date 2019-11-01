package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="DriverControlled", group="Competition")
public class DriverControlled extends LinearOpMode {

    Robot robot = new Robot();

    public void runOpMode(){

        //initialize subsystems
        robot.drivetrain.initialize(hardwareMap, telemetry);
        robot.foundationClaw.initialize(hardwareMap, telemetry);
        robot.stoneClaw.initialize(hardwareMap, telemetry);

        //settings
        robot.drivetrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.drivetrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.foundationClaw.setOpen();

        waitForStart();

        while(opModeIsActive()){

            //powers are reversed bc we are using the back as the front for this competition
            double xPower = Math.copySign(Math.pow(gamepad1.left_stick_x, 2), gamepad1.left_stick_x);

            double yPower = Math.copySign(Math.pow(gamepad1.left_stick_y, 2), -gamepad1.left_stick_y);

            //right is positive on the gamepad, which is opposite what the power formula uses so
            //it must be flipped
            double rotationPower = Math.copySign(Math.pow(gamepad1.right_stick_x, 2), -gamepad1.right_stick_x);

            robot.drivetrain.setPower(xPower, yPower, rotationPower);

            //FOUNDATION CLAW
            if (gamepad1.x) {
                robot.foundationClaw.setOpen();
                telemetry.addData("FoundationClaw:", "Open");
            }
            if (gamepad1.y) {
                robot.foundationClaw.setClosed();
                telemetry.addData("FoundationClaw:", "Closed");
            }

            //STONE CLAW
            if (gamepad1.dpad_right) {
                robot.stoneClaw.setClosed();
                telemetry.addData("StoneClaw:", "Closed");
            }
            if (gamepad1.dpad_left) {
                robot.stoneClaw.setOpen();
                telemetry.addData("StoneClaw:", "Open");
            }

            telemetry.update();

        }


    }
}
