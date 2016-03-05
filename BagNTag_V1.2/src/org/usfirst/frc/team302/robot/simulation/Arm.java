package org.usfirst.frc.team302.robot.simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.JPanel;

public class Arm extends JPanel implements Runnable
{

    int m_x_base;
    int m_y_base;

    double m_theta1;
    double m_theta2;

    final double h = 10;
    final double L = 48;
    final double l = 32;
    final double x1 = 1.5;
    final double extension = 14;
    final boolean readCustom = true;

    final double scale = 4;

    int m_currentLineInPoints;

    String fileName = "C:/Users/Derek/Desktop/AngleCalculations/angleCalculations" + (int) extension + "InchExtension.csv";

    String fileName2 = "C:/Users/Derek/Desktop/Path.csv";

    FileReader inputFile;
    BufferedReader buffReader;

    Vector<double[]> angles;

    public Arm(int x, int y)
    {
        m_x_base = x;
        m_y_base = y;
        m_currentLineInPoints = 0;
        angles = new Vector<double[]>(0);

        try
        {
            System.out.println("Trying to open file...");
            inputFile = new FileReader(fileName);
            buffReader = new BufferedReader(inputFile);

            String line;
            System.out.println("Trying to read file...");
            while ((line = buffReader.readLine()) != null)
            {
                System.out.println("Reading file...");

                double[] temp = { Double.parseDouble(line.split(", ")[2]), Double.parseDouble(line.split(", ")[3]),
                        Double.parseDouble(line.split(", ")[0]), Double.parseDouble(line.split(", ")[1]) };
                angles.add(temp);
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void move(double theta1, double theta2)
    {
        m_theta1 = theta1;
        m_theta2 = theta2;
        // System.out.println(this.getGraphics());
        // System.out.println(this.getParent());
        paintComponent(this.getGraphics());
    }

    @Override
    public void run()
    {
        move(angles.get(m_currentLineInPoints)[0], angles.get(m_currentLineInPoints)[1]);
        m_currentLineInPoints++;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        g.setColor(Color.black);

        // draw base pivot
        g.fillRect(m_x_base - 5, m_y_base - 5, 10, 10);

        // print out data
        g.drawString("Theta 1: " + angles.get(m_currentLineInPoints)[0], 10, 50);
        g.drawString("Theta 2: " + angles.get(m_currentLineInPoints)[1], 10, 70);
        g.drawString("Height: " + angles.get(m_currentLineInPoints)[2], 10, 30);
        g.drawString("Extension: " + angles.get(m_currentLineInPoints)[3], 10, 10);

        // draw robot base
        g.drawRect((int) (m_x_base - x1 * scale), m_y_base, (int) (36 * scale), (int) (10 * scale));

        // draw first arm segment
        g.drawLine((int) m_x_base, (int) m_y_base, (int) (m_x_base + (scale * l * Math.cos(m_theta1 * Math.PI / 180))),
                (int) (m_y_base - (scale * l * Math.sin(m_theta1 * Math.PI / 180))));

        // draw second arm segment
        g.drawLine((int) ((m_x_base + scale * (l * Math.cos(m_theta1 * Math.PI / 180)))),
                (int) ((m_y_base - scale * (l * Math.sin(m_theta1 * Math.PI / 180)))),
                (int) (m_x_base + scale * (l * Math.cos(m_theta1 * Math.PI / 180)) - scale * (L * Math.cos((m_theta2 - m_theta1) * Math.PI / 180))),
                (int) ((m_y_base - scale * (l * Math.sin(m_theta1 * Math.PI / 180))
                        - scale * (L * Math.sin((m_theta2 - m_theta1) * Math.PI / 180)))));

        g.setColor(Color.GREEN);

        // extension line (path that the arm follows)
        g.drawLine((int) (m_x_base - ((extension * scale) + scale * x1)), (int) (m_y_base + h * scale),
                (int) (m_x_base - (extension * scale) - scale * x1), (int) (m_y_base - 320));

        // max extension line
        g.drawLine((int) (m_x_base - (15 * scale) - (x1 * scale)), (int) (m_y_base + h * scale), (int) (m_x_base - (15 * scale) - (x1 * scale)),
                m_y_base - 320);

        // climbing bar
        g.drawLine((int) (m_x_base - (extension * scale) - scale * x1), (int) (m_y_base + h * scale - scale * (6 * 12 + 4)),
                (int) (m_x_base - (extension * scale) - scale * x1 + 5.25 * scale), (int) (m_y_base + h * scale - scale * (6 * 12 + 4)));

        g.drawRect((int) (m_x_base - 15 * scale - x1 * scale), (int) (m_y_base + h * scale), 10, 10);
    }

    public boolean isDone()
    {
        return (m_currentLineInPoints == (angles.size())) ? true : false;
    }

}
