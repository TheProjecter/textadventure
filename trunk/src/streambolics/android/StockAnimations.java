package streambolics.android;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class StockAnimations
{
    private final static int X_NORTH = -1;
    private final static int X_SOUTH = 1;
    private final static int Y_WEST = -1;
    private final static int Y_EAST = 1;

    private static Animation newApparate (int vert, int hor)
    {
        Animation a = new TranslateAnimation (Animation.RELATIVE_TO_SELF, hor, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, vert,
                Animation.RELATIVE_TO_SELF, 0);
        a.setDuration (600);
        return a;
    }

    private static Animation newDisapparate (int vert, int hor)
    {
        Animation a = new TranslateAnimation (Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, hor, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, vert);
        a.setDuration (600);
        return a;
    }

    private static Animation _ApparateNorth;
    private static Animation _ApparateSouth;
    private static Animation _ApparateEast;
    private static Animation _ApparateWest;
    private static Animation _ApparateNorthEast;
    private static Animation _ApparateSouthEast;
    private static Animation _ApparateNorthWest;
    private static Animation _ApparateSouthWest;

    private static Animation _DisapparateNorth;
    private static Animation _DisapparateSouth;
    private static Animation _DisapparateEast;
    private static Animation _DisapparateWest;
    private static Animation _DisapparateNorthEast;
    private static Animation _DisapparateSouthEast;
    private static Animation _DisapparateNorthWest;
    private static Animation _DisapparateSouthWest;

    public static Animation getApparateNorth ()
    {
        if (_ApparateNorth == null)
        {
            _ApparateNorth = newApparate (X_NORTH, 0);
        }
        return _ApparateNorth;
    }

    public static Animation getApparateSouth ()
    {
        if (_ApparateSouth == null)
        {
            _ApparateSouth = newApparate (X_SOUTH, 0);
        }
        return _ApparateSouth;
    }

    public static Animation getApparateEast ()
    {
        if (_ApparateEast == null)
        {
            _ApparateEast = newApparate (0, Y_EAST);
        }
        return _ApparateEast;
    }

    public static Animation getApparateWest ()
    {
        if (_ApparateWest == null)
        {
            _ApparateWest = newApparate (0, Y_WEST);
        }
        return _ApparateWest;
    }

    public static Animation getApparateNorthEast ()
    {
        if (_ApparateNorthEast == null)
        {
            _ApparateNorthEast = newApparate (X_NORTH, Y_EAST);
        }
        return _ApparateNorthEast;
    }

    public static Animation getApparateSouthEast ()
    {
        if (_ApparateSouthEast == null)
        {
            _ApparateSouthEast = newApparate (X_SOUTH, Y_EAST);
        }
        return _ApparateSouthEast;
    }

    public static Animation getApparateNorthWest ()
    {
        if (_ApparateNorthWest == null)
        {
            _ApparateNorthWest = newApparate (X_NORTH, Y_WEST);
        }
        return _ApparateNorthWest;
    }

    public static Animation getApparateSouthWest ()
    {
        if (_ApparateSouthWest == null)
        {
            _ApparateSouthWest = newApparate (X_SOUTH, Y_WEST);
        }
        return _ApparateSouthWest;
    }

    public static Animation getDisapparateNorth ()
    {
        if (_DisapparateNorth == null)
        {
            _DisapparateNorth = newDisapparate (X_NORTH, 0);
        }
        return _DisapparateNorth;
    }

    public static Animation getDisapparateSouth ()
    {
        if (_DisapparateSouth == null)
        {
            _DisapparateSouth = newDisapparate (X_SOUTH, 0);
        }
        return _DisapparateSouth;
    }

    public static Animation getDisapparateEast ()
    {
        if (_DisapparateEast == null)
        {
            _DisapparateEast = newDisapparate (0, Y_EAST);
        }
        return _DisapparateEast;
    }

    public static Animation getDisapparateWest ()
    {
        if (_DisapparateWest == null)
        {
            _DisapparateWest = newDisapparate (0, Y_WEST);
        }
        return _DisapparateWest;
    }

    public static Animation getDisapparateNorthEast ()
    {
        if (_DisapparateNorthEast == null)
        {
            _DisapparateNorthEast = newDisapparate (X_NORTH, Y_EAST);
        }
        return _DisapparateNorthEast;
    }

    public static Animation getDisapparateSouthEast ()
    {
        if (_DisapparateSouthEast == null)
        {
            _DisapparateSouthEast = newDisapparate (X_SOUTH, Y_EAST);
        }
        return _DisapparateSouthEast;
    }

    public static Animation getDisapparateNorthWest ()
    {
        if (_DisapparateNorthWest == null)
        {
            _DisapparateNorthWest = newDisapparate (X_NORTH, Y_WEST);
        }
        return _DisapparateNorthWest;
    }

    public static Animation getDisapparateSouthWest ()
    {
        if (_DisapparateSouthWest == null)
        {
            _DisapparateSouthWest = newDisapparate (X_SOUTH, Y_WEST);
        }
        return _DisapparateSouthWest;
    }

}
