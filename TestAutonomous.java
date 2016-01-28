package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Robotics on 1/11/2016.
 */
public class TestAutonomous extends AutonomousBase5459 {
    TestAutonomous () {};

    @Override
    public void loop () {
        run_using_encoders();
        ResetAllEncoders();
        PID();
        if(EncoderCount(drive_left_front) < 50) {
            set_drive_power(50, 50);
        }
            set_drive_power(0, 0);
            ResetAllEncoders();
        servoleft.setPosition(1.5);
    }
}
