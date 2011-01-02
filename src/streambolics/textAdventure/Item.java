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
import android.util.Log;

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
    private boolean _Fixed;
    private boolean _IsPlayer;
    private boolean _Concealed;
    private static final String NO_DESCR = "The game author did not provide a description for this";

    public static final String PLAYER_NAME = "Player";
    private static final String LOGTAG = "Item";

    public Item (Game aGame, String aName)
    {
        super (aGame);
        _Name = aName;
        _Description = NO_DESCR;
        _Size = 1;
        _InnerSize = -1;
        _OpeningSize = -1;
        if (_Name.equals (PLAYER_NAME))
        {
            _IsPlayer = true;
        }

    }

    public void makeProbableContainer ()
    {
        if (_InnerSize < 0 && _OpeningSize < 0)
        {
            _InnerSize = 9999;
            _OpeningSize = 9999;
        }
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

    public int getLargestFitSizeEver ()
    {
        return Math.min (_OpeningSize, _InnerSize);
    }

    public int getLargestFitSizeNow ()
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

    public int getOpeningSize ()
    {
        return _OpeningSize;
    }

    /***
     * The text to show in a menu when the object is used. The typical value is
     * "use" but can be "open"/"close" for doors and containers, or "flip" for
     * switches.
     */

    public String getOperateText ()
    {
        if (isOpenable ())
        {
            if (isOpen ())
            {
                return "Close it";
            }
            else
            {
                return "Open it";
            }
        }
        else
        {
            // TODO : make a property for this
            return "Operate it";
        }
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

    public boolean hasDescription ()
    {
        return !(_Description == null || _Description.equals ("") || _Description.equals (NO_DESCR));
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

    private boolean isOpenable ()
    {
        return _OpeningSize > 0;
    }

    private boolean isPlayer ()
    {
        return _IsPlayer;
    }

    public void operate ()
    {
        // TODO : call rules here first
        if (isOpenable ())
        {
            if (isLocked ())
            {
                reportLocked ();
            }
            else
            {
                _Open = !_Open;
                reportOpen ();
            }
        }
    }

    private void debug (String aMessage)
    {
        Log.d (LOGTAG, aMessage);
    }

    public void operateWith (Item aItem)
    {
        // TODO : call rules here first
        debug ("Trying tool " + aItem.getName () + " on " + _Name);
        if (aItem == _Key)
        {
            _Locked = !_Locked;
            reportLocked ();
        }
    }

    public void parse (Tokenizer t) throws GameEngineException
    {
        String k = t.getWord ();
        setNamedProperty (k, t.getRemainder ());
    }

    public void reportLocked ()
    {
        if (_Locked)
        {
            log (_Description + " is locked");
        }
        else
        {
            log (_Description + " is unlocked");
        }
    }

    public void reportOpen ()
    {
        if (_Open)
        {
            log (_Description + " is open");
        }
        else
        {
            log (_Description + " is closed.");
        }
    }

    public void save (BufferedWriter w) throws IOException
    {
        w.write ("%" + _Name + "\n");
        if (_Container != null)
        {
            w.write ("  LOCATION " + _Container.getName () + "\n");
        }
        saveBool (w, "OPEN", _Open);
        saveBool (w, "LOCKED", _Locked);
        saveBool (w, "LIT", _Lit);
        saveBool (w, "FIXED", _Fixed);
        saveBool (w, "CONCEALED", _Concealed);
    }

    private void saveBool (BufferedWriter w, String n, boolean v) throws IOException
    {
        w.write ("  " + n + " " + Boolean.toString (v) + "\n");
    }

    @Override
    public String toString ()
    {
        debug ("Who is silly enough to call toString? Item=" + _Name);
        return _Name;
    }

    public boolean isConcealed ()
    {
        return _Concealed;
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

    public void setNamedProperty (String aPropName, String aPropVal) throws GameEngineException
    {
        try
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
                _Open = Boolean.parseBoolean (aPropVal);
            }
            else if (aPropName.equals ("LOCKED"))
            {
                _Locked = Boolean.parseBoolean (aPropVal);
            }
            else if (aPropName.equals ("LIT"))
            {
                _Lit = Boolean.parseBoolean (aPropVal);
            }
            else if (aPropName.equals ("FIXED"))
            {
                _Fixed = Boolean.parseBoolean (aPropVal);
            }
            else if (aPropName.equals ("CONCEALED"))
            {
                _Concealed = Boolean.parseBoolean (aPropVal);
            }
            else if (aPropName.equals ("SIZE"))
            {
                _Size = Integer.parseInt (aPropVal);
            }
            else if (aPropName.equals ("INNERSIZE"))
            {
                _InnerSize = Integer.parseInt (aPropVal);
            }
            else if (aPropName.equals ("OPENINGSIZE"))
            {
                _OpeningSize = Integer.parseInt (aPropVal);
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
            else
            {
                throw new Exception ("Invalid property name " + aPropName);
            }
        }
        catch (Exception e)
        {
            throw new GameEngineException ("Unable to set property " + aPropName + " to " + aPropVal, e);
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

    public boolean tryTransferTo (Item aContainer)
    {
        if (_Container == aContainer)
        {
            return true;
        }
        else if (_Fixed)
        {
            log ("This item is fixed and cannot be moved");
            return false;
        }
        else if (!wouldExit (_Container))
        {
            if (_Container.isPlayer ())
            {
                log ("You cannot rid yourself of this item");
            }
            else
            {
                log ("You cannot remove this item from " + _Container.getDescription ());
            }
            return false;
        }
        else if (!wouldEnter (aContainer))
        {
            if (aContainer.isPlayer ())
            {
                log ("This item is too big to be carried.");
                if (aContainer.wouldAccomodateEver (this))
                {
                    log ("You may try dropping some other stuff you are carrying");
                }
            }
            else
            {
                log ("This item is too big to fit in " + aContainer.getDescription ());
            }
            return false;
        }
        else
        {
            setContainer (aContainer);
            return true;
        }
    }

    public void visitContents (ItemVisitor v)
    {
        getGame ().visitChildren (this, v);
    }

    public boolean wouldAccomodateEver (Item aItem)
    {
        return aItem.getSize () <= getLargestFitSizeEver ();
    }

    public boolean wouldAccomodateNow (Item aItem)
    {
        return aItem.getSize () <= getLargestFitSizeNow ();
    }

    public boolean wouldEnter (Item aContainer)
    {
        return (aContainer != null && aContainer.wouldAccomodateNow (this));
    }

    public boolean wouldExit (Item aContainer)
    {
        return (aContainer == null || aContainer.getOpeningSize () >= getSize ());
    }

    @Override
    public int getTextColor (Context aContext)
    {
        return getThemeProvider (aContext).getTextColor (aContext);
    }
}
