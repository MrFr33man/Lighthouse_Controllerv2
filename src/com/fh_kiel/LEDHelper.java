package com.fh_kiel;

import com.illposed.osc.*;
import com.illposed.osc.transport.OSCPortOut;
import java.net.*;
import java.util.*;
import java.io.IOException;

import static com.fh_kiel.Main.ableToSend;

/**
 * LEDHelper ist eine Helperklasse, die für das Handling der Objekte der Klasse LED zuständig ist.
 * Es enthält ein Array zur Speicherung eines Pakets an LEDs, welche dann per OSC versendet werden
 * Das Lighthouse hat 8 Zeilen      --> x index 0-7
 * Das Lighthouse hat 35 Spalten    --> y index 0-34
 */
public class LEDHelper {

    private static OSCPortOut oscPortOut;
    static ArrayList<Led> leds = new ArrayList<Led>();

    private static LEDHelper OBJ = null;

    /**
     * Implementation von Singleton
     */
    static {
        try {
            OBJ = new LEDHelper();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt den LEDHelper mit einem OSCPortOut und der zugehörigen IP und Port.
     * @throws IOException
     */
    private LEDHelper() throws IOException {
        //oscPortOut = new OSCPortOut(new InetSocketAddress("149.222.206.225", 9000));
        //Localhost
        oscPortOut = new OSCPortOut(new InetSocketAddress("localhost", 9001));
        oscPortOut.connect();
    }

    public static LEDHelper getInstance() {
        return OBJ;
    }

    /**
     * Led ist eine Klasse, die Objekte erstellen kann, welche genau den LEDs auf dem Lighthouse entsprechen.
     * Die Attribute sind dabei RGB und die XY Werte auf dem House
     */
    class Led {

        int r;
        int g;
        int b;

        int xIndex;
        int yIndex;

        Led(int r, int g, int b, int xIndex, int yIndex){
            this.r = r;
            this.g = g;
            this.b = b;
            this.xIndex = xIndex;
            this.yIndex = yIndex;
        }

        boolean gotIndex(int xIndex, int yIndex) {

            if (this.xIndex == xIndex && this.yIndex == yIndex) {

                return true;

            }

            return false;

        }

    }

    /**
     * SaveLedInfo erstellt eine LED und schreibt sie in das Array, dessen Inhalt später versendet wird
     * @param r
     * @param g
     * @param b
     * @param xIndex 0-34
     * @param yIndex 0-7
     */
    void SaveLedInfo(int r, int g, int b, int xIndex, int yIndex)
    {

        Led tmp = new Led(r,g,b,xIndex,yIndex);

        leds.add(tmp);

    }

    /**
     * SendLedInfo verschickt das Array an LEDs per OSC
     * @param led
     * @throws OSCSerializeException
     * @throws IOException
     */
    void SendLedInfo(Led led) throws OSCSerializeException, IOException {

        if(ableToSend) {
            String path = "/lighthouse/lightx" + led.xIndex + "y" + led.yIndex;

            // sends from "localhost"
            List<Object> args = new ArrayList<Object>(3);
            args.add(led.r);
            args.add(led.g);
            args.add(led.b);

            OSCMessage msg = new OSCMessage(path, args);
            oscPortOut.send(msg);

            path = "/segi1/info";

            args = new ArrayList<Object>(3);
            args.add(led.r);
            args.add(led.g);
            args.add(led.b);

            msg = new OSCMessage(path, args);
            oscPortOut.send(msg);
        } else {
            System.out.println("Not sending because other service is using the Lighthouse");
        }
    }

    /**
     * UpdateLeds überprüft, ob die Farbe der LED überschrieben werden muss
     * @throws OSCSerializeException
     * @throws IOException
     */
    void UpdateLeds() throws OSCSerializeException, IOException {

        for (int x = 0; x <= 34; x++){
            for (int y = 0; y <= 7; y++){
                for (int i = 0; i < leds.size(); i++) {
                    // Current Led gets changed
                    if (leds.get(i).gotIndex(x,y)){
                        SendLedInfo(leds.get(i));
                        i = leds.size();
                        break;

                    } // Clear
                    else if (i == leds.size()-1) {
                        SendLedInfo(new Led(0,0,0,x,y));

                    }
                }
            }
        }

    }

    /**
     * randInt gibt einen zufälligen Int Wert in einer Range von min-max zurück
     * @param min
     * @param max
     * @return
     */
    public int randInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * ActivateColumnRandomColor aktiviert eine komplette Spalte an Leds in einer random Farbe.
     * @param index
     */
    void ActivateColumnRandomColor(int index) {

        int r;
        int g;
        int b;

        for (int y = 0; y <= 7; y++) {

            r = randInt(0, 255);
            g = randInt(0, 255);
            b = randInt(0, 255);

            while (r <= 25 && g <= 25 && b <= 25){
                r = randInt(0, 255);
                g = randInt(0, 255);
                b = randInt(0, 255);
            }

            SaveLedInfo(r, g, b, index, y);
        }

        //UpdateLeds();

    }

    /**
     * ActivateRowRandomColor aktiviert eine komplette Reihe an Leds in einer random Farbe.
     * @param index
     * @throws OSCSerializeException
     * @throws IOException
     */
    void ActivateRowRandomColor(int index) throws OSCSerializeException, IOException {

        int r;
        int g;
        int b;

        for (int x = 0; x <= 34; x++) {

            r = randInt(0, 255);
            g = randInt(0, 255);
            b = randInt(0, 255);

            while (r <= 25 && g <= 25 && b <= 25){
                r = randInt(0, 255);
                g = randInt(0, 255);
                b = randInt(0, 255);
            }

            SaveLedInfo(r, g, b, x, index);
        }

        //UpdateLeds();

    }

    /**
     * Malt eine ganze Spalte im angegebenen RBG-Wert an
     * @param r
     * @param g
     * @param b
     * @param index 0-34
     * @throws OSCSerializeException
     * @throws IOException
     */
    void ActivateColumn(int r, int g, int b, int index) throws OSCSerializeException, IOException {

        for (int y = 0; y <= 7; y++) {
            SaveLedInfo(r, g, b, index, y);
        }

        //UpdateLeds();

    }

    /**
     * Malt einen Teil von (from) bis (to) in Spalte X in dem RGB-Wert an
     * @param r
     * @param g
     * @param b
     * @param x 0-34
     * @param from
     * @param to
     * @throws OSCSerializeException
     * @throws IOException
     */
    void ActivateColumnDynamic(int r, int g, int b, int x, int from, int to) throws OSCSerializeException, IOException {

        for (int y = to; y <= from; y++) {
            SaveLedInfo(r, g, b, x, y);
        }

        //UpdateLeds();

    }

     /**
     * Malt eine Zeile in dem angegebenen RBG-Wert an
     * @param r
     * @param g
     * @param b
     * @param index 0-7
     * @throws OSCSerializeException
     * @throws IOException
     */
    void ActivateRow(int r, int g, int b, int index) throws OSCSerializeException, IOException {

        for (int x = 0; x <= 34; x++) {
            SaveLedInfo(r, g, b, x, index);
        }

        //UpdateLeds();

    }

    /**
     * Malt einen Teil von (from) bis (to) in Reihe Y in dem RGB-Wert an
     * @param r
     * @param g
     * @param b
     * @param y
     * @param from
     * @param to
     * @throws OSCSerializeException
     * @throws IOException
     */
    void ActivateRowDynamic(int r, int g, int b, int y, int from, int to) throws OSCSerializeException, IOException {

        for (int x = from; x <= to; x++) {
            SaveLedInfo(r, g, b, x, y);
        }

        //UpdateLeds();

    }
}
