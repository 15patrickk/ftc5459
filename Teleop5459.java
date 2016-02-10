package com.qualcomm.ftcrobotcontroller.opmodes;

public class Teleop5459 extends Base5459 {
    boolean ziplineLeftPosition = false; // false = 0.8 /\ true = 0.4
    boolean ziplineRightPosition = false; // false = 0.2 /\ true = 0.6

    public Teleop5459() { }

    @Override
    public void loop() {
        // continuous controls
        double ThrottleLeft = gamepad1.left_stick_y;
        double ThrottleRight = gamepad1.right_stick_y;
        float Angle = gamepad2.right_stick_y; // 0.5<y<1 Power level lower "Initial burst then slow"
        float Extend = -1 * gamepad2.left_stick_y;
        boolean runZiplineLeft = gamepad2.x;
        boolean runZiplineRight = gamepad2.b;

        // discrete controls
        if(counter > threshhold) { // debounces buttons
            // [[TODO: properly document what these buttons do]]
            // [[TODO: cleaner method of debouncing]]
            if (gamepad2.dpad_up) {
                double posL = rodLeft.getPosition();
                double posR = rodRight.getPosition();

                if((posR - 0.05) > 0.0 && (posL + 0.05) < 1.0) {
                    rodLeft.setPosition(posL + .05);
                    rodRight.setPosition(posR - .05);
                }

                counter = 0; 
            }

            if (gamepad2.dpad_down) {
                double posL = rodLeft.getPosition();
                double posR = rodRight.getPosition();

                if((posR + 0.05) < 1.0 && (posL - 0.05) > 0.0) {
                    rodLeft.setPosition(posL - .05);
                    rodRight.setPosition(posR + .05);
                }

                counter = 0;
            }

            if (gamepad2.dpad_left) {
                double posC = rodCenter.getPosition();

                rodCenter.setPosition(posC + .05);

                counter = 0;
            }

            if (gamepad2.dpad_right) {
                double posC = rodCenter.getPosition();

                rodCenter.setPosition(posC - .05);

                counter = 0;
            }

            if (gamepad2.x || gamepad2.b) {
                rodCenter.setPosition(RCi);

                counter = 0;
            }

            /*
            if (gamepad2.y || gamepad2.a) {
                rodLeft.setPosition(RLi);
                rodRight.setPosition(RRi);

                counter = 0;
            }
            */

            if (gamepad1.x) { // left servo
                double setPos = !ziplineLeftPosition ? 0.95 : 0.5;
                ziplineLeft.setPosition(setPos);
                ziplineLeftPosition = !ziplineLeftPosition;

                counter = 0;
            }

            if (gamepad1.b) { // right servo
                double setPos = !ziplineRightPosition ? 0.03 : 0.45;
                ziplineRight.setPosition(setPos);
                ziplineRightPosition = !ziplineRightPosition;

                counter = 0;
            }
        }

        // scale motor inputs
        // [[TODO: WTF are we doing casting double to float when original variable is a double?!]]
        ThrottleLeft = (float)scaleInputs(ThrottleLeft);
        ThrottleRight = (float)scaleInputs(ThrottleRight);
        //AngleLeft = (float)scaleInputs(AngleLeft);
        Angle = (float)scaleInputs(Angle);
        //ExtendLeft = (float)scaleInputs(ExtendLeft);
        Extend = (float)scaleInputs(Extend);

        // set the drive motor power
        drive_left_front.setPower(ThrottleLeft);
        drive_left_back.setPower(ThrottleLeft);
        drive_right_front.setPower(ThrottleRight);
        drive_right_back.setPower(ThrottleRight);

        counter++;
    }


    double scaleInputs(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
