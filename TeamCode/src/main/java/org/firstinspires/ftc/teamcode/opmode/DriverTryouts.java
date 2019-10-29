package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="Driver Tryouts", group="tryouts")
public class DriverTryouts extends LinearOpMode {

    Robot robot = new Robot();

    ElapsedTime countdownTimer = new ElapsedTime();

    public final double MAX_TIME = 120;

    public boolean useMarkMethod = false;

    public void runOpMode(){

        robot.initialize(hardwareMap, telemetry);

        waitForStart();
        countdownTimer.reset();

        while(opModeIsActive() && countdownTimer.seconds() <= MAX_TIME){

            double xPower = gamepad1.left_stick_x;

            //up on the joystick is negative so it must be inverted to map the way you'd expect
            double yPower = -gamepad1.left_stick_y;

            //right is positive on the gamepad, which is opposite what the power formula uses so
            //it must be flipped
            double rotationPower = -gamepad1.right_stick_x;

            if(gamepad1.a) useMarkMethod = true;
            if(gamepad1.b) useMarkMethod = false;

            if(useMarkMethod) {
                // Mark Vadeika's Technique
                robot.drivetrain.frontLeft.setPower(Range.clip(-gamepad1.left_stick_y - gamepad1.left_stick_x, -1, 1));
                robot.drivetrain.frontRight.setPower(Range.clip(-gamepad1.right_stick_y + gamepad1.right_stick_x, -1, 1));
                robot.drivetrain.backLeft.setPower(Range.clip(-gamepad1.left_stick_y + gamepad1.left_stick_x, -1, 1));
                robot.drivetrain.backRight.setPower(Range.clip(-gamepad1.right_stick_y - gamepad1.right_stick_x, -1, 1));

                telemetry.addData("Drive Scheme: ", "Marquus");
            } else {
                robot.drivetrain.setPower(xPower, yPower, rotationPower);

                telemetry.addData("Drive Scheme: ", "Colin");
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


            telemetry.addData("Time remaining: ", MAX_TIME - countdownTimer.seconds());

        }



    }
}
