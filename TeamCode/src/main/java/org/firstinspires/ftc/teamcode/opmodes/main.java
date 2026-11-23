/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

import org.firstinspires.ftc.teamcode.AutoPathingTest;
import org.firstinspires.ftc.teamcode.TeleopPathingTest;
import org.firstinspires.ftc.teamcode.tests.LimelightUtil;

@Config
@TeleOp(name = "Sensor: Limelight3A", group = "Sensor")
public class main extends LinearOpMode {

    // Match Variables
    public static String alliance = "red";
    private byte frameTimer = 0;
    // Motors
    public static double intakeSpeed = 800, shooterSpeed = 800;
    LimelightUtil limeutils;
    @Override
    public void runOpMode() throws InterruptedException {
        // Driver Station Linking
        telemetry.setMsTransmissionInterval(11);
        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();

        // Limelight Initialization
        limeutils.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limeutils.limelight.pipelineSwitch(0);
        limeutils.limelight.start();

        // Motor Object Retrieval
        DcMotorEx motorIntake = hardwareMap.get(DcMotorEx.class, "intake");
        DcMotorEx motorShooter = hardwareMap.get(DcMotorEx.class, "shooter");

        // State Variable Initialization
        limeutils.redHiveHeights = new boolean[8];
        limeutils.blueHiveHeights = new boolean[8];
        byte avgRedState = 2;
        byte avgBlueState = 2;

        // Shooter Motor Configuration
        motorIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorIntake.setVelocityPIDFCoefficients(1, 0, 0, 0);
        motorIntake.setDirection(DcMotor.Direction.REVERSE);
        motorIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motorShooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorShooter.setVelocityPIDFCoefficients(1, 0, 0, 0);
        motorShooter.setDirection(DcMotor.Direction.FORWARD);
        motorShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Wait for Start Button clicked
        waitForStart();

        if ("red".equalsIgnoreCase(alliance)) {
            motorIntake.setVelocity(intakeSpeed);
            motorShooter.setVelocity(shooterSpeed);
            gamepad1.rumble(1000);
        }
        while (opModeIsActive()) {
            frameTimer += 1;
            motorIntake.setVelocity(intakeSpeed);
            motorShooter.setVelocity(shooterSpeed);
            telemetry.addData("Motor Velocity", "speed: %.2f", motorIntake.getVelocity());

            LLResult result = limeutils.limelight.getLatestResult();
            limeutils.reportLimelightMetrics();
            if (result.isValid()) {
                if (frameTimer >= 25) {
                    limeutils.resetHiveStates();
                    frameTimer = 0;
                }
                List<FiducialResult> fiducialResults = result.getFiducialResults();
                for (FiducialResult fr : fiducialResults) {
                    limeutils.judgeAndUpdateApril(fr);
                    avgRedState = limeutils.inferHiveState(limeutils.redHiveHeights);
                    avgBlueState = limeutils.inferHiveState(limeutils.blueHiveHeights);
                }
                telemetry.addData("Avg Red State", avgRedState);
                telemetry.addData("Avg Blue State", avgBlueState);
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            if (gamepad1.ps || limeutils.cachedNeedForLocalization) {
                //result = limeutils.runLocalization(0);
                if (result.isValid()) {
                    Pose3D pose3D = result.getBotpose();
                    telemetry.addData("Pose3D", pose3D);
                }
            }
            telemetry.update();
        }

        limeutils.stop();
    }
}
