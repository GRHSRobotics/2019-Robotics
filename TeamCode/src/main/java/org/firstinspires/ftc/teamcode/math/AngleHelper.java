package org.firstinspires.ftc.teamcode.math;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AngleHelper {

    /**
     * Returns the angle in a certain specified unit. This is used to simplify conversions in other classes.
     * See subsystem/Gyroscope.java
     * @param inputUnit The unit of the inputted angle
     * @param outputUnit The unit desired for the outputted angle
     * @param angle The angle to be operated on
     * @return
     */
    public static double expressAngle(AngleUnit inputUnit, AngleUnit outputUnit, double angle){
        if(inputUnit == outputUnit){
            return angle;
        } else if(outputUnit == AngleUnit.RADIANS){
            return Math.toRadians(angle);
        } else {
            return Math.toDegrees(angle);
        }
    }

    /**
     * Converts a field centric angle into a robot centric angle that can be fed to the drivetrain.
     * @param angleUnit The angle unit that the calculation is done in. This determines both the input unit
     *                  and the output unit.
     * @param desiredAngle The field-centric angle that is desired (-180, 180) or radian equivalent
     * @param currentAngle The current field-centric heading of the robot. This should come straight from
     *                     the gyroscope. (-180, 180) or radian equivalent
     * @return The robot-centric angle using the above parameters. Range of angle is [0, 2pi) as this
     *          is standard reference frame for angles and is necessary to perform mathematical operations
     *          on it.
     */
    public static double fieldToRobotCentric(AngleUnit angleUnit, double desiredAngle, double currentAngle){
        return desiredAngle - currentAngle + expressAngle(AngleUnit.DEGREES, angleUnit, 90);
    }
}
