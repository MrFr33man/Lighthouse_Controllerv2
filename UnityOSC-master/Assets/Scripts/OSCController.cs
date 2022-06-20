using UnityEngine;
using System.Collections;
using Leap;
using Leap.Unity;

public class OSCController : MonoBehaviour
{
    public static OSCController instance;

    // Wird vor Start aufgerufen
    void Awake()
    {
        instance = this;
    }

    public OSC osc;
    public bool useLeapmotionController = true;
    public string defaultMessage = "SwipeRight";

    private bool isSending;

    private Controller controller;
    private Frame frame;
    private Hand leftHand;
    private Hand rightHand;

    // Wird am Anfang einmal aufgerufen
    void Start()
    {
        controller = new Controller();

        if (!useLeapmotionController)
        {
            OSCMessage(defaultMessage);
        }
    }

    // Wird jeden Frame aufgerufen
    void Update()
    {
        if (useLeapmotionController)
        {         
            // Kein Leapmotion Connected
            if (controller == null)
            {
                print("CONTROLLER NOT CONNECTED!!!");
            }
            else
            {
                print("CONTROLLER CONNECTED");

                frame = controller.Frame();

                leftHand = frame.GetHand(Chirality.Left);
                rightHand = frame.GetHand(Chirality.Right);

                if (Pinching() || Swiping())
                {
                    print("GESTURE FOUND");
                } else
                {
                    print("GESTURE NOT FOUND!!!");
                }
            }
        }
    }

    // Wird aufgerufen wenn der Leap Motion Controller die Geste "Pinching" erkennt
    bool Pinching()
    {
        if (leftHand != null)
        {
            if (leftHand.IsPinching())
            {
                if (isSending == false)
                {
                    OSCMessage("EQ");
                    return true;
                }
            }
        }

        if (rightHand != null)
        {
            if (rightHand.IsPinching())
            {
                if (isSending == false)
                {
                    OSCMessage("JumpNRun");
                    return true;
                }
            }
        }

        return false;
    }

    // Wird aufgerufen wenn der Leap Motion Controller die Geste "Swiping" erkennt
    bool Swiping()
    {
        if (leftHand != null)
        {
            if (leftHand.GetFistStrength() > 0.8f)
            {
                if (isSending == false)
                {
                    OSCMessage("SwipeRight");
                    return true;
                }
            }
        }

        if (rightHand != null)
        {
            if (rightHand.GetFistStrength() > 0.8f)
            {
                if (isSending == false)
                {
                    OSCMessage("SwipeLeft");
                    return true;
                }
            }
        }

        return false;
    }

    // Sendet eine OSCMessage an das Lighthouse
    void OSCMessage(string animation)
    {
        try
        {
            isSending = true;
            OscMessage message;

            message = new OscMessage();
            message.address = "/Unity";
            message.values.Add(animation);
            osc.Send(message);

            StartCoroutine(ResetBool());

            print("OSC MESSAGE HAS BEEN SENDED");
        }
        catch (System.Exception)
        {
            print("OSC MESSAGE COULDNT BE SEND!!!");
            throw;
        }

    }

    // Setzt die Variable "isSending" nach 10 Sekunden auf false
    IEnumerator ResetBool()
    {
        yield return new WaitForSeconds(10);

        isSending = false;
    }
}
