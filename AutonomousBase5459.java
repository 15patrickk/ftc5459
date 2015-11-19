package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.ftcrobotcontroller.opmodes.Teleop5459;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.content.Context;
/**
 * Created by Robotics on 10/29/2015.
 */
public class AutonomousBase5459 extends OpMode {

    DcMotor drive_left_front;
    DcMotor drive_left_back;
    DcMotor drive_right_front;
    DcMotor drive_right_back;

    String ml1 = Teleop5459.ML1;
    String ml2 = Teleop5459.ML2;
    String mr1 = Teleop5459.MR1;
    String mr2 = Teleop5459.MR2;
    boolean check;

    //private SensorManager mSensorManager;
    //private Sensor mSensor;


    public AutonomousBase5459() {


    }

    @Override
    public void init(){

        //Teleop5459 Teleop5459vars = new Teleop5459();
       // String ML1 = Teleop5459vars.ML1;

        drive_left_front = hardwareMap.dcMotor.get(ml1);
        drive_left_front.setDirection(DcMotor.Direction.REVERSE);
        drive_left_back = hardwareMap.dcMotor.get(ml2);
        drive_left_back.setDirection(DcMotor.Direction.REVERSE);

        drive_right_front = hardwareMap.dcMotor.get(mr1);
        drive_right_back = hardwareMap.dcMotor.get(mr2);

        //mSensorManager = this.getSystemService(Context.SENSOR_SERVICE);
        //mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public void loop(){

    }

   //AFTER THIS COPY AND PASTED FROM TESTBOTHARDWARE ON SATURDAY OCTOBER 31ST SCARRY RIGHT?
    //BOOM
    //BOOM
    //BOOM


    // warning?
    boolean a_warning_generated () {
        return v_warning_generated;
    } // a_warning_generated

    // what is the warning?
    String a_warning_message () {
        return v_warning_message;
    } // a_warning_message

    // make a warning
    void m_warning_message (String p_exception_message) {

        if ( v_warning_generated ) {
            v_warning_message += ", "; }

        v_warning_generated = true;
        v_warning_message += p_exception_message;

    }

    @Override public void start () {
        // Only actions that are common to all Op-Modes (i.e. both automatic and manual) should be implemented here.

    }


    public float scale_motor_power (float p_power) {

        // Assume no scaling.
        float l_scale = 0.0f;

        // Ensure the values are legal.

        float l_power = Range.clip(p_power, -1, 1);

        float[] l_array =
                { 0.00f, 0.05f, 0.09f, 0.10f, 0.12f
                        , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
                        , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
                        , 1.00f, 1.00f
                };


        // Get the corresponding index for the specified argument/parameter.
        int l_index = (int)(l_power * 16.0);

        if (l_index < 0) {
            l_index = -l_index;
        }
        else if (l_index > 16) {
            l_index = 16;
        }

        if (l_power < 0) {
            l_scale = -l_array[l_index];
        }
        else {
            l_scale = l_array[l_index];
        }
        return l_scale;
    }

    //gen-p drive function
    double drive_power (DcMotor motor) {
        double l_return = 0.0;

        if (motor != null) {
            l_return = motor.getPower ();
        }
        return l_return;
    }

   public void set_drive_power (double p_left_power, double p_right_power) {
        if (drive_left_front != null) {
            drive_left_front.setPower (p_left_power);
        }

        if (drive_left_back != null) {
            drive_left_back.setPower (p_left_power);
        }

        if (drive_right_front != null) {
            drive_right_front.setPower (p_right_power);
        }
       if (drive_right_back != null) {
            drive_right_back.setPower(p_right_power);
        }
    }

