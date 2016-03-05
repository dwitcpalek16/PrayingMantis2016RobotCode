package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.subsystems.Shooter;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will reverse the shooter motors to intake boulders.
 * 
 * 
 * @version version 1: 2/1/2016 -- Zach Zweber -- initial command
 * @version version 2: 2/7/2016 -- Joe Witcpalek -- pulled into the command pattern
 * @version version 3: 2/13/2016 -- Klayton Smith -- add sensor for stopping intake
 * @version version 4: 2/18/2016 -- Joe Witcpalek - merge (Zach/Adam had changes that didn't have Klayton's change)
 * 
 * @author Zach Zweber
 */
public class IntakeBoulder extends Command
{

    private Shooter m_shooter; // The shooter subsystem
    private DigitalInput m_sensor;

    /**
     * Construct this command indicating that the shooter subsystem is used by this command.
     */
    public IntakeBoulder()
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_shooter = SubsystemFactory.getSubsystemFactory().getShooter();
        requires(m_shooter);
        m_sensor = new DigitalInput(RobotMap.SHOOTER_INTAKE_SENSOR);

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
        m_shooter.intakeBoulder();
    }

    /**
     * This returns true when the Command no longer needs to run execute() and false when the command needs to keep running.
     */
    protected boolean isFinished()
    {
        boolean isFinished = false;
        if ((m_shooter.getCurrentState() == Shooter.states.INTAKE) && (m_sensor.get()))
        {
            return true; // stop if the sensor is tripped
        }
        return isFinished;
    }

    /**
     * This is called once after isFinished returns true. It will stop the shooter motors.
     */
    protected void end()
    {
        m_shooter.stopMotors();  // stop the motors
    }

    /**
     * Called when another command which requires one or more of the same subsystems is scheduled to run
     */
    protected void interrupted()
    {

    }
}
