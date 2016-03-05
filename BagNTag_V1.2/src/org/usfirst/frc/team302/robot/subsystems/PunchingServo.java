package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.commands.CommandFactory;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @version version 2: 3/3/2016 -- Joe Witcpalek Add Review comments
 * 
 */

/*
 * Review Comments:
 * 1) Add JavaDoc
 * 2) Add comment next to each enum value
 * 3) Don't take default visibility on attributes specify public, protected or private explicitly
 */

public class PunchingServo extends Subsystem
{
    // Declares servo m_servo
    Servo m_servo;
    
    // Creates the enum "state" to keep track of the punching servo's state
    enum PunchState
    {
        PUNCHING, 
        RESETTING, 
        WAITING
    }
    PunchState m_state;
    
    // The angle the servo aims to be set at being declared as a double
    
    // Sets initial state to waiting and assigns the puncher servo to m_servo
    public PunchingServo()
    {
        m_state = PunchState.WAITING;
        m_servo = new Servo(RobotMap.SHOOTER_PUNCHER_SERVO);
    }

    public void initDefaultCommand()
    {
        setDefaultCommand(CommandFactory.getCommandFactory().getCommand("punchreset"));
    }
    
    // Sets state to punching and the servo angle to the goal angle, and if the punch is complete, starts resetting
    public void punch()
    {
        if(m_state != PunchState.RESETTING)
        {
            m_servo.setAngle(RobotMap.PUNCH_ANGLE);
            SmartDashboard.putNumber("punching output angle", m_servo.getAngle());
        }
        SmartDashboard.putString("punching state", m_state + "");
    }
    
    // Sets the servo state to resetting and angle back to the initial angle
    public void reset()
    {
        m_state = PunchState.RESETTING;
        m_servo.setAngle(RobotMap.PUNCH_RESET_ANGLE);
        SmartDashboard.putNumber("reset output angle", m_servo.getAngle());
        SmartDashboard.putString("punching state", m_state + "");
    }
    
    // Analyzes if the punch is complete, returns a boolean for if the punch is complete or not, and if it is, sets the puncher's state to waiting
    public boolean punchcomplete()
    {
        SmartDashboard.putNumber("punching completion", m_servo.getAngle());
        boolean iscomplete = false;
        double angle = m_servo.getAngle();
        if (angle >= RobotMap.PUNCH_ANGLE)
        {
            iscomplete = true;
            m_state = PunchState.PUNCHING;
        }
        SmartDashboard.putString("punching state", m_state + "");
        return iscomplete;
    }
    
    //Analyzed if the reset is complete, returns boolean indicating so
    public boolean resetcomplete()
    {
        SmartDashboard.putNumber("reset completion", m_servo.getAngle());
        boolean iscomplete = false;
        double angle = m_servo.getAngle();
        if (angle <= RobotMap.PUNCH_RESET_ANGLE)
        {
            iscomplete = true;
            m_state = PunchState.WAITING;
        }
        SmartDashboard.putString("punching state", m_state + "");
        return iscomplete;
    }
}
