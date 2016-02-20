package com.qualcomm.ftcrobotcontroller.opmodes;

public class Teleop5459 extends Base5459 {
    boolean ziplineLeftPosition = false; // false = 0.8 /\ true = 0.4
    boolean ziplineRightPosition = false; // false = 0.2 /\ true = 0.6
    boolean pushPosition = false;
    boolean scaling = false;

    double scaleFactor = 1;

    public Teleop5459() { }

    @Override
    public void loop() {
        // continuous controls
        double ThrottleLeft = gamepad1.left_stick_y;
        double ThrottleRight = gamepad1.right_stick_y;
        double ThrottleRod = gamepad2.right_stick_y;
        boolean runZiplineLeft = gamepad2.x;
        boolean runZiplineRight = gamepad2.b;

        // discrete controls
        if(counter > debounceThreshold) { // debounces buttons
            // [[TODO: cleaner method of debouncing]]
            if (gamepad2.dpad_up) { // takes L/R rods up
                double posL = rodLeft.getPosition();
                double posR = rodRight.getPosition();

                if((posR - 0.05) > 0.0 && (posL + 0.05) < 1.0) {
                    rodLeft.setPosition(posL + .05);
                    rodRight.setPosition(posR - .05);
                }
                counter = 0;
            }

            if (gamepad2.dpad_down) { // takes L/R rods down
                double posL = rodLeft.getPosition();
                double posR = rodRight.getPosition();

                if((posR + 0.05) < 1.0 && (posL - 0.05) > 0.0) {
                    rodLeft.setPosition(posL - .05);
                    rodRight.setPosition(posR + .05);
                    counter = 0;
                }
            }

            if (gamepad2.dpad_left) { // takes C rod left
                rodCenter.setPosition(0.54);
                counter = 0;
            } else if (gamepad2.dpad_right) { // moves C rod right
                rodCenter.setPosition(0.46);
                counter = 0;
            } else { // stops C rod motion
                rodCenter.setPosition(0.5);
            }

            if (gamepad1.a) { // toggle block pusher up/down
                double setPos = !pushPosition ? PSi : 0.5;
                push.setPosition(setPos);
                pushPosition = !pushPosition;

                counter = 0;
            }
		
    		if (gamepad2.a) {
    			double setPos = !pushPosition ? PSi : 0.5;
    			push.setPosition(setPos);
    			pushPosition = !pushPosition;

    			counter = 0;
    		}

            // toggles for third zipline
            if (gamepad1.left_bumper) { // toggle L zipline
                double setPos = !ziplineLeftPosition ? ZLi : 0.8;
                ziplineLeft.setPosition(setPos);
                ziplineLeftPosition = !ziplineLeftPosition;

                counter = 0;
            }

            if (gamepad1.right_bumper) { // toggle R zipline
                double setPos = !ziplineRightPosition ? ZRi : 0.20;
                ziplineRight.setPosition(setPos);
                ziplineRightPosition = !ziplineRightPosition;

                counter = 0;
            }

            if (gamepad1.x) { // toggle L zipline
                double setPos = !ziplineLeftPosition ? ZLi : 0.55;
                ziplineLeft.setPosition(setPos);
                ziplineLeftPosition = !ziplineLeftPosition;

                counter = 0;
            }

            if (gamepad1.b) { // toggle R zipline
                double setPos = !ziplineRightPosition ? ZRi : 0.45;
                ziplineRight.setPosition(setPos);
                ziplineRightPosition = !ziplineRightPosition;

                counter = 0;
            }

            if(gamepad2.right_bumper) {
                cb.setPosition(1.0);

                counter = 0;
            }

            if(gamepad2.left_bumper) {
                cb.setPosition(0.0);

                counter = 0;
            }

            if(gamepad2.left_stick_button) {
                cb.setPosition(CBi);

                counter = 0;
            }

            if (gamepad1.y) { // scale drive motors
                scaling = !scaling;
                scaleFactor = scaling ? 0.2 : 1;

                counter = 0;
            }
            telemetry.addData("CR Servo", rodCenter.getPosition());
        }

        // scale motor inputs
        ThrottleLeft = scaleInputs(ThrottleLeft);
        ThrottleRight = scaleInputs(ThrottleRight);
        ThrottleRod = scaleInputs(ThrottleRod);

        // set the drive motor power
        drive_left_front.setPower(ThrottleLeft);
        drive_left_back.setPower(ThrottleLeft);
        drive_right_front.setPower(ThrottleRight);
        drive_right_back.setPower(ThrottleRight);

        lift.setPower(ThrottleRod);

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

        dScale *= scaleFactor;

        return dScale;
    }

}
