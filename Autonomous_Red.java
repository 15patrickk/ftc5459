package org.firstinspires.ftc.teamcode;

/*
 * AutonomousRed: autonomous software to knock off a ball (red alliance version).
 * All of the code that runs in the autonomous period is in runOpMode().
 * Unlike teleop, there is no "loop" method; code runs in a straight line from top to bottom.
 *
 * multiple authors, February 2018
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name="Autonomous: KnockOff Red", group="WoodyBot")
public class Autonomous_Red extends LinearOpMode {
    WoodyBot robot = new WoodyBot();

    @Override
    public void runOpMode() {
        // initialize the robot hardware
        robot.init(hardwareMap);

        // initialize Vuforia
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = robot.vKey;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        RelicRecoveryVuMark pictograph = null;

        // load the pictograph dataset
        VuforiaTrackables relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        // send telemetry message to signify robot waiting
        telemetry.addData("Status", "Slouching");
        telemetry.update();

        waitForStart(); // wait for match to start (driver presses PLAY)

        // ---------- match code ----------

        // get visible pictograph
        relicTrackables.activate();
        for(int i = 0; i < 500; i++) pictograph = RelicRecoveryVuMark.from(relicTemplate);
        telemetry.addData("VuMark", "%s visible", pictograph);
        telemetry.update();
        sleep(1000);

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
        robot.TwistyThingy.setPosition(0);
        robot.ColorSense.setPosition(.61);
        sleep(300);

        robot.FrontMotorRight.setPower(-.25);
        robot.FrontMotorLeft.setPower(-.25);

        // fixes hang between autonomous and teleop
        robot.CS.enableLed(false);
        sleep(400);

        robot.FrontMotorRight.setPower(0);
        robot.FrontMotorLeft.setPower(0);

        // ---------- vision code ----------

        // take different actions depending on the pictograph visible at start of autonomous
        switch(pictograph) {
            case LEFT:
                break;

            case CENTER:
                robot.FrontMotorRight.setPower(.25);
                robot.FrontMotorLeft.setPower(.35);
                sleep(800);
                break;

            case RIGHT:
                break;

            default:
                // code if nothing is detected...
                break;
        }

        robot.FrontMotorRight.setPower(0);
        robot.FrontMotorLeft.setPower(0);

        sleep(500); // a final sleep before switching to teleop
    }
}
