package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.PunchingServo;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will reset the punching servo position.
 * 
 * @version version 1: 2/15/2016 -- Eldon Maffey -- Initial command
 * @version version 2: 2/20/2016 -- Eldon Maffey -- Added all functionality
 * 
 * @author Eldon Maffey
 */

public class PunchReset extends Command
{

    private PunchingServo m_servo;
    
    public PunchReset()
    {
        m_servo = SubsystemFactory.getSubsystemFactory().getPunchingServo();
        requires(m_servo);
    }
    
    protected void initialize()
    {
        
    }

    protected void execute()
    {
        m_servo.reset();
    }

    protected boolean isFinished()
    {
        return m_servo.resetcomplete();
    }

    protected void end()
    {
        
    }

    protected void interrupted()
    {
        
    }

}
