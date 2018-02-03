package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by 5459 on 2/2/2018.
 */

@TeleOp(name="Servo test bench")
public class ServoTestBench extends OpMode {
    WoodyBot robot = new WoodyBot();   // Use robot's hardware
    double servoAPos = 1;
    double servoBPos = 1 ;

    @Override
    public void init() { robot.init(hardwareMap); }

    @Override
    public void loop() {
        // testbench code
        if(gamepad1.x) {
            servoAPos = (servoAPos >= 1.0) ? 1.0 : servoAPos + 0.01;
        }

        if(gamepad1.a) {
            servoAPos = (servoAPos <= 0.0) ? 0.0 : servoAPos - 0.01;
        }

        if(gamepad1.y) {
            servoBPos = (servoBPos >= 1.0) ? 1.0 : servoBPos + 0.01;
        }

        if(gamepad1.b) {
            servoBPos = (servoBPos <= 0.0) ? 0.0 : servoBPos - 0.01;
        }

        robot.TwistyThingy.setPosition(servoAPos);
        robot.ColorSense.setPosition(servoBPos);

		/*
		 * Telemetry for debugging
		 */
        telemetry.addData("TwistyThingy", String.format("%.2f", servoAPos));
        telemetry.addData("ColorSense", String.format("%.2f", servoBPos));

        try{
            robot.waitForTick(60);
        }
        catch(Exception e) {}
    }
}
