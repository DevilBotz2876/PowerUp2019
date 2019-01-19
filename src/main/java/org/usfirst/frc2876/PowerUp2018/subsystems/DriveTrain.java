// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2876.PowerUp2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc2876.PowerUp2018.Robot;
import org.usfirst.frc2876.PowerUp2018.RobotMap;
import org.usfirst.frc2876.PowerUp2018.commands.XboxDrive;
import org.usfirst.frc2876.PowerUp2018.utilities.BHS_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
	// PRACTICE ROBOT SETTING
	// private final double MAX_RPM = 2800.0f;

	private final double MAX_RPM = 3380.0f;
	private final double kTurnToleranceDegrees = 1.0f;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final BHS_TalonSRX rightTalonSRX1 = RobotMap.driveTrainRightTalonSRX1;
	private final BHS_TalonSRX rightTalonSRX2 = RobotMap.driveTrainRightTalonSRX2;
	private final SpeedControllerGroup speedControllerGroupRight = RobotMap.driveTrainSpeedControllerGroupRight;
	private final BHS_TalonSRX leftTalonSRX3 = RobotMap.driveTrainLeftTalonSRX3;
	private final BHS_TalonSRX leftTalonSRX4 = RobotMap.driveTrainLeftTalonSRX4;
	private final SpeedControllerGroup speedControllerGroupLeft = RobotMap.driveTrainSpeedControllerGroupLeft;
	private final DifferentialDrive differentialDrive = RobotMap.driveTrainDifferentialDrive;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	private final BHS_TalonSRX rightMaster = RobotMap.rightMaster;
	private final BHS_TalonSRX leftMaster = RobotMap.leftMaster;
	private final BHS_TalonSRX rightFollower = RobotMap.rightFollower;
	private final BHS_TalonSRX leftFollower = RobotMap.leftFollower;

	private Ultrasonic usIntakeSensor = RobotMap.usIntakeSensor;
	private Ultrasonic usElevatorSensor = RobotMap.usElevatorSensor;

	public PIDController straightController;
	public PIDController distanceController;
	public PIDController turnController;
	public AHRS navx;
	public CameraServer server;
	public boolean sensitiveDriveOn = false;

	private int distanceOnTargets;
	private int turnOnTargets;

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		setDefaultCommand(new XboxDrive());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public DriveTrain() {

		navx = new AHRS(SPI.Port.kMXP);

		// Using velocity mode means we don't use diffDrive to set talons, which
		// means we don't pat the safey watchdog.  So turn it off.
		differentialDrive.setSafetyEnabled(false);

		rightMaster.setMode(ControlMode.Velocity);
		leftMaster.setMode(ControlMode.Velocity);

//		rightMaster.setMode(ControlMode.PercentOutput);
//		leftMaster.setMode(ControlMode.PercentOutput);
		
		rightMaster.setNeutralMode(NeutralMode.Coast);
		rightFollower.setNeutralMode(NeutralMode.Coast);
		leftMaster.setNeutralMode(NeutralMode.Coast);
		leftFollower.setNeutralMode(NeutralMode.Coast);

		rightMaster.configOpenloopRamp(0, 0);
		rightFollower.configOpenloopRamp(0, 0);
		leftMaster.configOpenloopRamp(0, 0);
		leftFollower.configOpenloopRamp(0, 0);

		rightMaster.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		leftMaster.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);

		leftMaster.setSensorPhase(false);
		rightMaster.setSensorPhase(true);

		// rightMaster.setInverted(true);
		
		leftMaster.setInverted(true);
		leftFollower.setInverted(true);
		
