package streambolics.textAdventure;

/*---------------------------------------------------------------------------------------------------

 Part of : Text Adventure Creator

 Copyright (C) 2010-2011  Stephan Leclercq

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

 ---------------------------------------------------------------------------------------------------*/

import streambolics.core.Tokenizer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Exit extends GameObject
{
    private static final String LOGTAG = "Exit";
    private Item _SourceRoom;
    private Item _DestinationRoom;
    private Item _Door;

    private void debug (String aMessage)
    {
        Log.d (LOGTAG, aMessage);
    }

    public Exit (Game aGame, Item aSourceRoom, Tokenizer t)
    {
        super (aGame);
        _SourceRoom = aSourceRoom;
        init (t);
    }

    public Exit (Game aGame, Item aSourceRoom, String d)
    {
        super (aGame);
        _SourceRoom = aSourceRoom;
        init (new Tokenizer (d));
    }

    private void init (Tokenizer t)
    {
        String d = t.getWord ();
        String k = t.getWord ();
        _SourceRoom.setProbableTheme ("ROOM");
        _SourceRoom.makeProbableContainer ();

        if (k.equals ("->"))
        {
            _Door = accessItem (d);
            _DestinationRoom = accessItem (t.getWord ());
            _Door.setProbableTheme (_SourceRoom.getTheme ());
            _Door.makeProbableContainer ();
        }
        else
        {
            _Door = null;
            _DestinationRoom = accessItem (d);
        }
        _DestinationRoom.setProbableTheme (_SourceRoom.getTheme ());
        _DestinationRoom.makeProbableContainer ();
    }

    public boolean isVisible ()
    {
        return _SourceRoom.hasLight () || (isOpen () && _DestinationRoom.hasLight ());
    }

    public boolean isConcealed ()
    {
        return _Door != null && _Door.isConcealed ();
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

    public void operate () throws GameEngineException
    {
        if (_Door != null)
        {
            _Door.operate ();
        }
    }

    public void operateWith (Item aItem) throws GameEngineException
    {
        if (_Door != null)
        {
            debug ("Trying key: " + aItem.getName ());
            _Door.operateWith (aItem);
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

    public boolean wouldAccomodate (Item aItem)
    {
        if (_Door != null && !_Door.wouldAccomodateNow (aItem))
        {
            return false;
        }
        if (!aItem.wouldExit (_SourceRoom) || !aItem.wouldEnter (_DestinationRoom))
        {
            return false;
        }
        return true;
    }
}
