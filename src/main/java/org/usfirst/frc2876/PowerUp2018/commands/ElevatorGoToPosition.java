// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2876.PowerUp2018.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2876.PowerUp2018.Robot;
import org.usfirst.frc2876.PowerUp2018.RobotMap;

/**
 *
 */
public class ElevatorGoToPosition extends Command {

	private double m_position;

	public ElevatorGoToPosition(double position) {
		m_position = position;
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		//double pos = SmartDashboard.getNumber(RobotMap.SD_KEY_ELEVATOR_POSITION_SETPOINT, m_position);
		Robot.elevator.setPosition(m_position);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
//		if(Robot.elevator.isBottom()){
//			Robot.elevator.setPosition(RobotMap.ELEVATOR_POSITION_SWITCH_CUBE);
//		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// TODO: this prob won't work b/c we want to get to setpoint but may be off
//		if (Robot.elevator.getCurrentPosition() == m_position) {
//			return true;
//		}
		return Robot.elevator.isOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}