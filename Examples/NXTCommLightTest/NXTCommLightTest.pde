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

  /* Light sensor on port 1 */
  int light = lego.getLight(lego.PORT_1);
  
  
  background(light*2.5);
  
  textAlign(CENTER);
  text("Light Level: " + light, width/2, height-light);
}


/* Change the sensor type.
   ACTIVE: Reads reflected light, emitted by a LED
   INACTIVE: Reads ambient light.
*/
void keyPressed() {
  if (key == 'a') {
    lego.setLightSensorType(lego.ACTIVE, lego.PORT_1);
    println("Setting type to ACTIVE");
  } 
  else if (key =='i') {
    lego.setLightSensorType(lego.INACTIVE, lego.PORT_1);
    println("Setting type to INACTIVE");
  }

}
