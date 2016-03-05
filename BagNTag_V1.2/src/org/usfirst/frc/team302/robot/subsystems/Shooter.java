package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.commands.CommandFactory;
import org.usfirst.frc.team302.robot.utilities.PIDController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is for the shooter subsystem. The Shoooter consists of two Talon SRXs driving motors in the opposite direction at the same speed. The
 * motors will spin inward to intake a boulder and outward to shoot a boulder.
 * 
 * <b>Change Log:</b>
 * 
 * @version <b>version 1:</b> 1/16/2016 -- Derek Witcpalek -- original dummy class
 * @version <b>version 2:</b> 1/29/2016 -- Joe Witcpalek -- refactor package name
 * @version <b>version 3:</b> 1/29/2016 -- Eldon Maffey -- Add logic for shooting and stopping motors
 * @version <b>version 4:</b> 2/3/ 2016 -- Zach Zweber -- Add PID logic
 * @version <b>version 5:</b> 2/8/2016 -- Joe Witcpalek -- Merge in version 4
 * @version <b>version 6:</b> 2/13/2016 -- Klayton Smith -- Add sensor
 * @version <b>version 7:</b> 2/18/2016 -- Joe Witcpalek -- Merge (latest version from Zach/Adam didn't include version 6)
 * @version <b>version 8:</b> 2/19/2016 -- Zach "zWEBz" Zweber -- Initialized PIDController
 * @version <b>version 9:</b> 2/18/2016 -- Joe Witcpalek -- Merge again (Zach's version was missing the previous changes)
 * @version <b>version 10:</b> 3/3/2016 -- Joe Witcpalek -- Add review comments
 *  
 * @author Derek Witcpalek
 */
/*
 * Review Commetns
 * 1) Add descriptive Javadoc
 * 2) Remove commented code or if it is still needed a comment about what isn't working
 * 3) Use descriptive variable names makeInRange takes in a, b and c which has meaningless values
 */
public class Shooter extends Subsystem
{

    CANTalon m_talon1;
    CANTalon m_talon2;
    PIDController m_pid;

    public enum states
    {
        STOP,           // Doing nothing
        SHOOT,          // Wheels spinning to shoot boulder
        INTAKE          // Wheels spinning to intake boulder
    }

    private states m_currentState = states.STOP;

    /**
     * Shooter -- construct the wheels and the sensor used for shooting/intaking boulders
     */
    public Shooter()
    {
        m_talon1 = new CANTalon(RobotMap.SHOOTER_MOTOR_LEFT);
        m_talon1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        m_talon2 = new CANTalon(RobotMap.SHOOTER_MOTOR_RIGHT);
        m_talon2.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        m_talon2.setInverted(true);
        m_pid = new PIDController(0, 0, 0, 0, 0);
        m_currentState = states.STOP;
    }

    /**
     * Set the default command for the subsystem. In this case stop the shooter motors.
     */
    public void initDefaultCommand()
    {
        setDefaultCommand(CommandFactory.getCommandFactory().getCommand("stopshooter"));
    }

    /**
     * shootBoulder - sets the state to SHOOT and runs the motors outward at a consistent speed to
     * shoot a boulder (hopefully) straight.
     */
    public void shootBoulder()
    {
        double a = 0; // correction value
//        a = m_pid.calculateOutput(m_talon1.getEncVelocity(), RobotMap.SHOOT_BOULDER_SPEED);
//        m_talon1.set(this.makeInRange(a, -RobotMap.SHOOT_BOULDER_SPEED, 1));
        m_talon1.set(-RobotMap.SHOOT_BOULDER_SPEED);
//        a = m_pid.calculateOutput(m_talon2.getEncVelocity(), RobotMap.SHOOT_BOULDER_SPEED);
//        m_talon2.set(this.makeInRange(a, -RobotMap.SHOOT_BOULDER_SPEED, 1));
        m_talon2.set(-RobotMap.SHOOT_BOULDER_SPEED);
        SmartDashboard.putNumber("Change", a);
        m_currentState = states.SHOOT;
    }

    /**
     * intakeBoulder -- sets the state to INTAKE and runs the motors inward to intake a boulder
     */
    public void intakeBoulder()
    {
        double a = 0; // correction value
//        a = m_pid.calculateOutput(m_talon1.getEncVelocity(), RobotMap.INTAKE_BOULDER_SPEED);
//        m_talon1.set(-this.makeInRange(-a, RobotMap.INTAKE_BOULDER_SPEED, 1));
        m_talon1.set(RobotMap.SHOOT_BOULDER_SPEED);
//        a = m_pid.calculateOutput(m_talon2.getEncVelocity(), RobotMap.INTAKE_BOULDER_SPEED);
//        m_talon2.set(-this.makeInRange(-a, RobotMap.INTAKE_BOULDER_SPEED, 1));
        m_talon2.set(RobotMap.SHOOT_BOULDER_SPEED);
        m_currentState = states.INTAKE;
    }

    /**
     * stopMotors -- sets the state to STOP and stops the motors.
     */
    public void stopMotors()
    {
        m_talon1.set(0.0);
        m_talon2.set(0.0);
        m_currentState = states.STOP;
    }

    /**
     * Returns the current state of the shooter
     * 
     * @return the state of the shooter (INTAKE, SHOOT or STOP)
     */
    public Shooter.states getCurrentState()
    {
        return m_currentState;
    }

    /**
     * makeInRange -- ???
     * 
     * @param a
     *            ??
     * @param b
     *            ??
     * @param c
     *            ??
     * @return ??
     */
    private double makeInRange(double a, double b, double c)
    {
        double d = 0;
        if (a + b > c)
        {
            d = 1;
        }
        else
        {
            d = a + b;
        }
        return d;
    }

}