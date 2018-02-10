package org.firstinspires.ftc.teamcode;

/*
 * WoodyBot: defines hardware on the robot.
 * This is NOT an opmode. DO NOT put code that controls motors or servos here.
 *
 * multiple authors, February 2018
 */

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WoodyBot {
    /* define all hardware on robot with global variables */
    // motors
    public DcMotor FrontMotorLeft;
    public DcMotor FrontMotorRight;
    public DcMotor Elevator1;
    public DcMotor Extender;

    // servos
    public Servo left_door;
    public Servo claw;
    public Servo rotator;
    public Servo ColorSense;
    public Servo Stupid;
    public Servo TwistyThingy;

    // sensors
    public ColorSensor CS;

    // internal variables (DO NOT MODIFY)
    HardwareMap hwMap = null;
    protected ElapsedTime period = new ElapsedTime();
    final String vKey = "AbFBF+7/////AAAAGa/gIXOlBktXs2sS6yoBjiIuh1aNum98eoDbvnjL19QRg5cvOdwMqBPOqfpp1EBPRmxEsInZKXFnwsNZendSBGEmdkf2nnSY3kTzHUXvr9VzNknBFPKHG1VVfKNLqscbUkiFVj8ubGv0xgl09aTZFg1qc+QkBkpNEZBHoIzCzK+M4qhRkdhq0glhmX8H/AdEeJG22S3PPylBXG03ZZZAtCozcN5PBIrvkZg+hg66vbfPzoAzQZCifsfzmT3LHAiiNvX69DBmfllexEBSxuqDID2y8toMFWx/4D4N+55hO7QcQc2qhLBMSwqKRfvGNDlc6ELcIuROkUPA5hXX/GJyrARwqc4vI3xvHMNBTFWp/Zkz";

    public WoodyBot() { }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map

        // initialize drive motors
        // +1 moves robot forward
        // -1 moves robot backward
        FrontMotorLeft = hwMap.dcMotor.get("FrontMotorLeft");
        FrontMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FrontMotorRight = hwMap.dcMotor.get("FrontMotorRight");
        FrontMotorRight.setDirection(DcMotor.Direction.REVERSE);
        FrontMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // initialize other motors
        Extender = hwMap.dcMotor.get("Extend");
        Elevator1 = hwMap.dcMotor.get("Elevator1");

        // initialize servos
        claw = hwMap.servo.get("claw");
        ColorSense = hwMap.servo.get("ColorSense");
        left_door = hwMap.servo.get("left_door");
        rotator = hwMap.servo.get("rot");
        Stupid = hwMap.servo.get("stupidstick");
        TwistyThingy = hwMap.servo.get("TwistyThingy");

        // initialize sensors
        CS = hwMap.get(ColorSensor.class, "CS");
    }


    //*
    //* waitForTick implements a periodic delay. However, this acts like a metronome with a regular
    //* periodic tick.  This is used to compensate for varying processing times for each cycle.
    //* The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
    //*
    //* @param periodMs  Length of wait cycle in mSec.
    //* @throws InterruptedException

    public void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}