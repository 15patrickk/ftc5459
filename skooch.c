/*
  skooch.c - library for the skooch platform
  this library makes a few assumptions:
    - drive motors are named "driveL" and "driveR"
    - drive motors are set so that (+) moves the robot forward and (-) backwards
    - the IR sensor is named "IRsensor"
*/

// include all the drivers we need. right now this file is manually generated.
#include "drivers_cat.h"

// DEFINEs makes code more readable
#define FWD true
#define BACK false
#define TETRIX_ROTATION_RATIO 1440

// GLOBAL VARIABLES are necessary for passing pollable sensor values
int IR_dir, IR_str;

// TASK IRtask() - continuously polls the IR sensor; breaks on error
task IRtask() {
	// set the DSP to the the desired mode (1200 Hz beacon)
  HTIRS2setDSPMode(IRsensor, DSP_1200);
  while(true) {
		if (!HTIRS2readEnhanced(IRsensor, IR_dir, IR_str)) { break; } // break on I2C err
		wait1Msec(100); // wait 100ms before polling again
  }
}

// FUNCTIONS

// realRound(number) - ROBOTC doesn't have a true round function
int realRound(float number) {
	// decimal is less than .5 --> number - floor(number) is negative
  // decimal is greater than .5 --> number - floor(number) is positive
	int rounded = number - floor(number) < 0 ? floor(number) : ceil(number);
	return rounded;
}

// calcIR(samples) - takes readings from the IR sensor and returns the average
int calcIR(int samples) {
	int sum = 0;
	for (int i = 0; i < samples; i++) {
		sum += IR_dir;
		wait1Msec(100); // since the IR polls at 100ms, this function must wait
	}
	return realRound(sum / samples); // returns the average as an int
}

// drive(rotations, direction) - given rotations, drives the robot straight
void drive(float rotations, bool direction) {
	nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;
	int target = rotations * TETRIX_ROTATION_RATIO;
	if (direction) { // forward
		while(nMotorEncoder[driveL] <= target || nMotorEncoder[driveR] <= target) {
			motor[driveL] = motor[driveR] = 30;
		}
		motor[driveL] = motor[driveR] = 0;
	}
	else { // backwards
		target *= -1;
		while(nMotorEncoder[driveL] >= target || nMotorEncoder[driveR] >= target) {
			motor[driveL] = motor[driveR] = -30;
		}
	}
	motor[driveL] = motor[driveR] = 0;
	nMotorEncoder[driveL] = nMotorEncoder[driveR] = 0;
}
