package com.qualcomm.ftcrobotcontroller.opmodes;

public class RNCBH extends Base5459 {
    public RNCBH() { }

    int v_state = 0;
    @Override
    public void loop() {
        switch (v_state) {
            case 0: // release wire servo
                wire.setPosition(0.5);
                v_state++;
                try { Thread.sleep(200); }
                catch(Exception ex) { }
                break;
            case 1: // drive forward 1 square (2 ft)
                drive(24, 0.75);
                try { Thread.sleep(200); }
                catch(Exception ex) { }
                v_state++;
                break;
            case 2: // turn 45 degrees counterclockwise
                turn_gyro_nPID(45, 0.5);
                try { Thread.sleep(200); }
                catch(Exception ex) { }
                v_state++;
                break;
            case 3: // drive forward 2 squares
                drive(48, 0.75);
                try { Thread.sleep(200); }
                catch(Exception ex) { }
                v_state++;
                break;
            case 4: // turn 45 degrees counterclockwise
                turn_gyro_nPID(45, 0.5);
                try { Thread.sleep(200); }
                catch(Exception ex) { }
                v_state++;
                break;
            case 5: // attempt to drive forward 3/4 square
                drive(9, 0.75);
                try { Thread.sleep(200); }
                catch(Exception ex) { }
                v_state++;
                break;
            case 6: // sense distance from wall; deduce number of blocks between wall and robot
                break;

            default:
                stop(); // [[TODO: test this to see if it works properly]]
        }
    }

    @Override
    public void stop() {
        drive_left_front.setPower(0);
        drive_left_back.setPower(0);
        drive_right_front.setPower(0);
        drive_right_back.setPower(0);
    }
}