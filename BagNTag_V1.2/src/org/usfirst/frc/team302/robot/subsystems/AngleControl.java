package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.utilities.PIDController;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * 
 * @version version 2: 3/3/2016 -- Joe Witcpalek Add Review comments
 */

/*
 * Review Comments:
 * 1) Is this class used?  If yes, needs lots of documentation otherwise it should be deleted.
 */
public class AngleControl
{

	AHRS ahrs;
	CANTalon m_FrontLeftMotor;
	CANTalon m_RearLeftMotor;
	CANTalon m_FrontRightMotor;
	CANTalon m_RearRightMotor;
	public PIDController turnController;
	private final double m_MaxTurnRate = 359.85;
	RobotDrive r;

	public AngleControl()
	{
		SmartDashboard.putNumber("Turn controller p", 0);
		SmartDashboard.putNumber("Turn controller i", 0);
		SmartDashboard.putNumber("Turn controller d", 0);

		ahrs = new AHRS(SPI.Port.kMXP);
		m_FrontLeftMotor = new CANTalon(1);
		m_FrontLeftMotor.setInverted(true);
		m_RearLeftMotor = new CANTalon(2);
		m_RearLeftMotor.setInverted(true);

		m_FrontRightMotor = new CANTalon(3);
		m_FrontRightMotor.setInverted(true);
		m_RearRightMotor = new CANTalon(4);
		m_RearRightMotor.setInverted(true);
		r = new RobotDrive(m_FrontLeftMotor, m_RearLeftMotor, m_FrontRightMotor, m_RearRightMotor);

		turnController = new PIDController(SmartDashboard.getNumber("Turn controller p"),
				SmartDashboard.getNumber("Turn controller i"), SmartDashboard.getNumber("Turn controller d"));
	}

	public void controlAngle(double angularRateTarget, double speed)
	{
		// ahrs.getRate(); //this is the rotational velocity
		double target = angularRateTarget;

		if (Math.abs(angularRateTarget) < 0.1)
		{
			target = 0;
		}
		double output = turnController.calculateOutput(ahrs.getRate(), target * m_MaxTurnRate / 20);

		// m_FrontLeftMotor.set(angularRateTarget + output);
		// m_RearLeftMotor.set(angularRateTarget + output);
		// m_FrontRightMotor.set(angularRateTarget + output);
		// m_RearRightMotor.set(angularRateTarget + output);

		double correctedOutput = angularRateTarget + output;

		SmartDashboard.putNumber("Left Front Talon Draw", m_FrontLeftMotor.getOutputCurrent());
		SmartDashboard.putNumber("Left Rear Talon Draw", m_RearLeftMotor.getOutputCurrent());
		SmartDashboard.putNumber("Right Front Talon Draw", m_FrontRightMotor.getOutputCurrent());
		SmartDashboard.putNumber("Right Rear Talon Draw", m_RearRightMotor.getOutputCurrent());

		if (correctedOutput > 1)
			correctedOutput = 1;
		else if (correctedOutput < -1)
			correctedOutput = -1;

		r.arcadeDrive(speed, Math.pow(correctedOutput, 3));

		SmartDashboard.putNumber("Target rotation", target * m_MaxTurnRate);
		SmartDashboard.putNumber("Actual turn rate", ahrs.getRate());
	}
}
