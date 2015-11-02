package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog; // for telemetry?
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo; // for eventual servos
import com.qualcomm.robotcore.util.Range; // for scaling
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.hardware.ModernRoboticsNxtDcMotorController; // for our legacy motor controllers? ***

//------------------------------------------------------------------------------

public class TestBotHardware extends OpMode {

	// DcMotorController drive_controller_left, drive_controller_right; // PUT THIS BACK IN SI NXT NO FUNCIONA
	DcMotor drive_left_front, drive_left_back, drive_right_front, drive_right_back;             //       ^^
    //LegacyModule legacy;                                                                        //       ^^
    //ModernRoboticsNxtDcMotorController drive_controller_left_NXT, drive_controller_right_NXT; // for NXT ^^

    public TestBotHardware () {}

    @Override
    public void init () {

    	drive_left_front = hardwareMap.dcMotor.get("Drive_Left_Front");
    	drive_left_front.setDirection(DcMotor.Direction.REVERSE);
        drive_left_back = hardwareMap.dcMotor.get("Drive_Left_Back");
        drive_left_back.setDirection(DcMotor.Direction.REVERSE);

    	drive_right_front = hardwareMap.dcMotor.get("Drive_Right_Front");
        drive_right_back = hardwareMap.dcMotor.get("Drive_Right_Back");

        //legacy = hardwareMap.legacyModule.get("Legacy");

        //drive_controller_left = hardwareMap.dcMotorController.get("Drive_Controller_Left");
        //drive_controller_right = hardwareMap.dcMotorController.get("Drive_Controller_Right");


        v_warning_generated = false; // for telemetry
        v_warning_message = "Can't map; ";

        //
        // Connect the drive wheel motors.
        //
        // The direction of the right motor is reversed, so joystick inputs can
        // be more generically applied.
        //
        try
        {
            drive_left_front = hardwareMap.dcMotor.get ("Drive_Left_Front");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("Drive_Left_Front");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            drive_left_front = null;
        }

        try
        {
            drive_left_back = hardwareMap.dcMotor.get ("Drive_Left_Back");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("Drive_Left_Back");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            drive_left_back = null;
        }

        try
        {
            drive_right_front = hardwareMap.dcMotor.get ("Drive_Right_Front");
            drive_right_front.setDirection (DcMotor.Direction.REVERSE);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("Drive_Right_Front");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            drive_right_front = null;
        }

        try
        {
            drive_right_back = hardwareMap.dcMotor.get ("Drive_Right_Back");
            drive_right_back.setDirection (DcMotor.Direction.REVERSE);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("Drive_Right_Back");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            drive_right_back = null;
        }

    } // init

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

    } // m_warning_message

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start () {
        // Only actions that are common to all Op-Modes (i.e. both automatic and
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //
    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Perform any actions that are necessary while the OpMode is running.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override public void loop () {
        //
        // Only actions that are common to all OpModes (i.e. both auto and\
        // manual) should be implemented here.
        // This method is designed to be overridden.
    } // loop

    //The system calls this member once when the OpMode is disabled.
    @Override public void stop () {
        // Nothing needs to be done for this method.
    } // stop

    // nonlinear joystick-scaler.
    float scale_motor_power (float p_power) {

        // Assume no scaling.
        float l_scale = 0.0f;

        // Ensure the values are legal.

        float l_power = Range.clip (p_power, -1, 1);

        float[] l_array =
            { 0.00f, 0.05f, 0.09f, 0.10f, 0.12f
            , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
            , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
            , 1.00f, 1.00f
            };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
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

    } // scale_motor_power

    //--------------------------------------------------------------------------
    //
    // a_DriveL_power
    //
    /**
     * Access the left drive motor's power level.
     */
    //gen-p drive function
    double drive_power (DcMotor motor) {
        double l_return = 0.0;

        if (motor != null) {
            l_return = motor.getPower ();
        }
        return l_return;
    }

   /* double drive_left_front_power ()
    {
        double l_return = 0.0;

        if (drive_left_front != null)
        {
            l_return = drive_left_front.getPower ();
        }

        return l_return;

    } // drive_left_front_power

    double drive_left_back_power ()
    {
        double l_return = 0.0;

        if (drive_left_back != null)
        {
            l_return = drive_left_back.getPower ();
        }

        return l_return;

    } // drive_left_back_power

    //--------------------------------------------------------------------------
    //
    // drive_right_power
    //
    /
     * Access the right drive motor's power level.

    double drive_right_front_power ()
    {
        double l_return = 0.0;

        if (drive_right_front != null)
        {
            l_return = drive_right_front.getPower ();
        }

        return l_return;
    }

    double drive_right_back_power ()
    {
        double l_return = 0.0;

        if (drive_right_back != null)
        {
            l_return = drive_right_back.getPower ();
        }

        return l_return;
    } // a_DriveR_power

    */
    //--------------------------------------------------------------------------
    //
    // set_drive_power
    //
    /**
     * Scale the joystick input using a nonlinear algorithm.
     */
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

    //--------------------------------------------------------------------------
    //
    // run_using_Drive_left_front_encoder
    //
    /**
     * Set the left drive wheel encoder to run, if the mode is appropriate.
     */

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
    /*
    public void run_using_drive_left_front_encoder ()

    {
        if (drive_left_front != null)
        {
            drive_left_front.setChannelMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    } // run_using_drive_left_front_encoder

    public void run_using_drive_left_back_encoder ()

    {
        if (drive_left_back != null)
        {
            drive_left_back.setChannelMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    } 

    //--------------------------------------------------------------------------
    //
    // run_using_drive_right_front_encoder
    //
    /
      Set the right drive wheel encoder to run, if the mode is appropriate.
     /
    public void run_using_drive_right_front_encoder ()

    {
        if (drive_right_front != null)
        {
            drive_right_front.setChannelMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    } // run_using_DriveR_encoder

    public void run_using_drive_right_back_encoder ()

    {
        if (drive_right_back != null)
        {
            drive_right_back.setChannelMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    } 

    //--------------------------------------------------------------------------
    //
    // run_using_encoders
    //
    *
     * Set both drive wheel encoders to run, if the mode is appropriate.

   /* public void run_all_encoders ()

    {
        //
        // Call other members to perform the action on both motors.
        //
        run_using_encoders(drive_left_front);
        run_using_encoders (drive_left_back);
        run_using_encoders (drive_right_front);
        run_using_encoders (drive_right_back);
    } // run_using_encoders

    //--------------------------------------------------------------------------
    //
    // run_without_encoder

     * Set the left drive wheel encoder to run, if the mode is appropriate.

    public void run_without_drive_left_front_encoder ()

    {
        if (drive_left_front != null)
        {
            if (drive_left_front.getChannelMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                drive_left_front.setChannelMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    } // run_without_DriveL_encoder

    public void run_without_drive_left_back_encoder ()

    {
        if (drive_left_back != null)
        {
            if (drive_left_back.getChannelMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                drive_left_back.setChannelMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    } // run_without_DriveL_encoder

    //--------------------------------------------------------------------------
    //
    // run_without_DriveR_encoder
    //

      Set the right drive wheel encoder to run, if the mode is appropriate.

    public void run_without_drive_right_front_encoder ()

    {
        if (drive_right_front != null)
        {
            if (drive_right_front.getChannelMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                drive_right_front.setChannelMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    } // run_without_DriveR_encoder

    public void run_without_drive_right_back_encoder ()

    {
        if (drive_right_back != null)
        {
            if (drive_right_back.getChannelMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                drive_right_back.setChannelMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    } // run_without_DriveR_encoder
*/
    //--------------------------------------------------------------------------
    //
    // run_without_drive_encoders
    //
    /**
     * Set both drive wheel encoders to run, if the mode is appropriate.
     */


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

    //--------------------------------------------------------------------------
    //
    // reset_DriveL_encoder
    //
    /*
     * Reset the left drive wheel encoder.

    public void reset_drive_left_front_encoder ()

    {
        if (drive_left_front != null)
        {
            drive_left_front.setChannelMode
                ( DcMotorController.RunMode.RESET_ENCODERS
                );
        }

    } // 

    public void reset_drive_left_back_encoder ()

    {
        if (drive_left_back != null)
        {
            drive_left_back.setChannelMode
                ( DcMotorController.RunMode.RESET_ENCODERS
                );
        }

    }

    //--------------------------------------------------------------------------
    //
    // reset_DriveR_encoder
    //

     * Reset the right drive wheel encoder.

    public void reset_drive_right_front_encoder ()

    {
        if (drive_right_front != null)
        {
            drive_right_front.setChannelMode
                ( DcMotorController.RunMode.RESET_ENCODERS
                );
        }

    } // reset_DriveR_encoder

    public void reset_drive_right_back_encoder ()

    {
        if (drive_right_back != null)
        {
            drive_right_back.setChannelMode
                ( DcMotorController.RunMode.RESET_ENCODERS
                );
        }

    } 
*/
    //--------------------------------------------------------------------------
    //
    // reset_drive_encoders
    //
    /**
     * Reset both drive wheel encoders.
     */

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

    /*
    //--------------------------------------------------------------------------
    //
    // left_encoder_count
    //

     * Access the left encoder's count.

    int left_front_encoder_count ()
    {
        int l_return = 0;

        if (drive_left_front != null)
        {
            l_return = drive_left_front.getCurrentPosition ();
        }

        return l_return;

    } // left_encoder_count

    int left_back_encoder_count ()
    {
        int l_return = 0;

        if (drive_left_back != null)
        {
            l_return = drive_left_back.getCurrentPosition ();
        }

        return l_return;

    } 

    //--------------------------------------------------------------------------
    //
    // right_encoder_count
    //

    int right_front_encoder_count ()

    {
        int l_return = 0;

        if (drive_right_front != null)
        {
            l_return = drive_right_front.getCurrentPosition ();
        }

        return l_return;

    } // right_encoder_count

    int right_back_encoder_count ()

    {
        int l_return = 0;

        if (drive_right_back != null)
        {
            l_return = drive_right_back.getCurrentPosition ();
        }

        return l_return;

    }

    */

    int encoder_count (DcMotor motor)
    {
        int l_return = 0;

        if (motor != null)
        {
            l_return = motor.getCurrentPosition ();
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //
    // has_DriveL_encoder_reached
    //
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

        //
        // Have the motor shafts turned the required amount?
        //
        // If they haven't, then the op-mode remains in this state (i.e this
        // block will be executed the next time this method is called).
        //
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

    //--------------------------------------------------------------------------
    //
    // have_drive_encoders_reset
    //
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

    } // have_drive_encoders_reset

    //--------------------------------------------------------------------------
    //
    // v_warning_generated
    //
    /**
     * Indicate whether a message is a available to the class user.
     */
    private boolean v_warning_generated = false;

    //--------------------------------------------------------------------------
    //
    // v_warning_message
    //
    /**
     * Store a message to the user if one has been generated.
     */
    private String v_warning_message;

    //--------------------------------------------------------------------------
    //
    // driveL
    //
    /**
     * Manage the aspects of the left drive motor.
     */
   // private DcMotor drive_left_front;
   // private DcMotor drive_left_back;

    //--------------------------------------------------------------------------
    //
    // driveR
    //
    /**
     * Manage the aspects of the right drive motor.
     */
//    private DcMotor drive_right_front;
//    private DcMotor drive_left_back;
} // PushBotHardware
