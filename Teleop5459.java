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

    DcMotor lift_angle_left;
    DcMotor lift_angle_right;
    DcMotor lift_extend_left;
    DcMotor lift_extend_right;

    public static final String ML3 = "Lift_Angle_Left";
    public static final String MR3 = "Lift_Angle_Right";
    public static final String ML4 = "Lift_Extend_Left";
    public static final String MR4 = "Lift_Extend_Right";

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
        //drive_right_back.setDirection(DcMotor.Direction.REVERSE);

        lift_angle_left = hardwareMap.dcMotor.get(ML3);
        lift_angle_right = hardwareMap.dcMotor.get(MR3);

        lift_extend_left = hardwareMap.dcMotor.get(ML4);
        lift_extend_right = hardwareMap.dcMotor.get(MR4);


    }

    @Override
    public void loop() {

        float ThrottleLeft = gamepad1.left_stick_y;
        float ThrottleRight = gamepad1.right_stick_y;
        float AngleLeft = gamepad2.left_stick_y; //COnfigure new joyalgo - 0<y<0.5 = igher power
        float AngleRight = gamepad2.right_stick_y; // 0.5<y<1 Power level lower "Initial burst then slow "
        float ExtendLeft = gamepad2.left_trigger;
        float ExtendRight = gamepad2.right_trigger;
        boolean RetractLeft = gamepad2.left_bumper;
        boolean RetractRight = gamepad2.right_bumper;

        boolean Airhorn = gamepad1.dpad_left; // need to import sound from com.android.sti.stocksoundeffects.res.raw

        ThrottleLeft = (float)scaleInputs(ThrottleLeft);
        ThrottleRight = (float)scaleInputs(ThrottleRight);
        AngleLeft = (float)scaleInputs(AngleLeft);
        AngleRight = (float)scaleInputs(AngleRight);
        ExtendLeft = (float)scaleInputs(ExtendLeft);
        ExtendRight = (float)scaleInputs(ExtendRight);

        drive_left_front.setPower(ThrottleLeft);
          drive_left_back.setPower(ThrottleLeft);
        drive_right_front.setPower(ThrottleRight);
        drive_right_back.setPower(ThrottleRight);

        if (RetractLeft){
            lift_extend_left.setDirection(DcMotor.Direction.FORWARD);
        }

        else{
            lift_extend_left.setDirection(DcMotor.Direction.REVERSE);
        }

        if (RetractRight){
            lift_extend_right.setDirection(DcMotor.Direction.REVERSE);
        }

        else {
            lift_extend_right.setDirection(DcMotor.Direction.FORWARD);
        }

        lift_angle_left.setPower(AngleLeft);
        lift_angle_right.setPower(AngleRight);
        lift_extend_left.setPower(ExtendLeft);
        lift_extend_right.setPower(ExtendRight);





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
