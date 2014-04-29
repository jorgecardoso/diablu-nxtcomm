/*
 * NXTCommandGetOutputState.java
 *
 * Created on 13 de Setembro de 2007, 11:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.citar.diablu.nxt.protocol;

/**
 *
 * @author Jorge Cardoso
 */
public class NXTCommandGetOutputState extends NXTCommand {
    
    /**
     * Output port index on buffer;
     */
    private final static int INPUT_PORT_INDEX = 2;
    
    
    /**
     * The response to this command.
     */
    private NXTResponseGetOutputState response;
    
    /**
     * Constructs a new <code>NXTCommandGetOutputState</code> object which will read values from input port 0.
     *
     */
    public NXTCommandGetOutputState() {
        // just to override the default constructor from the base class.
        this((byte)0);
    }
    
    /**
     *
     * Constructs a new <code>NXTCommandGetOutputState</code> object with a given input port.
     *
     *
     * @param inputPort The input port to read values from. Range: (0 - 3).
     */    
    public NXTCommandGetOutputState(byte inputPort) {
        super(true);
        
        buffer = new byte[] {DIRECT_COMMAND_RESPONSE_REQUIRED, 0x06, inputPort};
        //this.setInputPort(inputPort);
        response = new NXTResponseGetOutputState();
    }
    
    protected NXTResponse getResponse() {
        return this.response;
    }
    
    /**
     * Returns the input port on which we want to read values.
     * 
     * @return The input port.
     */
    public byte getInputPort() {
        return buffer[INPUT_PORT_INDEX];
    }
    
    /**
     * Sets the input port on which we want to read values.
     * 
     * @param inputPort The input port.
     */
    public void setInputPort(byte inputPort) {
       buffer[INPUT_PORT_INDEX] = inputPort;
    }    
}
