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

import streambolics.core.Logger;
import streambolics.core.Tokenizer;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class Exit extends GameObject
{
    private Item _SourceRoom;
    private Item _DestinationRoom;
    private Item _Door;

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

        if (k.equals ("->"))
        {
            _Door = accessItem (d);
            _DestinationRoom = accessItem (t.getWord ());
            _Door.setProbableTheme (_SourceRoom.getTheme ());
        }
        else
        {
            _Door = null;
            _DestinationRoom = accessItem (d);
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

    public void operate ()
    {
        if (_Door == null)
        {
            log ("Nothing happens");
        }
        else
        {
            _Door.operate ();
        }
    }

    public void operateWith (Item aItem, Logger aLogger)
    {
        if (_Door == null)
        {
            aLogger.log ("Nothing happens");
        }
        else
        {
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
}
