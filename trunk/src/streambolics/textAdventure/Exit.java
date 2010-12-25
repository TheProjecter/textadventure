package streambolics.textAdventure;

import streambolics.core.Logger;
import streambolics.core.Tokenizer;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class Exit
{
    private Game _Game;
    private Item _SourceRoom;
    private Item _DestinationRoom;
    private Item _Door;

    public Exit (Game aGame, Item aSourceRoom, Tokenizer t)
    {
        _Game = aGame;
        _SourceRoom = aSourceRoom;
        init (t);
    }

    public Exit (Game aGame, Item aSourceRoom, String d)
    {
        _Game = aGame;
        _SourceRoom = aSourceRoom;
        init (new Tokenizer (d));
    }

    private void init (Tokenizer t)
    {
        String d = t.getWord ();
        String k = t.getWord ();

        if (k.equals ("->"))
        {
            _Door = _Game.accessItem (d);
            _DestinationRoom = _Game.accessItem (t.getWord ());
        }
        else
        {
            _Door = null;
            _DestinationRoom = _Game.accessItem (d);
        }
    }

    public boolean isVisible ()
    {
        return _SourceRoom.hasLight () || (isOpen () && _DestinationRoom.hasLight ());
    }

    public boolean isOpen ()
    {
        return (_Door == null) || (_Door.isOpen ());
    }

    public boolean isLocked ()
    {
        return _Door != null && _Door.isLocked ();
    }

    public Item getDestination ()
    {
        return _DestinationRoom;
    }

    public String getDescription ()
    {
        if (_Door == null)
        {
            return "An obvious exit";
        }
        else if (_Door.isOpen ())
        {
            return _Door.getDescription ();
        }
        else
        {
            return _Door.getDescription () + " (closed)";
        }
    }

    public void operate (Logger aLogger)
    {
        if (_Door == null)
        {
            aLogger.Log ("Nothing happens");
        }
        else
        {
            _Door.operate (aLogger);
        }
    }

    public void operateWith (Item aItem, Logger aLogger)
    {
        if (_Door == null)
        {
            aLogger.Log ("Nothing happens");
        }
        else
        {
            _Door.operateWith (aItem, aLogger);
        }
    }

    public Item getDoor ()
    {
        return _Door;
    }

    public Drawable getFloorDrawable (Context aContext)
    {
        if (_Door == null)
        {
            return _SourceRoom.getFloorDrawable (aContext);
        }
        else
        {
            return _Door.getFloorDrawable (aContext);
        }
    }

    public Drawable getWallDrawable (Context aContext)
    {
        if (_Door == null)
        {
            return _SourceRoom.getWallDrawable (aContext);
        }
        else
        {
            return _Door.getWallDrawable (aContext);
        }
    }

    public Drawable getOpenDoorDrawable (Context aContext)
    {
        if (_Door == null)
        {
            return _SourceRoom.getOpenDoorDrawable (aContext);
        }
        else
        {
            return _Door.getOpenDoorDrawable (aContext);
        }
    }

    public Drawable getClosedDoorDrawable (Context aContext)
    {
        if (_Door == null)
        {
            return _SourceRoom.getClosedDoorDrawable (aContext);
        }
        else
        {
            return _Door.getClosedDoorDrawable (aContext);
        }
    }
}
