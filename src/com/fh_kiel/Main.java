package com.fh_kiel;

import com.illposed.osc.*;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.OSCPortIn;
import java.io.IOException;

public class Main {

    private static OSCPortIn oscPortIn;
    private static MessageSelector oscPatternAddressMessageSelector;
    private static OSCMessageListener listener;
    private static AnimationHandler animationHandler;

    public static void main(String[] args) throws IOException, InterruptedException, OSCSerializeException {
        /* start oscP5, listening for incoming messages at port 8001 of OSCBroadcaster */
        oscPortIn = new OSCPortIn(8001);

        animationHandler = AnimationHandler.getInstance();

        listener = event -> {
            try {
                oscEvent(event);
            } catch (InterruptedException | OSCSerializeException | IOException e) {
                e.printStackTrace();
            }
        };
        oscPatternAddressMessageSelector = new OSCPatternAddressMessageSelector("/Unity");
        oscPortIn.getDispatcher().addListener(oscPatternAddressMessageSelector, listener);
        oscPortIn.startListening();

        /**
         * Diese Schleife sorgt dafür, dass das Programm sich nicht selbst beendet, so dass der Listener weiter
         * nach OSC-Nachrichten horchen kann
         */
        while (true) {
            Thread.sleep(1);
        }
    }

    /**
     * Wird ausgelöst, wenn eine OSC-Message ankommt. Diese wird dann auf Inhalt geprüft und je nach Inhalt wird eine
     * andere Animation ausgelöst
     * @param theOscMessage
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    static void oscEvent(OSCMessageEvent theOscMessage) throws InterruptedException, OSCSerializeException, IOException {
        /* print the address pattern and the typetag of the received OscMessage */
        System.out.println("### received an osc message.");
        System.out.println("Message received: " + theOscMessage.getMessage().getAddress());

        String tmp = theOscMessage.getMessage().getArguments().toString();

        System.out.println("\n" + tmp);

        if (tmp.contains("SwipeRight")) {
            animationHandler.SwipeRight();
        }

        if (tmp.contains("SwipeLeft")) {
            animationHandler.SwipeLeft();
        }

        if (tmp.contains("JumpNRun")) {
            animationHandler.JumpNRun();
        }

        if (tmp.contains("EQ")) {
            animationHandler.EQ();
        }
    }
}
