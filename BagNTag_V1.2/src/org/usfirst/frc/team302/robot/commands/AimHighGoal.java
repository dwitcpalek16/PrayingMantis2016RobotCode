
package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.AimShooter;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;
/**
 * This class will move the shooter to high goal position.
 *
 *
 * @version version 1: 2/6/2016 -- Zach and Adam -- initial command
 *
 * @author Zach Zweber and Adam Armstrong
 */
public class AimHighGoal extends Command
{

    private AimShooter m_aimShooter; // The shooter subsystem

    public AimHighGoal()
    {
        // Use requires() here to declare subsystem dependencies
        m_aimShooter = SubsystemFactory.getSubsystemFactory().getAimShooter();
        requires(m_aimShooter);
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
        m_aimShooter.setState(AimShooter.AimState.HIGHGOAL);
    }

    /**
     * This returns true when the Command no longer needs to run execute() and false when the command needs to keep running.
     */
    protected boolean isFinished()
    {
        return false;
    }

    /**
     * This is called once after isFinished returns true. It will stop the aim motor.
     */
    public void end()
    {
        m_aimShooter.stopMotors();
    }

    /**
     * Called when another command which requires one or more of the same subsystems is scheduled to run
     */
    protected void interrupted()
    {
    }
}
