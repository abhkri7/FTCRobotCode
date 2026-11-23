package org.firstinspires.ftc.teamcode.backend;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.dependencies.pedro.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RobotCommons {
    // Pedro Paths
    public static Follower follower = Constants.create(hardwareMap);
    static private Timer pathTimer, opModeTimer;
    public enum PathState{
        RUNNING,
        STALLED,
        CANCELLING

    }

    public static void monitorPedroPaths() {
        telemetry.addData("x", follower.pose().x());
        telemetry.addData("y", follower.pose().y());
        telemetry.addData("heading", follower.pose().heading());

        if (follower.currentPath() != null) {
            telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
            telemetry.addData("Path number", follower.pathIndex());
        }

    }
    public static void tickPedro(){
        follower.update();
        Scheduler.execute();
    }
    static public Path generateCompoundPath(MetaPose... metaPoses) {
        // This functions allows through-paths & control-paths to be combined
        int start = 0, end = 1;
        int numberOfPaths = 0;

        // Store minipaths that will later be combined
        List<Path> pathList = new ArrayList<>();

        for (int i = 1; i < metaPoses.length; i++) {
            if (metaPoses[i].poseType != MetaPose.MetaPoseType.PULL_POINT) {
                // Add path chunk to the list
                pathList.add(Paths.curve(Arrays.copyOfRange(metaPoses, end, i+1)));
                numberOfPaths++;

                // Update start and end indices
                start = end;
                end = i;
            }
        }

        // Convert the PathsList to an eligible type for combination
        Path[] convertedPathList = pathList.toArray(new Path[numberOfPaths]);
        return Paths.path(convertedPathList);

    }
    // Poses
    public static PoseFactory poseFactory = PoseFactory.degrees();
    static void updateCurrentPose(Pose pose){
        follower.setPose(pose);
        follower.update();
    }

    // Game Info
    static Alliance alliance = RobotPoints.setRobotPoints(18.5, 5, 5, 5, 5, 5);

    // OpModes
    static DrivePowers powers = ManualDrive.fieldCentric(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, follower.pose().heading());
    public static void opModeInitialize(LinearOpMode opMode, Pose startPose, Alliance alliance) {
        // Initialize Follower and Scheduler
        follower.setPose(startPose);
        Scheduler.reset();

        // Setup Poses
        BIOBUZZPoints.createPoses(poseFactory, alliance);
        RobotCommons.alliance = alliance;

        // Wait for Activation
        opMode.waitForStart();
    }
    // Tele-Op Controller
    public static void controllerDrive(){
        ManualDrive.driveOrHold(follower, powers);
    }
}
