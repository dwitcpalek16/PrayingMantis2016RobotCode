package org.usfirst.frc.team302.robot.subsystems;

/**
 * This is a class used to get the angles and height and extension of the arm at
 * various different setpoint positions
 * 
 * Arm setpoints are in the format: {height, extension, first joint angle, second joint angle}
 * 
 * <h1>Change Log:</h1>
 * 
 * <b>version 1:</b>
 *          <p>
 *          1/29/2016 --Derek Witcpalek -- Original creation of the setpoints;
 *          they still need to be set
 *          </p>
 * version 2: 3/3/2016 -- Joe Witcpalek Add Review comments
 * 
 * 
 * @author Derek
 * @version version 2: 3/3/2016
 * 
 */

/*
 * Review Comments
 * 1) Don't need as many significant digits on your set points
 * 2) I'd contend that it would be more readable/maintainable if each state were in a separate 
 *    class and you had an interface such as IArmSetpoints that each implemented.  So, 
 *    there would be ChevalDeFriseDownSetpoints that implements this interface for instance.
 *    The interface could have the following methods:  getHeight, getExtension, getShoulderJointAngle,
 *    getElbowJointAngle. Then the Arm class which is the only one that calls it could have an
 *    array of IArmSetpoints objects that corresponds to the ArmState enum (which could move to 
 *    Arm.java).  These would be created with for each set point.  Then where you are currently 
 *    getPosition and dealing with indexing into the array (e.g. ... 
 *          ArmSetpoints.getPosition(Target)[2]))
 *    you would 
 *          setPoints[Target].getFirstAngle()
 *          
 *    This isolates each setPoint from all of the others, making your code more robust 
 *    (you don't have to be concerned that you will be tweaking the Sally Port end hook
 *    set point and inadvertently change the drawbridge start end point).
 */
public class ArmSetpoints
{

    // Arm setpoints are in the format: {height, extension, first joint angle, second joint angle}

    private static final double[] CHEVAL_DE_FRISE_DOWN_SETPOINT = { 1.0141371013307108, 10.001163268061898, 55, 3.5 };								// good
    private static final double[] DRAWBRIDGE_END_SETPOINT = { 1.0141371013307108, 10.001163268061898, 55, 3.5 };								// good
    private static final double[] PORTCULLIS_START_SETPOINT = { 3.005999671708121, 10.010186260606531, 65, 2.8 };
    private static final double[] CHEVAL_DE_FRISE_START_SETPOINT = { 11.986974792778978, 9.998582217334643, 10.19999999999998, 11.19999999999997 };	// good
    private static final double[] SALLY_PORT_END_HOOK_SETPOINT = { 23.98490905846637, 10.003140110594842, 40, 25 };									// good
    private static final double[] DRAWBRIDGE_HOOK_SETPOINT = { 27.974091722725284, 9.993767579922412, 45, 46 };									// good
    private static final double[] SALLY_PORT_START_SETPOINT = { 27.974091722725284, 9.993767579922412, 35, 40 };									// good
    private static final double[] DRAWBRIDGE_START_SETPOINT = { 36.993079081105925, 9.997517968723486, 40, 55 };									// good
    private static final double[] PORTCULLIS_END_SETPOINT = { 36.993079081105925, 9.997517968723486, 27.40000000000012, 48.30000000000041 };
    private static final double[] CLIMB_END_SETPOINT = { 36.993079081105925, 9.997517968723486, 27.40000000000012, 48.30000000000041 };
    private static final double[] EXTENDED_TO_PREPARE_TO_CLIMB_SETPOINT = { 83.00191717396177, 5.015826018141382, 87.89999999999928,
            163.49999999999 };
    private static final double[] CLIMB_HOOK_SETPOINT = { 76.00342150938539, 9.980707180825094, 73.00000000000013, 131.0999999999968 };
    private static final double[] CLIMB_SETPOINT = { 76.00342150938539, 9.980707180825094, 73.00000000000013, 131.0999999999968 };
    private static final double[] STARTING_POSITION_SETPOINT = { 43.02171180527519, 9.993163342193576, 32.700000000000195, 58.2000000000005 };

    public static enum ArmState
    {
        CHEVAL_DE_FRISE_DOWN, 
        DRAWBRIDGE_END, 
        PORTCULLIS_START, 
        CHEVAL_DE_FRISE_START, 
        SALLY_PORT_END_HOOK, 
        DRAWBRIDGE_HOOK, 
        SALLY_PORT_START, 
        DRAWBRIDGE_START, 
        PORTCULLIS_END, 
        CLIMB_END, 
        EXTENDED_TO_PREPARE_TO_CLIMB, 
        CLIMB_HOOK, 
        CLIMB, 
        STARTING_POSITION
    }

    /**
     * Call this method to get the description of a position
     * 
     * @param state
     *            this is the state you want to get the position of
     * 
     * @return returns a position descriptor array in the form: {height,
     *         extension, first joint angle, second joint angle}
     */
    public static double[] getPosition(ArmState state)
    {
        double[] value = null;
        switch (state)
        {
        case CHEVAL_DE_FRISE_DOWN:
            value = CHEVAL_DE_FRISE_DOWN_SETPOINT;
            break;
        case DRAWBRIDGE_END:
            value = DRAWBRIDGE_END_SETPOINT;
            break;
        case PORTCULLIS_START:
            value = PORTCULLIS_START_SETPOINT;
            break;
        case CHEVAL_DE_FRISE_START:
            value = CHEVAL_DE_FRISE_START_SETPOINT;
            break;
        case SALLY_PORT_END_HOOK:
            value = SALLY_PORT_END_HOOK_SETPOINT;
            break;
        case DRAWBRIDGE_HOOK:
            value = DRAWBRIDGE_HOOK_SETPOINT;
            break;
        case SALLY_PORT_START:
            value = SALLY_PORT_START_SETPOINT;
            break;
        case DRAWBRIDGE_START:
            value = DRAWBRIDGE_START_SETPOINT;
            break;
        case PORTCULLIS_END:
            value = PORTCULLIS_END_SETPOINT;
            break;
        case CLIMB_END:
            value = CLIMB_END_SETPOINT;
            break;
        case EXTENDED_TO_PREPARE_TO_CLIMB:
            value = EXTENDED_TO_PREPARE_TO_CLIMB_SETPOINT;
            break;
        case CLIMB_HOOK:
            value = CLIMB_HOOK_SETPOINT;
            break;
        case CLIMB:
            value = CLIMB_SETPOINT;
            break;
        case STARTING_POSITION:
            value = STARTING_POSITION_SETPOINT;
            break;
        default:
            value = null;
            break;
        }

        return value;
    }
}
