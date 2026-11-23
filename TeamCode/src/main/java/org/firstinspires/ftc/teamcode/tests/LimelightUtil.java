package org.firstinspires.ftc.teamcode.tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.Arrays;
public class LimelightUtil {
    // Localization
    public Limelight3A limelight;
    public boolean cachedNeedForLocalization = false;
    public boolean[] redHiveHeights, blueHiveHeights;
    public void reportLimelightMetrics() {
        LLStatus status = limelight.getStatus();
        telemetry.addData("Name", "%s", status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());
    }
    public void resetHiveStates() {
        Arrays.fill(redHiveHeights, 0, 4, false);
        Arrays.fill(redHiveHeights, 4, 8, true);
        Arrays.fill(blueHiveHeights, 0, 4, false);
        Arrays.fill(blueHiveHeights, 4, 8, true);
    }
    public Pose3D runLocalization(int quadrant) {
        LLResult result = limelight.getLatestResult();
        cachedNeedForLocalization = !result.isValid();
        limelight.pipelineSwitch(quadrant);
        return result.getBotpose();
    }
    public void judgeAndUpdateApril(LLResultTypes.FiducialResult fr) {
        if (fr == null || fr.getRobotPoseTargetSpace() == null) return;
        Position relativePosition = fr.getRobotPoseTargetSpace().getPosition();
        if (relativePosition == null) return;

        int id = fr.getFiducialId();
        boolean state = relativePosition.z >= 74;

        if (id >= 30 && id <= 37) {
            redHiveHeights[id - 30] = state;
        } else if (id >= 38 && id <= 45) {
            blueHiveHeights[id - 38] = state;
        }
        telemetry.addData("Position!!!", " X: %.2f Y: %.2f", relativePosition.x, relativePosition.y);
    }
    public byte inferHiveState(boolean[] hiveHeights) {
        int hiveAvg = 0;
        for (int i = 0; i < 4; i++) {
            hiveAvg += hiveHeights[i] ? 3 : 1;
        }
        for (int i = 4; i < 8; i++) {
            hiveAvg += hiveHeights[i] ? 1 : 3;
        }
        return (byte) hiveAvg;
    }
    //public double reportHiveStateConfidence()
    public void stop(){
        limelight.stop();
    }
}
