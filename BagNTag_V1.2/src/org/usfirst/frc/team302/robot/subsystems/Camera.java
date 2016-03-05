package org.usfirst.frc.team302.robot.subsystems;

import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.Point;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * This class is used to find the vision targets on the goals
 * 
 * <h1>Change Log:</h1>
 * 
 * @version version 1: 2/27/2016 -- Derek Witcpalek
 *          <p>
 *          Initial working class with the ability to find good targets at 18 feet and
 *          return the x and y coordinates of the good target
 *          </p>
 *          
 *          
 * @author Derek
 *
 */
public class Camera {

	
	private static Camera m_Camera = null;

	private int session;
	
	private Image frame;
	private Image binaryFrame;
	
	private final int m_MinimumHue = 0;
	private final int m_MaximumHue = 140;
	private final int m_MinimumSaturation = 250;
	private final int m_MaximumSaturation = 360;
	private final int m_MinimumValue = 200;
	private final int m_MaximumValue = 360;
	
	private final double m_TargetRatio = 0.7;
	
	public static final int m_CameraImageWidth = 640;
	public static final int m_CameraImageHeight = 480;
	
	private int m_TargetX;
	private int m_TargetY;
	
	
	private boolean targetFound = false;
	
	private enum Data
	{
		X,
		Y,
		TOP,
		LEFT,
		BOTTOM,
		RIGHT,
		AREA,
		HEIGHT,
		WIDTH
	}
	
	
	/**
	 * Creator -- You can't call this 
	 * HA, HA, HA....................
	 * 
	 * @param name	what camera are you using
	 */
	private Camera(String name)
	{
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        binaryFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
		
		session = NIVision.IMAQdxOpenCamera(name,
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		
        NIVision.IMAQdxConfigureGrab(session);
        
        updateImage();
        CameraServer.getInstance().setImage(frame);
	}
	
	
	/**
	 * Call this to get the camera object
	 * This is a singleton class
	 * 
	 * @param name	the name of the camera you are using
	 * @return		the camera object
	 */
	public static Camera getCamera(String name)
	{
		if(m_Camera == null)
		{
			m_Camera = new Camera(name);
		}
		
		return m_Camera ;
	}
	
	
	/**
	 * Gets the current image from the camera and stores it in an image variable
	 */
	private void updateImage()
	{
		NIVision.IMAQdxGrab(session, frame, 1);
	}
	
	
	
	/**
	 * Get the proportion of height to width
	 * 
	 * @param height -- ???
	 * @param width -- ???
	 * 
	 * @return	ratio of height to width (in the form height/width)
	 */
	private double getTargetProportionalityScore(double height, double width) 
    {
    	//actual ratio should be 0.7
    	double ratio = height / width;
    	
    	return ratio;    	
    }
	
	
	/**
	 * Get an image draw the crosshairs on the target for drivers and put it on the camera
	 */
	public void getAndSetImage()
	{
		updateImage();
		drawCrosshairs();
		CameraServer.getInstance().setImage(frame);
	}
	
	
	/**
	 * Draw a bounding box around the target and set the target x and y attribute 
	 */
	 public void findTarget()
    {
    	int bestTargetIndex = -1;
    	
        updateImage();
        

        //filter the image using HSV values to get a black and white image of matches
        NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, 
				new NIVision.Range(m_MinimumHue, m_MaximumHue), 
				new NIVision.Range(m_MinimumSaturation, m_MaximumSaturation), 
				new NIVision.Range(m_MinimumValue, m_MaximumValue));	        
        
        
        //Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);
        
		

		//Create a vector to store the particles that are determined to be within the width range
		//and also are within a tolerance of the correct ratio of height to width
		Vector<double[]> goodParticles = new Vector<double[]>();
		
		
		
		//analyze the particles in the binary image
		for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
		{
			double x = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
			double y = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
			
			double top = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			double left = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			double bottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			double right = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			
			double area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
			SmartDashboard.putNumber("Particle 0 area:", area);
			
			
			double[] data = {x, y, top, left, bottom, right, area, bottom-top, right-left};
			
			
//				particles.add(data);
			
			double ratio = getTargetProportionalityScore(data[Data.HEIGHT.ordinal()], data[Data.WIDTH.ordinal()]);
			
			SmartDashboard.putNumber("Ratio", ratio);
			
			
			//these are the requirements a particle has to meet to be considered good
			//ratio needs to be 50% to 120% of the target ratio
			//the width needs to be at least 0 and less than 150 pixels -- this will ensure that the
			//target is far enough away to realistically be a target
			if(ratio / m_TargetRatio <= 1.2 && ratio / m_TargetRatio >= 0.5 && (right - left) < 150 && (right - left) > 50)
			{
				goodParticles.add(data);
			}
		}
		
		SmartDashboard.putNumber("Good Particles", goodParticles.size());
		
		
		//have any of the filtered particles met the requirements for being good?
		if(goodParticles.size() != 0)
		{
			double bestWidth = 0;
			
			
			//find the best match by finding the match with the greatest width
			for(int index = 0; index < goodParticles.size(); index++)
			{
				if(goodParticles.get(index)[Data.WIDTH.ordinal()] > bestWidth)
				{
					bestTargetIndex = index;
				}
			}
			
			
			
			SmartDashboard.putNumber("top: ", goodParticles.get(bestTargetIndex)[Data.TOP.ordinal()]);
			SmartDashboard.putNumber("left: ", goodParticles.get(bestTargetIndex)[Data.LEFT.ordinal()]);
			SmartDashboard.putNumber("bottom: ", goodParticles.get(bestTargetIndex)[Data.BOTTOM.ordinal()]);
			SmartDashboard.putNumber("right: ", goodParticles.get(bestTargetIndex)[Data.RIGHT.ordinal()]);
			
			NIVision.Rect center = new NIVision.Rect((int)(goodParticles.get(bestTargetIndex)[Data.HEIGHT.ordinal()]/2-5+goodParticles.get(bestTargetIndex)[Data.TOP.ordinal()]), 
					(int)(goodParticles.get(bestTargetIndex)[Data.WIDTH.ordinal()]/2-5+goodParticles.get(bestTargetIndex)[Data.LEFT.ordinal()]), 10, 10);
			
			NIVision.Rect boundingRectangle = new NIVision.Rect((int)goodParticles.get(bestTargetIndex)[Data.TOP.ordinal()], 
					(int)goodParticles.get(bestTargetIndex)[Data.LEFT.ordinal()], 
					(int)(goodParticles.get(bestTargetIndex)[Data.HEIGHT.ordinal()]), 
					(int)(goodParticles.get(bestTargetIndex)[Data.WIDTH.ordinal()]));
			
			
			NIVision.imaqDrawShapeOnImage(frame, frame, center, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
			NIVision.imaqDrawShapeOnImage(frame, frame, boundingRectangle, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
			
			m_TargetX = (int) (goodParticles.get(bestTargetIndex)[Data.LEFT.ordinal()] + (goodParticles.get(bestTargetIndex)[Data.WIDTH.ordinal()] / 2));
			m_TargetY = (int) (goodParticles.get(bestTargetIndex)[Data.TOP.ordinal()] + (goodParticles.get(bestTargetIndex)[Data.HEIGHT.ordinal()] / 2));
		}
		else
		{
			m_TargetX = m_CameraImageWidth / 2;
			m_TargetY = m_CameraImageHeight  / 2;
		}
		if(bestTargetIndex != -1)
		{
			targetFound = true;
		}
		else
		{
			targetFound = false;
		}
		SmartDashboard.putNumber("Best target Index", bestTargetIndex);
		
		SmartDashboard.putNumber("X: ", m_TargetX);
		SmartDashboard.putNumber("Y: ", m_TargetY);
		
        CameraServer.getInstance().setImage(frame);
        
        goodParticles.clear();
    }

	 
	 /**
	  * Draw Crosshair Lines on the screen to help the driver find where the shooter will fire
	  */
	 private void drawCrosshairs()
	 {
		 NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(m_CameraImageWidth/2-50, m_CameraImageHeight/2), new Point(m_CameraImageWidth/2+50, m_CameraImageHeight/2), 1.0f);
		 NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, new Point(m_CameraImageWidth/2, m_CameraImageHeight/2-50), new Point(m_CameraImageWidth/2, m_CameraImageHeight/2+50), 1.0f);
	 }
	 
	 
	 /**
	  * This will get the x position of the last target found
	  * 
	  * @return	x-position	this is the x-position of the target
	  */
	 public int getX()
	 {
		 return m_TargetX;
	 }
	 
	 
	 /**
	  * This will get the y position of the last target found
	  * 
	  * @return	y-position	this is the y-position of the target
	  */
	 public int getY()
	 {
		 //y-positions in the vision code are measured from the top of an image, so this
		 //subracts the position the camera gives from the height of the image
		 return m_CameraImageHeight - m_TargetY;
	 }
	 
	 
	 public boolean isTargetFound()
	 {
		 return targetFound;
	 }
}
