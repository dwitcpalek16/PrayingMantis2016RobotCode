package org.usfirst.frc.team302.robot.utilities;

/**
 * This interface defines that all angle sensors will be able to tell you the angle they are at
 * 
 * 
 * <h1>Change Log:</h1>
 * 
 * @version <b>version 1:</b> 2/10/2016 -- Derek Witcpalek -- Original creation of the interface
 *
 * @author Derek
 *
 */
public interface IAngleSensor
{

    /**
     * This will return the angle that the sensor is reading
     * 
     * @param input
     *            what is the sensor reading?
     * @return ` angle of the sensor
     */
    public double getAngle(double input);

}