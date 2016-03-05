package org.usfirst.frc.team302.robot.utilities;

/**
 * This is a class used to control the movement of mechanisms.
 * You can create a controller with a p term, an i term, and/or a d term.
 * 
 * <h1>Change Log: </h1>
 * 
 * @version <b>version 1:</b>
 * 			<p>
 * 				1/29/2016 --Derek Witcpalek -- Original implementation of a PID controller
 * 				this has not yet been tested
 * 			</p>
 * 
 * @author Derek
 *
 */
public class PIDController {

	private double m_proportionalConstant;
	private double m_integralConstant;
	private double m_derivativeConstant;
	private double m_maxChangeOutput;
	private double m_tolerance;
	private double m_previousOutput;
	private double m_output;
	private double m_error;
	private double m_previousError;
	private double m_deltaError;
	private double m_sumOfError;
	
	/**
	 * Creator
	 * 
	 * @param pidConstants array of constants in the order proportional, integral, derivative
	 */
	public PIDController(double[] pidConstants) {
		this(pidConstants[0], pidConstants[1], pidConstants[2], 1, 0.5);
	}

	
	/**
	 * Creator
	 * 
	 * @param p	proportional
	 * @param i	integral
	 * @param d	derivative
	 */
	public PIDController(double p, double i, double d)
	{
		this(p, i, d, 1, 0.5);
	}
	
	
	/**
	 * Creator
	 * 
	 * @param pidConstants		array of pid constants in the order proportional, integral, derivative
	 * @param maxChangeOutput	this is currently unsupported -- will be used to set a limit on the amount
	 * 							that the output of this controller can change by
	 */
	public PIDController(double[] pidConstants, double maxChangeOutput)
	{
		this(pidConstants[0], pidConstants[1], pidConstants[2], maxChangeOutput, 0.5);
	}
	
	
	/**
	 * Creator
	 * 
	 * @param p					proportional
	 * @param i					integral
	 * @param d					derivative
	 * @param maxChangeOutput	this is currently unsupported -- will be used to set a limit on the amount
	 * 							that the output of this controller can change by
	 * @param tolerance			this is the acceptable amount of error for the controller
	 */
	public PIDController(double p, double i, double d, double maxChangeOutput, double tolerance)
	{
		m_proportionalConstant = p;
		m_integralConstant = i;
		m_derivativeConstant = d;
		m_maxChangeOutput = maxChangeOutput;
		m_tolerance = tolerance;
		m_previousOutput = 0;
		m_output = 0;
		m_error = 0;
		m_previousError = 0;
		m_deltaError = 0;
		m_sumOfError = 0;
	}
	
	
	/**
	 * This will set the PID constants after a controller has already been created
	 * 
	 * @param p		proportional
	 * @param i		integral
	 * @param d		derivative
	 */
	public void setPIDConstants(double p, double i, double d)
	{
		setP(p);
		setI(i);
		setD(d);
	}
	
	
	/**
	 * Sets the proportional constant after the controller has been created
	 * 
	 * @param p		proportional
	 */
	public void setP(double p)
	{
		m_proportionalConstant = p;
	}
	
	
	/**
	 * Sets the integral constant after the controller has been created
	 * 
	 * @param i		integral
	 */
	public void setI(double i)
	{
		m_integralConstant = i;
	}
	
	
	/**
	 * Sets the derivative constant after the controller has been created
	 * 
	 * @param d		derivative
	 */
	public void setD(double d)
	{
		m_derivativeConstant = d;
	}
	
	
	/**
	 * Use this to get the new output of the controller every loop
	 * 
	 * @param processVariable	this is the sensor value
	 * @param setpoint			this is the value you want the sensor to read
	 * @return	Output			this is either a change in output or the output that the motor should
	 * 							be at
	 */
	public double calculateOutput(double processVariable, double setpoint)
	{
		m_previousOutput = m_output;
		
		m_previousError = m_error;
		m_error = setpoint - processVariable;
		m_deltaError = m_previousError - m_error;
		m_sumOfError += m_error;
		
		return makeInRange(calculateP() + calculateI() + calculateD());
	}
	
	
	/**
	 * You can't call this
	 * 
	 * Calculates the proportional output of the controller
	 * 
	 * @return	proportional term
	 */
	private double calculateP()
	{
		return m_proportionalConstant * m_error;
	}
	
	
	/**
	 * You can't call this
	 * 
	 * Calculates the integral output of the controller
	 * 
	 * Sum of error is set to zero if the error is within the tolerance range
	 * 
	 * if the sum of the error is acting against your proportional term, reset the sum to just be the current error
	 * 
	 * @return	integral term
	 */
	private double calculateI()
	{
		if(Math.abs(m_error) <= m_tolerance)
		{
			m_sumOfError = 0;
		}
		else if(m_error > 0 && m_sumOfError < 0) 
		{
			m_sumOfError = m_error;
		}
		else if(m_error < 0 && m_sumOfError > 0)
		{
			m_sumOfError = m_error;
		}
		else
		{
			//good: the error's sign matches the error sum's sign
			//that means the error sum is having a positive influence
			//on the final controller output
		}
		return m_integralConstant * m_sumOfError;
	}
	
	
	/**
	 * You can't call this
	 * 
	 * Calculates the derivative output of the controller
	 * 
	 * @return	derivative term
	 */
	private double calculateD()
	{
		return m_derivativeConstant * m_deltaError;
	}
	
	
	/**
	 * this will ensure that the output of the controller is between -1 and 1
	 * 
	 * @param output			the unmodified output
	 * @return	new output		the corrected output
	 */
	private double makeInRange(double output) 
	{
		double corrected = 0;
		
		if(output > 1) 
		{
			corrected = 1;
		}
		else if(output < -1) 
		{
			corrected = -1;
		}
		else 
		{
			corrected = output;
		}
		
		return corrected;
	}
	
	
	/**
	 * Get the current error as calculated the last time you called calculate output
	 * 
	 * @return error	how far you are from the target
	 */
	public double getError()
	{
		return m_error;
	}
	
}
