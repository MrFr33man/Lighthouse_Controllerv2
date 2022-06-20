package com.fh_kiel;

import com.illposed.osc.*;
import java.io.IOException;

/**
 * AnimationHandler dient zum aufrufen der Methoden für Animationen, welche Objekte
 * der Klasse LED in ein Array der LEDHelper schreiben, welches dann iterativ über OSC versendet wird
 * Das Lighthouse hat 8 Zeilen      --> x index 0-7
 * Das Lighthouse hat 35 Spalten    --> y index 0-34
 * @author Alexander Munkelt, Fabian Gusek, Felix Seeburg
 */
public class AnimationHandler {

    /**
     * Singleton Implementation
     * Es gibt nur EINE Instanz der Klasse, welche alle Methoden aufruft
     */
    private static LEDHelper helper;

    private static AnimationHandler OBJ = null;

    static {
        try {
            OBJ = new AnimationHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AnimationHandler() throws IOException {
        helper = LEDHelper.getInstance();
    }

    public static AnimationHandler getInstance() {
        return OBJ;
    }

    /**
     * writeUmgebung() malt den den statischen Hintergrund für die Superman-Animation(jumpNRun)
     */
    void writeUmgebung(){
        helper.saveLedInfo(255, 0, 0, 15, 5);
        helper.saveLedInfo(255, 255, 255, 15, 6);
        helper.saveLedInfo(255, 0, 0, 16, 5);
        helper.saveLedInfo(255, 255, 255, 16, 6);

        for(int i = 0; i <= 14; i++){
            helper.saveLedInfo(0, 0, 255, i, 7);
        }
        for(int i = 15; i <= 34; i++){
            helper.saveLedInfo(102, 255, 51, i, 7);
        }
    }

    /**
     * jumpNRun ist eine der Animationen, die vom Nutzer durch eine Geste ausgelöst werden kann
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    void jumpNRun() throws InterruptedException, OSCSerializeException, IOException {

        int delay = 100;

        /**
         * Male Umgebung und laufendes Männchen
         */
        //Diverschen 64,110,122 Frame 1
        for (int j = 34; j >= 25; j--){
            Thread.sleep(delay);
            helper.leds.clear();

            writeUmgebung();

            helper.saveLedInfo(64, 110, 122, j-2, 6);
            helper.saveLedInfo(64, 110, 122, j-5, 6);

            helper.saveLedInfo(255, 0, 0, j, 5);
            helper.saveLedInfo(64, 110, 122, j-3, 5);
            helper.saveLedInfo(64, 110, 122, j-4, 5);

            helper.saveLedInfo(255, 0, 0, j-1, 4);
            helper.saveLedInfo(64, 110, 122, j-4, 4);
            helper.saveLedInfo(64, 110, 122, j-6, 4);

            helper.saveLedInfo(255, 0, 0, j-2, 3);
            helper.saveLedInfo(64, 110, 122, j-4, 3);
            helper.saveLedInfo(64, 110, 122, j-7, 3);

            helper.saveLedInfo(64, 110, 122, j-3, 2);
            helper.saveLedInfo(64, 110, 122, j-4, 2);
            helper.saveLedInfo(64, 110, 122, j-5, 2);

            helper.saveLedInfo(64, 110, 122, j-3, 1);
            helper.saveLedInfo(64, 110, 122, j-4, 1);
            helper.saveLedInfo(255, 0, 0, j-5, 1);

            helper.saveLedInfo(64, 110, 122, j-3, 0);
            helper.saveLedInfo(64, 110, 122, j-4, 0);
            helper.saveLedInfo(64, 110, 122, j-5, 0);

            helper.updateLeds();
        }


        /**
         * Verwandlung
         */
        helper.leds.clear();
        writeUmgebung();
        //X18-22 Y1-5
        helper.activateRowDynamic(0, 255, 0, 1, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 2, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 3, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 4, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 5, 18, 22);

        helper.updateLeds();
        helper.leds.clear();
        writeUmgebung();

        helper.activateRowDynamic(0, 255, 0, 1, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 2, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 3, 18, 19);
        helper.saveLedInfo(255, 255, 255, 20, 3);
        helper.activateRowDynamic(0, 255, 0, 3, 21, 22);
        helper.activateRowDynamic(0, 255, 0, 4, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 5, 18, 22);

        helper.updateLeds();
        helper.leds.clear();
        writeUmgebung();

        helper.activateRowDynamic(0, 255, 0, 1, 18, 22);
        helper.activateRowDynamic(0, 255, 0, 5, 18, 22);
        helper.activateColumnDynamic(0, 255, 0, 18, 1, 5);
        helper.activateColumnDynamic(0, 255, 0, 22, 1, 5);

        helper.activateRowDynamic(255, 255, 255, 2, 19, 21);

        helper.saveLedInfo(255, 255, 255, 19, 3);
        helper.saveLedInfo(0, 255, 0, 20, 3);
        helper.saveLedInfo(255, 255, 255, 21, 3);

        helper.activateRowDynamic(255, 255, 255, 4, 19, 21);

        helper.updateLeds();
        helper.leds.clear();
        writeUmgebung();

        helper.activateRowDynamic(255, 255, 255, 1, 18, 22);
        helper.activateColumnDynamic(255, 255, 255, 18, 1, 5);
        helper.activateRowDynamic(0, 255, 0, 2, 19, 21);
        helper.activateRowDynamic(0, 255, 0, 3, 19, 21);
        helper.activateRowDynamic(0, 255, 0, 4, 19, 21);
        helper.activateColumnDynamic(255, 255, 255, 22, 1, 5);
        helper.activateRowDynamic(255, 255, 255, 5, 18, 22);

        helper.updateLeds();
        helper.leds.clear();
        writeUmgebung();

        helper.activateRowDynamic(255, 255, 255, 1, 18, 22);
        helper.activateRowDynamic(255, 255, 255, 2, 18, 22);
        helper.activateRowDynamic(255, 255, 255, 3, 18, 22);
        helper.activateRowDynamic(255, 255, 255, 4, 18, 22);
        helper.activateRowDynamic(255, 255, 255, 5, 18, 22);

        helper.updateLeds();
        helper.leds.clear();
        writeUmgebung();

        helper.saveLedInfo(255, 255, 255, 18, 1);
        helper.saveLedInfo(255, 255, 255, 22, 1);

        helper.saveLedInfo(255, 255, 255, 19, 2);
        helper.saveLedInfo(255, 255, 255, 21, 2);

        helper.saveLedInfo(255, 255, 255, 20, 3);

        helper.saveLedInfo(255, 255, 255, 19, 4);
        helper.saveLedInfo(255, 255, 255, 21, 4);

        helper.saveLedInfo(255, 255, 255, 18, 5);
        helper.saveLedInfo(255, 255, 255, 22, 5);

        helper.updateLeds();
        helper.leds.clear();
        writeUmgebung();

        /**
         * Fliegendes Männchen
         */
        for (int a = 22; a >= -7; a--){
            Thread.sleep(50);
            helper.leds.clear();

            writeUmgebung();

            helper.activateRowDynamic(64, 110, 122, 0, a-6, a-4);

            helper.saveLedInfo(255, 0, 0, a-6, 1);
            helper.activateRowDynamic(64, 110, 122, 1, a-5, a-4);

            helper.activateRowDynamic(64, 110, 122, 2, a-6, a-4);
            helper.activateRowDynamic(255, 0, 0, 2, a-3, a-2);
            helper.saveLedInfo(64, 110, 122, a, 2);

            helper.activateRowDynamic(64, 110, 122, 3, a-4, a-2);

            helper.activateRowDynamic(64, 110, 122, 4, a-6, a-4);
            helper.saveLedInfo(64, 110, 122, a, 4);

            helper.updateLeds();
        }
    }

    /**
     * swipeRight ist eine der Animationen, die vom Nutzer durch eine Geste ausgelöst werden kann
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    void swipeRight() throws InterruptedException, OSCSerializeException, IOException {

        int delay = 100;

        helper.leds.clear();

        helper.activateColumnRandomColor(0);
        helper.updateLeds();

        Thread.sleep(delay);

        helper.activateColumnRandomColor(1);
        helper.updateLeds();

        Thread.sleep(delay);

        helper.activateColumnRandomColor(2);
        helper.updateLeds();

        for (int x = 1; x <= 52; x++) {
            Thread.sleep(delay);

            helper.leds.clear();

            helper.activateColumnRandomColor(x);
            helper.activateColumnRandomColor(x+1);
            helper.activateColumnRandomColor(x+2);

            helper.activateColumnRandomColor(x-4);
            helper.activateColumnRandomColor(x-3);
            helper.activateColumnRandomColor(x-2);

            helper.activateColumnRandomColor(x-8);
            helper.activateColumnRandomColor(x-7);
            helper.activateColumnRandomColor(x-6);

            helper.activateColumnRandomColor(x-12);
            helper.activateColumnRandomColor(x-11);
            helper.activateColumnRandomColor(x-10);

            helper.activateColumnRandomColor(x-16);
            helper.activateColumnRandomColor(x-15);
            helper.activateColumnRandomColor(x-14);

            helper.updateLeds();
        }
    }

    /**
     * swipeLeft ist eine der Animationen, die vom Nutzer durch eine Geste ausgelöst werden kann
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    void swipeLeft() throws InterruptedException, OSCSerializeException, IOException {

        int delay = 100;

        helper.leds.clear();

        helper.activateColumnRandomColor(34);
        helper.updateLeds();

        Thread.sleep(delay);

        helper.activateColumnRandomColor(33);
        helper.updateLeds();

        Thread.sleep(delay);

        helper.activateColumnRandomColor(32);
        helper.updateLeds();

        for (int x = 31; x >= -17; x--) {
            Thread.sleep(delay);

            helper.leds.clear();

            helper.activateColumnRandomColor(x);
            helper.activateColumnRandomColor(x-1);
            helper.activateColumnRandomColor(x-2);

            helper.activateColumnRandomColor(x+4);
            helper.activateColumnRandomColor(x+3);
            helper.activateColumnRandomColor(x+2);

            helper.activateColumnRandomColor(x+8);
            helper.activateColumnRandomColor(x+7);
            helper.activateColumnRandomColor(x+6);

            helper.activateColumnRandomColor(x+12);
            helper.activateColumnRandomColor(x+11);
            helper.activateColumnRandomColor(x+10);

            helper.activateColumnRandomColor(x+16);
            helper.activateColumnRandomColor(x+15);
            helper.activateColumnRandomColor(x+14);

            helper.updateLeds();
        }
    }

    /**
     * swipeLeft ist eine der Animationen, die vom Nutzer durch eine Geste ausgelöst werden kann
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    void eq() throws InterruptedException, OSCSerializeException, IOException {

        int[] y = {7, 4, 5, 7, 1, 4, 3, 3, 5, 7, 5, 3, 1, 0, 2, 4, 6, 6, 4, 3, 3, 4, 5, 4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 3, 7};

        int delay = 100;
        for (int j = 0; j < 100; j++) {
            helper.leds.clear();
            for (int x = 0; x < 35; x++) {
                int r = helper.randInt(0, 255);
                int g = helper.randInt(0, 255);
                int b = helper.randInt(0, 255);
                helper.activateColumnDynamic(r, g, b, x, 7, y[x]);
            }
            helper.updateLeds();
            Thread.sleep(delay);

            for (int i = 0; i < y.length; i++) {
                int erg = helper.randInt(0, 1);
                if (erg == 0) {
                    if (y[i] != 7) {
                        y[i]++;
                    } else {
                        y[i]--;
                    }
                } else {
                    if (y[i] != 0) {
                        y[i]--;
                    } else {
                        y[i]++;
                    }
                }
            }
        }
    }
}
