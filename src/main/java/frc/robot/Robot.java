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
//import frc.robot.CanbusDistanceSensor;
import frc.robot.LIDAR_Subsystem;
import frc.robot.PID;



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
  public static LIDAR_Subsystem m_lidar = new LIDAR_Subsystem();
  public static PID pid_distance = new PID(150);
  public static PID pid_angle = new PID(0);

  /*
  public static int distanceSensorLoad = 20;
  public static int distanceSensorLoad2 = 21;
  public static byte[] hwdataLoad = new byte[8];
  public static double loadSensorSerial;
  */
  public static int b = 0; 
  
  
  //distance between two lidar
 //public static final double LIDAR_DIST = 57.15;
  /*
  public double P = 1;
  public double I = 0.01;
  public double Error = 0.0;
  public double desiredAngle = 0.0;
  public double integral = 0.0;
  */
  public double powerRight;
  public double powerLeft;
  public double straightPower;
  public double turningPowerRight;
  public double turningPowerLeft;

  


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

    if(b == 0){
    double angle = m_lidar.findAngle();
    SmartDashboard.putNumber("Angle", angle);
   // pid_angle.rightMotor(angle);
    // pid_angle.leftMotor(angle);
   // double distance = m_lidar.getDistance();
    double power = pid_angle.getMotor(angle);
      if(power > 0){
        power = Math.abs(power);
        turningPowerRight = power;
        turningPowerLeft = 0;
      }
      else{
        power = Math.abs(power);
        turningPowerLeft = power;
        turningPowerRight = 0;
      }
    }
    if(b == 10){
      double distance = m_lidar.getDistance();
      SmartDashboard.putNumber("distance", distance);

      double power = pid_distance.getMotor(distance);

      straightPower = Math.abs(power);
    }

    powerRight = straightPower + turningPowerRight;
    powerLeft = straightPower + turningPowerLeft;


    SmartDashboard.putNumber("powerRight", powerRight);
    SmartDashboard.putNumber("powerLeft", powerLeft);
    SmartDashboard.putNumber("straightPower", straightPower);

    SmartDashboard.putNumber("turningPowerRight", turningPowerRight);
    SmartDashboard.putNumber("turningPowerLeft", turningPowerLeft);


    b++;

    if(b == 20){
      b = 0; 
    }
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
//PID for turning
/*
  public void PID(double angle){
    
    Error = angle - desiredAngle;

    integral += (Error*0.2); //Integral increased by error*time (0.2 seconds is a norm, possibly incorrect)
    
    SmartDashboard.putNumber("Integral", integral);

    if(angle > desiredAngle){

    powerRight = P*Math.abs(Error) +  I*Math.abs(integral);

    powerLeft = 0;

    }
    else{
      powerLeft = P*Math.abs(Error) +  I*Math.abs(integral);

      powerRight = 0; 
      
    }
    SmartDashboard.putNumber("Power Left", powerLeft);
    SmartDashboard.putNumber("Power Right", powerRight);



    
  }
  */
}
