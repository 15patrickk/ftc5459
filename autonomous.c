#pragma config(Hubs,  S4, HTServo,  HTMotor,  HTMotor,  HTMotor)
#pragma config(Sensor, S1,     IRsensor,       sensorHiTechnicIRSeeker1200)
#pragma config(Sensor, S4,     ,               sensorI2CMuxController)
#pragma config(Motor,  mtr_S4_C2_1,     driveR,        tmotorTetrix, openLoop, reversed, encoder)
#pragma config(Motor,  mtr_S4_C2_2,     driveL,        tmotorTetrix, openLoop, encoder)
#pragma config(Motor,  mtr_S4_C3_1,     liftLower,     tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S4_C3_2,     liftUpper,     tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S4_C4_1,     conveyor,      tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S4_C4_2,     collector,     tmotorTetrix, openLoop)
#pragma config(Servo,  srvo_S4_C1_1,    door,                 tServoStandard)
// above copy-pasted from teleop.c, valid 2014-11-15
// template copy-pasted, valid [yyyy-mm-dd]

// autonomous template - waitForStart, includes, etc.

#include "skooch.c" // include our common library
#include "JoystickDriver.c" // handles the FMS

// main() - the main task for the robot, basically an infinite loop.
// This must be the last routine in the file.
// At the end of the autonomous period, the FMS will autonmatically halt execution.

task main() {
	// initialization
	nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;

	waitForStart(); // wait for FMS to start autonomous

	startTask(IRtask); // start polling the IR sensors
}
