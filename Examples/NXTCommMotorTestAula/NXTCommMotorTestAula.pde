/*
 * NXTCommTest.pde
 *
 * Created on 07 de March 2007
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

LegoNXT lego;


void setup() {
  size(400, 400);


  font = loadFont("ArialNarrow-24.vlw");
  textFont(font);

  lego = new LegoNXT(this, "COM18");
  frameRate(10);
}


void draw() {

}


void keyPressed() {

  if (key == '1') {
    lego.motorForwardLimit(LegoNXT.MOTOR_A, 60, 180);
    //lego.motorForwardLimit(LegoNXT.MOTOR_B, 60, 180);
  } 
  else if(key =='2') {
    lego.motorForward(LegoNXT.MOTOR_A, 60);
  } 
  else if (key == '3') {
    lego.motorForwardLimit(LegoNXT.MOTOR_A, 30, 90);
  } 
  else if (key =='q') {
    lego.motorStop(LegoNXT.MOTOR_A);
  } 
  else if(key == 'w') {
    lego.motorHandBrake(LegoNXT.MOTOR_B);
  } 
  else if (key == 'e') {
    lego.motorStop(LegoNXT.MOTOR_C);
  }
}


void stop() {
  println("Stop");
  lego.motorStop(LegoNXT.MOTOR_A);
  lego.motorStop(LegoNXT.MOTOR_B);
  lego.motorStop(LegoNXT.MOTOR_C);
}
