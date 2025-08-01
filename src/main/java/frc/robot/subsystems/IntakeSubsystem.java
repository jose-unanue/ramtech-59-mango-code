package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax innerIntakeMotor;
    private final SparkMax outerIntakeMotor;
    private final Compressor compressor;
    private final Solenoid piston;

    public IntakeSubsystem() {
        innerIntakeMotor = new SparkMax(Constants.INNER_INTAKE_MOTOR, MotorType.kBrushless);
        outerIntakeMotor = new SparkMax(Constants.OUTER_INTAKE_MOTOR, MotorType.kBrushless);
        piston = new Solenoid(Constants.PNEUMATIC_HUB, PneumaticsModuleType.REVPH, Constants.SOLENOID);
        compressor = new Compressor(Constants.PNEUMATIC_HUB, PneumaticsModuleType.REVPH);
        compressor.enableAnalog(60, 120);
    }

    public Command Intake(double power) {
        return run(() -> {
            innerIntakeMotor.set(power);
            outerIntakeMotor.set(power);
            piston.set(true);
        }).finallyDo(interrupted -> {
            innerIntakeMotor.set(0);
            outerIntakeMotor.set(0);
            piston.set(false);
        });
    }

    public Command Outtake(double power) {
        return run(() -> {
            innerIntakeMotor.set(-power);
            outerIntakeMotor.set(-power);
            piston.set(true);
        }).finallyDo(interrupted -> {
            innerIntakeMotor.set(0);
            outerIntakeMotor.set(0);
            piston.set(false);
        });
    }
}
