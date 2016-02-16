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

    public DelayChoose() {
        timeDelay.delete() // purge old delay on runtime
    }

    @Override
    public void loop() {
        // driver display
        String timeDisplay = String.format("Time: %d", waitTime);
        telemetry.addData("01", timeDisplay);
        if(!hasChosen) { telemetry.addData("02", "Choosing..."); }
        else { telemetry.addData("02", "Delay chosen! Change OpMode now.");

        // read the gamepads. DPad must be released between each press.
        if(gamepad1.dpad_up && !dpadUp) {
            waitTime++;
        }
        else if(gamepad1.dpad_down && !dpadDown) {
            waitTime = waitTime > 0 ? waitTime-- : waitTime; // clip the time at 0
        }
        else if(gamepad1.a) {
            try {
                hasChosen = true;
                PrintWriter pw = new PrintWriter(timeDelay);
                pw.println(waitTime);
                pw.close()
            }
            catch(Exception e) {
                telemetry.addData("03", "Unable to write out to file!");
            }
        }

        // set the debouncing variables to the current gamepad state
        dpadUp = gamepad1.dpad_up;
        dpadDown = gamepad1.dpad_down;
    }
}
