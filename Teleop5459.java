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
    public static final String ML1 = "Drive_Left_Front";
    public static final String ML2 = "Drive_Left_Back";
    public static final String MR1 = "Drive_Right_Front";
    public static final String MR2 = "Drive_Right_Back";

    public Teleop5459() {

    }


    @Override
    public void init() {
        drive_left_front = hardwareMap.dcMotor.get(ML1);
        drive_left_front.setDirection(DcMotor.Direction.REVERSE);
        drive_left_back = hardwareMap.dcMotor.get(ML2);
        drive_left_back.setDirection(DcMotor.Direction.REVERSE);

        drive_right_front = hardwareMap.dcMotor.get(MR1);
        drive_right_back = hardwareMap.dcMotor.get(MR2);

    }

    @Override
    public void loop() {

        float ThrottleLeft = gamepad1.left_stick_y;
        float ThrottleRight = gamepad1.right_stick_y;
        boolean Airhorn = gamepad1.dpad_left; // need to import sound from com.android.sti.stocksoundeffects.res.raw

        ThrottleLeft = (float)scaleInputs(ThrottleLeft);
        ThrottleRight = (float)scaleInputs(ThrottleRight);

        drive_left_front.setPower(ThrottleLeft);
        drive_left_back.setPower(ThrottleLeft); //all of these set power functions want a double,
        drive_right_front.setPower(ThrottleRight); // but accept a float, why?
        drive_right_back.setPower(ThrottleRight); // check for potential problems and confirm by removeing comments


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
