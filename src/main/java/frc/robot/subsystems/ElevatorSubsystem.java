package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {

    private final SparkMax leftElevatorMotor;
    private final SparkMax rightElevatorMotor;
    
    public ElevatorSubsystem() {
        leftElevatorMotor = new SparkMax(Constants.LEFT_ELEVATOR_MOTOR, MotorType.kBrushless);
        rightElevatorMotor = new SparkMax(Constants.RIGHT_ELEVATOR_MOTOR, MotorType.kBrushless);
        
    }

    public Command RaiseElevator(double power) {
        return run(() -> {
            leftElevatorMotor.set(power);
            rightElevatorMotor.set(-power);
        }).finallyDo(interrupted -> {
            leftElevatorMotor.set(0);
            rightElevatorMotor.set(0);
        });
    }

    public Command LowerElevator(double power) {
        return run(() -> {
            leftElevatorMotor.set(-power);
            rightElevatorMotor.set(power);
        }).finallyDo(interrupted -> {
            leftElevatorMotor.set(0);
            rightElevatorMotor.set(0);
        });
    }

}
