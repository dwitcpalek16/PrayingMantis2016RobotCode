package org.usfirst.frc.team302.robot;

import java.io.BufferedReader;
import java.io.FileReader;

import org.usfirst.frc.team302.robot.commands.CommandFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI extends Robot
{
    /**
     * This class is the glue that binds the controls on the physical operator interface to the commands and command groups that allow control of the
     * robot.
     */
    
    // constants for the tokens on each comma separated value line of the profile
    private final int m_stickToken = 0;
    private final int m_buttonToken = 1;
    private final int m_commandToken = 2;
    private final int m_stateToken = 3;
    private final int m_tokensPerLine = 4;
    
    // constants for the joystick IDs 
    private final int m_invalidJoystickID  = -1;
    private final int m_driverJoystickID  = 0;
    private final int m_copilotJoystickID = 1;
    
    private final int m_invalidState = -1;
    private final int m_pressedID = 1;
    private final int m_heldID = 2;
    private final int m_released = 3;
    private final int m_trigger = 4;
    
    private final int m_buttonInvalid = -1;
    private final int m_buttonA = 1;
    private final int m_buttonB= 2;
    private final int m_buttonX = 3;    
    private final int m_buttonY = 4;    
    private final int m_upperRightButton = 5; 
    private final int m_upperLeftButton = 6;    
    private final int m_buttonBack = 7; 
    private final int m_buttonStart = 8;    
    private final int m_buttonRStick = 9;
    private final int m_buttonLStick = 10;
    private final int m_buttonLTrigger = 11;
    private final int m_buttonRTrigger = 12;

    private Joystick m_driverControl;
    private Joystick m_copilotControl;
    private static OI m_oi = null;

    private Button[] driverButtons;
    private Button[] copilotButtons;
    private boolean m_success = false;

    // Button button = new JoystickButton(stick, buttonNumber);
    
    
    
    public OI()
    {
        //// CREATING BUTTONS
        // One type of button is a joystick button which is any button on a
        //// joystick.
        // You create one by telling it which joystick it's on and which button
        // number it is.
        // Joystick stick = new Joystick(port);
        m_driverControl = new Joystick(RobotMap.DRIVER_CONTROLLER);
        m_copilotControl = new Joystick(RobotMap.COPILOT_CONTROLLER);
        
        copilotButtons = new Button[10];
        driverButtons = new Button[10];

        for(int y = 0; y <= 9; y++)
        {
            copilotButtons[y] = new JoystickButton(m_copilotControl, y + 1);
        }
        
        for(int y = 0; y <= 9; y++)
        {
            driverButtons[y] = new JoystickButton(m_driverControl, y + 1);
        }
    }

    public static OI getOI()
    {
    	if(m_oi == null)
    	{
    		m_oi = new OI();
    	}
    	return m_oi;
    }
    
    public void readFile(String oiSelected)
    {
    
        //fileName must be the full directory on the robot
        String fileName = "/home/admin/" + oiSelected + ".txt";
        //read that file!
        try
        {
            FileReader inputFile = new FileReader(fileName);
            BufferedReader buffReader = new BufferedReader(inputFile);

            String line;

            while ((line = buffReader.readLine()) != null)   
            {
                // Skip comment lines that begin with a #
                if (line.trim().startsWith("#"))
                {
                    continue;
                }
                // Parse the line and associate the command to the appropriate button
                assignCommand(line);
            }
            buffReader.close();

        } 
        catch (Exception e)
        {
            System.out.println("Error while reading file line by line:" + e.getMessage());
            m_success = false;
        }

        // Add default behavior if there was a failure parsing
//        if (!m_success)
//        {
//            driverButtons[0].whileHeld(CommandFactory.getCommandFactory().getCommand("aimintake"));
//            driverButtons[1].whileHeld(CommandFactory.getCommandFactory().getCommand("aimfolded"));
//            driverButtons[2].whileHeld(CommandFactory.getCommandFactory().getCommand("aimhighgoal"));
//            driverButtons[3].whileHeld(CommandFactory.getCommandFactory().getCommand("aimlowgoal"));
//            driverButtons[8].whileHeld(CommandFactory.getCommandFactory().getCommand("aimneutral"));
//            driverButtons[9].whileHeld(CommandFactory.getCommandFactory().getCommand("punch"));
//            driverButtons[4].whileHeld(CommandFactory.getCommandFactory().getCommand("shiftup"));
//            driverButtons[5].whileHeld(CommandFactory.getCommandFactory().getCommand("shiftdown"));
//        }
    }
    
    /**
     * assignCommand - given a comma separated line from the command-button mapping profile, this method will  
     *                 parse the string into 4 tokens (stick, button, button state, command).  This will be validated
     *                 and if it is good, assign the command to the button action.
     * @param line	   line from the configuration file that doesn't begin with a comment character
     */
    public void assignCommand(String line)
    {
        SmartDashboard.putString("Line", line);
        if (line != null)
        {
            // Parse the string into 3 tokens ButtonID, ButtonState and CommandID where each thing is separated by commas
            String[] items = line.split(",");
            if (items.length == m_tokensPerLine)
            {
                // Get Joystick ID
                int stickID = getStickID(items[m_stickToken]);
                
                if (stickID > m_invalidJoystickID)
                {
                    Joystick stick = getStick(stickID);
                    if (stick != null)
                    {               
                        // Get Button ID
                        int buttonID = getButtonID(items[m_buttonToken]); // if using Button Description
                        
                        if (buttonID > m_buttonInvalid)
                        {
                            int stateID = getStateID(items[m_stateToken].toLowerCase());
                            
                            if(stateID > m_invalidState)
                            {
                                // Get Command 
                                String commandName = items[m_commandToken].toLowerCase(); // if using Command Description
                                
                                if(stickID == 0)
                                {
                                    switch(stateID)
                                    {
                                    case 1:
                                        driverButtons[buttonID - 1].whenPressed(CommandFactory.getCommandFactory().getCommand(commandName));
                                        break;
                                        
                                    case 2:
                                        driverButtons[buttonID - 1].whileHeld(CommandFactory.getCommandFactory().getCommand(commandName));
                                        break;
                                        
                                    case 3:
                                        driverButtons[buttonID - 1].whenReleased(CommandFactory.getCommandFactory().getCommand(commandName));
                                        break;
                                        
                                    case 4:
                                        if(isPressed(m_driverControl, buttonID - 9))
                                        {
                                            CommandFactory.getCommandFactory().getCommand(commandName).start();
                                        }
                                        break;
                                        
                                    default:
                                        break;
                                    }
                                    
                                }
                                else if(stickID == 1)
                                {
                                    switch(stateID)
                                    {
                                    case 1:
                                        copilotButtons[buttonID - 1].whenPressed(CommandFactory.getCommandFactory().getCommand(commandName));
                                        break;
                                        
                                    case 2:
                                        copilotButtons[buttonID - 1].whileHeld(CommandFactory.getCommandFactory().getCommand(commandName));
                                        break;
                                        
                                    case 3:
                                        copilotButtons[buttonID - 1].whenReleased(CommandFactory.getCommandFactory().getCommand(commandName));
                                        break;
                                        
                                    case 4:
                                        if(isPressed(m_copilotControl, buttonID - 9))
                                        {
                                            CommandFactory.getCommandFactory().getCommand(commandName).start();
                                        }
                                        break;
                                        
                                    default:
                                        break;
                                    }
                                }
                                else
                                {
                                    System.out.println("you done messed up");
                                }
                            }
                        }
                        else
                        {
                            System.out.println("invalid line - invalid stick");
                        }
                    }
                    else
                    {
                        System.out.println("invalid line - invalid stick");
                    }
                }
                else
                {
                    System.out.println("invalid line - invalid stick identifer:" + line);
                }
            }
            else
            {
                System.out.println("invalid line - not enough tokens:" + line);
            }
        }
    }
    
    /*
     * getStickID - Given a stick label, this returns the ID of the stick (driver or co-pilot)
     * 
     * @param stickLabel = string read from the file that indicates what Joystick to associate the 
     * command to
     * @return int version of the button state
     */
    public int getStickID(String stickLabel)
    {
        int stickID = m_invalidJoystickID;
        if (stickLabel != null)
        {
            String lcStickLabel = stickLabel.toLowerCase();    // make it lowercase for compares
            switch (lcStickLabel)
            {
                case "driverjoystick":
                    stickID = m_driverJoystickID;
                    break;
                
                case "copilotjoystick":
                    stickID = m_copilotJoystickID;
                    break;
                                
                default:
                    System.out.println("Invalid stick Label:" + stickLabel);
                    stickID = m_invalidJoystickID;   
                    break;
            }
        }
        return stickID;
    }
    /*
      getStick - given a stick ID (driver or co-pilot), this will return the joystick to process.
      
      @param stickID = 1 = driver, 2 = co-pilot
      @return int version of the button state
     */
    public Joystick getStick(int stickID)
    {
        Joystick stick = null;
        switch (stickID)
        {
            case 0:
                if (m_driverControl == null)
                {
                    m_driverControl = new Joystick(m_driverJoystickID);
                }
                stick = m_driverControl;
                break;
            
            case 1:
                if (m_copilotControl == null)
                {
                    m_copilotControl = new Joystick(m_copilotJoystickID);
                }
                stick = m_copilotControl;
                break;
                            
            default:
                System.out.println("Invalid stick id:" + stickID);
                stick = null;   
                break;
        }
        return stick;
    }   
    
    /**
     * getButtonID - given a button label, this returns the corresponding button ID.
     * 
     * @param buttonLabel = string identifying the button to map
     * @return button identifier (int)
     */
        public int getButtonID(String buttonLabel)
        {
            int buttonID = m_buttonInvalid;
            if (buttonLabel != null)
            {
                String lcButtonLabel = buttonLabel.toLowerCase(); // make it lowercase for compares

                switch (lcButtonLabel)
                {
                    case "x":
                        buttonID = m_buttonX;
                        break;
                
                    case "a":
                        buttonID = m_buttonA;
                        break;
                
                    case "b":
                        buttonID = m_buttonB;
                        break;
                
                    case "y":
                        buttonID = m_buttonY;
                        break;
                
                    case "LB":
                        buttonID = m_upperLeftButton;
                        break;
                
                    case "RB":
                        buttonID = m_upperRightButton;
                        break;
                
                    case "back":
                        buttonID = m_buttonBack;
                        break;
                
                    case "start":
                        buttonID = m_buttonStart;
                        break;
                
                    case "lstick":
                        buttonID = m_buttonLStick;
                        break;
                
                    case "rstick":
                        buttonID = m_buttonRStick;
                        break;
                        
                    case "lt":
                        buttonID = m_buttonLTrigger;
                        break;
                        
                    case "rt":
                        buttonID = m_buttonRTrigger;
                        break;
                        
                    default:
                        System.out.println("Invalid button Label:" + buttonLabel);
                        break;
                }
            }
            return buttonID;
        }
        /**
         * getStateID - determines the number of the state based on the text file.
         *  
         *  @param stateName = string of the state name
         *  @return stateID = the number corresponding with the name
         *  
         */
        public int getStateID(String stateName)
        {
            int stateID = m_invalidState;
            switch (stateName)
            {
            case "whenpressed":
                stateID = m_pressedID;
                break;
                
            case "whileheld":
                stateID = m_heldID;
                break;
                
            case "whenreleased":
                stateID = m_released;
                break;
                
            case "trigger":
                stateID = m_trigger;
                break;
                
            default:
                break;
            }
            
            return stateID;
        }
        
        public double getValue(Joystick m_joystick, int axis)
        {
            return m_joystick.getRawAxis(axis);
        }
        
        public boolean isPressed(Joystick m_joystick, int axis)
        {
            double value;
            boolean trigger = false;
            value = getValue(m_joystick, axis);
            if (value > 0)
            {
                trigger = true;
            }else{
                trigger = false;
            }
            return trigger;
        }
        
        public Joystick getCopilotJoystick()
        {
        	return m_copilotControl;
        }
        
        public Joystick getDriverJoystick()
        {
        	return m_driverControl;
        }
}
