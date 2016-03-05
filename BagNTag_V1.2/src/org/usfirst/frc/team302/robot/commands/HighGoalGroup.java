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
public class HighGoalGroup extends CommandGroup
{
    public HighGoalGroup()
    {
        addParallel(new AimHighGoal());
        addParallel(new ShootBoulder());
    }
}
