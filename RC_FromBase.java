package com.qualcomm.ftcrobotcontroller.opmodes;

public class RC_FromBase extends AutonomousBase5459 {
    public RC_FromBase() { }

    public void runOpMode() throws InterruptedException {
        int v_state = 0;

        while (opModeIsActive()) {
            switch (v_state) {

                case 0:
                    wire.setPosition(0.5);
                    v_state++;
                    break;
            }
        }
    }
}