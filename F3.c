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
// template copy-pasted, valid 2014-10-30

// F3 autonomous - start from floor, kickstand, block other ramp

#include "skooch.c" // include our common library
#include "JoystickDriver.c" // handles the FMS

task main() {
	// initialization
	nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;

	waitForStart(); // wait for FMS to start autonomous

	startTask(IRtask); // start polling the IR sensors
}
