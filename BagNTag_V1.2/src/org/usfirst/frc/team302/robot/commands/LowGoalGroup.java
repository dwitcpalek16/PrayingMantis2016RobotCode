package org.usfirst.frc.team302.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 * This class will run AimLowGoal and ShootBoulder in Parallel 
 *
 *
 * @version version 1: 2/17/2016 -- Zach and Adam -- initial command group
 *
 * @author Zach Zweber and Adam Armstrong
 */

//Runs code
public class LowGoalGroup extends CommandGroup
{
    /**
     * Runs AimLowGoal and ShootBoulder in parallel[
     */
    public LowGoalGroup()
    {
    	addParallel(new AimLowGoal());
    	addParallel(new ShootBoulder());
    }
}
