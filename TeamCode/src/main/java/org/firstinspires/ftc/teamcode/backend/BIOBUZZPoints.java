package org.firstinspires.ftc.teamcode.backend;



import com.pedropathing.api.PoseFactory;
import static org.firstinspires.ftc.teamcode.backend.MetaPose.MetaPoseType.ACTION_POINT;

import org.firstinspires.ftc.teamcode.backend.Alliance;
public class BIOBUZZPoints {
    BIOBUZZPoints(Alliance alliance){
    }

    // Declare Poses
    public static MetaPose HC, HF_FRONT, HF_H, HF_X;
    public static MetaPose HPC, HPF_FRONT, HPF_H, HPF_X;
    public static MetaPose EC, EF_FRONT, EF_H, EF_X;
    static MetaPose EPC, EPF_FRONT, EPF_H, EPF_X;

    // Game Setup
    public static double flowerEffectiveHALFWidth = 3.125, flowerEffectiveLength = 3.5;

    /*
    Example for a flower and its nearest center
    +++++++++++++++
    |HPC
    |
    |
    |HPF_RED
    |   HPF_FRONT
    |HPF_BLUE
    |
    ++++++++++++++++

     */
    // H = Home
    // E = Enemy
    // P = Perpendicular
    // C = Center
    // F = Flower
    // X = Launch/Collect
    static public void createPoses(PoseFactory poseFactory, Alliance alliance){
        if (alliance == Alliance.BLUE_ALLIANCE) {
            poseFactory.mirrorAroundPoint(0, 0);
        }

        HC = new MetaPose(ACTION_POINT, poseFactory,-72 + RobotPoints.driveCenterOffsetBack,0,0);
        HF_FRONT = new MetaPose(ACTION_POINT, poseFactory,-72 + RobotPoints.driveCenterOffsetFront + flowerEffectiveLength,-24,180);
        HF_H = new MetaPose(ACTION_POINT, poseFactory,-72 + RobotPoints.driveCenterOffsetBack,-24 - flowerEffectiveHALFWidth - RobotPoints.driveCenterOffsetLeft,180);
        HF_X = new MetaPose(ACTION_POINT, poseFactory,-72 + RobotPoints.driveCenterOffsetBack,-24 + flowerEffectiveHALFWidth + RobotPoints.driveCenterOffsetRight,180);

        HPC = new MetaPose(ACTION_POINT,poseFactory, 0,-72 + RobotPoints.driveCenterOffsetBack,90);
        HPF_FRONT = new MetaPose(ACTION_POINT, poseFactory,24, -72 + RobotPoints.driveCenterOffsetFront + flowerEffectiveLength,90);
        HPF_H = new MetaPose(ACTION_POINT,poseFactory, 24 + flowerEffectiveHALFWidth + RobotPoints.driveCenterOffsetLeft, -72 + RobotPoints.driveCenterOffsetFront,90);
        HPF_X = new MetaPose(ACTION_POINT, poseFactory,72 - flowerEffectiveHALFWidth - RobotPoints.driveCenterOffsetFront, -72 + RobotPoints.driveCenterOffsetLeft,0);

        EC = new MetaPose(ACTION_POINT, poseFactory,72 - RobotPoints.driveCenterOffsetBack,0,180);
        EF_FRONT = new MetaPose(ACTION_POINT,poseFactory, 72 - RobotPoints.driveCenterOffsetFront + flowerEffectiveLength,24,0);
        EF_H = new MetaPose(ACTION_POINT,poseFactory, 72 - RobotPoints.driveCenterOffsetFront,24 + flowerEffectiveHALFWidth + RobotPoints.driveCenterOffsetLeft,0);
        EF_X = new MetaPose(ACTION_POINT, poseFactory,72 - RobotPoints.driveCenterOffsetFront,24 - flowerEffectiveHALFWidth - RobotPoints.driveCenterOffsetRight,0);

        EPC = new MetaPose(ACTION_POINT, poseFactory,0, 72 - RobotPoints.driveCenterOffsetBack,-90);
        EPF_FRONT = new MetaPose(ACTION_POINT,poseFactory, -24, 72 - RobotPoints.driveCenterOffsetFront + flowerEffectiveLength,-90);
        EPF_H = new MetaPose(ACTION_POINT,poseFactory, -24 - flowerEffectiveHALFWidth - RobotPoints.driveCenterOffsetLeft, 72 - RobotPoints.driveCenterOffsetFront,-90);
        EPF_X = new MetaPose(ACTION_POINT,poseFactory, -72 + flowerEffectiveHALFWidth + RobotPoints.driveCenterOffsetFront, 72 - RobotPoints.driveCenterOffsetRight,180);
    }
}
