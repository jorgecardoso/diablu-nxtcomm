/*
 * NXTCommandResetMotorPosition.java
 *
 * Created on 14 de Setembro de 2007, 14:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.citar.diablu.nxt.protocol;

/**
 *
 * @author Jorge Cardoso
 */
public class NXTCommandResetMotorPosition extends NXTCommand{
    
    /**
     * Output port index on buffer;
     */
    private final static int OUTPUT_PORT_INDEX = 2;
    
    /**
     * The index of the 'relative' parameter on buffer.
     */
    private final static int RELATIVE_INDEX = 3;
    
    
    /**
     * The response to this command.
     */
    private NXTResponseStatus response;
    
    /**
     * Constructs a new <code>NXTCommandResetMotorPosition</code> object.
     *
     */
    public NXTCommandResetMotorPosition() {
        // just to override the default constructor from the base class.
        this((byte)0);
    }
    
    /**
     *
     * Constructs a new <code>NXTCommandResetMotorPosition</code> object with a given output port.
     *
     *
     * @param inputPort The input port to read values from. Range: (0 - 2).
     */    
    public NXTCommandResetMotorPosition(byte outputPort) {
        super(true);
        
        buffer = new byte[] {DIRECT_COMMAND_RESPONSE_REQUIRED, 0x0A, outputPort, 0x00};
        //this.setInputPort(inputPort);
        response = new NXTResponseStatus();
    }
    
    protected NXTResponse getResponse() {
        return this.response;
    }    
    
    /**
     * @param relative True: position relative to last movement, False: absolute position.
     */
    public void setRelative(boolean relative) {
        buffer[RELATIVE_INDEX] = relative ? (byte)1 : (byte)0;
    }
    
}
