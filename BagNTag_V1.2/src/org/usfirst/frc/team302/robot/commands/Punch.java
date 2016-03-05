package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.PunchingServo;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will activate the servo motor to punch the boulder into the shooter wheels.
 * 
 * @version version 1: 2/13/2016 -- Eldon Maffey -- Initial command
 * @version version 2: 2/15/2016 -- Eldon Maffey -- Added punching state
 * 
 * @author Eldon Maffey
 */

public class Punch extends Command
{
    // Gets servo from PunchingServo subsystem
    private PunchingServo m_servo;
    
    public Punch()
    {
        m_servo = SubsystemFactory.getSubsystemFactory().getPunchingServo();
        requires(m_servo);
    }

    protected void initialize()
    {
        
    }

    protected void execute()
    {
        m_servo.punch();
    }

    protected boolean isFinished()
    {
        return m_servo.punchcomplete();
    }

    protected void end()
    {
    
    }

    protected void interrupted()
    {
        
    }

}
