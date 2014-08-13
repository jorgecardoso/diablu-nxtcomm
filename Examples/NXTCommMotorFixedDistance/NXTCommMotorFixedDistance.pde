/*
 * NXTCommMotorFixedDistance.pde
 *
 * Created on 03 de December 2007
 *
 *  NXTComm: A java library to control the NXT Brick.
 *  This is part a of the DiABlu Project (http://diablu.jorgecardoso.org)
 *
 *  Copyright (C) 2007  Jorge Cardoso
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *  You can reach me by
 *  email: jorgecardoso <> ieee org
 *  web: http://jorgecardoso.org
 */

import pt.citar.diablu.processing.nxt.*;

PFont font;

int power = 50;
long rotationCount = 0;
long rotationLimit = 45;

LegoNXT lego;


void setup() {
  size(400, 400);

  font = loadFont("ArialNarrow-24.vlw");
  textFont(font);

  lego = new LegoNXT(this, "COM18");
  frameRate(10);
}


void draw() {
  background(0);
  rotationCount =  lego.getMotorRotationCount(LegoNXT.MOTOR_A);

  // find out how much we need to go further
  long dif = rotationLimit - rotationCount;

  // a long way: power to half throttle (we can increase power if we're still further than this)
  if(abs(dif) > 180) {
    power = 50;
  } 
  else if (abs(dif) <= 5) { // close enough, stop
    power = 0;
  } 
  else { // use a quadratic function: power will increase as a function of distance from 2 (or -2) to 50 (or -50).
    if (dif > 0) {
      power = (int)((dif*dif)/650)+2;
    } 
    else {
      power = -(int)((dif*dif)/650)-2;
    }

  }
  lego.motorForward(LegoNXT.MOTOR_A, power);

  text("Limit: " + rotationLimit + " Count: " +rotationCount + " Power: "+power, 20, 200);
}


// configured several 'distances'
void keyPressed() {
  
  if (key == '1') {
    rotationLimit = 45;
    lego.resetMotorPosition(LegoNXT.MOTOR_A, false);
  }   
  else if (key == '2') {
    rotationLimit = 90;
    lego.resetMotorPosition(LegoNXT.MOTOR_A, false);
  } 
  else if (key == '3') {
    rotationLimit = 180;
    lego.resetMotorPosition(LegoNXT.MOTOR_A, false);
  } 
  else if (key == '4') {
    rotationLimit = 360;
    lego.resetMotorPosition(LegoNXT.MOTOR_A, false);
  }
  else if (key =='q') {
    lego.motorHandBrake(LegoNXT.MOTOR_A);
  } 
  else if (key == 's') {
    System.out.println("TL: " + lego.getMotorTachoLimit(LegoNXT.MOTOR_A) + 
      " TC: " +lego.getMotorTachoCount(LegoNXT.MOTOR_A) +
      " BTC: " + lego.getMotorBlockTachoCount(LegoNXT.MOTOR_A) + 
      " RC: " + lego.getMotorRotationCount(LegoNXT.MOTOR_A) );
  } 
  else if (key == 'r') {
    lego.resetMotorPosition(LegoNXT.MOTOR_A, false);
  }

}


void stop() {
  println("Stop");
  lego.motorStop(LegoNXT.MOTOR_A);
}
