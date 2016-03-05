package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.Shooter;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will reverse the shooter motors to intake boulders.
 * 
 * @version version 1: 2/1/2016 -- Zach Zweber -- initial command
 * @version version 2: 2/7/2016 -- Joe Witcpalek -- pulled into the command pattern
 * 
 * @author Zach Zweber
 */
public class StopShooter extends Command
{
    private Shooter m_shooter; // The shooter subsystem

    /**
     * Construct this command indicating that the shooter subsystem is used by this command.
     */
    public StopShooter()
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_shooter = SubsystemFactory.getSubsystemFactory().getShooter();
        requires(m_shooter);
    }

    /**
     * Called just before this Command runs the first time
     */
    protected void initialize()
    {
    }

    /**
     * Reverse the shooter motors to intake a boulder. This is called repeatedly when this is scheduled to run.
     */
    protected void execute()
    {
        m_shooter.stopMotors();
    }

    /**
     * This returns true when the Command no longer needs to run execute() and false when the command needs to keep running.
     */
    protected boolean isFinished()
    {
        return true;
    }

    /**
     * This is called once after isFinished returns true. It will stop the shooter motors.
     */
    protected void end()
    {
    }

    /**
     * Called when another command which requires one or more of the same subsystems is scheduled to run
     */
    protected void interrupted()
    {
    }
}
