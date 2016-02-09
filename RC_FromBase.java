package com.qualcomm.ftcrobotcontroller.opmodes;

public class RC_FromBase extends Base5459 {
    public RC_FromBase() { }

    int v_state = 0;
    @Override
    public void loop() {
        switch (v_state) {
            case 0:
                wire.setPosition(0.5);
                v_state++;
                break;
        }
    }
}