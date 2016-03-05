package org.usfirst.frc.team302.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 * This class will run IntakeBoulder.java and AimIntake in Parallel 
 *
 *
 * @version version 1: 2/17/2016 -- Zach and Adam -- initial command group
 *
 * @author Zach Zweber and Adam Armstrong
 */

//Runs code
public class IntakeGroup extends CommandGroup
{
    public IntakeGroup()
    {
        addParallel(new AimIntake());
        addParallel(new IntakeBoulder());
    }
}