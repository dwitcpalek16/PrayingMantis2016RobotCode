
package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.subsystems.Drive;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will drive (control) the robot using an arcade (game) method. It will the joystick values as read to control the robot.
 * 
 * @version version 1: 2/1/2016 -- Eric Smith and Klayton Smith -- initial command
 * @version version 2: 2/7/2016 -- Joe Witcpalek -- pulled into the command pattern
 * 
 * @author Eric Smith and Klayton Smith
 */
public class LinearArcadeDrive extends Command
{

    protected Drive m_drive;        // drive subsystem
    protected Joystick m_joystick;  // gamepad that will control the robot

    /**
     * Construct this command and initialize the attributes (driver controller and drive subsystem).
     */
    public LinearArcadeDrive()
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
        return m_joystick.getRawAxis(4);
    }

    private double getMoveStickValue()
    {
        return m_joystick.getRawAxis(1);
    }

    /**
     * Make this return true when this Command no longer needs to run execute()
     */
    protected boolean isFinished()
    {
        return false;
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
