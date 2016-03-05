package org.usfirst.frc.team302.robot.utilities;

/**
 * This class will be used to calculate angle targets for our robot. Since the gyro
 * goes from 360 degrees to 0 immediately, the robot needs to know that 359 degrees
 * is really close in position to 0 degrees. This math class is used to find things
 * such as the smallest angle between two positions or the angle you need to go to if
 * you want to turn a certain degree amount. The class recognizes that 0 and 360 are 
 * the same position and will give you a 0 degree difference between them.
 * 
 * <h1>Change Log:</h1>
 * 
 * @version <b>version 1:</b>
 * 			<p>
 * 				1/19/2016 --Derek Witcpalek -- Original implementation of <i>add()</i>,
 * 				<i>subtract()</i>, and <i>smallestAngle()</i> methods. I also tested the
 * 				math that this class does and it looked solid. 
 * 			</p>
 * 
 * 
 * @author Derek
 *
 */
public class AngularMath {
	
	private static enum RotationDirection {
		COUNTER_CLOCKWISE,
		NONE,
		CLOCKWISE
	}
	
	/**
	 * This is used to find the angle you are targeting in a clockwise rotation
	 * 
	 * @param currentAngle	The starting angle of the robot
	 * @param changeInAngle	How far you want to turn
	 * @return	TargetAngle	Where you should want to end up
	 */
	public static double add(double currentAngle, double changeInAngle)
	{
//		if(changeInAngle > 360) throw new ArgumentOutOfRangeException();
		double sum = currentAngle + changeInAngle;
		double targetAngle;
		
		
		//if the angle is larger than 360, return the amount that it is past 360
        if (sum > 360)
            targetAngle = (sum - 360);
		
		//if the angle is less than 0, return 360 - the amount it is below zero
		//since the sum is negative (current angle can't be), you just add
        else if (sum < 0)
            targetAngle = (360 + sum);
		
		//otherwise, the sum is all good and you can just return that as the new angle
        else
            targetAngle = sum;
		
		return targetAngle;
	}
	
	
	/**
	 * This is used to find the angle you are targeting in a counter-clockwise rotation
	 * 
	 * @param currentAngle	The starting angle of the robot
	 * @param changeInAngle	How far you want to turn
	 * @return	TargetAngle Where you should want to end up
	 */
	public static double subtract(double currentAngle, double changeInAngle)
	{
		//cheat and use the add method with a negative because adding a negative is subtracting
		return add(currentAngle, -changeInAngle);
	}
	
	
	/**
	 * This method is used to figure out how far you need to turn to get to a specific
	 * target angle. It is designed to give you the smallest angle needed to get there
	 * so that you choose to rotate the robot the correct direction.
	 * 
	 * @param currentAngle		The starting angle of the robot
	 * @param targetAngle		Where you want to end up
	 * @return	angleOfRotation	How much you must rotate the robot
	 */
	private static double getSmallestAngle(double currentAngle, double targetAngle)
	{
		double option1;
		double option2;
		
		//find the change in angle going clockwise and counter-clockwise
		option1 = Math.abs(currentAngle - targetAngle);
		
		option2 = (currentAngle > targetAngle) //which angle is the biggest
				? (360 - currentAngle) + (targetAngle) //current angle is bigger --> go towards 360 
													   //and add target
						: (360 - targetAngle) + currentAngle; //target is bigger --> go towards 0 and 
															  //add distance target is from 360
		
		
		return (option1 < option2) ? option1 : option2;
	}
	
	
	/**
	 * This method is used to figure out the direction you should rotate between two angles
	 * to have the smallest angle to turn through.
	 * 
	 * @param currentAngle	The starting angle of the robot
	 * @param targetAngle	Where you want to end up
	 * @param smallestAngle --??
	 * @return	RotationDirection	Which way? (Clockwise, counter-clockwise, no rotation)
	 */
	private static RotationDirection getBestRotationDirection(double currentAngle, double targetAngle, double smallestAngle)
	{
		RotationDirection direction = RotationDirection.NONE;
		
		double difference = currentAngle - targetAngle;
		
		if((currentAngle > targetAngle)) 
		{
			if(difference == smallestAngle)
			{
				direction = RotationDirection.COUNTER_CLOCKWISE;
			}
			else if(difference != smallestAngle)
			{
				direction = RotationDirection.CLOCKWISE;
			}
			
		}
		else if((currentAngle < targetAngle))
		{
			if((-difference) == smallestAngle)
			{
				direction = RotationDirection.CLOCKWISE;
			}
			else if((-difference) != smallestAngle)
			{
				direction = RotationDirection.COUNTER_CLOCKWISE;
			}
		}
		else //(currentAngle == targetAngle) or smallestAngle == 180
		{
			direction = RotationDirection.NONE;
		}
		
		return direction;
		
	}
	
	
	/**
	 * This tells you how far to rotate and in which direction to get to a specific angle.
	 * 
	 * @param currentAngle	The starting angle of the robot
	 * @param targetAngle	Where you want to end up
	 * @return	Descriptor	Array-- first element is angle you must rotate, second element is
	 * 							the direction (denoted by +1 for clockwise, 
	 * 							-1 for counter-clockwise, or 0 for no turn)
	 */
	public static double[] getBestRotationDescriptor(double currentAngle, double targetAngle)
	{
		double bestAngle = getSmallestAngle(currentAngle, targetAngle);
		RotationDirection direction = getBestRotationDirection(currentAngle, targetAngle, bestAngle);
		
		direction.compareTo(RotationDirection.NONE);
		double[] best = {bestAngle, (double)direction.compareTo(RotationDirection.NONE)};
		return best;
	}
}
