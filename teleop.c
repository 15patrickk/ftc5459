#pragma config(Hubs,  S4, HTServo,  HTMotor,  HTMotor,  HTMotor)
#pragma config(Sensor, S1,     ultrasound,     sensorSONAR)
#pragma config(Sensor, S2,     IRsensor,       sensorHiTechnicIRSeeker1200)
#pragma config(Sensor, S3,     GYROsensor,     sensorI2CHiTechnicGyro)
#pragma config(Motor,  mtr_S4_C2_1,     driveR,        tmotorTetrix, openLoop, reversed, encoder)
#pragma config(Motor,  mtr_S4_C2_2,     driveL,        tmotorTetrix, openLoop, encoder)
#pragma config(Motor,  mtr_S4_C3_1,     liftLower,     tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S4_C3_2,     liftUpper,     tmotorTetrix, openLoop)
#pragma config(Motor,  mtr_S4_C4_1,     conveyor,      tmotorTetrix, openLoop, reversed)
#pragma config(Motor,  mtr_S4_C4_2,     collector,     tmotorTetrix, openLoop)
#pragma config(Servo,  srvo_S4_C1_1,    door,                 tServoStandard)
#pragma config(Servo,  srvo_S4_C1_2,    tubeHook,             tServoStandard)
#pragma config(Servo,  srvo_S4_C1_3,    servo3,               tServoNone)
#pragma config(Servo,  srvo_S4_C1_4,    servo4,               tServoNone)
#pragma config(Servo,  srvo_S4_C1_5,    servo5,               tServoNone)
#pragma config(Servo,  srvo_S4_C1_6,    servo6,               tServoNone)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

#include "skooch.c" // include our common library
#include "JoystickDriver.c"  // handles the FMS

// HEADS UP: the values in the debug window for joysticks and buttons are incorrect.

int LUT[129]; // the lookup table (LUT) for our joystick values
int direction = 1;

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
	if(sgn(joyVal) == -1) { // if the joystick is negative (pushed back)
		return (-LUT[abs(joyVal)]); // make the position positive, return negative LUT value
	}
	else if(sgn(joyVal) == 1) { // if the joystick is positive (pushed forward)
		return LUT[joyVal]; // return LUT value
	}
	return 0; // if it's not (+) or (-), it must be zero
}

//

// discreteButtons() - handles all the buttons that manipulate discrete functions.
// We need this to reduce jitter with these controls while not lagging the main task.
task discreteButtons() {
	servo[door] = 0; // initialization; fixes twitch on first press
	while(true) {
		// door control: if the button is pressed, toggle the door
		if (joy1Btn(3)) {
			// if the door is completely closed, open it; else, close it
			servo[door] = servo[door] == 0 ? 255 : 0;
			wait1Msec(1000); // reduces jitter
		}
		// reverse button: reverse the drive motors to make dumping balls easier
		if (joy1Btn(1)) {
			direction *= -1;
			wait1Msec(500);
		}
	}
}

// main() - the main task for the robot, essentially an infinite loop.
// This must be the last routine in the file.
// Gamepad information is sent ~30 times per second (every ~40 ms) from SAMANTHA.
// At the end of teleop, the SAMANTHA will automatically halt execution.
task main() {
	initializeLUT(); // initialize the LUT before control starts to save time
	waitForStart(); // wait for start of tele-op phase

	startTask(discreteButtons); // start the discrete button handler

	// the main loop - handles all the continuous controls on the gamepad
	while(true) {
		getJoystickSettings(joystick); // updates gamepad info when the FMS sends it

		// JOYSTICKS

		// drivetrain (gamepad 1): use the algorithm to assign motor values
		motor[driveL] = direction * joyAlgo(joystick.joy1_y1);
		motor[driveR] = direction * joyAlgo(joystick.joy1_y2);

		// BUTTONS - common across both gamepads

		// upper lift control: run the upper lift segment in forward/reverse
		// the nested ternary raises (POV up) and lowers (POV down) the upper lift, else stops
		motor[liftUpper] = joystick.joy1_TopHat == 0 ? 90 : joystick.joy1_TopHat == 4 ? -50 : 0;

		// lower lift control: run the lower lift in forward/reverse
		// the nested ternary raises (5) and lowers (7) the lower lift, else stops
		motor[liftLower] = joy1Btn(5) || joy2Btn(5) ? 90 : joy1Btn(7) || joy2Btn(7) ? -50 : 0;

		// collector and conveyor control: run these mechanisms concurrently
		// nested ternary runs these mechanisms forward (8) and backwards (10), else stops
		// since this is a continuous button, it doesn't belong in discreteButtons()
		motor[collector] = motor[conveyor] = joy1Btn(8) ? 75 : joy1Btn(10) ? -100 : 0;

		// beep: play a sound if this button is pressed
		if (joy1Btn(2) || joy2Btn(2)) { playTone(1000, 2); }
	}
}
