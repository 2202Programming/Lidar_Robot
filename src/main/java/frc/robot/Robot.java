/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CanbusDistanceSensor;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public static Motor_Subsystem  m_motor = new Motor_Subsystem();
  public static XboxController m_controller = new XboxController(0);
  public static int distanceSensorLoad = 20;
  public static int distanceSensorLoad2 = 21;
  public static byte[] hwdataLoad = new byte[8];
  public static double loadSensorSerial;
  public static int b = 0; 
  
  //distance between two lidar
  public static final double LIDAR_DIST = 57.15;




  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    int[] temp = { 0, 0 };
    int[] temp2 = {0, 0};
  
      if(b%20 == 0){

      temp = CanbusDistanceSensor.getDistanceMM(distanceSensorLoad);

      double lidarValue = (double)temp[0];
      
        SmartDashboard.putNumber("Lidar 20", Math.round(lidarValue));

      }

      if(b%20 == 10){

        temp2 = CanbusDistanceSensor.getDistanceMM(distanceSensorLoad2);
  
        double lidarValue = (double)temp2[0];
        
          SmartDashboard.putNumber("Lidar 21", Math.round(lidarValue));
  
        }

      //to print angle to smart dashboard
      if(b%20 == 15){
        temp = CanbusDistanceSensor.getDistanceMM(distanceSensorLoad);
        temp2 = CanbusDistanceSensor.getDistanceMM(distanceSensorLoad2);

        double dist1 = Math.round((double)temp[0]);
        double dist2 = Math.round((double)temp2[0]);
        double angle = findAngle(dist1, dist2);

        SmartDashboard.putNumber("Angle", angle);

      }


      b++;
      
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  //finds angle of surface lidar is hitting
  public double findAngle(double dist1, double dist2){

    double difference = dist1 - dist2;

    double angle = Math.atan(difference/LIDAR_DIST) * 100;

    System.out.println("Dist1: " + dist1 + "Dist2: " + dist2 + "angle: " + angle);

    return angle;

  }
}
