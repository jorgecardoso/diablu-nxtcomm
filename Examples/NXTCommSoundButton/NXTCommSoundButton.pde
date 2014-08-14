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


LegoNXT lego;


void setup() {
  size(400, 400);
  
  lego = new LegoNXT(this, "/dev/tty.NXT-DevB");
  frameRate(20);
}


void draw() {

  background(0);
  
  /* Sound sensor on port 0*/
  int db = lego.getDB(lego.PORT_1);
  println("DB Level: " + db);
  fill(db/100.0*255, 255-db/100.0*255, 255-db/100.0*255);
  rect(0, height, width, -db);
   	
  fill(255);
  textAlign(CENTER);
  text("Sound Level", width/2, height-db);
   
   
  if (lego.getButtonState(lego.PORT_2)) {
    fill(0, 0, 255);
    rect(0, 50, 100, 50);  
  }
  
  if (lego.getButtonState(lego.PORT_3)) {
    fill(0, 0, 255);
    rect(width, 50, -100, 50);  
  }  
}

void stop() {
  println("Stop");
}
