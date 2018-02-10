package org.firstinspires.ftc.teamcode;

/*
 * BasicTeleop: driver control software used in matches.
 * Almost all code that you need to write will be in the loop() method.
 *
 * multiple authors, February 2018
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="potato op", group="WoodyBot")
public class BasicTeleop extends OpMode {
    private WoodyBot robot = new WoodyBot();   // Use robot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    // code runs ONCE when driver hits INIT
    @Override
    public void init() {
        // Initialize the hardware variables. The init() method does all the work here
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting
        telemetry.addData("Say", "I like potatoes");    //
        updateTelemetry(telemetry);

        // disable the encoders since the robot will be under driver control
        robot.FrontMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.FrontMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // code runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() { }

    // code runs ONCE when the driver hits PLAY
    @Override
    public void start() {
        robot.ColorSense.setPosition(.6); // bring arm back up
        robot.TwistyThingy.setPosition(.5); // rotate back into the robot
    }

    // code runs REPEATEDLY when driver hits play
    // put driver controls (drive motors, servos, etc.) HERE
    @Override
    public void loop() {
        double elevup = gamepad2.right_trigger;
        boolean elevdown = gamepad2.right_bumper;
        boolean dooropen = gamepad2.b;
        boolean doorclose = gamepad2.a;
        double extendin = gamepad2.left_trigger;
        boolean extendout = gamepad2.left_bumper;
        boolean rotateup = gamepad2.y;
        boolean rotatedown = gamepad2.x;
        boolean clawclose = gamepad2.dpad_up;
        boolean clawopen = gamepad2.dpad_down;
        boolean left = gamepad1.dpad_left;
        boolean right = gamepad1.dpad_right;
        boolean stupidopen = gamepad2.left_stick_button;
        boolean stupidclose = gamepad2.right_stick_button;
        double ThrottleRight = gamepad1.right_stick_y;
        double ThrottleLeft = gamepad1.left_stick_y;

        //slow-mo mode
        if(gamepad1.right_bumper) {
            robot.FrontMotorLeft.setPower(ThrottleLeft /4);
            robot.FrontMotorRight.setPower(ThrottleRight / 4);
        }
        else {
            robot.FrontMotorLeft.setPower(ThrottleLeft / 2);
            robot.FrontMotorRight.setPower(ThrottleRight / 2);
        }
        // this is technically the correct way to do this. -PK
//        double ElevatorPower = 0;
//        if(gamepad1.left_bumper == true) {
//            ElevatorPower = -1;
//            telemetry.addData("Elevator","UP");
//        }
//        else if(gamepad1.left_trigger > 0.85) {
//            ElevatorPower = 1;
//            telemetry.addData("Elevator", "DOWN");
//        }
//        else {
//            ElevatorPower = 0;
//            telemetry.addData("Elevator","OFF");
//        }
//if(right == true)
//{
   // robot.SideMotor.setPower(1.0);
//}
//if(right == false)
//{
 //   robot.SideMotor.setPower(0.0);
//}
//if(left == true)
//{
  //  robot.SideMotor.setPower(-1.0);
//}
//if(left == false)
//{
   // robot.SideMotor.setPower(0.0);
//}
        if(stupidopen==true)
        {
            robot.Stupid.setPosition(1);
        }
        else if (stupidclose) {
                robot.Stupid.setPosition(0);
        }
        if (rotateup == true) {
            robot.rotator.setPosition(.7);
        }
        else if (rotatedown == true) {
            robot.rotator.setPosition(0);
        }
        if (clawopen) {
            robot.claw.setPosition(1);
        }
        else if (clawclose) {
            robot.claw.setPosition(0.5);
        }
        if (extendout) {
            robot.Extender.setPower(.5);
        } else if (extendout == false) {
            robot.Extender.setPower(0);
        }
        if (dooropen) {
            robot.left_door.setPosition(.5);
            //robot.right_door.setPosition(0.0);
            telemetry.addData("door", "open");
        }
        else if (doorclose) {
            robot.left_door.setPosition(.9);
            //robot.right_door.setPosition(0.3);
            telemetry.addData("door", "closed");

        }
        if (elevup > 0) {
            robot.Elevator1.setPower(-1.0);
            telemetry.addData("elevator", "up");
        } else if (elevup == 0) {
            robot.Elevator1.setPower(0.0);
            telemetry.addData("elevator", " stopped");
        }

        if (elevdown) {
            robot.Elevator1.setPower(1.0);
            telemetry.addData("elevator", " down");

        }
        else if (!elevdown) {
            robot.Elevator1.setPower(0.0);
            telemetry.addData("elevator", " stopped");
        }

        updateTelemetry(telemetry);
    }
}