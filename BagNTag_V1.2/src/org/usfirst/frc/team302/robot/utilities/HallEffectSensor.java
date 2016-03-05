package org.usfirst.frc.team302.robot.utilities;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This is a class to work with hall effect sensors
 * 
 * <h1>Change Log: </h1>
 * 
 * @version <b>version 1:</b>
 * 			<p>
 * 				2/22/2016 --Derek Witcpalek -- Original implementation of hall effect sensor
 * 			</p>
 * 
 * @author Derek
 *
 */
public class HallEffectSensor implements IAngleSensor {
	
	AnalogInput sensor;
	
	
	/**
	 * Creator
	 * 
	 * @param channel	where is the sensor plugged in?
	 */
	public HallEffectSensor(int channel)
	{
		sensor = new AnalogInput(channel);
	}
	
	
	/**
	 * Get the current angle of the hall effect sensor
	 * 
	 * @return	angle measured by the sensor
	 */
	public double getAngle(double input)
	{
		SmartDashboard.putNumber("Hall Voltage", sensor.getVoltage());
		return ((180/4.6) * (sensor.getVoltage()-0.2));
	}
}
