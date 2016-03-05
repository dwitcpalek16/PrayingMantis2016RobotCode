package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.OI;
import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.subsystems.AimShooter;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ManualAimShooter extends Command
{
	private AimShooter m_mAimShooter;
	
	public ManualAimShooter()
	{
		m_mAimShooter = SubsystemFactory.getSubsystemFactory().getAimShooter();
		requires(m_mAimShooter);
	}

	protected void initialize()
	{
		
	}

	protected void execute()
	{
		m_mAimShooter.manualMoveMotor(OI.getOI().getDriverJoystick().getPOV());
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void end()
	{

	}

	protected void interrupted()
	{

	}

}
