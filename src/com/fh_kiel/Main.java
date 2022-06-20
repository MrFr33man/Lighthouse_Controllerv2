package com.fh_kiel;

import com.illposed.osc.*;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.OSCPortIn;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    private static OSCPortIn oscPortIn;
    private static MessageSelector oscPatternAddressMessageSelector;
    private static MessageSelector selectorInfo;
    private static OSCMessageListener listener;
    private static OSCMessageListener listenerTogether;
    private static AnimationHandler animationHandler;

    public static boolean ableToSend = true;

    /**
     * In der Main werden der Empfangsport für den Listener festgelegt und wenn eine OSC-Message ankommt, wird ein Interrupt
     * ausgelöst, der die OSC-Nachricht analysiert
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws OSCSerializeException
     */
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

        listenerTogether = event -> {
            acceptMessage(event);
        };
        oscPatternAddressMessageSelector = new OSCPatternAddressMessageSelector("/Unity");
        selectorInfo = new OSCPatternAddressMessageSelector("/segi1/info");
        oscPortIn.getDispatcher().addListener(oscPatternAddressMessageSelector, listener);
        oscPortIn.getDispatcher().addListener(selectorInfo, listenerTogether);
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
            animationHandler.swipeRight();
        }

        if (tmp.contains("SwipeLeft")) {
            animationHandler.swipeLeft();
        }

        if (tmp.contains("JumpNRun")) {
            animationHandler.jumpNRun();
        }

        if (tmp.contains("EQ")) {
            animationHandler.eq();
        }
    }

    /**
     * Diese Methode prüft, ob der Übertragungskanal auf das LightHouse frei ist. Wenn für fünf Sekunden niemand
     * an das LighHouse gesendet hat, können die eigenen OSC-Nachrichten gesendet werden
     * @param event
     */
    static void acceptMessage(OSCMessageEvent event) {
        System.out.println("Info gotten from: " + event.getMessage().getAddress());
        List<Object> data = event.getMessage().getArguments();
        String info = data.get(0).toString();
        System.out.println("Infodata: " + info);
        if (!info.equalsIgnoreCase("enlightenme")) {
            ableToSend = false;
            Thread newThread = new Thread(() -> {
                try {
                    System.out.println("Stop sending");
                    TimeUnit.SECONDS.sleep(5);
                    ableToSend = true;
                    System.out.println("Start sending");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            newThread.start();
        }
    }
}
