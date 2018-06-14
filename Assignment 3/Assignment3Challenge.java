/* Code for ENGR110 Assignment
 * Name: Daniel Armstrong
 * Usercode:armstrdani1
 * ID:300406381
 *
 * Worked with Wanja Leuthold.
 */

import ecs100.*;

import java.util.*;

/**
 * FSM Controller for a simulated Lift.
 * The core of the controller is the signal(String sensor) method
 * which is called by the lift every time a sensor
 * is signalled.
 * <p>
 * Note, when your controller is started, the lift will be on floor 1 with the doors closed.
 * You should set the initial state of the controller to match this.
 */
public class LiftController {

    /**
     * The field that stores the current state of the FSM
     */
    private String state = "AtFloor1";
    // Floor Requests (Flags)
    private boolean req1 = false;
    private boolean req2 = false;
    private boolean req3 = false;
    private boolean isClosed = true;
    private boolean OverCapacity = false;

    /**
     * The field containing the Lift.
     * The signal method will call methods on the lift to operate it
     */
    private Lift lift;  // the lift that is being controlled.

    // possible actions on the lift that you can perform:
    //lift.moveUp()             to start the lift moving up
    // lift.moveDown()           to start the lift moving down
    // lift.stop()               to stop the lift
    // lift.openDoor()           to start the doors opening
    // lift.closeDoor()          to start the doors closing
    // lift.restartTimer(1000)   to set the time for 1000 milliseconds
    // lift.turnWarningLightOn() to turn the warning light on
    // lift.turnWarningLightOff()to turn the warning light off

    /**
     * The Constructor is passed the lift that it is controlling.
     */
    public LiftController(Lift lift) {
        this.lift = lift;
    }

    /**
     * Receives a change in a sensor value that may trigger an action and state change.
     * If there is a transition out of the current state associated with this
     * sensor signal,
     * - it will perform the appropriate action (if any)
     * - it will transition to the next state
     * (by calling changeToState with the new state).
     * <p>
     * Possible sensor values that you can respond to:
     * (note, you may not need to respond to all of them)
     * "request1"   "request2"   "request3"
     * "atF1"   "atF2"   "atF3"
     * "startUp"   "startDown"
     * "doorClosed"   "doorOpened"   "doorMoving"
     * "timerExpired"
     * "doorSensor"
     * "withinCapacity"   "overCapacity"
     * <p>
     * You can either have one big method, or you can break it up into
     * a separate method for each state
     */


    public void signal(String sensor) {
        UI.printf("In state: %s, got sensor: %s%n", state, sensor);

        //MOVING TO 1
        if (state.equals("movingTo1")) {
            if (sensor.equals("atF1")) {
                lift.stop();
                lift.openDoor();
                isClosed = false;
                lift.restartTimer(5000);
                state = "AtFloor1";
            } else if (sensor.equals("request2")) {
                req2 = true;
            } else if (sensor.equals("request3")) {
                req3 = true;
            }
        }
        //FLOOR 1
        else if (state.equals("AtFloor1")) {
            if (sensor.equals("request1")) {
                lift.openDoor();
                isClosed = false;
                lift.restartTimer(5000);
            } else if (sensor.equals("request2")) {
                req2 = true;
            } else if (sensor.equals("request3")) {
                req3 = true;
            } else if (sensor.equals("timerExpired")) {
                lift.closeDoor();
            } else if (sensor.equals("doorClosed")) {
                isClosed = true;
            } else if (sensor.equals("doorSensor")) {
                lift.restartTimer(3000);
            } else if (sensor.equals("overCapacity")) {
                lift.openDoor();
                lift.turnWarningLightOn();
                lift.restartTimer(5000);
                OverCapacity = true;
            } else if (sensor.equals("withinCapacity")) {
                OverCapacity = false;
                lift.turnWarningLightOff();
                lift.closeDoor();
            }
            if (isClosed && req2 && !OverCapacity) {
                lift.moveUp();
                req2 = false;
                state = "movingTo2";
            } else if (isClosed && req3 && !OverCapacity) {
                lift.moveUp();
                req3 = false;
                state = "movingTo3";
            }
        }
        //MOVING TO 2
        else if (state.equals("movingTo2")) {
            if (sensor.equals("atF2")) {
                lift.stop();
                lift.openDoor();
                isClosed = false;
                lift.restartTimer(5000);
                state = "AtFloor2";
            } else if (sensor.equals("request1")) {
                req1 = true;
            } else if (sensor.equals("request3")) {
                req3 = true;
            }
        }
        //FLOOR 2
        else if (state.equals("AtFloor2")) {
            if (sensor.equals("request1")) {
                req1 = true;
            } else if (sensor.equals("request2")) {
                lift.openDoor();
                isClosed = false;
                lift.restartTimer(5000);
            } else if (sensor.equals("request3")) {
                req3 = true;
            } else if (sensor.equals("timerExpired")) {
                lift.closeDoor();
            } else if (sensor.equals("doorClosed")) {
                isClosed = true;
            } else if (sensor.equals("doorSensor")) {
                lift.restartTimer(3000);
            } else if (sensor.equals("overCapacity")) {
                lift.openDoor();
                lift.turnWarningLightOn();
                lift.restartTimer(5000);
                OverCapacity = true;
            } else if (sensor.equals("withinCapacity")) {
                OverCapacity = false;
                lift.turnWarningLightOff();
                lift.closeDoor();
            }
            if (isClosed && req3 && !OverCapacity) {
                lift.moveUp();
                req3 = false;
                state = "movingTo3";
            } else if (isClosed && req1 && !OverCapacity) {
                lift.moveDown();
                req1 = false;
                state = "movingTo1";
            }

        }
        //MOVING TO 3
        else if (state.equals("movingTo3")) {
            if (sensor.equals("atF3")) {
                lift.stop();
                lift.openDoor();
                isClosed = false;
                lift.restartTimer(5000);
                state = "AtFloor3";
            } else if (sensor.equals("request1")) {
                req1 = true;
            } else if (sensor.equals("request2")) {
                req2 = true;
            }
        }
        //FLOOR 3
        else if (state.equals("AtFloor3")) {
            if (sensor.equals("request1")) {
                req1 = true;
            } else if (sensor.equals("request2")) {
                req2 = true;
            } else if (sensor.equals("request3")) {
                lift.openDoor();
                isClosed = false;
                lift.restartTimer(3000);
            } else if (sensor.equals("timerExpired")) {
                lift.closeDoor();
            } else if (sensor.equals("doorClosed")) {
                isClosed = true;
            } else if (sensor.equals("doorSensor")) {
                lift.restartTimer(3000);
            } else if (sensor.equals("overCapacity")) {
                lift.openDoor();
                lift.turnWarningLightOn();
                lift.restartTimer(5000);
                OverCapacity = true;
            } else if (sensor.equals("withinCapacity")) {
                OverCapacity = false;
                lift.turnWarningLightOff();
                lift.closeDoor();
            }
            if (isClosed && req1 && !OverCapacity) {
                lift.moveDown();
                req1 = false;
                state = "movingTo1";
            } else if (isClosed && req2 && !OverCapacity) {
                lift.moveDown();
                req2 = false;
                state = "movingTo2";
            }
        }
    }
}

