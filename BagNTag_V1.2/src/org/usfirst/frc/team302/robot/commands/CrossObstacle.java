
package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.Drive.obstacleDriveState;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrossObstacle extends Command
{

    org.usfirst.frc.team302.robot.subsystems.Drive m_Drive;

    public CrossObstacle()
    {
        m_Drive = SubsystemFactory.getSubsystemFactory().getDrive();
        requires(m_Drive);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        //m_Drive.driveOverObstacle(0.8);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        if (m_Drive.obstacleState == obstacleDriveState.DONE)
        {
            return true;
        }
        else
            return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        m_Drive.obstacleState = obstacleDriveState.STOPPED;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    }
}
