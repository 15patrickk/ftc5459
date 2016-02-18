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
        String timeDisplay = String.format("Time: %d", waitTime);
        telemetry.addData("01", timeDisplay);
        if(!hasChosen) { telemetry.addData("02", "Choosing..."); }
        else { telemetry.addData("02", "Delay chosen! Change OpMode now."); }

        // read the gamepads. if any key is press, add a small debounce delay
        if(gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.a) {
            if(gamepad1.dpad_up) {
                waitTime++;
            }
            else if(gamepad1.dpad_down) {
                waitTime = waitTime > 0 ? waitTime-- : waitTime; // clip the time at 0
            }
            else if(gamepad1.a) {
                try {
                    hasChosen = true;
                    PrintWriter pw = new PrintWriter(timeDelay);
                    pw.println(waitTime);
                    pw.close();
                }
                catch(Exception e) {
                    telemetry.addData("03", "Unable to write out to file!");
                }
            }
            try { Thread.sleep(300); }
            catch(Exception e) { }
        }
    }
}
