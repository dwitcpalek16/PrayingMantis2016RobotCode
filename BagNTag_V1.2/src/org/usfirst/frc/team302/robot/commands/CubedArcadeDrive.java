
package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.subsystems.Drive;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will drive (control) the robot using an arcade (game) method. It will cube the joystick values to change the sensitivity.
 * 
 * @version version 1: 2/1/2016 -- Eric Smith and Klayton Smith -- initial command
 * @version version 2: 2/7/2016 -- Joe Witcpalek -- pulled into the command pattern
 * 
 * @author Eric Smith and Klayton Smith
 */
public class CubedArcadeDrive extends Command
{

    protected Drive m_drive;        // drive subsystem
    protected Joystick m_joystick;  // gamepad that will control the robot

    /**
     * Construct this command and initialize the attributes (driver controller and drive subsystem).
     */
    public CubedArcadeDrive()
    {
        // Use requires() here to declare subsystem dependencies
        m_drive = SubsystemFactory.getSubsystemFactory().getDrive();
        requires(m_drive);
        m_joystick = new Joystick(RobotMap.DRIVER_CONTROLLER);
    }

    /**
     * Called just before this Command runs the first time
     */
    protected void initialize()
    {
        //turns off the brakes on the talons
        m_drive.setBrake(false);
    }

    /**
     * drive the robot using cubed inputs of the joystick with one joystick being the forward/backward movement and the other being left/right
     * turning. This is called repeatedly when this is scheduled to run.
     */
    protected void execute()
    {
        double move = getMoveStickValue();

        double rotate = getRotateStickValue();

        m_drive.arcadeDrive(move, rotate);
    }

    /**
     * Read the rotation stick value and return the cube of this value.
     * 
     * @return value to rotate the robot left/right
     */
    private double getRotateStickValue()
    {
        return Math.pow(m_joystick.getRawAxis(0), 3.0);
    }

    /**
     * Read the move stick value and return the cube of this value.
     * 
     * @return the value to move the robot forward/backward
     */
    private double getMoveStickValue()
    {
        return Math.pow(m_joystick.getRawAxis(1), 3.0);
    }

    /**
     * Make this return true when this Command no longer needs to run execute()
     */
    protected boolean isFinished()
    {
        return false; // not finished
    }

    /**
     * Called once after isFinished returns true
     */
    protected void end()
    {
    }

    /**
     * Called when another command which requires one or more of the same
     */
    protected void interrupted()
    {
    }
}
