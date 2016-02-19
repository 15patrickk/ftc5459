package com.qualcomm.ftcrobotcontroller.opmodes;

public class RNCBH extends Base5459 {
    public RNCBH() { }

    double distance_from_wall = 0;

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
            case 6: // sense distance from wall
                distance_from_wall = opticalLeft.getLightDetected() * clear_wall;
                v_state++;
                break;
            case 7: // sense color of beacon light (LEFT SIDE)
                color.enableLed(false);
                if(color.blue() > 127) {
                    if(distance_from_wall < 3) { // MEASURE/TEST
                        // swing a little
                    } else {
                        // swing a lot
                    }
                } else {
                    if(distance_from_wall < 3) {
                        // swing backwards, a little
                    } else {
                        // swing backwards, a lot
                    }
                }
                v_state++;
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