
package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * This subsystem defines the shifting subsystem
 * 
 * @version <b>version 1:</b> 1/16/2016 -- Derek Witcpalek -- original dummy class
 * @version <b>version 2:</b> 2/1/2016 -- Eric Smith and Klayton Smith -- Fill in the details
 * @version <b>version 3:</b> 2/21/2016 -- Josh Baker added pressure sensor code
 * @version <b>version 4:</b> 3/3/2016 -- Joe Witcpalek Add Review comments
 */

/*
 * Review Comments
 * 1) Add more Javadoc (and remove references to drive this is shift
 * 2) Remember naming conventions
 * 3) Have a consistent left margin
 * 4) Use RobotMap for the solenoid ID
 * 5) Don't believe updateDefaultCommand is used
 */
public class Shift extends Subsystem
{


	private boolean m_HighGear = false;
    private Solenoid m_TransmissionSolenoid;
    private AnalogInput ai;

    /**
     * Initialize the motors, encoders, and solonoid for the the robot drive.
     */
    public Shift()
    {

        m_TransmissionSolenoid = new Solenoid(0);
        m_TransmissionSolenoid.set(false);
        ai = new AnalogInput(RobotMap.PRESSURE_SENSOR);
    }

    /**
     * Updates the default command on this subsystem
     * 
     * @param command
     *            the command to run by default
     */
    public void updateDefaultCommand(Command command)
    {
        setDefaultCommand(command);
    }

    /**
     * 
     */
    public void initDefaultCommand()
    {
    	
    }
        
    
    /**
     * Determines whether or not we can shift anymore (hopefully)
     * 
     * @return boolean true = low on pressure, so stop shifting, false = continue shifting
     */
    private boolean stopOrNot()
    {
        boolean isStop = false;
        double pressure = 250 * (ai.getVoltage() / RobotMap.SUPPLY_VOLTAGE)-25;
        if(pressure < RobotMap.PRESSURE_MIN)
        {
            isStop = true;
        }else
        {
            isStop = false;
        }
        
        return isStop;
    }
    
    public void shift(Boolean direction)
    {
        SmartDashboard.putNumber("Value", ai.getVoltage());
        SmartDashboard.putBoolean("Stop or Not", stopOrNot());
        
        if(stopOrNot())
        {
            m_TransmissionSolenoid.set(RobotMap.LOW_GEAR);
        }else
        {
            m_TransmissionSolenoid.set(direction);
        }
    }
    
    
    /**
     * This will tell you if the robot is in low gear
     * 
     * @return	are you in low gear
     */
    public boolean isLowGear()
    {
    	return !m_HighGear;
    }
    
    
    /**
     * This will tell you if the robot is in high gear
     * 
     * @return	are you in high gear
     */
    public boolean isHighGear()
    {
    	return m_HighGear;
    }

}
        


