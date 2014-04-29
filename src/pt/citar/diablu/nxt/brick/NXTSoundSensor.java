/*
 * NXTSoundSensor.java
 *
 * Created on 22 de Janeiro de 2007, 23:42
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
import java.io.IOException;


/**
 *
 * @author Jorge Cardoso
 */
public class NXTSoundSensor extends NXTComponent {
    
    private NXTCommandGetInputValues getInputValues;
    private NXTResponseInputValues inputValues;
    
    public NXTSoundSensor(NXTBrick brick, byte portAttached) {
        super(brick, portAttached);
        initialize();
    }
    
    public void initialize() {
        
        NXTCommandSetInputMode inputMode = new NXTCommandSetInputMode(this.portAttached, 
                NXTResponseInputValues.SOUND_DB_TYPE, 
                NXTResponseInputValues.PCT_FULL_SCALE_MODE);
        try {
            this.brick.getChannel().sendCommand(inputMode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getInputValues = new NXTCommandGetInputValues(this.portAttached);        
    }    
    
    public int getDB() {
        try {
            inputValues = (NXTResponseInputValues)brick.getChannel().sendCommand(getInputValues);
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
        return inputValues.getScaledValue();        
    }
}
