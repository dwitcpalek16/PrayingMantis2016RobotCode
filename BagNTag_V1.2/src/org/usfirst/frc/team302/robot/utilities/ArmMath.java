package org.usfirst.frc.team302.robot.utilities;

public class ArmMath
{

    private static double h = 10; // this is the height the base of the arm is off the
    // ground
    private static double l = 32; // this is the length of the first arm segment (pivot
    // to pivot)
    private static double L = 48; // this is the length of the second arm segment
    // (pivot to end effector)
    private static double x1 = 8; // this is the distance the arm is mounted in from
    // the outside of the frame

    private double theta1; // this is the angle between the base and the first
                           // segment
    private double theta2; // this is the angle between the first segment and
                           // the second segment

    public ArmMath(double h, double l, double L, double x1)
    {
        this.h = h;
        this.l = l;
        this.L = L;
        this.x1 = x1;
    }

    public static double getHeight(double theta1, double theta2)
    {
        return (h + l * Math.sin(theta1 * Math.PI / 180) + L * (Math.sin((theta2 - theta1) * Math.PI / 180)));
    }

    public static double getExtension(double theta1, double theta2)
    {
        return (L * Math.cos(((theta2 - theta1) * Math.PI / 180)) - (x1 + l * Math.cos(theta1 * Math.PI / 180)));
    }

    public double[] getArmAngleTargets(double extension, double height)
    {
        double[] temp = { 0, 0 };
        return temp;
    }
}
