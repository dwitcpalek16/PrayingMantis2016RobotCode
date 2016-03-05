package org.usfirst.frc.team302.robot.utilities;

/**
 * This class is designed to help coordinate the movement between the two joints
 * of the arm by comparing their errors and adjusting outputs accordingly.
 * 
 * <h1>Change Log:</h1>
 * 
 * @version version 1: 2/14/2016 -- Derek Witcpalek
 *          <p>
 *          Initial class creation with a static method to output scale factors
 *          to adjust the outputs by.
 *          </p>
 * 
 * @author Derek
 *
 */
public class CoordinatedOutput
{

    /**
     * @author Derek
     * 
     *         This method is used to get scale factors to coordinate two motions
     *         so that the targets is reached at similar moments in time.
     * 
     * @param error1
     *            The error of the first joint
     * @param error2
     *            The error of the second joint
     * 
     * @return scale factors The scales to correct the outputs with.
     *         These are intended to be in the form:
     *         {first joint, second joint}, but it returns
     *         a scale for the first error in array position 0
     *         and a scale for the second error in array
     *         position 1.
     */

    // TODO change names to scale1 and scale2
    public static double[] coordinateOutputs(double error1, double error2)
    {
        double output1 = 1.;
        double output2 = 1.;
        double[] output = { 0.000000, 0. };

        double scale = 1.;

        // avoid divide by zero errors
        if (error1 == 0 || error2 == 0)
        {
            output1 = 1.;
            output2 = 1.;
        }

        // make the second output smaller if the first joint's error is larger
        else if (Math.abs(error1) > Math.abs(error2))
        {
            scale = Math.abs(error2 / error1);
            output1 = 1.;
            output2 = error2 * scale;// TODO check
        }

        // make the first output smaller if the second joint's error is larger
        else if (Math.abs(error2) > Math.abs(error1))
        {
            scale = Math.abs(error1 / error2);
            output1 = error1 * scale;// TODO check
            output2 = 1.;
        }
        else
        {
            output1 = 1.;
            output2 = 1.;
        }

        output[0] = output1;
        output[1] = output2;

        return output;// TODO check that numbers are not so small that scales are huge
    }
}
