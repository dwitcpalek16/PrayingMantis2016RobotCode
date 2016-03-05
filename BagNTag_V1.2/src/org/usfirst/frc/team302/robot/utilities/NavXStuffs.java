package org.usfirst.frc.team302.robot.utilities;

import org.usfirst.frc.team302.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

public class NavXStuffs
{
    static AHRS m_navX;
    
    public static AHRS getNavX()
    {
    	if(m_navX == null)
    	{
        m_navX = new AHRS(RobotMap.NAVX_PORT);
    	}
    	return m_navX;
    }
    
    public void reset()
    {
        m_navX.reset();
    }
    
    public double getYaw()
    {
        return m_navX.getYaw();
    }
    
    public double getPitch()
    {
        return m_navX.getRoll();
    }
    
    public double getRoll()
    {
        return m_navX.getPitch();
    }
    
    public float getDisplacementY()
    {
        return m_navX.getDisplacementX();
    }
    
    public double getDisplacementX()
    {
        return m_navX.getDisplacementY();
    }
    
    public double getDisplacementZ()
    {
        return m_navX.getDisplacementZ();
    }
    
    public double getAngle()
    {
      return m_navX.getAngle();
    }
}
    
