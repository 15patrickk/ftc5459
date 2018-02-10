package org.firstinspires.ftc.teamcode;

/*
 * ServoTestBench: debugging aid to determine the numerical positions of two servos.
 * To change the servos being tested, change the servoA and servoB variables.
 * To change the starting positions, change servoAPos and servoBPos.
 * Buttons:
 *   - X: increase servo A by 0.01
 *   - A: decrease servo A by 0.01
 *   - Y: increase servo B by 0.01
 *   - B: decrease servo B by 0.01
 *
 * Patrick Knight, February 2018
 */

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo test bench")
public class ServoTestBench extends OpMode {
    WoodyBot robot = new WoodyBot();   // Use robot's hardware

    Servo servoA = robot.TwistyThingy;
    double servoAPos = 1;

    Servo servoB = robot.ColorSense;
    double servoBPos = 1;

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

        servoA.setPosition(servoAPos);
        servoB.setPosition(servoBPos);

		/*
		 * Telemetry for debugging
		 */
        telemetry.addData("TwistyThingy", String.format("%.2f", servoAPos));
        telemetry.addData("ColorSense", String.format("%.2f", servoBPos));

        try{
            robot.waitForTick(60);
        }
        catch(Exception e) {
            telemetry.addLine("Missed wait time!");
        }
    }
}
