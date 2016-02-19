package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.File;
import java.io.PrintWriter;
import android.os.Environment;

public class DelayChoose extends OpMode {
    File timeDelay = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "timer.txt");
    int waitTime = 0;
    boolean hasChosen = false;

    boolean dpadUp = false;
    boolean dpadDown = false;

    public DelayChoose() {}

    @Override
    public void init() {
        timeDelay.delete(); // purge old delay on runtime
    }

    @Override
    public void loop() {
        // driver display
        telemetry.addData("Seconds to delay", waitTime);
        if(!hasChosen) { telemetry.addData("", "Choosing..."); }
        else { telemetry.addData("", "Delay chosen! Change OpMode now."); }

        // read the gamepads. DPad must be released between each press.
        if(gamepad1.dpad_up && !dpadUp) {
            waitTime++;
        }

        if(gamepad1.dpad_down && !dpadDown) {
            waitTime--;
        }

        if(gamepad1.a) {
            waitTime = waitTime >= 0 ? waitTime : 0; // clip at 0
            try {
                hasChosen = true;
                PrintWriter pw = new PrintWriter(timeDelay);
                pw.println(waitTime);
                pw.close();
            }
            catch(Exception e) {
                telemetry.addData("Error!", "Unable to write out to file.");
            }
        }

        // set the debouncing variables to the current gamepad state
        dpadUp = gamepad1.dpad_up;
        dpadDown = gamepad1.dpad_down;
    }
}
