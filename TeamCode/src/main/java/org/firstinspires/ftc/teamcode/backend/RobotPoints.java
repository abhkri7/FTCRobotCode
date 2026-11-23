package org.firstinspires.ftc.teamcode.backend;

// Measurements about the Robot for use in PedroPoses
public class RobotPoints {
    // All Measurements are in INCHES
    // The Drivetrain center is not equivalent to the Body's center in most robots
    public static double bodyWidth, bodyLength;
    public static double driveCenterOffsetLeft,
            driveCenterOffsetRight,
            driveCenterOffsetBack,
            driveCenterOffsetFront;

    public static Alliance setRobotPoints(double bW, double bL, double dCOL, double dCOR, double dCOB, double dCOF) {
        bodyWidth = bW;
        bodyLength = bL;
        driveCenterOffsetLeft = dCOL;
        driveCenterOffsetRight = dCOR;
        driveCenterOffsetBack = dCOB;
        driveCenterOffsetFront = dCOF;
        return Alliance.PLACEHOLDER;
    }
}