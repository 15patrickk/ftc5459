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

    String  ml1 = Teleop5459.ML1;
    String ml2 = Teleop5459.ML2;
    String mr1 = Teleop5459.MR1;
    String mr2 = Teleop5459.MR2;

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


    // do we has warning?
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


    float scale_motor_power (float p_power) {

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
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
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

    void set_drive_power (double p_left_power, double p_right_power)

    {
        if (drive_left_front != null)
        {
            drive_left_front.setPower (p_left_power);
        }
        if (drive_left_back != null)
        {
            drive_left_back.setPower (p_left_power);
        }

        if (drive_right_front != null)
        {
            drive_right_front.setPower (p_right_power);
        }
        if (drive_right_back != null)
        {
            drive_right_back.setPower(p_right_power);
        }

    } // set_drive_power



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



    // vers 1: turn off specific encoder
    public void run_without_drive_encoders (DcMotor motor)

    {
        if (motor != null)
        {
            if (motor.getChannelMode () ==
                    DcMotorController.RunMode.RESET_ENCODERS)
            {
                motor.setChannelMode
                        ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                        );
            }
        }

    }

    //vers 2: turn off All encoders.
    public void run_without_drive_encoders ()

    {
        //
        // Call other members to perform the action on both motors.
        //
        run_without_drive_encoders(drive_left_front);
        run_without_drive_encoders(drive_left_back);
        run_without_drive_encoders(drive_right_front);
        run_without_drive_encoders(drive_right_back);

    } // run_without_drive_encoders



    //vers 1: reset one drive encoder
    public void reset_drive_encoders (DcMotor motor)

    {
        if (motor != null)
        {
            motor.setChannelMode
                    (DcMotorController.RunMode.RESET_ENCODERS
                    );
        }

    }

    //vers 2: reset all encoders.
    public void reset_drive_encoders ()

    {
        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_drive_encoders(drive_left_front);
        reset_drive_encoders(drive_left_back);
        reset_drive_encoders(drive_right_front);
        reset_drive_encoders(drive_right_back);

    } // reset_drive_encoders



    int encoder_count (DcMotor motor)
    {
        int l_return = 0;

        if (motor != null)
        {
            l_return = motor.getCurrentPosition ();
        }

        return l_return;

    }


    /**
     * Indicate whether the left drive motor's encoder has reached a value.
     */
    boolean has_left_front_encoder_reached (double p_count)

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (drive_left_front != null)
        {
            //
            // Has the encoder reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (drive_left_front.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_DriveL_encoder_reached

    boolean has_left_back_encoder_reached (double p_count)

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (drive_left_back != null)
        {
            //
            // Has the encoder reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (drive_left_back.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_DriveL_encoder_reached

    //--------------------------------------------------------------------------
    //
    // has_DriveR_encoder_reached
    //
    /**
     * Indicate whether the right drive motor's encoder has reached a value.
     */
    boolean has_right_front_encoder_reached (double p_count)

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (drive_right_front != null)
        {
            //
            // Have the encoders reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (drive_right_front.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_DriveR_encoder_reached

    boolean has_right_back_encoder_reached (double p_count)

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (drive_right_back != null)
        {
            //
            // Have the encoders reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (drive_right_back.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }

        //
        // Return the status.
        //
        return l_return;

    }

    //--------------------------------------------------------------------------
    //
    // have_drive_encoders_reached
    //
    /**
     * Indicate whether the drive motors' encoders have reached a value.
     */
    boolean have_drive_encoders_reached ( double p_left_count, double p_right_count)

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Have the encoders reached the specified values?
        //
        if (has_left_front_encoder_reached (p_left_count) &&
                has_left_back_encoder_reached (p_left_count) &&
                has_right_front_encoder_reached (p_right_count) &&
                has_right_back_encoder_reached (p_right_count))
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // have_encoders_reached

    //--------------------------------------------------------------------------
    //
    // drive_using_encoders
    //
    /**
     * Indicate whether the drive motors' encoders have reached a value.
     */
    boolean drive_using_encoders( double p_left_power, double p_right_power,
                                  double p_left_count, double p_right_count)

    {
        //
        // Assume the encoders have not reached the limit.
        //
        boolean l_return = false;

        //
        // Tell the system that motor encoders will be used.
        //
        run_using_encoders ();

        //
        // Start the drive wheel motors at full power.
        //
        set_drive_power (p_left_power, p_right_power);

        // Have the motor shafts turned the required amount?
        // If they haven't, then the op-mode remains in this state (i.e this
        // block will be executed the next time this method is called).
        if (have_drive_encoders_reached (p_left_count, p_right_count))
        {
            //
            // Reset the encoders to ensure they are at a known good value.
            //
            reset_drive_encoders ();

            //
            // Stop the motors.
            //
            set_drive_power (0.0f, 0.0f);

            //
            // Transition to the next state when this method is called
            // again.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // drive_using_encoders

    //--------------------------------------------------------------------------
    //
    // has_DriveL_encoder_reset
    //
    /**
     * Indicate whether the left drive encoder has been completely reset.
     */
    boolean has_left_front_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Has the left front encoder reached zero?
        //
        if (encoder_count(drive_left_front) == 0)

        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_DriveL_encoder_reset

    boolean has_left_back_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Has the left front encoder reached zero?
        //
        if (encoder_count(drive_left_back) == 0)
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    }

    //--------------------------------------------------------------------------
    //
    // has_DriveR_encoder_reset
    //
    /**
     * Indicate whether the left drive encoder has been completely reset.
     */
    boolean has_right_front_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Has the right encoder reached zero?
        //
        if (encoder_count(drive_right_front) == 0)
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_DriveR_encoder_reset

    boolean has_right_back_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Has the right encoder reached zero?
        //
        if (encoder_count(drive_right_back) == 0);
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    }


    /**
     * Indicate whether the encoders have been completely reset.
     */
    boolean have_drive_encoders_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Have the encoders reached zero?
        //
        if (has_left_front_encoder_reset () && has_left_back_encoder_reset () &&
                has_right_front_encoder_reset () && has_right_back_encoder_reset ())
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
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
