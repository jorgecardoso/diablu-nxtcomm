/*
 * NXTCommandGetBatteryLevel.java
 *
 * Created on 01 de Outubro de 2007, 11:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.citar.diablu.nxt.protocol;

/**
 *
 * @author Jorge Cardoso
 */
public class NXTCommandGetBatteryLevel extends NXTCommand {
    
   
    /**
     * The response to this command.
     */
    private NXTResponseGetBatteryLevel response;
    
    
    /**
     *
     * Constructs a new <code>NXTCommandGetBatteryLevel</code> object.

     */    
    public NXTCommandGetBatteryLevel() {
        super(true);
        
        buffer = new byte[] {DIRECT_COMMAND_RESPONSE_REQUIRED, 0x0B};
        //this.setInputPort(inputPort);
        response = new NXTResponseGetBatteryLevel();
        //System.out.println("Response: " + response);
    }
    
    protected NXTResponse getResponse() {
        return this.response;
    }
     
}
