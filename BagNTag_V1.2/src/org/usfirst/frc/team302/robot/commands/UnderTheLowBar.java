package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;
import org.usfirst.frc.team302.robot.subsystems.Drive.obstacleDriveState;
import edu.wpi.first.wpilibj.command.Command;

public class UnderTheLowBar extends Command
{
    org.usfirst.frc.team302.robot.subsystems.Drive m_Drive;
	
	public UnderTheLowBar()
	{
        m_Drive = SubsystemFactory.getSubsystemFactory().getDrive();
        requires(m_Drive);
	}
	
	protected void initialize()
	{
		
	}

	protected void execute()
	{
		m_Drive.lowBar(RobotMap.MINIMAL_SPEED);
	}

	protected boolean isFinished()
	{
		if (m_Drive.obstacleState == obstacleDriveState.DONE)
        {
            return true;
        }
        else
            return false;
	}

	protected void end()
	{
        m_Drive.obstacleState = obstacleDriveState.STOPPED;
	}

	protected void interrupted()
	{
		
	}
	
}
