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

import streambolics.core.Tokenizer;
import android.content.Context;
import android.graphics.drawable.Drawable;

/***
 * A general item. Items are used throughout the application to represent all
 * objects with which the player will interact, including the player itself,
 * rooms, doors, and operable objects.
 * 
 * @author Stéphan Leclercq
 * 
 */

public class Item extends GameObject implements ThemeProvider
{
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

    private ThemeProvider _ThemeProvider;
    private String _Theme;

    public Item (Game aGame, String aName)
    {
        super (aGame);
        _Name = aName;
        _Description = "The game author did not provide a description for this";
        _Size = 1;
        _InnerSize = 9999;
        _OpeningSize = 9999;
    }

    /***
     * Called when the item was clicked in a user interface.
     */

    public void clicked ()
    {
        getGame ().itemClicked (this);
    }

    @Override
    public Drawable getClosedDoorDrawable (Context aContext)
    {
        return getThemeProvider (aContext).getClosedDoorDrawable (aContext);
    }

    public int getContainedSize ()
    {
        return getGame ().getContainedSize (this);
    }

    public Item getContainer ()
    {
        return _Container;
    }

    public String getDescription ()
    {
        return _Description;
    }

    public Exit getEastExit ()
    {
        return _East;
    }

    @Override
    public Drawable getFloorDrawable (Context aContext)
    {
        return getThemeProvider (aContext).getFloorDrawable (aContext);
    }

    @Override
    public Drawable getInventoryDrawable (Context aContext)
    {
        return getThemeProvider (aContext).getInventoryDrawable (aContext);
    }

    public Item getKey ()
    {
        return _Key;
    }

    public int getLargestFitSize ()
    {
        return Math.min (_OpeningSize, getSizeLeft ());
    }

    public String getName ()
    {
        return _Name;
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

    public Exit getNorthEastExit ()
    {
        return _NorthEast;
    }

    public Exit getNorthExit ()
    {
        return _North;
    }

    public Exit getNorthWestExit ()
    {
        return _NorthWest;
    }

    @Override
    public Drawable getOpenDoorDrawable (Context aContext)
    {
        return getThemeProvider (aContext).getOpenDoorDrawable (aContext);
    }

    /***
     * The text to show in a menu when the object is used. The typical value is
     * "use" but can be "open"/"close" for doors and containers, or "flip" for
     * switches.
     */

    public String getOperateText ()
    {
        return "Use";
    }

    public int getSize ()
    {
        return _Size;
    }

    public int getSizeLeft ()
    {
        return _InnerSize - getContainedSize ();
    }

    public Exit getSouthEastExit ()
    {
        return _SouthEast;
    }

    public Exit getSouthExit ()
    {
        return _South;
    }

    public Exit getSouthWestExit ()
    {
        return _SouthWest;
    }

    /***
     * Get the name of the theme.
     * 
     * @return The name of the theme, or a null string if it was never set.
     */
    public String getTheme ()
    {
        return _Theme;
    }

    private ThemeProvider getThemeProvider (Context aContext)
    {
        if (_ThemeProvider == null)
        {
            if (_Theme == null)
            {
                _Theme = "DEFAULT";
            }
            _ThemeProvider = StockThemes.getTheme (aContext, _Theme);
        }
        return _ThemeProvider;
    }

    @Override
    public Drawable getWallDrawable (Context aContext)
    {
        return getThemeProvider (aContext).getWallDrawable (aContext);
    }

    public Exit getWestExit ()
    {
        return _West;
    }

    public boolean hasLight ()
    {
        return (_LightSource == null) || (_LightSource.isLit ());
    }

    public boolean isLit ()
    {
        return _Lit;
    }

    public boolean isLocked ()
    {
        return _Locked;
    }

    public boolean isOpen ()
    {
        return _Open;
    }

    public void operate ()
    {
        // TODO : call rules here first
        // TODO : only open/close if it has a meaning
        _Open = !_Open;
        reportOpen ();
    }

    public void operateWith (Item aItem)
    {
        // TODO : call rules here first
        if (aItem == _Key)
        {
            _Locked = !_Locked;
            reportLocked ();
        }
    }

    public void parse (Tokenizer t)
    {
        String k = t.getWord ();
        setNamedProperty (k, t.getRemainder ());
    }

    public void reportLocked ()
    {
        if (_Locked)
        {
            log (_Name + " is locked");
        }
        else
        {
            log (_Name + " is unlocked");
        }
    }

    public void reportOpen ()
    {
        if (_Open)
        {
            log (_Name + " is open");
        }
        else
        {
            log (_Name + " is closed.");
        }
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

    private void saveBool (BufferedWriter w, String n, boolean v) throws IOException
    {
        w.write ("  " + n + " " + (v ? "1" : "0") + "\n");
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
        setContainer (accessItem (aLocation));
    }

    public void setDescription (String aDescription)
    {
        _Description = aDescription;
    }

    public void setNamedProperty (String aPropName, String aPropVal)
    {
        if (aPropName.equals ("LOCATION"))
        {
            setContainer (aPropVal);
        }
        else if (aPropName.equals ("LIGHT"))
        {
            _LightSource = accessItem (aPropVal);
            _LightSource.setProbableTheme ("LAMP");
        }
        else if (aPropName.equals ("KEY"))
        {
            _Key = accessItem (aPropVal);
            _Key.setProbableTheme ("KEY");
        }
        else if (aPropName.equals ("ICON"))
        {
            setTheme (aPropVal);
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
            _North = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("SOUTH"))
        {
            _South = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("EAST"))
        {
            _East = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("WEST"))
        {
            _West = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("SOUTHWEST"))
        {
            _SouthWest = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("NORTHWEST"))
        {
            _NorthWest = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("SOUTHEAST"))
        {
            _SouthEast = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("NORTHEAST"))
        {
            _NorthEast = new Exit (getGame (), this, aPropVal);
        }
        else if (aPropName.equals ("BEUH"))
        {
        }
    }

    public void setNorthExit (String d)
    {
        _North = new Exit (getGame (), this, d);
    }

    /***
     * Sets the probable value for the theme. The probable value will not
     * overwrite any other value, but may be overwritten by specific themes. The
     * goal of the probable theme is to provide a default theme for items base
     * on their usage. For example, if an item is referenced as a door to exit a
     * room, the probable theme of the door is the same as the theme of the
     * room.
     * 
     * @param aTheme
     *            The probable theme.
     */
    public void setProbableTheme (String aTheme)
    {
        if (_Theme == null)
        {
            setTheme (aTheme);
        }
    }

    private void setTheme (String aTheme)
    {
        _Theme = aTheme;
        _ThemeProvider = null;
    }

    public void visitContents (ItemVisitor v)
    {
        getGame ().visitChildren (this, v);
    }

    public boolean wouldFit (Item i)
    {
        return i.getSize () <= getLargestFitSize ();
    }
}
