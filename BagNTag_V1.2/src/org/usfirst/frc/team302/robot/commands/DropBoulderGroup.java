package org.usfirst.frc.team302.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This class will run Shootboulder and AimIntake in Parallel 
 *
 *
 * @version version 1: 2/18/2016 -- Adam -- initial command group
 *
 * @author Adam Armstrong
 */

public class DropBoulderGroup extends CommandGroup
{
    public DropBoulderGroup()
    {
        addParallel(new AimIntake());
        addParallel(new ShootBoulder());
    }
}
