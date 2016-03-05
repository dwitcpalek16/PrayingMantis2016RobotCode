package org.usfirst.frc.team302.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class determines the actions to be performed during autonomous mode
 * 
 * @author Josh Baker
 */
public class SelectAutonMode
{
	private SendableChooser m_position;
	private final String p1 = "Position 1";
	private final String p2 = "Position 2";
	private final String p3 = "Position 3";
	private final String p4 = "Position 4";
	private final String p5 = "Position 5";
	
	private SendableChooser m_defense;
	private final String cross = "Cross";
	private final String reach = "Reach";
	private final String cdf = "Cheval De Frise";
	private final String portcullis = "Portcullis";
	private final String sallyPort = "Sally Port";
	private final String noMove = "No Move";
	
	private SendableChooser m_shoot;
	private final String high = "High Goal";
	private final String low = "Low Goal";
	private final String drop = "Drop";
	private final String noShoot = "No Shoot";
	
	private SendableChooser m_move;
	private final String forward = "Forward";
	private final String backward = "Backward";
	private final String turnRight = "Turn Right";
	private final String turnLeft = "Turn Left";
	
	public SelectAutonMode()
	{
		m_defense = new SendableChooser();
		m_shoot = new SendableChooser();
		m_position = new SendableChooser();
		m_move = new SendableChooser();
	}
	
	/**
	 * getPosition() -- Determines what position we are in 
	 * 					based on a choice on the smartDashboard
	 * @return Selected position
	 */
	public String getPosition()
    {
    	m_position.addDefault(p1, p1);
    	m_position.addObject(p2, p2);
    	m_position.addObject(p3, p3);
    	m_position.addObject(p4, p4);
    	m_position.addObject(p5, p5);
    	SmartDashboard.putData("Position", m_position);
    	return (String) m_position.getSelected();
    }
	
	/**
	 * positionCommand() -- Determines what command to run
	 * 
	 * @return command
	 */
	public Command positionCommand()
	{
		String position = getPosition();
		Command command = null;
		switch(position)
		{
		case p1:
			command = null;
			break;
		case p2:
			command = null;
			break;
		case p3:
			command = null;
			break;
		case p4:
			command = null;
			break;
		case p5:
			command = null;
			break;
		default:
			command = null;
			break;
		}
		return command;
	}
	
	/**
	 * getDefenseAction() -- Determines what Defense Action we want 
	 * 						 based on a choice on the smartDashboard
	 * @return Selected Defense Action
	 */
    public String getDefenseAction()
    {
    	m_defense.addDefault(noMove, noMove);
    	m_defense.addObject(cross, cross);
    	m_defense.addObject(reach, reach);
    	m_defense.addObject(cdf, cdf);
    	m_defense.addObject(portcullis, portcullis);
    	m_defense.addObject(sallyPort, sallyPort);
    	SmartDashboard.putData("Defense Action", m_defense);
    	return (String) m_defense.getSelected();
    }
    
	/**
	 * defenseCommand() -- Determines what command to run
	 * 
	 * @return command
	 */
    public Command defenseCommand()
    {
    	String defense = getDefenseAction();
    	Command command = null;
    	switch(defense)
    	{
    	case noMove:
    		command = null;
    		break;
    	case cross:
    		command = null;
    		break;
    	case reach:
    		command = null;
    		break;
    	case cdf:
    		command = null;
    		break;
    	case portcullis:
    		command = null;
    		break;
    	case sallyPort:
    		command = null;
    		break;
    	default:
    		command = null;
    		break;
    	}
    	return command;
    }
    
	/**
	 * getShootAction() -- Determines what Shoot Action we want 
	 * 					   based on a choice on the smartDashboard
	 * @return Selected Shoot Action
	 */
    public String getShootAction()
    {
    	m_shoot.addDefault(noShoot, noShoot);
    	m_shoot.addObject(high, high);
    	m_shoot.addObject(low, low);
    	m_shoot.addObject(drop, drop);
    	SmartDashboard.putData("Shooting Action", m_shoot);
    	return (String) m_shoot.getSelected();
    }
    
	/**
	 * shootCommand() -- Determines what command to run
	 * 
	 * @return command
	 */
    public Command shootCommand()
    {
    	String shoot = getShootAction();
    	Command command = null;
    	switch(shoot)
    	{
    	case noShoot:
    		command = null;
    		break;
    	case high:
    		command = null;
    		break;
    	case low:
    		command = null;
    		break;
    	case drop:
    		command = null;
    		break;
    	default:
    		command = null;
    		break;
    	}
    	return command;
    }
    
	/**
	 * getMoveAction() -- Determines what Move Action we want 
	 * 					  based on a choice on the smartDashboard
	 * @return Selected Move Action
	 */
    public String getMoveAction()
    {
    	m_move.addDefault(noMove, noMove);
    	m_move.addObject(forward, forward);
    	m_move.addObject(backward, backward);
    	m_move.addObject(turnLeft, turnLeft);
    	m_move.addObject(turnRight, turnRight);
    	SmartDashboard.putData("Move Action", m_move);
    	return (String) m_move.getSelected();
    }
    
	/**
	 * moveCommand() -- Determines what command to run
	 * 
	 * @return command
	 */
    public Command moveCommand()
    {
    	String move = getMoveAction();
    	Command command = null;
    	switch(move)
    	{
    	case noMove:
    		command = null;
    		break;
    	case forward:
    		command = null;
    		break;
    	case backward:
    		command = null;
    		break;
    	case turnLeft:
    		command = null;
    		break;
    	case turnRight:
    		command = null;
    		break;
    	default:
    		command = null;
    		break;
    	}
    	return command;
    }
}
