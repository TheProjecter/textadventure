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

import java.io.BufferedWriter;
import java.io.IOException;

import streambolics.core.Logger;
import streambolics.core.Tokenizer;
import android.content.Context;
import android.graphics.drawable.Drawable;

/***
 * 
 * @author Stéphan Leclercq
 * 
 *         A general item.
 * 
 */

public class Item
{
    private Game _Game;
    private String _Name;
    private String _Description;
    private Item _Container;

    private Exit _North;
    private Exit _South;
    private Exit _East;
    private Exit _West;
    private Exit _NorthWest;
    private Exit _SouthWest;
    private Exit _NorthEast;
    private Exit _SouthEast;

    private boolean _Open;
    private boolean _Locked;
    private boolean _Lit;

    private Item _LightSource;
    private Item _Key;
    private int _Size;
    private int _InnerSize;
    private int _OpeningSize;

    public Item (Game aGame, String aName)
    {
        _Name = aName;
        _Game = aGame;
        _Description = "The game author did not provide a description for this";
        _Size = 1;
        _InnerSize = 9999;
        _OpeningSize = 9999;
    }

    public String getName ()
    {
        return _Name;
    }

    public String getDescription ()
    {
        return _Description;
    }

    public boolean isOpen ()
    {
        return _Open;
    }

    public boolean isLocked ()
    {
        return _Locked;
    }

    private void saveBool (BufferedWriter w, String n, boolean v) throws IOException
    {
        w.write ("  " + n + " " + (v ? "1" : "0") + "\n");
    }

    public void Save (BufferedWriter w) throws IOException
    {
        w.write ("%" + _Name + "\n");
        if (_Container != null)
        {
            w.write ("  LOCATION " + _Container.getName () + "\n");
        }
        saveBool (w, "OPEN", _Open);
        saveBool (w, "LOCKED", _Locked);
        saveBool (w, "LIT", _Lit);
    }

    public boolean hasLight ()
    {
        return (_LightSource == null) || (_LightSource.isLit ());
    }

    public boolean isLit ()
    {
        return _Lit;
    }

    public void reportOpen (Logger aLogger)
    {
        if (_Open)
        {
            aLogger.Log (_Name + " is open");
        }
        else
        {
            aLogger.Log (_Name + " is closed.");
        }
    }

    public void reportLocked (Logger aLogger)
    {
        if (_Locked)
        {
            aLogger.Log (_Name + " is locked");
        }
        else
        {
            aLogger.Log (_Name + " is unlocked");
        }
    }

    public void operate (Logger aLogger)
    {
        _Open = !_Open;
        reportOpen (aLogger);
    }

    public void operateWith (Item aItem, Logger aLogger)
    {
        if (aItem == _Key)
        {
            _Locked = !_Locked;
            reportLocked (aLogger);
        }
    }

    public void visitContents (ItemVisitor v)
    {
        _Game.visitChildren (this, v);
    }

    public int getContainedSize ()
    {
        return _Game.getContainedSize (this);
    }

    public int getSize ()
    {
        return _Size;
    }

    public int getSizeLeft ()
    {
        return _InnerSize - getContainedSize ();
    }

    public int getLargestFitSize ()
    {
        return Math.min (_OpeningSize, getSizeLeft ());
    }

    public boolean wouldFit (Item i)
    {
        return i.getSize () <= getLargestFitSize ();
    }

    public Exit getNorthExit ()
    {
        return _North;
    }

    public void setNorthExit (String d)
    {
        _North = new Exit (_Game, this, d);
    }

    public Exit getSouthExit ()
    {
        return _South;
    }

    public Exit getWestExit ()
    {
        return _West;
    }

    public Exit getEastExit ()
    {
        return _East;
    }

    public Item getContainer ()
    {
        return _Container;
    }

    public void setContainer (Item aContainer)
    {
        if (aContainer != _Container)
        {
            _Container = aContainer;
            // TODO : Report the fact that the item has moved.
        }
    }

    public void setContainer (String aLocation)
    {
        setContainer (_Game.accessItem (aLocation));
    }

    public void setNamedProperty (String aPropName, String aPropVal)
    {
        if (aPropName.equals ("LOCATION"))
        {
            setContainer (aPropVal);
        }
        else if (aPropName.equals ("LIGHT"))
        {
            _LightSource = _Game.accessItem (aPropVal);
        }
        else if (aPropName.equals ("OPEN"))
        {
            _Open = aPropVal.equals ("1");
        }
        else if (aPropName.equals ("LOCKED"))
        {
            _Locked = aPropVal.equals ("1");
        }
        else if (aPropName.equals ("LIT"))
        {
            _Lit = aPropVal.equals ("1");
        }
        else if (aPropName.equals ("SIZE"))
        {
        }
        else if (aPropName.equals ("INNERSIZE"))
        {
        }
        else if (aPropName.equals ("OPENINGSIZE"))
        {
        }
        else if (aPropName.equals ("NORTH"))
        {
            _North = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("SOUTH"))
        {
            _South = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("EAST"))
        {
            _East = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("WEST"))
        {
            _West = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("SOUTHWEST"))
        {
            _SouthWest = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("NORTHWEST"))
        {
            _NorthWest = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("SOUTHEAST"))
        {
            _SouthEast = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("NORTHEAST"))
        {
            _NorthEast = new Exit (_Game, this, aPropVal);
        }
        else if (aPropName.equals ("BEUH"))
        {
        }
    }

    public void parse (Tokenizer t)
    {
        String k = t.getWord ();
        setNamedProperty (k, t.getRemainder ());
    }

    public void setDescription (String aDescription)
    {
        _Description = aDescription;
    }

    public Exit getNorthWestExit ()
    {
        return _NorthWest;
    }

    public Exit getSouthEastExit ()
    {
        return _SouthEast;
    }

    public Exit getNorthEastExit ()
    {
        return _NorthEast;
    }

    public Exit getSouthWestExit ()
    {
        return _SouthWest;
    }

    public Item getKey ()
    {
        return _Key;
    }

    public int getIconResourceId ()
    {
        // TODO retrieve an icon based on IconName
        return R.drawable.icon;
    }

    public String getNamedProperty (String aPropName)
    {
        if (aPropName.equals ("LOCATION"))
        {
            return _Container.getName ();
        }
        else if (aPropName.equals ("OPEN"))
        {
            return _Open ? "1" : "0";
        }
        else if (aPropName.equals ("LOCKED"))
        {
            return _Locked ? "1" : "0";
        }
        else if (aPropName.equals ("LIT"))
        {
            return _Lit ? "1" : "0";
        }
        else
        {
            return "";
        }
    }

    public Drawable getFloorDrawable (Context aContext)
    {
        return StockDrawables.Floor (aContext);
    }

    public Drawable getWallDrawable (Context aContext)
    {
        return StockDrawables.Wall (aContext);
    }

    public Drawable getOpenDoorDrawable (Context aContext)
    {
        return StockDrawables.OpenDoor (aContext);
    }

    public Drawable getClosedDoorDrawable (Context aContext)
    {
        return StockDrawables.ClosedDoor (aContext);
    }

}
