package org.usfirst.frc.team302.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class is used to interface with the range finder sensor we have
 * connected to the navX board.
 * 
 * This code was originally found on chiefdelphi, but works for our purpose.
 * 
 * 
 * 
 * <h1>Change Log:</h1>
 * 
 * @version <b>version 1:</b>
 *          <p>
 *          1/18/2016 --Derek Witcpalek -- Code as found on chiefdelphi with
 *          some comments added in to explain the mysteries found in the code
 *          </p>
 * 
 * 
 * @author unknown
 */
public class LidarSensor extends SensorBase
{

	I2C m_i2c;

	public LidarSensor()
	{
		// sets up the communications line with the sensor
		m_i2c = new I2C(I2C.Port.kMXP, 0x62);

		initLidar();
	}

	public void initLidar()
	{
		// nothing to do
	}

	/**
	 * this is used to find the distance the laser range finder is from an
	 * object
	 * 
	 * @return Distance an integer representing the distance in an undetermined
	 *         unit
	 */
	public int getDistance()
	{

		byte[] buffer;
		buffer = new byte[2];

		// I'm guessing this is telling the laser to send out a beam
		m_i2c.write(0x00, 0x04); // send a 4 to address 0 on the i2c
									// communications

		// Wait for 0.1 seconds before checking the sensor
		Timer.delay(0.1); // waits 0.1 seconds

		// Check the value that the laser gets back
		m_i2c.read(0x8f, 2, buffer); // read(what address to read, how many
										// bytes to read, where to store the
										// data)

		/*
		 * this might take a little more research
		 * 
		 * link talking about different operation in java
		 * http://www.tutorialspoint.com/java/java_basic_operators.htm
		 * 
		 * 
		 * 
		 * Here is what the example did: 60<<2
		 * 
		 * 128,64,32,16 8,4,2,1 0000 0000
		 * 
		 * 60 is 0011 1100 60<<2 is 1111 0000
		 * 
		 * A byte is a set of 8 bits (bits are 0 or 1); it represents a value
		 * ranging from 0 to 255
		 * 
		 * So if the value of the byte in buffer[0] is 1001 1001, buffer[0] << 8
		 * = 1001 1001 0000 0000 = 2^8 + 2^11 + 2^12 + 2^15 = 39,168
		 * 
		 * the smallest value the first part could return is 0, while the
		 * largest is 65,535
		 * 
		 * the first half casts an unsigned long to be an integer that unsigned
		 * long is made by shifting the byte in position 0 of the array 8 bits
		 * to the left
		 * 
		 * the second half takes the byte in the second position of the array
		 * and turns it into an unsigned integer
		 * 
		 * the sum of the first and second halves is the value that is returned
		 * when getDistance() is called
		 */
		return (int) ((Integer.toUnsignedLong(buffer[0] << 8) + Byte.toUnsignedInt(buffer[1])) / 2.5);
	}

	public void initDefaultCommand()
	{
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}
