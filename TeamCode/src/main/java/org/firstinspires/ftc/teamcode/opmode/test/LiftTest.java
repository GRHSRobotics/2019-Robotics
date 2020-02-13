package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name="LiftTest", group="test")
public class LiftTest extends LinearOpMode {
    Robot robot = new Robot();

    ElapsedTime downTimer = new ElapsedTime();
    ElapsedTime runTimer = new ElapsedTime();

    boolean intakeChanged = false;
    boolean intakeUp = false;

    public void runOpMode(){
        robot.lift.initialize(hardwareMap, telemetry, false);
        robot.drivetrain.initialize(hardwareMap, telemetry, false);



        waitForStart();

        downTimer.reset();
        runTimer.reset();

        while(opModeIsActive()){


            telemetry.update();
        }
    }
}