//		leftMaster.setInverted(false);
//		leftFollower.setInverted(false);
		
		//PRACTICE BOT SETTINGS
		// leftMaster.setInverted(true);
		// leftFollower.setInverted(true);
		// leftMaster.config_kP(0, 0.3729, 0);
		// leftMaster.config_kF(0, 0.3761, 0);
		// rightMaster.config_kP(0, 0.3639, 0);
		// rightMaster.config_kF(0, 0.3503, 0);
		
		leftMaster.config_kP(0, .2, 0);
		leftMaster.config_kF(0, .3138, 0);
		rightMaster.config_kP(0, .2, 0);
		rightMaster.config_kF(0, .3027, 0);

		// leftMaster.config_kP(0, 1.25, 0);
		// leftMaster.config_kF(0, 0.3468, 0);
		// rightMaster.config_kP(0, 1, 0);
		// rightMaster.config_kF(0, 0.3789, 0);

		
		straightController = new PIDController(.04, 0, 0, navx, new PIDOutput() {
			public void pidWrite(double output) {
				SmartDashboard.putNumber("StraightPid Output", output);
				// Don't output any values to the talons to make robot move
				// here. Instead use the outputs in other places so we can
				// combine multiple PID controllers.
			}
		});
		straightController.setOutputRange(-MAX_RPM, MAX_RPM);

		distanceController = new PIDController(.02, 0, .02, 0, new AvgEncoder(), new PIDOutput() {
			public void pidWrite(double output) {
				SmartDashboard.putNumber("DistancePid Output", output);
				// leftMaster.set(-output);
				// rightMaster.set(-output);
				// tankDrive(output, output);
			}
		});

		// distanceController.setAbsoluteTolerance(Robot.oi.getkDistanceTolerance());
		distanceController.setAbsoluteTolerance(2);
		distanceController.setOutputRange(-MAX_RPM, MAX_RPM);

		turnController = new PIDController(.006, 0, 0, 0, navx, new PIDOutput() {
			public void pidWrite(double output) {
				output = output * MAX_RPM;
				SmartDashboard.putNumber("TurnPid Output", output);
				 double minMove = 700.0f;
				 output = minRpm(output, minMove);
				 SmartDashboard.putNumber("TurnPid min Output", output);

				 // double min = 0.25;
				// output = minRpm(output, min);
				// SmartDashboard.putNumber("TurnPid min Output", output);
				leftMaster.set(output);
				rightMaster.set(output);
				//tankDrive(output, -output);

			}
		});

		// turnController.setOutputRange(-0.5, 0.5);
		//turnController.setOutputRange(-MAX_RPM, MAX_RPM);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setAbsoluteTolerance(kTurnToleranceDegrees);
		turnController.setContinuous(true);

	}

	public void setBrakeMode(boolean brake) {
		if (brake) {
			rightMaster.setNeutralMode(NeutralMode.Brake);
			rightFollower.setNeutralMode(NeutralMode.Brake);
			leftMaster.setNeutralMode(NeutralMode.Brake);
			leftFollower.setNeutralMode(NeutralMode.Brake);
		} else {
			rightMaster.setNeutralMode(NeutralMode.Coast);
			rightFollower.setNeutralMode(NeutralMode.Coast);
			leftMaster.setNeutralMode(NeutralMode.Coast);
			leftFollower.setNeutralMode(NeutralMode.Coast);
		}
	}

	public double getLeftVelocityValue() {
		return leftMaster.getSelectedSensorVelocity(0);
	}

	public double getRightVelocityValue() {
		return rightMaster.getSelectedSensorVelocity(0);
	}

	private double adjustSpeed(double speed) {
		// Adjust sensitivity of joysticks. When the joystick is barely
		// pressed/moved send small output to motors. When joystick
		// is press/moved alot send BIG output to motors.
		// y=a(x^3)+(1-a)x
		double a = .2;
		return (a * (speed * speed * speed)) + ((1 - a) * speed);
	}

	private double adjustRotate(double rotate) {
		// rotate *= .7;
	
		return adjustSpeed(rotate);
	}

	// Don't let drivers move robot too fast when CG is high. This will need
	// tuning.
	public double adjustVelocityElevator(double speed) {
		if (Robot.elevator.getCurrentPosition() > RobotMap.ELEVATOR_POSITION_SWITCH_CUBE) {
			if (speed > 0) {
				return MAX_RPM / 2;
			} else {
				return -MAX_RPM / 2;
			}
		}
		return speed;
	}

	// Don't let drivers move robot too fast when CG is high. This will need
	// tuning.
	public double adjustJoystickElevator(double speed) {
		boolean isHigh = Robot.elevator.getCurrentPosition() < RobotMap.ELEVATOR_POSITION_SLOW_DRIVE;
		if (isHigh) {
			if (speed > RobotMap.SENSITIVE_HIGH_DRIVE_SPEED) {
				return RobotMap.SENSITIVE_HIGH_DRIVE_SPEED;
			} else if (speed < -RobotMap.SENSITIVE_HIGH_DRIVE_SPEED) {
				return -RobotMap.SENSITIVE_HIGH_DRIVE_SPEED;
			}
		}
		if (sensitiveDriveOn) {
			if (speed > RobotMap.SENSITIVE_DRIVE_SPEED) {
				return RobotMap.SENSITIVE_DRIVE_SPEED;
			} else if (speed < -RobotMap.SENSITIVE_DRIVE_SPEED) {
				return -RobotMap.SENSITIVE_DRIVE_SPEED;
			}
		}
		return speed;
	}

	public boolean isPIDRunning() {
		return distanceController.isEnabled() || turnController.isEnabled();
	}

	public boolean isDistancePIDRunning() {
		return distanceController.isEnabled();

	}

	public boolean isTurnPIDRunning() {
		return turnController.isEnabled();
	}

	public void arcadeDrive(double xSpeed, double zRotation) {
		differentialDrive.arcadeDrive(xSpeed, zRotation);
		// differentialDrive.arcadeDrive(adjustSpeed(xSpeed), zRotation);
		// differentialDrive.arcadeDrive(adjustSpeed(xSpeed),
		// adjustRotate(zRotation));
		// differentialDrive.curvatureDrive(xSpeed, zRotation, false);
	}

	public void setVelocityArcadeJoysticks(double speed, double rotate) {
//		rotate = adjustJoystickElevator(rotate);
//		speed = adjustJoystickElevator(speed);
		speed = adjustSpeed(speed);
		rotate = adjustRotate(rotate);
		if (speed > 0.0) {
			if (rotate > 0.0) {
				leftMaster.set(ControlMode.Velocity, (speed - rotate) * MAX_RPM);
				rightMaster.set(ControlMode.Velocity, Math.max(speed, rotate) * MAX_RPM);
			} else {
				leftMaster.set(ControlMode.Velocity, Math.max(speed, -rotate) * MAX_RPM);
				rightMaster.set(ControlMode.Velocity, (speed + rotate) * MAX_RPM);
			}
		} else {
			if (rotate > 0.0) {
				leftMaster.set(ControlMode.Velocity, -Math.max(-speed, rotate) * MAX_RPM);
				rightMaster.set(ControlMode.Velocity, (speed + rotate) * MAX_RPM);
			} else {
				leftMaster.set(ControlMode.Velocity, (speed - rotate) * MAX_RPM);
				rightMaster.set(ControlMode.Velocity, -Math.max(-speed, -rotate) * MAX_RPM);
			}
		}
	}

	public void setStraightVelocityArcadeJoysticks(double speed) {
		// double straight = straightController.get();
		// if (speed > 0.0) {
		// leftMaster.set(ControlMode.Velocity, -Math.max(-straight, speed) *
		// MAX_RPM);
		// rightMaster.set(ControlMode.Velocity, (straight + speed) * MAX_RPM);
		// } else {
		// leftMaster.set(ControlMode.Velocity, (straight - speed) * MAX_RPM);
		// rightMaster.set(ControlMode.Velocity, -Math.max(-straight, -speed) *
		// MAX_RPM);
		// }

		double straight = straightController.get();
		double left;
		double right;

		if (straight > 0.0) {
			// turn right
			left = speed;
			right = speed + straight;
		} else {
			// turn left
			left = speed - straight;
			right = speed;
		}
		leftMaster.set(-left * MAX_RPM);
		rightMaster.set(right * MAX_RPM);
		SmartDashboard.putNumber("Left Side", -left);
		SmartDashboard.putNumber("Right Side", -right);
		System.out.println(straight);
	}

	public void setVelocityStraightDistance() {
		double straight = straightController.get();
		double distance = distanceController.get();
		double left;
		double right;

		if (straight > 0.0) {
			// turn right
			// tankDrive(straight, 0);
			left = distance;
			right = distance - straight;
		} else {
			// turn left
			// tankDrive(0, -straight);
			left = distance + straight;
			right = distance;
		}
		left = left * MAX_RPM;
		right = -right * MAX_RPM;
		
		left = minRpm(left, 500);
		right = minRpm(right, 500);
		
		leftMaster.set(left);
		rightMaster.set(right);
		SmartDashboard.putNumber("Left Side", left);
		SmartDashboard.putNumber("Right Side", right);
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		differentialDrive.tankDrive(leftSpeed, rightSpeed);
	}

	public void initializeCamera() {
		server = CameraServer.getInstance();
		// server.setQuality(50);
		UsbCamera serverUsb = server.startAutomaticCapture("cam0", 0);
		serverUsb.setFPS(15);
		serverUsb.setResolution(160, 120);

	}
	
	public boolean navxIsMoving(){
		return navx.isMoving();
	}

	public void updateSmartDashboard() {
		SmartDashboard.putData(this);

		SmartDashboard.putData("NavX", navx);
		SmartDashboard.putNumber("navX angle", navx.getAngle());
//		SmartDashboard.putBoolean("is navx connecticut?", navx.isConnected());
//		SmartDashboard.putData("Differential Drive Data", differentialDrive);
		SmartDashboard.putBoolean("is navX moving", navx.isMoving());
//		SmartDashboard.putBoolean("is navX rotating", navx.isRotating());
		
		SmartDashboard.putBoolean("DriveTrain isSensitive", sensitiveDriveOn);

		SmartDashboard.putNumber("Right Velocity", rightMaster.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Left Velocity", leftMaster.getSelectedSensorVelocity(0));

//		SmartDashboard.putData("DistancePID", distanceController);
//		SmartDashboard.putNumber("DistancePID Error", distanceController.getError());

//		SmartDashboard.putData("TurnPID", turnController);
//		SmartDashboard.putNumber("TurnPID Error", turnController.getError());

//		SmartDashboard.putData("StraightPID", straightController);
//		SmartDashboard.putNumber("StraightPID Error", straightController.getError());
//		SmartDashboard.putBoolean("isStraightPIDRunning", isStraightRunning());

		getDistance();

	}

	private double minRpm(double inputRpm, double minRpm) {
		double outputRpm = inputRpm;
		if (Math.abs(inputRpm) < minRpm) {
			if (outputRpm < 0) {
				outputRpm = -minRpm;
			} else if (outputRpm > 0) {
				outputRpm = minRpm;
			}
		}
		return outputRpm;
	}

	private double minOutput(double inputOutput, double minOutput) {
		double output = inputOutput;
		if (Math.abs(inputOutput) < minOutput) {
			if (output < 0) {
				output = -minOutput;
			} else if (output > 0) {
				output = minOutput;
			}
		}
		return output;
	}

	public void velocityDistance() {
		double distance = distanceController.get();
		tankDrive(distance, distance);
	}

	public void startDistance(double distance) {
		// distanceController.setAbsoluteTolerance(Robot.oi.getkDistanceTolerance());
		distanceOnTargets = 0;

		distanceController.reset();
		resetEncoders();
		distanceController.setSetpoint(distance);
		distanceController.enable();
	}

	public boolean isDistanceRunning() {
		return distanceController.isEnabled();
	}

	public boolean isDistanceDone() {
		if (distanceController.onTarget()) {
			distanceOnTargets++;
		}
		return (distanceOnTargets > 10);
	}

	public void stopDistance() {
		distanceController.reset();
		leftMaster.set(0);
		rightMaster.set(0);
	}

	public PIDController getDistancePID() {
		return distanceController;
	}

	// _______________________________________________________________________________________________________

	public PIDController getStraightPID() {
		return straightController;
	}

	public boolean isStraightRunning() {
		return straightController.isEnabled();
	}

	public void startStraight() {
		// Don't need to reset navx angle to zero here. To drive straight we
		// just need to pick whatever angle the robot is at when we start the
		// PID controller.
		// stopTurn();
		straightController.reset();
		navx.reset();
		// straightController.setSetpoint(navx.getAngle());
		straightController.enable();
	}

	public void setStraightAngle() {
		straightController.setSetpoint(navx.getAngle());
	}

	public void stopStraight() {
		straightController.reset();
		straightController.disable();
		// leftMaster.set(0);
		// rightMaster.set(0);

	}

	// _______________________________________________________________________________________________________

	public boolean isTurnRunning() {
		return turnController.isEnabled();
	}

	public boolean isTurnDone() {
		if (turnController.onTarget()) {
			turnOnTargets++;
		}
		return (turnOnTargets > 0);
	}

	public void startTurn(double turn) {
		turnOnTargets = 0;
		turnController.reset();
		navx.reset();
		resetEncoders();
		turnController.setSetpoint(turn);
		turnController.enable();
	}

	public void stopTurn() {
		turnController.reset();
		leftMaster.set(0);
		rightMaster.set(0);
	}

	public PIDController getTurnPID() {
		return turnController;
	}

	private static final double WHEEL_DIAMETER = 6.50;
	private static final double PULSES_PER_REV = 4096;

	public double nativeToInches(double nativeUnits) {
		double circumference = Math.PI * WHEEL_DIAMETER;
		return (nativeUnits / PULSES_PER_REV) * circumference;
	}

	public void resetEncoders() {
		leftMaster.setSelectedSensorPosition(leftMaster.getDeviceID(), 0, 0);
		rightMaster.setSelectedSensorPosition(rightMaster.getDeviceID(), 0, 0);
	}

	public double getDistance() {
		double l = nativeToInches(leftMaster.getSelectedSensorPosition(0));
		double r = nativeToInches(rightMaster.getSelectedSensorPosition(0));
		double av = (-r + l) / 2; // Right side encoder is negative going
									// forward
		SmartDashboard.putNumber("Left Distance", l);
		SmartDashboard.putNumber("Right Distance", r);
		SmartDashboard.putNumber("Average Distance", av);
		return av;
	}

	private class AvgEncoder implements PIDSource {

		public double pidGet() {
			return getDistance();
		}

		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub

		}

	}

}