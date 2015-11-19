package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Robotics on 11/19/2015.
 */
public class Autonomous5459 extends AutonomousBase5459 {
    public Autonomous5459(){

    }

    @Override
    public void start(){
        super.start();
        ResetAllEncoders();
        v_state = 0;
    }

    @Override
    public void loop(){
        switch (v_state){

            case 0:
                ResetAllEncoders();
                v_state++;
                 
            break;

            case 1:
                run_using_encoders();
                set_drive_power(0.5f, 0.5f);

                if(have_drive_encoders_reached (2880, 2880)){
                    set_drive_power(0.0f, 0.0f);
                    v_state++;
                }
            break;
        }

    }
    private int v_state;
}
