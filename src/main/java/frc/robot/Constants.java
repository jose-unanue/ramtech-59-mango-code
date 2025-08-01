package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

public final class Constants {

    public static final double ROBOT_MASS = 105 * 0.453592;
    public static final Matter CHASSIS = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
    public static final double LOOP_TIME = 0.13;
    public static final double MAX_SPEED = Units.feetToMeters(14.5);

    public static final class DrivebaseConstants {
        public static final double WHEEL_LOCK_TIME = 10;
    }

    public static class OperatorConstants {
        public static final double DEADBAND = 0.1;
        public static final double LEFT_Y_DEADBAND = 0.1;
        public static final double RIGHT_X_DEADBAND = 0.1;
        public static final double TURN_CONSTANT = 6;
    }

    // Ball Intake IDs
    public static final int PNEUMATIC_HUB = 2;
    public static final int SOLENOID = 2;
    public static final int PRESSURE_SENSOR = 0;
    public static final int INNER_INTAKE_MOTOR = 24;
    public static final int OUTER_INTAKE_MOTOR = 23;

    // Elevator IDs
    public static final int LEFT_ELEVATOR_MOTOR = 9;
    public static final int RIGHT_ELEVATOR_MOTOR = 8;

    // Ball Shooter IDs
    public static final int SMALL_RIGHT_SHOOTER_MOTOR = 43;
    public static final int BIG_LEFT_SHOOTER_MOTOR = 50;
    public static final int BIG_RIGHT_SHOOTER_MOTOR = 51;

}
