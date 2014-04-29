/*
 * NXTResponseGetBatteryLevel.java
 *
 * Created on 01 de Outubro de 2007, 11:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.citar.diablu.nxt.protocol;

/**
 *
 * @author Jorge Cardoso
 */
public class NXTResponseGetBatteryLevel  extends NXTResponse {
    
    /**
     * The output port value index on packet.
     */
    private byte VOLTAGE_INDEX = 3;

  
   
    
    /**
     * @return The current limit on a movement in progress, if any.
     */
    public int getBatteryLevel() {
        return  ((0xff & buffer[VOLTAGE_INDEX]) | ((0xff & buffer[VOLTAGE_INDEX+1]) << 8));
    }
    
   
}
