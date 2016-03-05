package org.usfirst.frc.team302.robot.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class BruteForceAngleCalculations
{

    public static void writing(String line, boolean newLine, int extension)
    {
        try
        {

            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter("C:/Users/Derek/Desktop/AngleCalculations/angleCalculations" + extension + "InchExtension" + ".csv", true)));

            char[] charLine = line.toCharArray();
            for (int i = 0; i < (charLine.length - 1); i++)
            {
                out.append(charLine[i]);
            }
            if (newLine)
                out.append(System.lineSeparator());
            else
                out.append(", ");
            out.close();
        } catch (IOException e)
        {
            System.err.println("Problem writing to the file statsTest.txt");
        }
    }

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        double h = 10;
        double H = 39;
        double L = 45;
        double l = 28;
        double x1 = 8;
        // double x2 = 14;

        Vector<double[]> goodValues = new Vector<double[]>(0);
        Scanner in = new Scanner(System.in);

        double height = 0;
        double extension = 0;
        double[] temp = { 1000, 1000, 1000, 1000 };

        for (double x2 = -10; x2 <= 15; x2 += 1)
        {
            for (double H2 = 0; H2 <= (L + l + h); H2 += 5)
            {
                for (double theta1 = 0; theta1 < 180; theta1 += 0.1)
                {
                    for (double theta2 = 0; theta2 < 180; theta2 += 0.1)
                    {
                        height = (h + l * Math.sin(theta1 * Math.PI / 180) + L * (Math.sin((theta2 - theta1) * Math.PI / 180)));
                        extension = (L * Math.cos(((theta2 - theta1) * Math.PI / 180)) - (x1 + l * Math.cos(theta1 * Math.PI / 180)));

                        if (extension <= (x2 + 0.05) && extension >= (x2 - 0.05) && height <= (H2 + 0.05) && height >= (H2 - 0.05))
                        {
                            double[] angleSetpoints = { height, extension, theta1, theta2 };
                            goodValues.add(angleSetpoints);
                            // System.out.println("Height is: " + height +
                            // "-------------------Extension is: " + extension +
                            // "-------------------theta1 is: " + theta1 +
                            // "-------------------theta2 is: " + theta2);
                        }

                    }

                }

                temp[0] = 1000;
                temp[1] = 1000;
                temp[2] = 1000;
                temp[3] = 1000;
                for (int i = 0; i <= goodValues.size() - 1; i++)
                {
                    if (((Math.abs(goodValues.elementAt(i)[0] - H)) + (Math.abs(goodValues.elementAt(i)[1] - x2))) < ((Math.abs(temp[0] - H))
                            + (Math.abs(temp[1] - x2))))
                    {
                        temp = goodValues.elementAt(i);
                    }
                }
                System.out.println(
                        H2 + ": height: " + temp[0] + "-----extension: " + temp[1] + "-----theta 1: " + temp[2] + "-----theta 2: " + temp[3]);
                writing(temp[0] + ", " + temp[1] + ", " + temp[2] + ", " + temp[3], false, (int) Math.round(temp[1]));
                goodValues.clear();
            }
            writing("", true, (int) Math.round(temp[1]));
        }

    }

}
