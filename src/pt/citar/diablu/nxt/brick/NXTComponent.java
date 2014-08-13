/*
 * NXTComponent.java
 *
 * Created on 3 de Fevereiro de 2007, 17:34
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
 *  You can reach me by:
 *  email: jorgecardoso <> ieee org
 *  web: http://jorgecardoso.org
 */

package pt.citar.diablu.nxt.brick;


/**
 * This class represents a sensor or a motor of the NXT. This class is an abstract class, meaning that programs never use it directly.
 * You should use one of the subclasses of NXTComponent.
 *
 * @author Jorge Cardoso
 */
public abstract class NXTComponent {
    
    /**
     * The NXT Brick.
     */
    protected NXTBrick brick;    
    /**
     * The port to which this sensor is connected. (0 - 3)
     */
    protected byte portAttached;   
    
    public NXTComponent() {
        
    }
    
    /**
     * Constructs a new component of the brick given a port to which the component is attached.
     *
     * @param brick The NXT Brick.
     * @param portAttached The port to which this component is attached.
     */
    public NXTComponent(NXTBrick brick, byte portAttached) {
        this.brick = brick;
        this.portAttached = portAttached;      
    }
    
}
