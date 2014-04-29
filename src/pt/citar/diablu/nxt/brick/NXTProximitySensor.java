/*
 * NXTProximitySensor.java
 *
 * Created on 22 de Janeiro de 2007, 23:43
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

import java.io.IOException;
import pt.citar.diablu.nxt.protocol.*;

/**
 * The NXTProximitySensor class represents the ultrasonic sensor...
 *
 * @author Jorge Cardoso
 */
public class NXTProximitySensor extends NXTComponent {
    
    NXTCommandLSWrite singleShot;
    NXTCommandLSWrite readByte0;
    NXTCommandLSRead lsRead;
    
    
    /**
     * Coonstructs a new NXTProximitySensor object given the NXTBrick and the port number the
     * sensor is connected to.
     *
     * 
     * @param brick The NXTBrick.
     * @param portAttached The port to which the sensor is attached. (0 - 3).
     */
    public NXTProximitySensor(NXTBrick brick, byte portAttached) {
        super(brick, portAttached);
        initialize();
        singleShot = new NXTCommandLSWrite(portAttached, (byte)3, (byte)0, new byte[] {0x02, 0x41, 0x01}, false);
        readByte0 = new NXTCommandLSWrite(portAttached, (byte)2, (byte)1, new byte[] {0x02, 0x42}, false);
        lsRead = new NXTCommandLSRead(portAttached, true);
    }
    
    
    public void initialize() {
        // Set the sensor to a I2C sensor.
        NXTCommandSetInputMode inputMode = new NXTCommandSetInputMode(this.portAttached,
                NXTResponseInputValues.LOWSPEED_9V_TYPE,
                NXTResponseInputValues.RAW_MODE);
        try {
            this.brick.getChannel().sendCommand(inputMode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    
    
    public int getDistance() {
        try {
            // read single shot
            brick.getChannel().sendCommand(singleShot);
            
            // read measurement byte 0
            brick.getChannel().sendCommand(readByte0);
            
            NXTResponseLSRead response = (NXTResponseLSRead)brick.getChannel().sendCommand(lsRead);
            
            // wait for the bus 
            while (response.getStatus() == response.PENDING_COMMUNICATION_TRANSACTION_IN_PROGRESS || 
                    response.getStatus() == response.SPECIFIED_CHANNEL_CONNECTION_NOT_CONFIGURED_OR_BUSY) {
                
                response = (NXTResponseLSRead)brick.getChannel().sendCommand(lsRead);            
            }
            if (response.getBytesRead() == 0) {                
                return 0;
            }
            // return first byte
            return response.getData()[0];
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
       // return inputValues.getScaledValue();
    }
    
}

