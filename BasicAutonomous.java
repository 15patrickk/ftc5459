
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BasicAutonomous", group="NicoleBot")
public class BasicAutonomous extends LinearOpMode {

    /* Declare OpMode members. */
    NicoleBot         robot   = new NicoleBot();   //


    @Override
    public void runOpMode() throws InterruptedException {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        robot.FrontMotorRight.setPower(1.0);
        sleep(1000);//CHANGE VALUE

        robot.FrontMotorLeft.setPower(1.0);
        sleep(1000);//CHANGE VALUE

        robot.FrontMotorRight.setPower(0.0);
        sleep(1000); //CHANGE VALUE

        //telemetry.addData("distance", distance);
        updateTelemetry(telemetry);

    }
}
