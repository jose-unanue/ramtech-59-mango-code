package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkMax smallRightShooterMotor;
    private final SparkMax bigLeftShooterMotor;
    private final SparkMax bigRightShooterMotor;
    
    public ShooterSubsystem() {
        smallRightShooterMotor = new SparkMax(Constants.SMALL_RIGHT_SHOOTER_MOTOR, MotorType.kBrushless);
        bigLeftShooterMotor = new SparkMax(Constants.BIG_LEFT_SHOOTER_MOTOR, MotorType.kBrushless);
        bigRightShooterMotor = new SparkMax(Constants.BIG_RIGHT_SHOOTER_MOTOR, MotorType.kBrushless);
        
    }

    public Command SpinUp(double targetRPM) {
        return run(() -> {
            smallRightShooterMotor.set(1.0);
            bigLeftShooterMotor.set(1.0);
            bigRightShooterMotor.set(-1.0);
        })
        .until(() -> hitTargetRPM(targetRPM))
        .withTimeout(2.0);
    }

    public Command RetreatShooter(double power) {
        return run(() -> {
            smallRightShooterMotor.set(-power);
            bigLeftShooterMotor.set(-power);
            bigRightShooterMotor.set(power);
        }).finallyDo(interrupted -> {
            smallRightShooterMotor.set(0);
            bigLeftShooterMotor.set(0);
            bigRightShooterMotor.set(0);
        });
    }

    private Boolean hitTargetRPM(double targetRPM) {
        return (Math.abs(smallRightShooterMotor.getEncoder().getVelocity() - targetRPM) < 100) && (Math.abs(bigLeftShooterMotor.getEncoder().getVelocity() - targetRPM) < 100) && (Math.abs(bigRightShooterMotor.getEncoder().getVelocity() - targetRPM) < 100);
    }
}
