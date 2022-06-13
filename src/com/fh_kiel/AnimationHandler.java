package com.fh_kiel;

import com.illposed.osc.*;
import java.io.IOException;

/**
 * AnimationHandler dient zum aufrufen der Methoden für Animationen, welche Objekte
 * der Klasse LED in ein Array der LEDHelper schreiben, welches dann iterativ über OSC versendet wird
 * Das Lighthouse hat 8 Zeilen      --> x index 0-7
 * Das Lighthouse hat 35 Spalten    --> y index 0-34
 * @author Alexander Munkelt & Fabian Gusek
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
     * WriteUmgebung() malt den den statischen Hintergrund für die Superman-Animation(JumpNRun)
     */
    void WriteUmgebung(){
        helper.SaveLedInfo(255, 0, 0, 15, 5);
        helper.SaveLedInfo(255, 255, 255, 15, 6);
        helper.SaveLedInfo(255, 0, 0, 16, 5);
        helper.SaveLedInfo(255, 255, 255, 16, 6);

        for(int i = 0; i <= 14; i++){
            helper.SaveLedInfo(0, 0, 255, i, 7);
        }
        for(int i = 15; i <= 34; i++){
            helper.SaveLedInfo(102, 255, 51, i, 7);
        }
    }

    /**
     * JumpNRun ist eine der Animationen, die vom Nutzer durch eine Geste ausgelöst werden kann
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    void JumpNRun() throws InterruptedException, OSCSerializeException, IOException {

        int delay = 100;

        //Diverschen 64,110,122 Frame 1
        for (int j = 34; j >= 25; j--){
            Thread.sleep(delay);
            helper.leds.clear();

            WriteUmgebung();

            helper.SaveLedInfo(64, 110, 122, j-2, 6);
            helper.SaveLedInfo(64, 110, 122, j-5, 6);

            helper.SaveLedInfo(255, 0, 0, j, 5);
            helper.SaveLedInfo(64, 110, 122, j-3, 5);
            helper.SaveLedInfo(64, 110, 122, j-4, 5);

            helper.SaveLedInfo(255, 0, 0, j-1, 4);
            helper.SaveLedInfo(64, 110, 122, j-4, 4);
            helper.SaveLedInfo(64, 110, 122, j-6, 4);

            helper.SaveLedInfo(255, 0, 0, j-2, 3);
            helper.SaveLedInfo(64, 110, 122, j-4, 3);
            helper.SaveLedInfo(64, 110, 122, j-7, 3);

            helper.SaveLedInfo(64, 110, 122, j-3, 2);
            helper.SaveLedInfo(64, 110, 122, j-4, 2);
            helper.SaveLedInfo(64, 110, 122, j-5, 2);

            helper.SaveLedInfo(64, 110, 122, j-3, 1);
            helper.SaveLedInfo(64, 110, 122, j-4, 1);
            helper.SaveLedInfo(255, 0, 0, j-5, 1);

            helper.SaveLedInfo(64, 110, 122, j-3, 0);
            helper.SaveLedInfo(64, 110, 122, j-4, 0);
            helper.SaveLedInfo(64, 110, 122, j-5, 0);

            helper.UpdateLeds();
        }


        helper.leds.clear();
        WriteUmgebung();
        //X18-22 Y1-5
        helper.ActivateRowDynamic(0, 255, 0, 1, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 2, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 3, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 4, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 5, 18, 22);

        helper.UpdateLeds();
        helper.leds.clear();
        WriteUmgebung();

        helper.ActivateRowDynamic(0, 255, 0, 1, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 2, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 3, 18, 19);
        helper.SaveLedInfo(255, 255, 255, 20, 3);
        helper.ActivateRowDynamic(0, 255, 0, 3, 21, 22);
        helper.ActivateRowDynamic(0, 255, 0, 4, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 5, 18, 22);

        helper.UpdateLeds();
        helper.leds.clear();
        WriteUmgebung();

        helper.ActivateRowDynamic(0, 255, 0, 1, 18, 22);
        helper.ActivateRowDynamic(0, 255, 0, 5, 18, 22);
        helper.ActivateColumnDynamic(0, 255, 0, 18, 1, 5);
        helper.ActivateColumnDynamic(0, 255, 0, 22, 1, 5);

        helper.ActivateRowDynamic(255, 255, 255, 2, 19, 21);

        helper.SaveLedInfo(255, 255, 255, 19, 3);
        helper.SaveLedInfo(0, 255, 0, 20, 3);
        helper.SaveLedInfo(255, 255, 255, 21, 3);

        helper.ActivateRowDynamic(255, 255, 255, 4, 19, 21);

        helper.UpdateLeds();
        helper.leds.clear();
        WriteUmgebung();

        helper.ActivateRowDynamic(255, 255, 255, 1, 18, 22);
        helper.ActivateColumnDynamic(255, 255, 255, 18, 1, 5);
        helper.ActivateRowDynamic(0, 255, 0, 2, 19, 21);
        helper.ActivateRowDynamic(0, 255, 0, 3, 19, 21);
        helper.ActivateRowDynamic(0, 255, 0, 4, 19, 21);
        helper.ActivateColumnDynamic(255, 255, 255, 22, 1, 5);
        helper.ActivateRowDynamic(255, 255, 255, 5, 18, 22);

        helper.UpdateLeds();
        helper.leds.clear();
        WriteUmgebung();

        helper.ActivateRowDynamic(255, 255, 255, 1, 18, 22);
        helper.ActivateRowDynamic(255, 255, 255, 2, 18, 22);
        helper.ActivateRowDynamic(255, 255, 255, 3, 18, 22);
        helper.ActivateRowDynamic(255, 255, 255, 4, 18, 22);
        helper.ActivateRowDynamic(255, 255, 255, 5, 18, 22);

        helper.UpdateLeds();
        helper.leds.clear();
        WriteUmgebung();

        helper.SaveLedInfo(255, 255, 255, 18, 1);
        helper.SaveLedInfo(255, 255, 255, 22, 1);

        helper.SaveLedInfo(255, 255, 255, 19, 2);
        helper.SaveLedInfo(255, 255, 255, 21, 2);

        helper.SaveLedInfo(255, 255, 255, 20, 3);

        helper.SaveLedInfo(255, 255, 255, 19, 4);
        helper.SaveLedInfo(255, 255, 255, 21, 4);

        helper.SaveLedInfo(255, 255, 255, 18, 5);
        helper.SaveLedInfo(255, 255, 255, 22, 5);

        helper.UpdateLeds();
        helper.leds.clear();
        WriteUmgebung();

        for (int a = 22; a >= -7; a--){
            Thread.sleep(50);
            helper.leds.clear();

            WriteUmgebung();

            helper.ActivateRowDynamic(64, 110, 122, 0, a-6, a-4);

            helper.SaveLedInfo(255, 0, 0, a-6, 1);
            helper.ActivateRowDynamic(64, 110, 122, 1, a-5, a-4);

            helper.ActivateRowDynamic(64, 110, 122, 2, a-6, a-4);
            helper.ActivateRowDynamic(255, 0, 0, 2, a-3, a-2);
            helper.SaveLedInfo(64, 110, 122, a, 2);

            helper.ActivateRowDynamic(64, 110, 122, 3, a-4, a-2);

            helper.ActivateRowDynamic(64, 110, 122, 4, a-6, a-4);
            helper.SaveLedInfo(64, 110, 122, a, 4);

            helper.UpdateLeds();
        }
    }

    /**
     * SwipeRight ist eine der Animationen, die vom Nutzer durch eine Geste ausgelöst werden kann
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    void SwipeRight() throws InterruptedException, OSCSerializeException, IOException {

        int delay = 100;

        helper.leds.clear();

        helper.ActivateColumnRandomColor(0);
        helper.UpdateLeds();

        Thread.sleep(delay);

        helper.ActivateColumnRandomColor(1);
        helper.UpdateLeds();

        Thread.sleep(delay);

        helper.ActivateColumnRandomColor(2);
        helper.UpdateLeds();

        for (int x = 1; x <= 52; x++) {
            Thread.sleep(delay);

            helper.leds.clear();

            helper.ActivateColumnRandomColor(x);
            helper.ActivateColumnRandomColor(x+1);
            helper.ActivateColumnRandomColor(x+2);

            helper.ActivateColumnRandomColor(x-4);
            helper.ActivateColumnRandomColor(x-3);
            helper.ActivateColumnRandomColor(x-2);

            helper.ActivateColumnRandomColor(x-8);
            helper.ActivateColumnRandomColor(x-7);
            helper.ActivateColumnRandomColor(x-6);

            helper.ActivateColumnRandomColor(x-12);
            helper.ActivateColumnRandomColor(x-11);
            helper.ActivateColumnRandomColor(x-10);

            helper.ActivateColumnRandomColor(x-16);
            helper.ActivateColumnRandomColor(x-15);
            helper.ActivateColumnRandomColor(x-14);

            helper.UpdateLeds();
        }
    }

    /**
     * SwipeLeft ist eine der Animationen, die vom Nutzer durch eine Geste ausgelöst werden kann
     * @throws InterruptedException
     * @throws OSCSerializeException
     * @throws IOException
     */
    void SwipeLeft() throws InterruptedException, OSCSerializeException, IOException {

        int delay = 100;

        helper.leds.clear();

        helper.ActivateColumnRandomColor(34);
        helper.UpdateLeds();

        Thread.sleep(delay);

        helper.ActivateColumnRandomColor(33);
        helper.UpdateLeds();

        Thread.sleep(delay);

        helper.ActivateColumnRandomColor(32);
        helper.UpdateLeds();

        for (int x = 31; x >= -17; x--) {
            Thread.sleep(delay);

            helper.leds.clear();

            helper.ActivateColumnRandomColor(x);
            helper.ActivateColumnRandomColor(x-1);
            helper.ActivateColumnRandomColor(x-2);

            helper.ActivateColumnRandomColor(x+4);
            helper.ActivateColumnRandomColor(x+3);
            helper.ActivateColumnRandomColor(x+2);

            helper.ActivateColumnRandomColor(x+8);
            helper.ActivateColumnRandomColor(x+7);
            helper.ActivateColumnRandomColor(x+6);

            helper.ActivateColumnRandomColor(x+12);
            helper.ActivateColumnRandomColor(x+11);
            helper.ActivateColumnRandomColor(x+10);

            helper.ActivateColumnRandomColor(x+16);
            helper.ActivateColumnRandomColor(x+15);
            helper.ActivateColumnRandomColor(x+14);

            helper.UpdateLeds();
        }
    }

}
