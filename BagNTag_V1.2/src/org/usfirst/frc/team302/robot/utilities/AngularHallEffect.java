package org.usfirst.frc.team302.robot.utilities;

/**
 * This is a math class for hall effect sensors to get the angle that a mechanism is currently at
 * 
 * <h1>Change Log:</h1>
 * 
 * @version <b>version 1:</b> 1/29/2016 --Derek Witcpalek -- Original creation of the class
 * 
 * 
 * @author Derek
 *
 */
public class AngularHallEffect implements IAngleSensor
{

    private double m_distanceFromPivot;

    /**
     * Creator
     * 
     * @param distanceFromPivot
     *            how far is the sensor mounted from the pivot point
     */
    public AngularHallEffect(double distanceFromPivot)
    {
        m_distanceFromPivot = distanceFromPivot;
    }

    public double getAngle(double input)
    {
        return (Math.atan(input / m_distanceFromPivot));
    }

}
