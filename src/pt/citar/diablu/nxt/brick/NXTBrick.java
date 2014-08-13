/*
 * NXTBrick.java
 *
 * Created on 21 de Janeiro de 2007, 14:46
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

package pt.citar.diablu.nxt.brick;


import pt.citar.diablu.nxt.protocol.*;
import java.io.*;
/**
 * The NXTBrick class just holds de connection channel for the Brick.
 *
 * @author Jorge Cardoso
 */
public class NXTBrick {
    
    private static NXTCommChannel channel;
    
    private NXTCommandGetBatteryLevel getBatteryLevel;
    private NXTResponseGetBatteryLevel batteryLevel;
    
    /** Creates a new instance of NXTBrick
     *
     * @param channel The NXTCommChannel used to communicate with the brick.
     */
    public NXTBrick(NXTCommChannel channel) {
        this.channel = channel;
        getBatteryLevel = new NXTCommandGetBatteryLevel();
        //System.out.println(channel);
    }
    
 
    /**
     * Returns the channel used to communicate.
     *
     * @return The NXTCommChannel used to communicate with the brick.
     */
    public NXTCommChannel getChannel() {
        return channel;
    }
    
    
    /**
     * Returns the battery level.
     *
     * @return The battery level in millivolts.
     */
    public int getBatteryLevel() {
        //System.out.println(channel);
        try {
            batteryLevel = (NXTResponseGetBatteryLevel)channel.sendCommand(getBatteryLevel);
            //System.out.println("Battery evel: " + batteryLevel);
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
        return batteryLevel.getBatteryLevel();
        
    }
}
