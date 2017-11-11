/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="potato op", group="WoodyBot")
public class BasicTeleop extends OpMode {

    /* Declare OpMode members. */
    WoodyBot robot = new WoodyBot();   // Use robot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    double Rposition = 0.5;
    double Lposition = 0.5;
    // code runs ONCE when driver hits INIT
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "I like potatos");    //
        updateTelemetry(telemetry);
        if (robot.CS instanceof SwitchableLight) {
            ((SwitchableLight)robot.CS).enableLight(true);
        }
    }

    // code runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
    }

    // code runs ONCE when the driver hits PLAY
    @Override
    public void start() {
    }

    // code runs REPEATEDLY when driver hits play
    // put driver controls (drive motors, servos, etc.) HERE
    @Override
    public void loop() {
        //int color = robot.CS.red();
        double elevup = gamepad2.right_trigger;
        boolean elevdown = gamepad2.right_bumper;
        double ThrottleLeft = gamepad1.left_stick_y;
        double ThrottleRight = gamepad1.right_stick_y;
        boolean dooropen = gamepad2.b;
        boolean doorclose = gamepad2.a;
        double extendin = gamepad2.left_trigger;
        boolean extendout = gamepad2.left_bumper;
        boolean rotateup = gamepad2.y;
        boolean rotatedown = gamepad2.x;
        boolean clawclose = gamepad2.dpad_up;
        boolean clawopen = gamepad2.dpad_down;


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

        if (rotateup == true) {
            robot.rotator.setPosition(.7);
        }
        if (rotatedown == true) {
            robot.rotator.setPosition(0.0);
        }
        if (clawopen) {
            robot.claw.setPosition(1);
        }
        if (clawclose) {
            robot.claw.setPosition(0.0);
        }
        if (extendout) {
            robot.Extender.setPower(.5);
        } else if (extendout == false) {
            robot.Extender.setPower(0);
        }
        if (dooropen) {
            robot.left_door.setPosition(.5);
            robot.right_door.setPosition(0.0);
            telemetry.addData("door", "open");
        }
        if (doorclose) {
            robot.left_door.setPosition(.9);
            robot.right_door.setPosition(0.3);
            telemetry.addData("door", "closed");


        }
        if (elevup > .01) {
            robot.Elevator1.setPower(-1);
            telemetry.addData("elevator", "up");
        } else if (elevup == 0) {
            robot.Elevator1.setPower(0.0);
            telemetry.addData("elevator", " stopped");
        }
        if (elevdown == true) {
            robot.Elevator1.setPower(1.0);
            telemetry.addData("elevator", " down");

        } else if (elevdown == false) {
            robot.Elevator1.setPower(0.0);
            telemetry.addData("elevator", " stopped");
        }

        if (Rposition > 1) {
            Rposition = 1;
        } else if (Rposition < 0) {
            Rposition = 0;
        }
        if (Lposition > 1) {
            Lposition = 1;
        } else if (Rposition < 0) {
            Rposition = 0;
        }


        robot.FrontMotorLeft.setPower(ThrottleLeft / 1.5);
        robot.FrontMotorRight.setPower(-ThrottleRight / 1.5);
        //color = robot.CS.red();
        // telemetry
        //telemetry.addData("Red",color);
        telemetry.addData("left", "%.2f", ThrottleLeft);
        telemetry.addData("right", "%.2f", ThrottleRight);
        NormalizedRGBA colors;
        colors = robot.CS.getNormalizedColors();

        /** Use telemetry to display feedback on the driver station. We show the conversion
         * of the colors to hue, saturation and value, and display the the normalized values
         * as returned from the sensor.
         * @see <a href="http://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html">HSV</a>*/


        telemetry.addLine()
                .addData("a", "%.3f", colors.alpha)
                .addData("r", "%.3f", colors.red)
                .addData("g", "%.3f", colors.green)
                .addData("b", "%.3f", colors.blue);


        updateTelemetry(telemetry);
    }
}

