#pragma config(Hubs,  S1, HTMotor,  HTMotor,  HTMotor,  HTServo)
#pragma config(Sensor, S1,     ,               sensorI2CMuxController)
#pragma config(Sensor, S3,     IRsensor,       sensorI2CCustom)
#pragma config(Motor,  mtr_S1_C1_1,     driveL,        tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C1_2,     driveR,        tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S1_C2_1,     collector,     tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C2_2,     conveyor,      tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S1_C3_1,     upper,         tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C3_2,     lower,         tmotorTetrix, openLoop)
#pragma config(Servo,  srvo_S1_C4_1,    door,                 tServoStandard)
// above copy-pasted from teleop.c, valid 2014-10-30
// template copy-pasted, valid [yyyy-mm-dd]

// autonomous template - waitForStart, includes, etc.

#include "skooch.c" // include our common library
#include "JoystickDriver.c" // handles the FMS

	/*
	// ramp() - drives to the ramp
	void ramp() {
	servo[door] = 0;
	// in theory: while the robot is more than 10 away from the wall, drive forward
	while(SensorValue[frontSonar] > 10) {
	motor[driveL] = motor[driveR] = 50;
	motor[lift] = 50;
	}
	PlayTone(2500, 100);
	motor[driveL] = motor[driveR] = 0; // STOP once we get near the wall
	nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;
	wait1Msec(500);

	// turn ~90 degrees right
	while(nMotorEncoder[driveL] < 1700) {
	motor[driveL] = 60;
	motor[driveR] = -30;
	}
	motor[driveL] = motor[driveR] = 0;
	wait1Msec(500);

	// run lift down
	motor[lift] = -20;
	wait1Msec(750);
	motor[lift] = 0;

	// in theory: drive forward enough to get to the ramp
	driveFor(2.25, true);
	PlayTone(1750, 100);
	wait1Msec(500);

	// in theory: turn 90 degrees right (face ramp)
	while(nMotorEncoder[driveL] < 2300) {
	motor[driveL] = 60;
	motor[driveR] = -30;
	}
	motor[driveL] = motor[driveR] = 0;
	wait1Msec(500);
	nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;
	// in theory: drive far enough to get completely on the ramp
	while(nMotorEncoder[driveL] <= 4464) { // 3.25 revs
	motor[driveL] = motor[driveR] = 60;
	}
	motor[driveL] = motor[driveR] = 0;
	}
	*/

	// main() - the main task for the robot, basically an infinite loop.
	// This must be the last routine in the file.
	// At the end of the autonomous period, the FMS will autonmatically halt execution.

	task main() {
		waitForStart(); // wait for FMS to start autonomous

		// initialization
		nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;

		startTask(IRtask); // start polling the IR sensors

	}
task main() {
	// initialization
	nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;

	waitForStart(); // wait for FMS to start autonomous

	startTask(IRtask); // start polling the IR sensors
}