    public void run_using_encoders () {

        if (drive_left_front != null) {
            drive_left_front.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
        if (drive_left_back != null) {
            drive_left_back.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
        if (drive_right_front != null) {
            drive_right_front.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
        if (drive_right_back != null) {
            drive_right_back.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    }



    //TURN OFF specific encoder
    public void run_without_drive_encoder (DcMotor motor) {
        if (motor != null) {
            if (motor.getChannelMode () == DcMotorController.RunMode.RESET_ENCODERS) {
                motor.setChannelMode
                        ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
    }

    //turn off All encoders.
    public void run_without_encoders () {
        run_without_drive_encoder(drive_left_front);
        run_without_drive_encoder(drive_left_back);
        run_without_drive_encoder(drive_right_front);
        run_without_drive_encoder(drive_right_back);
    }

    //RESET one drive encoder
    public void ResetMotorEncoder (DcMotor motor) {
        if (motor != null) {
            motor.setChannelMode
                    (DcMotorController.RunMode.RESET_ENCODERS);
        }
    }


    public void ResetAllEncoders () {
        ResetMotorEncoder(drive_left_front);
        ResetMotorEncoder(drive_left_back);
        ResetMotorEncoder(drive_right_front);
        ResetMotorEncoder(drive_right_back);
    }

   public int EncoderCount (DcMotor motor) {
        int l_return = 0;

        if (motor != null) {
            l_return = motor.getCurrentPosition ();
        }
        return l_return;
    }

    boolean has_left_front_encoder_reached (double p_count) {
        boolean l_return = false;

        if (drive_left_front != null) {
            // TODO Implement stall code using these variables.
            if (Math.abs (drive_left_front.getCurrentPosition ()) > p_count) {
                l_return = true;
            }
        }
        return l_return;
    }

    boolean has_left_back_encoder_reached (double p_count) {
        boolean l_return = false;

        if (drive_left_back != null) {
            // TODO Implement stall code using these variables.
            if (Math.abs (drive_left_back.getCurrentPosition ()) > p_count) {
                l_return = true;
            }
        }
        return l_return;
    }


    boolean has_right_front_encoder_reached (double p_count) {
        boolean l_return = false;

        if (drive_right_front != null) {
            if (Math.abs (drive_right_front.getCurrentPosition ()) > p_count) {
                l_return = true;
            }
        }


        return l_return;
    }


    boolean has_right_back_encoder_reached (double p_count) {
        boolean l_return = false;

        if (drive_right_back != null) {
            if (Math.abs (drive_right_back.getCurrentPosition ()) > p_count)
            {
                l_return = true;
            }
        }
        return l_return;
    }


    boolean have_drive_encoders_reached ( double p_left_count, double p_right_count) {

        boolean l_return = false;

        if (has_left_front_encoder_reached (p_left_count) && has_left_back_encoder_reached (p_left_count) &&
            has_right_front_encoder_reached (p_right_count) && has_right_back_encoder_reached (p_right_count))
        {

            l_return = true;
        }
        return l_return;
    }


    public boolean Drive( double p_left_power, double p_right_power, double p_left_count, double p_right_count) {
        boolean l_return = false;
        run_using_encoders();
        set_drive_power (p_left_power, p_right_power);

        if (!check){
            ResetAllEncoders();
            check = true;
        }
        // TODO: 11/19/2015  just a place holder
        if (have_drive_encoders_reached (p_left_count, p_right_count))
        {
            ResetAllEncoders();
            set_drive_power (0.0f, 0.0f);
            l_return = true;
            check = false;
        }
        return l_return;
    }


    boolean has_left_front_encoder_reset () {
        boolean l_return = false;

        if (EncoderCount(drive_left_front) == 0)
        {
            l_return = true;
        }
        return l_return;
    }


    boolean has_left_back_encoder_reset () {
        boolean l_return = false;

        if (EncoderCount(drive_left_back) == 0)
        {
            l_return = true;
        }
        return l_return;
    }


    boolean has_right_front_encoder_reset () {
        boolean l_return = false;

        if (EncoderCount(drive_right_front) == 0)
        {
            l_return = true;
        }
        return l_return;

    }

    boolean has_right_back_encoder_reset () {
        boolean l_return = false;
        if (EncoderCount(drive_right_back) == 0);
        {
            l_return = true;
        }
        return l_return;

    }



    boolean have_drive_encoders_reset () {
        boolean l_return = false;

        if (has_left_front_encoder_reset () && has_left_back_encoder_reset () &&
                has_right_front_encoder_reset () && has_right_back_encoder_reset ())
        {
            l_return = true;
        }
        return l_return;

    }

    /**
     * Indicate whether a message is a available to the class user.
     */
    private boolean v_warning_generated = false;

    /**
     * Store a message to the user if one has been generated.
     */
    private String v_warning_message;


}
