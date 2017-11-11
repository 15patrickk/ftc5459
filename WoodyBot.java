package org.firstinspires.ftc.teamcode;
import android.graphics.Color;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.ar.pl.SensorController;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

/**
 * This is NOT an opmode.
 * For future changes, consult comments below.
 *
 */
public class WoodyBot {
 // define all hardware on robot

 public DcMotor FrontMotorLeft;
 public DcMotor FrontMotorRight;
 public DcMotor Elevator1;
 public DcMotor Extender;
 public Servo right_door;
 public Servo left_door;
 public Servo claw;
 public Servo rotator;
 public NormalizedColorSensor CS;


 /* local OpMode members. */

 HardwareMap hwMap           =  null;
 private ElapsedTime period  = new ElapsedTime();

 public WoodyBot(){ }

 /* Initialize standard Hardware interfaces */

 public void init(HardwareMap ahwMap) {
 hwMap = ahwMap; // reference to hardware map

 // initialize motors
 Extender = hwMap.dcMotor.get("Extend");
 Elevator1 = hwMap.dcMotor.get("Elevator1");
 FrontMotorLeft = hwMap.dcMotor.get("FrontMotorLeft");
 FrontMotorRight = hwMap.dcMotor.get("FrontMotorRight");
 //CS = hwMap.colorSensor.get("CS");
 right_door = hwMap.servo.get("right_door");
 left_door = hwMap.servo.get("left_door");
 claw = hwMap.servo.get("claw");
 rotator = hwMap.servo.get("rot");
  CS = hwMap.get(NormalizedColorSensor.class, "CS");
 // initialize servos

 // Set all motors and servos to zero power
 FrontMotorLeft.setPower(0.0);
 FrontMotorRight.setPower(0.0);
 Elevator1.setPower(0.0);
 //right_door.setPosition(0.0);   //Enabling these caused the servos to move in init, which is illegal
 //left_door.setPosition(0.0);
 claw.setPosition(0.0);
 rotator.setPosition(0.5);
// CS.enableLed(false);

 }


 //*
 //* waitForTick implements a periodic delay. However, this acts like a metronome with a regular
 //* periodic tick.  This is used to compensate for varying processing times for each cycle.
 //* The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
 //*
 //* @param periodMs  Length of wait cycle in mSec.
 //* @throws InterruptedException


 public void waitForTick(long periodMs) throws InterruptedException {

 long  remaining = periodMs - (long)period.milliseconds();

 // sleep for the remaining portion of the regular cycle period.
 if (remaining > 0)
 Thread.sleep(remaining);

 // Reset the cycle clock for the next pass.
 period.reset();
 }
 }