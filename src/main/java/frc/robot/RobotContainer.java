package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.*;
import swervelib.SwerveInputStream;
import java.io.File;

public class RobotContainer {

  private final CommandXboxController driverXbox = new CommandXboxController(0);
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
  private final ElevatorSubsystem elevator = new ElevatorSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();

  // Standard driving control: left stick = movement, right stick X = turn
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
      () -> driverXbox.getLeftY() * -1,     // forward/back
      () -> driverXbox.getLeftX() * -1)     // strafe
      .withControllerRotationAxis(driverXbox::getRightX)  // turn
      .deadband(OperatorConstants.DEADBAND)
      .scaleTranslation(0.8)
      .allianceRelativeControl(true);  // field-oriented
  
  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(
                                                            driverXbox::getRightX,
                                                            driverXbox::getRightY)
                                                            .headingWhile(true);

  Command driveFieldOrientedDirectAngle = drivebase.driveFieldOriented(driveDirectAngle);

  Command driveFieldOrientedAngularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);

  public RobotContainer() {
    configureBindings();

    drivebase.centerModulesCommand();

    // Set the standard drive command as default
    drivebase.setDefaultCommand(driveFieldOrientedDirectAngle);

  }

  private void configureBindings() {

    // Shoot with Right Trigger, Retreat Shooter with Left Trigger
    driverXbox.a().whileTrue(new SequentialCommandGroup(
      shooter.SpinUp(2800),
      elevator.RaiseElevator(1.0)
      ));
    driverXbox.b().whileTrue(new SequentialCommandGroup(
      shooter.RetreatShooter(0.2),
      elevator.LowerElevator(0.2)
      ));
    driverXbox.x().whileTrue(intake.Intake(0));
    driverXbox.y().whileTrue(intake.Outtake(0));

    // Zero gyro with A button
    driverXbox.rightBumper().onTrue(Commands.runOnce(drivebase::zeroGyro));

    // Lock wheels with left bumper
    driverXbox.leftBumper().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());

    // Add more bindings here as needed
  }

  public Command getAutonomousCommand() {
    return null;
  }

  public void setMotorBrake(boolean brake) {
    drivebase.setMotorBrake(brake);
  }
}
