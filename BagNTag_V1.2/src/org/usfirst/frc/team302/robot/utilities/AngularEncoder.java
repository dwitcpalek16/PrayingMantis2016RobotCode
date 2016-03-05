package org.usfirst.frc.team302.robot.utilities;

/**
 * This is a math class for encoders. You create this and then call the getAngle method to get the angle that it is reading
 * 
 * 
 * <h1>Change Log:</h1>
 * 
 * @version version 1: 2/10/2016 -- Derek Witcpalek -- Original creation of the angular encoder class
 * 
 * 
 * @author Derek
 *
 */
public class AngularEncoder implements IAngleSensor
{

    private double m_countsPerRevolution;
    private boolean m_inverted;

    /**
     * Creator
     * 
     * @param countsPerRevolution
     *            number of counts for one revolution of the encoder
     * @param inverted
     *            is the encoder going the wrong direction
     */
    public AngularEncoder(double countsPerRevolution, boolean inverted)
    {
        m_countsPerRevolution = countsPerRevolution;
        m_inverted = inverted;
    }

    public double getAngle(double input)
    {
        return (m_inverted) ? -(input / m_countsPerRevolution * 360) : (input / m_countsPerRevolution * 360);
    }

}
