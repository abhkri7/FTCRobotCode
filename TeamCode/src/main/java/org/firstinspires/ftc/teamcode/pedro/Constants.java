package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("drive_lf");
        c.frontRightName.set("drive_rf");
        c.backLeftName.set("drive_lb");
        c.backRightName.set("drive_rb");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-1.3497396153727854);
        c.yPodOffset.set(-6.836201825479823);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.20751028030932003);
                Controller secondaryTranslationalForward = Controller.proportional(0.07666950379748427);
                Controller primaryTranslationalLateral = Controller.proportional(0.2826338510369403);
                Controller secondaryTranslationalLateral = Controller.proportional(0.10442565584256044);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01918304570828523));
                c.brake.set(Controller.proportionalFeedforward(0.016305588852042443));

                c.headingFeedback.set(Controller.proportional(3.60472611746003));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04033728688931572, 0.00954286093482087));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05072021992839783, 0.04073886848209054));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0021945047145033566, 0.0023700950689758385));

                c.maxAchievableForwardVelocity.set(56.76547081629644);
                c.maxAchievableStrafeVelocity.set(45.133724194212206);
                c.naturalForwardDeceleration.set(46.043008703529885);
                c.naturalStrafeDeceleration.set(72.426163622343);
            }
    );
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}