package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range; //didnt need to do this, only used y input on controllers

/**
 * Created by Robotics on 10/26/2015.
 */
public class Teleop5459Minimal extends OpMode {
    Servo servoleft;
    Servo servoright;
    public static final String SL = "ServoLeft";
    public static final String SR = "ServoRight";

    // thresholds
    public static final double SPALeft = 0.0;
    public static final double SPARight = 1.0;
    public static final double SPCLeft = 0.5;
    public static final double SPCRight = 0.5;
    public static final double SPBLeft = 0.9;
    public static final double SPBRight = 0.1;

    // left inits 0.0
    // right inits 1.0
    // left position1 0.8
    // left position2 0.4
    // right position1 0.2
    // right position2 0.6

    boolean servoLeftPosition = false; // false = 0.8 /\ true = 0.4
    boolean servoRightPosition = false; // false = 0.2 /\ true = 0.6

    public long counter;
    public static final long threshhold = 50;

    public Teleop5459Minimal() { // self constructor
    }

    @Override
    public void init() {
        servoleft = hardwareMap.servo.get("ServoLeft");
        servoright = hardwareMap.servo.get("ServoRight");

        servoleft.setPosition(0.0);
        servoright.setPosition(1.0);

        counter = 0;
    }

    @Override
    public void loop() {
        boolean runServoLeft = gamepad2.x;
        boolean runServoRight = gamepad2.b;

        if(counter > threshhold) {
            if (gamepad2.x) { // left servo
                double setPos = !servoLeftPosition ? 0.5 : 0.9;
                servoleft.setPosition(setPos);
                servoLeftPosition = !servoLeftPosition;

                counter = 0;
            }
            if (gamepad2.b) { // right servo
                double setPos = !servoRightPosition ? 0.45 : 0.05;
                servoright.setPosition(setPos);
                servoRightPosition = !servoRightPosition;

                counter = 0;
            }

        }

        counter++;
    }
}
