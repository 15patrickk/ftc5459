public class RNCBH extends Base5459 {
    public RNCBH() { }

    int v_state = 0;
    
    @Override
    public void loop() {

        imu.getIMUGyroAngles(rollAngle, pitchAngle, yawAngle);

        \\use the quaternions! yawAngle[1], pitchAngle[1], rollAngle[1].

        switch (v_state) {
            case 0:
                wire.setPosition(0.5);
                v_state++;
                try { Thread.sleep(1000); }
                catch(Exception ex) { }
                break;
            case 1:
                ziplineLeft.setPosition(1);
                try { Thread.sleep(1000); }
                catch(Exception ex) { }
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
