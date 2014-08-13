/*
 * NXTCommProximityTest.pde
 *
 * Created on 16 July 2007
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
background(0);
  /* ultrasonic sensor on port 0*/
  int distance = lego.getDistance(0);
    
  fill(255);
  rect(0, height-distance*4-20, width, 20);
  textAlign(CENTER);
  textMode(SCREEN);
  fill(0);
  text("Distance: ~" + distance + " cm", width/2, height-distance*4);
}


