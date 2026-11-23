package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import org.firstinspires.ftc.teamcode.backend.*;
import static org.firstinspires.ftc.teamcode.backend.Alliance.RED_ALLIANCE;
import static org.firstinspires.ftc.teamcode.backend.MetaPose.MetaPoseType.ACTION_POINT;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.controllerDrive;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.follower;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.opModeInitialize;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.poseFactory;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.backend.MetaPose;
import org.firstinspires.ftc.teamcode.dependencies.pedro.Constants;
@TeleOp
public class TeleopPathingTest extends LinearOpMode {
    Pose startPose = new MetaPose(ACTION_POINT, poseFactory,0,0,0);
    @Override
    public void runOpMode(){
        opModeInitialize(this, startPose, RED_ALLIANCE);
        controllerDrive();
        telemetry.addData("Coordinates:","x: %.2f y: %.2f heading: %.2f", follower.pose().x(),follower.pose().y(),Math.toDegrees(follower.pose().heading()));
        follower.update();
    }
}
