package org.firstinspires.ftc.teamcode.opmodes;
import com.pedropathing.ivy.Command;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static org.firstinspires.ftc.teamcode.backend.Alliance.RED_ALLIANCE;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.follower;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.monitorPedroPaths;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.opModeInitialize;
import static org.firstinspires.ftc.teamcode.backend.RobotCommons.tickPedro;


import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.backend.BIOBUZZPoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Autonomous(name = "AutoPath", group = "Autonomous")
public class AutoPathingTest extends LinearOpMode {
    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                //follow(follower, path1())
        );
    }
    @Override
    public void runOpMode() {
        opModeInitialize(this, BIOBUZZPoints.EC, RED_ALLIANCE);
        schedule(autoRoutine());
        while (opModeIsActive()) {
            monitorPedroPaths();
            tickPedro();
            telemetry.update();
        }
    }

}
