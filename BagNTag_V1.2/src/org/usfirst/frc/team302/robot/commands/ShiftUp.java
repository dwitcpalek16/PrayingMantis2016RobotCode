
package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.Shift;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftUp extends Command
{

    Shift m_shift;

    public ShiftUp()
    {
        m_shift = SubsystemFactory.getSubsystemFactory().getShifter();
        requires(m_shift);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {

        m_shift.shift(true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return SubsystemFactory.getSubsystemFactory().getShifter().isHighGear();
    }

    // Called once after isFinished returns true
    protected void end()
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    }
}
