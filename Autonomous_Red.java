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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Autonomous: KnockOff Red", group="WoodyBot")
public class Autonomous_Red extends LinearOpMode {
    WoodyBot robot = new WoodyBot();
    private ElapsedTime runtime = new ElapsedTime();

    static final double FORWARD_SPEED = 0.7;
    static final double TURN_SPEED = 0.5;

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Slouching");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.ColorSense.setPosition(0.65); // arm straight up
        sleep(1000);
        robot.TwistyThingy.setPosition(0.13); // rotate sensor into position
        sleep(1000);
        robot.ColorSense.setPosition(0.03); // arm down
        sleep(2000);
        int red = robot.CS.red();
        int blue = robot.CS.blue();
        sleep(500);

        if(red > blue) { // facing red ball - move left/CCW to knock off red ball
            telemetry.addData("Color", "blue");
            telemetry.update();
            sleep(1000);
            robot.TwistyThingy.setPosition(0);
            sleep(1500);
            robot.ColorSense.setPosition(0.6); // retract arm
            sleep(1500);
        }
        if(red < blue) { // facing blue ball - move right/CW to knock off blue ball
            telemetry.addData("Color","red");
            telemetry.update();
            sleep(1000);
            robot.TwistyThingy.setPosition(0.3); // rotate right
            sleep(1500);
            robot.ColorSense.setPosition(0.6); // retract arm
            sleep(1500);
        }
        //robot.ColorSense.setPosition(.4);
        sleep(100);
        robot.FrontMotorLeft.setPower(0.32);
        robot.FrontMotorRight.setPower(0.32);
        sleep(1000);
        robot.FrontMotorLeft.setPower(0);
        robot.FrontMotorRight.setPower(0);

        robot.CS.enableLed(false); // fixes hang between autonomous and teleop
        sleep(500);
    }
}
