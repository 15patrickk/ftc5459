package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range; //didnt need to do this, only used y input on controllers

/**
 * Created by Robotics on 10/26/2015.
 */
public class Teleop5459 extends OpMode {

    DcMotor drive_left_front;
    DcMotor drive_left_back;
    DcMotor drive_right_front;
    DcMotor drive_right_back;
    Servo servoleft;
    Servo servoright;
    public static final String ML1 = "Drive_Front_Left";
    public static final String ML2 = "Drive_Back_Left";
    public static final String MR1 = "Drive_Front_Right";
    public static final String MR2 = "Drive_Back_Right";
    public static final String SL = "ServoLeft";
    public static final String SR = "ServoRight";

    public static final double SPALeft = 0.0;
    public static final double SPARight = 1.0;
    public static final double SPBLeft = 0.8;
    public static final double SPBRight = 0.3;
    public long counter;
    public static final long threshhold = 90;

    DcMotor lift_angle_left;
    DcMotor lift_angle_right;
    DcMotor lift_extend_left;
    DcMotor lift_extend_right;

    public static final String MLA = "LiftAngle";
    public static final String MLE = "LiftExtend";

    public Teleop5459() {

    }


    @Override
    public void init() {
        drive_left_front = hardwareMap.dcMotor.get(ML1);
        drive_left_front.setDirection(DcMotor.Direction.REVERSE);
        drive_left_back = hardwareMap.dcMotor.get(ML2);
        //drive_left_back.setDirection(DcMotor.Direction.REVERSE);
        drive_right_front = hardwareMap.dcMotor.get(MR1);
        //drive_right_front.setDirection(DcMotor.Direction.REVERSE);//comment out
        drive_right_back = hardwareMap.dcMotor.get(MR2);
        drive_right_back.setDirection(DcMotor.Direction.REVERSE);

        lift_angle_right = hardwareMap.dcMotor.get(MLA);
        lift_extend_right = hardwareMap.dcMotor.get(MLE);
        //lift_extend_right.setDirection(DcMotor.Direction.REVERSE);

        servoleft = hardwareMap.servo.get("ServoLeft");
        servoright = hardwareMap.servo.get("ServoRight");

        servoleft.setPosition(SPALeft);
        servoright.setPosition(SPARight);

        counter = 0;

    }

    @Override
    public void loop() {
        float ThrottleLeft = gamepad1.left_stick_y;
        float ThrottleRight = gamepad1.right_stick_y;
        float Angle = gamepad2.right_stick_y; // 0.5<y<1 Power level lower "Initial burst then slow "
        float Extend = gamepad2.left_stick_y;
        boolean runservoleft = gamepad2.x;
        boolean runservoright = gamepad2.b;
        boolean Airhorn = gamepad1.dpad_left; // need to import sound from com.android.sti.stocksoundeffects.res.raw

        if(runservoleft && (servoleft.getPosition() == SPALeft) && (counter > threshhold)) {
            servoleft.setPosition(SPBLeft);
            counter = 0;
        }
        else if(runservoleft && (counter > threshhold)){
            servoleft.setPosition(SPALeft);
            counter = 0;
        }
        if(runservoright && (servoright.getPosition() == SPARight) && (counter > threshhold)) {
            servoright.setPosition(SPBRight);
            counter = 0;
        }
        else if(runservoright && (counter > threshhold)){
            servoright.setPosition(SPARight);
            counter = 0;
        }

        ThrottleLeft = (float)scaleInputs(ThrottleLeft);
        ThrottleRight = (float)scaleInputs(ThrottleRight);
        //AngleLeft = (float)scaleInputs(AngleLeft);
        Angle = (float)scaleInputs(Angle);
        //ExtendLeft = (float)scaleInputs(ExtendLeft);
        Extend = (float)scaleInputs(Extend);

        drive_left_front.setPower(ThrottleLeft);
        drive_left_back.setPower(ThrottleLeft);
        drive_right_front.setPower(ThrottleRight);
        drive_right_back.setPower(ThrottleRight);

        lift_angle_right.setPower(Angle);
        lift_extend_right.setPower(Extend);

        counter ++;
    }


    double scaleInputs(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;

    }
}
