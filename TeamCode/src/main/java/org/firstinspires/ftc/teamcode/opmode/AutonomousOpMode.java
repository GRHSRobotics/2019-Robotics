package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class AutonomousOpMode extends LinearOpMode {


    private VuforiaLocalizer vuforia;

    public Robot robot = new Robot();

    //CONSTANTS
    final double POSITION_THRESHOLD = 0.3; //inches
    final double TURN_THRESHOLD_DEGREES = 5; //degrees
    final double P_TURN = 0.3; //power per degree

    final double P_TURN_COEFF = 0.012; //for sample code
    final double HEADING_THRESHOLD = 1; //for sample code
    final double MIN_DRIVE_POWER = 0;


    //TODO write all of these methods
/*
    public void gyroTurn(double desiredAngle, AngleUnit angleUnit) {

        boolean angleReached = false;

        //switch to degrees if input is in radians
        if (angleUnit == AngleUnit.RADIANS) {
            desiredAngle = Math.toDegrees(desiredAngle);

        }

        double error;
        double turnPower;
        while (opModeIsActive() && !angleReached) {


            error = desiredAngle - robot.gyroscope.getHeading(AngleUnit.DEGREES);
            while(error > 180) error -= 360;
            while(error <= -180) error += 360;

            if(Math.abs(error) <= TURN_THRESHOLD_DEGREES){
                angleReached = true;
            }

            //simple P correction
            turnPower = P_TURN * error;

            robot.drivetrain.setPower(0, 0, turnPower);

            telemetry.addData("Error: ", error);
            telemetry.addData("Heading: ", robot.gyroscope.getHeading(AngleUnit.DEGREES));
            telemetry.update();

        }

        robot.drivetrain.setPower(0, 0, 0);
    }
    */

    public void gyroTurn (  double speed, double angle, AngleUnit angleUnit, double maxTimeS) {
        ElapsedTime timer = new ElapsedTime();
        if(angleUnit == AngleUnit.RADIANS){
            angle = Math.toDegrees(angle);
        }
        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF) && timer.seconds() < maxTimeS) {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
        }
    }

    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double rotationSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            rotationSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rotationSpeed = speed * steer;

            //give the motor power a minimum to prevent stalling
            if(Math.abs(rotationSpeed) < MIN_DRIVE_POWER){
                rotationSpeed = MIN_DRIVE_POWER;
            }
        }

        robot.drivetrain.setPower(0, 0, rotationSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", rotationSpeed);

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - robot.gyroscope.getHeading(AngleUnit.DEGREES);
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  +ve = steer left
     * @param error   Error angle in robot relative degrees
     * @param PCoeff  Proportional Gain Coefficient
     * @return
     */
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void driveX(double xInches, double power){
        robot.drivetrain.setOrigin();

        int distance = (int)(xInches * Math.sqrt(2) * robot.drivetrain.COUNTS_PER_INCH);

        robot.drivetrain.frontLeft.setTargetPosition(robot.drivetrain.frontLeft.getCurrentPosition() + distance);
        robot.drivetrain.frontRight.setTargetPosition(robot.drivetrain.frontRight.getCurrentPosition() - distance);
        robot.drivetrain.backLeft.setTargetPosition(robot.drivetrain.backLeft.getCurrentPosition() - distance);
        robot.drivetrain.backRight.setTargetPosition(robot.drivetrain.backRight.getCurrentPosition() + distance);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.drivetrain.setPower(power, 0, 0);

        while(opModeIsActive() && robot.drivetrain.isBusy()){
            telemetry.addData("X Target: ", xInches);
            telemetry.addData("X Position: ", robot.drivetrain.getXInches());
            telemetry.update();
        }

        robot.drivetrain.setPower(0, 0, 0);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void driveY(double yInches, double power){
        robot.drivetrain.setOrigin();

        int distance = (int)(yInches * Math.sqrt(2) * robot.drivetrain.COUNTS_PER_INCH);

        robot.drivetrain.frontLeft.setTargetPosition(robot.drivetrain.frontLeft.getCurrentPosition() + distance);
        robot.drivetrain.frontRight.setTargetPosition(robot.drivetrain.frontRight.getCurrentPosition() + distance);
        robot.drivetrain.backLeft.setTargetPosition(robot.drivetrain.backLeft.getCurrentPosition() + distance);
        robot.drivetrain.backRight.setTargetPosition(robot.drivetrain.backRight.getCurrentPosition() + distance);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.drivetrain.setPower(0, power, 0);

        while(opModeIsActive() && robot.drivetrain.isBusy()){
            telemetry.addData("Y Target: ", yInches);
            telemetry.addData("Y Position: ", robot.drivetrain.getYInches());
            telemetry.update();
        }

        robot.drivetrain.setPower(0, 0, 0);

        robot.drivetrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }


    public void basicDriveToPosition ( double xInches, double yInches){

            robot.drivetrain.setOrigin();

            boolean targetReached = false;
            boolean yReached = false;
            boolean xReached = false;

            while (opModeIsActive() && !targetReached) {

                //use funky formula to assign motor power
                //TODO look into PID or better formula for this (maybe based on error instead of total displacement?)
                double xPower = xInches / (Math.abs(xInches) + Math.abs(yInches));
                double yPower = yInches / (Math.abs(xInches) + Math.abs(yInches));



                //conditions for ending movement
                //TODO this is basic and doesn't account for overshoots
                if (Math.abs(robot.drivetrain.getXInches() - xInches) < POSITION_THRESHOLD || xInches == 0) {
                    xReached = true;
                    xPower = 0;
                }
                if (Math.abs(robot.drivetrain.getYInches() - yInches) < POSITION_THRESHOLD || yInches == 0) {
                    yReached = true;
                    yPower = 0;
                }
                if (xReached && yReached) {
                    targetReached = true;
                }

                //set motor powers
                robot.drivetrain.setPower(xPower, yPower, 0);

                telemetry.addData("xInches: " ,robot.drivetrain.getXInches());
                telemetry.addData("yInches: ", robot.drivetrain.getYInches());
                telemetry.addData("xReached: ", xReached);
                telemetry.addData("yReached: ", yReached);
                telemetry.update();
            }

            robot.drivetrain.setPower(0, 0, 0);
    }

    public void scanIt () {


            final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
            final boolean PHONE_IS_PORTRAIT = false;

            final String VUFORIA_KEY =
                    "AR1NGjD/////AAABmRNHhw4urkcYu6OsCz4GxO9HaxexcrZrSNGBfCYsc8miWAyyHlu53AsvQ0AMdhXKpFuLLm0Dej3xk4agW4J4tOXGu+hPnigkbDyr5HhVrGXPGxFyNCpJUHx+Sr6UMygVYr5b+z78sdhUeN2o4KBHClV+VzRnAuG0h4GiWh+58fPYhqIIRboPe41XAbmNWwCIqAG+1y5XXaENN0jq99vO4e4GgzYzQdAQtK4Jrq4pkIZev+fI5K2B500kIkiVv3YrnC1JkQNIfibntc+98DKcN7hbJ3TWJmHndB9vesnlzPnDEJ/q9j+V+w82/icXhZ58Jcu+QMu/iuo7eEZeCLQ8S5BqotKIbxP3mCW31jh93Btc ";

            final float mmPerInch = 25.4f;
            final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

            final float stoneZ = 2.00f * mmPerInch;

            final float halfField = 72 * mmPerInch;
            final float quadField = 36 * mmPerInch;

            OpenGLMatrix lastLocation = null;
            VuforiaLocalizer vuforia = null;

            WebcamName webcamName = null;

            boolean targetVisible = false;
            float phoneXRotate = 0;
            float phoneYRotate = 0;
            float phoneZRotate = 0;

            VuforiaTrackables targetsSkyStone = null;

            while (targetVisible = true) {
                if (targetVisible = false) {
                    webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

                    int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
                    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

                    parameters.vuforiaLicenseKey = VUFORIA_KEY;

                    parameters.cameraName = webcamName;

                    vuforia = ClassFactory.getInstance().createVuforia(parameters);

                    targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

                    VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
                    stoneTarget.setName("Stone Target");
                    VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
                    blueRearBridge.setName("Blue Rear Bridge");
                    VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
                    redRearBridge.setName("Red Rear Bridge");
                    VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
                    redFrontBridge.setName("Red Front Bridge");
                    VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
                    blueFrontBridge.setName("Blue Front Bridge");
                    VuforiaTrackable red1 = targetsSkyStone.get(5);
                    red1.setName("Red Perimeter 1");
                    VuforiaTrackable red2 = targetsSkyStone.get(6);
                    red2.setName("Red Perimeter 2");
                    VuforiaTrackable front1 = targetsSkyStone.get(7);
                    front1.setName("Front Perimeter 1");
                    VuforiaTrackable front2 = targetsSkyStone.get(8);
                    front2.setName("Front Perimeter 2");
                    VuforiaTrackable blue1 = targetsSkyStone.get(9);
                    blue1.setName("Blue Perimeter 1");
                    VuforiaTrackable blue2 = targetsSkyStone.get(10);
                    blue2.setName("Blue Perimeter 2");
                    VuforiaTrackable rear1 = targetsSkyStone.get(11);
                    rear1.setName("Rear Perimeter 1");
                    VuforiaTrackable rear2 = targetsSkyStone.get(12);
                    rear2.setName("Rear Perimeter 2");

                    // For convenience, gather together all the trackable objects in one easily-iterable collection */
                    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
                    allTrackables.addAll(targetsSkyStone);

                    /**
                     * In order for localization to work, we need to tell the system where each target is on the field, and
                     * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
                     * Transformation matrices are a central, important concept in the math here involved in localization.
                     * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
                     * for detailed information. Commonly, you'll encounter transformation matrices as instances
                     * of the {@link OpenGLMatrix} class.
                     *
                     * If you are standing in the Red Alliance Station looking towards the center of the field,
                     *     - The X axis runs from your left to the right. (positive from the center to the right)
                     *     - The Y axis runs from the Red Alliance Station towards the other side of the field
                     *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
                     *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
                     *
                     * Before being transformed, each target image is conceptually located at the origin of the field's
                     *  coordinate system (the center of the field), facing up.
                     */

                    // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
                    // Rotated it to to face forward, and raised it to sit on the ground correctly.
                    // This can be used for generic target-centric approach algorithms
                    stoneTarget.setLocation(OpenGLMatrix
                            .translation(0, 0, stoneZ)
                            .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, AngleUnit.DEGREES, 90, 0, -90)));


                    //
                    // Create a transformation matrix describing where the phone is on the robot.
                    //
                    // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
                    // Lock it into Portrait for these numbers to work.
                    //
                    // Info:  The coordinate frame for the robot looks the same as the field.
                    // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
                    // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
                    //
                    // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
                    // pointing to the LEFT side of the Robot.
                    // The two examples below assume that the camera is facing forward out the front of the robot.

                    // We need to rotate the camera around it's long axis to bring the correct camera forward.
                    if (CAMERA_CHOICE == BACK) {
                        phoneYRotate = -90;
                    } else {
                        phoneYRotate = 90;
                    }

                    // Rotate the phone vertical about the X axis if it's in portrait mode
                    if (PHONE_IS_PORTRAIT) {
                        phoneXRotate = 90;
                    }

                    // Next, translate the camera lens to where it is on the robot.
                    // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
                    final float CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
                    final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
                    final float CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

                    OpenGLMatrix robotFromCamera = OpenGLMatrix
                            .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                            .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, AngleUnit.DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

                    /**  Let all the trackable listeners know where the phone is.  */
                    for (VuforiaTrackable trackable : allTrackables) {
                        ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
                    }

                    targetsSkyStone.activate();

                    while (!isStopRequested()) {

                        // check all the trackable targets to see which one (if any) is visible.
                        targetVisible = false;
                        for (VuforiaTrackable trackable : allTrackables) {
                            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                                telemetry.addData("Visible Target", trackable.getName());
                                targetVisible = true;

                                // getUpdatedRobotLocation() will return null if no new information is available since
                                // the last time that call was made, or if the trackable is not currently visible.
                                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                                if (robotLocationTransform != null) {
                                    lastLocation = robotLocationTransform;
                                }
                                break;
                            }
                        }

                        // Provide feedback as to where the robot is located (if we know).
                        if (targetVisible) {
                            // express position (translation) of robot in inches.
                            VectorF translation = lastLocation.getTranslation();
                            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                            // express the rotation of the robot in degrees.
                            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, AngleUnit.DEGREES);
                            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
                        } else {
                            telemetry.addData("Visible Target", "none");
                        }
                        telemetry.update();
                    }


                } else {

                    targetsSkyStone.deactivate();

                    telemetry.addData("finished", "none");


                }
            }
     }

     public void runOpMode(){}
}
    


    //never ran but is necessary to not have error messag