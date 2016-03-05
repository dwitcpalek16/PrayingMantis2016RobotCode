package org.usfirst.frc.team302.robot.simulation;

import java.util.Scanner;

import javax.swing.JFrame;

public class Animation
{

    public static void main(String[] args) throws InterruptedException
    {
        Scanner in = new Scanner(System.in);

        JFrame animation = new JFrame("Animation");
        animation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        animation.setVisible(true);

        animation.setSize(800, 800);

        Arm arm = new Arm(150, 500);

        animation.add(arm);

        Thread armAnimation = new Thread(arm);

        boolean continuousRunning = false;
        String character = null;

        while (!arm.isDone())
        {
            if (!continuousRunning)
            {
                character = in.next();
            }

            armAnimation.run();
            Thread.sleep(200);

            if (character.equalsIgnoreCase("r"))
                continuousRunning = true;

        }

        while (!(character = in.next()).equals("q"))
        {

        }

        System.exit(0);
    }

}
