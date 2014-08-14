/*
 * NXTCommTest.pde
 *
 * Created on 07 de March 2007
 * Modified on 14 August 2014 
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
import processing.serial.*;
import pt.citar.diablu.processing.nxt.*;


int power = 50;
LegoNXT lego;


void setup() {
  size(400, 400);

  lego = new LegoNXT(this, "/dev/tty.NXT-DevB");
  frameRate(10);
}


void draw() {

}


void keyPressed() {
  println(key);

  if (key == '1') {
    lego.motorForwardLimit(LegoNXT.MOTOR_A, power, 360);
  }   
  else if (key == '2') {
    lego.motorForward(LegoNXT.MOTOR_A, power);
  } 
  else if (key == '+') {
    power += 5;
    println(power);
  } 
  else if (key == '-') {
    power -= 5;
    println(power);
  }
  else if (key =='q') {
    lego.motorHandBrake(LegoNXT.MOTOR_A);
  } 
  else if (key == 's') {
    System.out.println("TL: " + lego.getMotorTachoLimit(LegoNXT.MOTOR_A) + 
      " TC: " +lego.getMotorTachoCount(LegoNXT.MOTOR_A) +
      " BTC: " + lego.getMotorBlockTachoCount(LegoNXT.MOTOR_A) + 
      " RC: " + lego.getMotorRotationCount(LegoNXT.MOTOR_A) );
  } else if (key == 'r') {
    lego.resetMotorPosition(LegoNXT.MOTOR_A, false);
  }

}


void stop() {
  println("Stop");
  lego.motorStop(LegoNXT.MOTOR_A);
}
