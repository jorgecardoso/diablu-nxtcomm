/*
 * Main.java
 *
 * Created on 3 de Dezembro de 2006, 13:00
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

package pt.citar.diablu.nxt.protocol;




import java.io.*;

/**
 *
 * @author Jorge Cardoso
 */
public class Main {


    /* Stop processing  */
    private boolean stop = false;

    private Thread main;

    /** Creates a new instance of Main */
    public Main() {

        NXTCommBluetoothSerialChannelJSSC channel = new NXTCommBluetoothSerialChannelJSSC();

        try {


            channel.openChannel("COM11");

            NXTCommandPlayTone playTone = new NXTCommandPlayTone(4000, 500);
            playTone.setResponseRequired(true);
            playTone.setFrequency(2000);
            System.out.println(playTone);
            NXTResponseStatus status = (NXTResponseStatus)channel.sendCommand(playTone);

            boolean c = true;
            NXTCommandSetInputMode sim = new NXTCommandSetInputMode((byte)0, NXTResponseInputValues.LIGHT_INACTIVE_TYPE, NXTResponseInputValues.RAW_MODE);
            channel.sendCommand(sim);
            NXTCommandGetInputValues iv = new NXTCommandGetInputValues((byte)0);
            NXTResponseInputValues riv;
            
            NXTCommandSetOutputState output;
            
            output = new NXTCommandSetOutputState((byte)0, 
                    (byte)10, 
                    (byte) (NXTCommandSetOutputState.MODE_MOTOR_ON| NXTCommandSetOutputState.MODE_REGULATED), 
                    NXTCommandSetOutputState.REGULATION_MODE_MOTOR_SPEED, 
                    (byte)0, 
                    NXTCommandSetOutputState.RUN_STATE_RUNNING, 0);
            
            channel.sendCommand(output);
            output = new NXTCommandSetOutputState((byte)0, 
                    (byte)50, 
                    (byte) (NXTCommandSetOutputState.MODE_MOTOR_ON| NXTCommandSetOutputState.MODE_REGULATED),
                    NXTCommandSetOutputState.REGULATION_MODE_MOTOR_SPEED, 
                    (byte)0, 
                    NXTCommandSetOutputState.RUN_STATE_RAMP_UP, 
                    100);
            
           //channel.sendCommand(output);
            do {

                riv = (NXTResponseInputValues)channel.sendCommand(iv);
                System.out.println("Valor: " + riv.getNormalizedValue() +  "Snsor type: " + riv.getSensorType());

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {

                }
            } while (c);
            channel.closeChannel();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        } catch (RuntimeException re) {
            
             System.err.println(re.getMessage());
        }


         System.out.println("Done!");


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Main();
    }

}
