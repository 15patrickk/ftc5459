#pragma config(Hubs,  S1, HTMotor,  HTMotor,  HTMotor,  none)
#pragma config(Sensor, S1,     ,               sensorI2CMuxController)
#pragma config(Sensor, S3,     IRsensor,       sensorI2CCustom)
#pragma config(Motor,  mtr_S1_C1_1,     driveL,        tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C1_2,     driveR,        tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S1_C2_1,     collector,     tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C2_2,     conveyor,      tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S1_C3_1,     upper,         tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S1_C3_2,     lower,         tmotorTetrix, openLoop)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

#include "skooch.c" // include our common library
#include "JoystickDriver.c"  // handles the FMS

int LUT[129]; // the lookup table (LUT) for our joystick values

// initializeLUT() - precomputes values for our joystick algorithm
void initializeLUT() {
	for(int i = 0; i <= 10; i++) { // manually generate the deadzone
		LUT[i] = 0;
	}
	for(int i = 11; i <= 128; i++){ // generate the table values using the algorithm
		// equivalent to: ((i + 4)^2)/175
		LUT[i] = realRound(pow((i + 4), 2) / 175);
	}
}

// joyAlgo(joyVal) - converts raw joystick values to scaled values using the LUT
int joyAlgo(int joyVal) {
	if(sgn(joyVal) == -1) { // if the joystick is "negative" (pushed forward)
		return (LUT[abs(joyVal)]); // make the _position_ positive, return LUT value
	}
	else if(sgn(joyVal) == 1) { // if the joystick is "positive"
		return (LUT[joyVal]); // return LUT value, make the _value_ negative
	}
	return 0; // if it's not (+) or (-), it must be zero
}

/*
// discreteButtons() - handles all the buttons that manipulate discrete functions.
// We need this to reduce jitter with these controls while not lagging the main task.
task discreteButtons() {
	servo[door] = 0; // initialization; fixes twitch on first press
	while(true) {
		// door control: if the button is pressed, toggle the door
		if (joy1Btn(6) || joy2Btn(6)) {
			servo[door] = servo[door] == 0 ? 255 : 0; // if less than halfway open, open all the way; else close
			wait1Msec(1000); // reduces jitter
		}
	}
}
*/

// main() - the main task for the robot, essentially an infinite loop.
// This must be the last routine in the file.
// Game controller information is sent ~30 times per second (every ~40 ms) from SAMANTHA.
// At the end of teleop, the SAMANTHA  will automatically halt execution.
task main() {
	initializeLUT(); // initialize the LUT before control starts to save time
	waitForStart(); // wait for start of tele-op phase

	// StartTask(discreteButtons); // start the discrete button handler

	// the main loop - handles all the continuous controls on the gamepad
	while(true) {
		getJoystickSettings(joystick); // updates gamepad info when the FMS sends it

		// JOYSTICKS

		// drivetrain (gamepad 1): use the algorithm to assign motor values
		motor[driveL] = joyAlgo(joystick.joy1_y1);
		motor[driveR] = joyAlgo(joystick.joy1_y2);

		// BUTTONS - common across both gamepads

		// lower lift control: run the lower lift segment in forward/reverse
		// this is a continuous control, thus it doesn't belong in discreteButtons()
		/* the nested ternary statement is the equivalent of saying:
			if the POV hat is pushed up, raise the lift. else...
				if the POV hat is pushed down, lower the lift. else...
					set the motor to zero.
		*/
		motor[lower] = joystick.joy1_TopHat == 0 ? 60 : joystick.joy1_TopHat == 4 ? -20 : 0;

		// upper lift control: run the upper lift in forward/reverse
		/* the nested ternary statement is the equivalent of saying:
			if the up button (5) is pressed, raise the lift. else...
				if the down button (7) is pressed, lower the lift. else...
					set the motor to zero.
		*/
		motor[upper] = joy1Btn(5) || joy2Btn(5) ? 60 : joy1Btn(7) || joy2Btn(7) ? -20 : 0;

		// collector control: if the buttons are pressed, run the conveyor in the corresponding direction
		// since this is a continuous button, it doesn't belong in discreteButtons()
		motor[collector] = joy1Btn(8) ? 75 : 0;

		// conveyor control: if the buttons are pressed, run the conveyor in the corresponding direction
		// since this is a continuous button, it doesn't belong in discreteButtons()
		motor[conveyor] = joy1Btn(6) ? 75 : 0;

		// reverse: run all the conveyors in reverse
		if (joy1Btn(10)) { motor[collector] = motor[conveyor] = -100; }

		// beep: play a sound if this button is pressed
		if (joy1Btn(2) || joy2Btn(2)) { playTone(1000, 2); }
	}
}
