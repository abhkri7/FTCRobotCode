package org.firstinspires.ftc.teamcode.backend;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;

// A convenience class for event scheduling and succinct path-forming
public class MetaPose extends Pose {

    // Pose Types
    MetaPoseType poseType;
    PoseFactory poseFactory;

    // An enum that signifies the type of MetaPose
    public enum MetaPoseType {
        // Pose Types
        ACTION_POINT(1),
        PASS_POINT(2),
        PULL_POINT(3);

        // Pose-Type Value Correlation
        final int value;
        private MetaPoseType(int type)
        {
            this.value = type;
        }
    }

    // MetaPose Constructors
    public MetaPose(MetaPoseType type, PoseFactory factory, double cx, double cy, double cheading) {
        this(factory.of(cx, cy, cheading));
        this.poseFactory = factory;
        this.poseType = type;
    }
    private MetaPose(Pose pose) {
        // This private constructor allows running the PoseFactory function before the super
        super(pose.x(),pose.y(), pose.heading());
    }

    public MetaPose(MetaPose metaPose){
        super(metaPose.x(), metaPose.y(), metaPose.heading());
        this.poseFactory = metaPose.poseFactory;
        this.poseType = metaPose.poseType;
    }

    // Overrided Functions (so they return a MetaPose too)
    @Override
    public MetaPose withHeading(double heading){
        return new MetaPose(poseType, poseFactory, x(), y(), heading);
    }
    @Override
    public MetaPose withX(double x){
        return new MetaPose(poseType, poseFactory, x, y(), heading());
    }
    @Override
    public MetaPose withY(double y){
        return new MetaPose(poseType, poseFactory, x(), y, heading());
    }

    // Corresponding Function to allow type changes
    public MetaPose withType(MetaPoseType type){
        return new MetaPose(type, poseFactory, x(), y(), heading());
    }
}