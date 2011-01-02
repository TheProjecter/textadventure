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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import streambolics.android.PopupMenu;
import streambolics.android.SquareBoardLayout;
import streambolics.android.StandardTextView;
import streambolics.core.Logger;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class TextAdventureRoom extends Activity implements Logger, UserInterface
{
    private static final String LOGTAG = "TextAdventureRoom";
    private String _BaseFile;
    private Game _Game;
    private Item _Player;
    private Item _CurrentRoom;
    private Item _LastRoom;

    // Top level views

    private SquareBoardLayout Layout_All;
    private RoomLayout Layout_Room;
    private InventoryView Text_Inventory;
    private StandardTextView Text_Events;

    // Shortcuts to Layout_Room

    private WallButton Button_North;
    private WallButton Button_South;
    private WallButton Button_East;
    private WallButton Button_West;
    private WallButton Button_NorthEast;
    private WallButton Button_SouthWest;
    private WallButton Button_SouthEast;
    private WallButton Button_NorthWest;
    private StandardTextView Text_Room;
    private FloorDrawable Text_Room_Background;

    private InventoryView Text_Contents;

    private TranslateAnimation _SlideOut;

    // Since popup dialogs are not really "modal" and Java does not really
    // support closures, these
    // variables hold the context of buttons in popup menus.

    private Exit _CurrentExit;
    private Item _CurrentTool;
    private Item _CurrentItem;
    private PopupMenu _CurrentPopupMenu;
    private boolean _ActionAttempted;

    private void clearEvents ()
    {
        try
        {
            Text_Events.setText ("");
        }
        catch (Throwable t)
        {
            Log.e (LOGTAG, "ClearEvents", t);
        }
    }

    @Override
    public void declareLoss (String aMessage)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void declareWin (String aMessage)
    {
        // TODO Auto-generated method stub
    }

    private void describeExit (Exit aExit, WallButton aButton)
    {
        if (aExit != null && aExit.isVisible ())
        {
            aButton.setExit (_CurrentRoom, aExit);
        }
        else if (_CurrentRoom.hasLight ())
        {
            aButton.setExit (_CurrentRoom, null);
        }
        else
        {
            aButton.setInvisible ();
        }

        if (_CurrentRoom != _LastRoom)
        {
            aButton.Apparate ();
        }
    }

    public void attemptAction ()
    {
        _ActionAttempted = true;
    }

    private void describeRoom ()
    {
        if (_ActionAttempted && Text_Events.getText ().toString ().trim ().equals (""))
        {
            log ("Nothing happens");
        }
        _ActionAttempted = false;
        if (_CurrentRoom == null)
        {
            _CurrentRoom = _Game.accessItem ("?EmptyRoom?");
        }

        describeExit (_CurrentRoom.getNorthExit (), Button_North);
        describeExit (_CurrentRoom.getSouthExit (), Button_South);
        describeExit (_CurrentRoom.getEastExit (), Button_East);
        describeExit (_CurrentRoom.getWestExit (), Button_West);
        describeExit (_CurrentRoom.getNorthWestExit (), Button_NorthWest);
        describeExit (_CurrentRoom.getSouthEastExit (), Button_SouthEast);
        describeExit (_CurrentRoom.getNorthEastExit (), Button_NorthEast);
        describeExit (_CurrentRoom.getSouthWestExit (), Button_SouthWest);

        _LastRoom = _CurrentRoom;
        ThemeProvider t;

        if (_CurrentRoom.hasLight ())
        {
            Text_Room.setText (_CurrentRoom.getDescription ());
            t = _CurrentRoom;
        }
        else
        {
            Text_Room.setText ("It's dark in here...");
            t = StockThemes.getTheme (this, "PITCHBLACK");
        }
        Text_Room_Background.setFloor (t.getFloorDrawable (this));
        Text_Room.setTextColor (t.getTextColor (this));
        Text_Room.invalidate ();

        Text_Inventory.setContainer (_Player, StockThemes.getDefault (this));
        Text_Contents.setContainer (_CurrentRoom, t);
    }

    private void exitSelected (Exit aExit, int slidex, int slidey)
    {
        clearEvents ();
        if (_CurrentTool != null)
        {
            itemSelected (aExit.getDoor ());
        }
        else
        {
            moveTo (aExit, slidex, slidey);
        }
    }

    private Item getObviousKey (Exit e)
    {
        Item door = e.getDoor ();
        if (door == null)
        {
            return null;
        }
        Item key = door.getKey ();
        if (key == null)
        {
            return null;
        }
        if (key.getContainer () != _Player)
        {
            return null;
        }
        return key;
    }

    @Override
    public void itemClicked (Item aItem)
    {
        itemSelected (aItem);
    }

    private void itemSelected (Item aItem)
    {
        clearEvents ();
        if (aItem != null)
        {
            debug ("Item Selected: " + aItem.getName ());
        }

        flushCurrentItem ();

        if (_CurrentTool != null)
        {
            attemptAction ();
            if (aItem != null)
            {
                aItem.operateWith (_CurrentTool);
            }
            _CurrentTool = null;
            describeRoom ();
        }
        else
        {
            _CurrentItem = aItem;
            _CurrentPopupMenu = new PopupMenu (this);
            _CurrentPopupMenu.setBackgroundResource (R.drawable.roundedcorners);
            _CurrentPopupMenu.addTitle ("What do you want to do");
            _CurrentPopupMenu.addTitle ("with " + aItem.getDescription () + "?");

            // TODO : Set the title of the popup menu

            _CurrentPopupMenu.addEntry (StockDrawables.Wall (this), aItem.getOperateText (), new View.OnClickListener ()
            {
                @Override
                public void onClick (View aView)
                {
                    if (_CurrentItem != null)
                    {
                        attemptAction ();
                        _CurrentItem.operate ();
                    }
                    flushCurrentItem ();
                    describeRoom ();
                }
            });
            _CurrentPopupMenu.addEntry (StockDrawables.OpenDoor (this), "Use it on something", new View.OnClickListener ()
            {
                @Override
                public void onClick (View aView)
                {
                    attemptAction ();

                    if (_CurrentItem != null || _CurrentItem.tryTransferTo (_Player))
                    {
                        _CurrentTool = _CurrentItem;
                        log ("Now select the item to operate on");
                    }
                    flushCurrentItem ();
                    describeRoom ();
                }
            });
            if (aItem.getContainer () == _Player)
            {
                _CurrentPopupMenu.addEntry (StockDrawables.ClosedDoor (this), "Drop it here", new View.OnClickListener ()
                {
                    @Override
                    public void onClick (View aView)
                    {
                        if (_CurrentItem != null)
                        {
                            _CurrentItem.tryTransferTo (_CurrentRoom);
                        }
                        flushCurrentItem ();
                        describeRoom ();
                    }
                });
            }
            else
            {
                _CurrentPopupMenu.addEntry (StockDrawables.ClosedDoor (this), "Carry it", new View.OnClickListener ()
                {
                    @Override
                    public void onClick (View aView)
                    {
                        if (_CurrentItem != null)
                        {
                            _CurrentItem.tryTransferTo (_Player);
                        }
                        flushCurrentItem ();
                        describeRoom ();
                    }
                });
            }
            _CurrentPopupMenu.addEntry (StockDrawables.OpenDoor (this), "Ooops! Nothing", new View.OnClickListener ()
            {
                @Override
                public void onClick (View aView)
                {
                    _CurrentTool = null;
                    flushCurrentItem ();
                    describeRoom ();
                }
            });

            // TODO : if item is a container, add "look inside"
            // TODO : if there is a container somewhere, add
            // "drop into another item"

            _CurrentPopupMenu.show (Text_Room);
        }
    }

    private void flushCurrentItem ()
    {
        if (_CurrentPopupMenu != null)
        {
            _CurrentPopupMenu.dismiss ();
        }
        _CurrentPopupMenu = null;
        _CurrentItem = null;
    }

    private void loadFile (String aFileName)
    {
        try
        {
            loadStream (openFileInput (aFileName));
        }
        catch (FileNotFoundException e)
        {
            // do nothing...
        }
    }

    private void loadGame ()
    {
        _Game = new Game (this);
        if (_BaseFile.equals ("BuiltIn"))
        {
            loadResource (R.raw.builtin);
        }
        else
        {
            loadFile (_BaseFile + ".game");
        }
        _Player = _Game.accessItem ("Player");
        if (_Player.getContainer () == null)
        {
            Item i = _Game.accessItem ("Hall");
            i.setDescription ("a large hall");
            _Player.setContainer (i);
            i.setNorthExit ("Door -> TreasureRoom");

            i = _Game.accessItem ("Door");
            i.setDescription ("a wooden door");

        }
    }

    private void loadResource (int rid)
    {
        loadStream (getResources ().openRawResource (rid));
    }

    private void loadSave (String aName)
    {
        loadFile (_BaseFile + "." + aName + ".save");
        _CurrentRoom = _Player.getContainer ();
    }

    private void loadStream (InputStream s)
    {
        try
        {
            BufferedReader r = new BufferedReader (new InputStreamReader (s));
            try
            {
                _Game.parse (r);
            }
            finally
            {
                r.close ();
            }
            _Game.verify ();
        }
        catch (Exception e)
        {
            displayException ("Load error", e);
        }
    }

    public void displayError (String aAction, String aError)
    {
        // TODO : Move this method to StandardActivity

        new AlertDialog.Builder (this).setTitle (aAction).setMessage (aError).setNeutralButton ("OK", new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
                // TODO : exit the application ???
            }
        }).show ();
    }

    public void displayException (String aAction, Exception e)
    {
        // TODO : Move this method to StandardActivity

        displayError (aAction, e.getMessage ());
    }

    @Override
    public void log (String aMessage)
    {
        String s = Text_Events.getText ().toString ().trim ();
        if (s.equals (""))
        {
            s = aMessage;
            Toast.makeText (this, aMessage, Toast.LENGTH_SHORT).show ();
        }
        else
        {
            s = s + '\n' + aMessage;
        }
        Text_Events.setText (s);
    }

    private void moveTo (Exit aExit, int slidex, int slidey)
    {
        flushCurrentItem ();
        _SlideOut = new TranslateAnimation (Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, slidex, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, slidey);
        _SlideOut.setDuration (600);
        _SlideOut.setAnimationListener (new AnimationListener ()
        {

            @Override
            public void onAnimationEnd (Animation animation)
            {
                moveTo (_CurrentExit.getDestination ());
            }

            @Override
            public void onAnimationRepeat (Animation animation)
            {
            }

            @Override
            public void onAnimationStart (Animation animation)
            {
            }
        });

        if (aExit == null)
        {
            log ("No exit in that direction");
        }
        else if (aExit.isOpen ())
        {
            if (aExit.wouldAccomodate (_Player))
            {
                _CurrentExit = aExit;
                slideOutOfRoom ();
            }
            else
            {
                log ("You are too large for the door");
            }
        }
        else if (aExit.isLocked ())
        {
            Item k = getObviousKey (aExit);

            if (k == null)
            {
                log ("The exit is locked");
            }
            else
            {
                proposeOpenExit (aExit, k, "The door is locked. Should we try to unlock it with " + k.getDescription () + "?");
            }
        }
        else
        {
            proposeOpenExit (aExit, null, "The door is closed. Should we try to open it?");
        }

    }

    private void moveTo (Item aRoom)
    {
        _Player.setContainer (aRoom);
        _CurrentRoom = aRoom;
        describeRoom ();
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);

        Layout_Room = new RoomLayout (this);
        Text_Inventory = new InventoryView (this);
        Text_Events = new StandardTextView (this);
        Layout_All = new SquareBoardLayout (this, Layout_Room, Text_Inventory, Text_Events);
        Layout_All.setGridSize (Layout_Room.getGridSize ());
        Text_Events.setText ("Here are events");

        setContentView (Layout_All);
        Button_North = Layout_Room.getNorthButton ();
        Button_South = Layout_Room.getSouthButton ();
        Button_East = Layout_Room.getEastButton ();
        Button_West = Layout_Room.getWestButton ();

        Button_NorthWest = Layout_Room.getNorthWestButton ();
        Button_SouthWest = Layout_Room.getSouthWestButton ();
        Button_NorthEast = Layout_Room.getNorthEastButton ();
        Button_SouthEast = Layout_Room.getSouthEastButton ();

        Text_Room = Layout_Room.getDescriptionView ();
        Text_Room_Background = new FloorDrawable (null);
        Text_Room.setBackgroundDrawable (Text_Room_Background);
        Text_Contents = Layout_Room.getContentsView ();

        Button_North.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getNorthExit (), 0, 1);
            }
        });

        Button_South.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getSouthExit (), 0, -1);
            }
        });

        Button_East.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getEastExit (), -1, 0);
            }
        });

        Button_West.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getWestExit (), 1, 0);
            }
        });

        Button_SouthEast.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getSouthEastExit (), -1, -1);
            }
        });
        Button_NorthWest.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getNorthWestExit (), 1, 1);
            }
        });
        Button_SouthWest.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getSouthWestExit (), 1, -1);
            }
        });
        Button_NorthEast.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                exitSelected (_CurrentRoom.getNorthEastExit (), -1, 1);
            }
        });

        // TODO : retrieve the game name from the intent.
        _BaseFile = "BuiltIn";
        loadGame ();

        loadSave ("Autosave");

        describeRoom ();
    }

    @Override
    public void onPause ()
    {
        super.onPause ();
        saveSave ("Autosave");
    }

    private void proposeOpenExit (Exit aExit, Item aKey, String aProposition)
    {
        _CurrentExit = aExit;
        _CurrentTool = aKey;
        new AlertDialog.Builder (this).setTitle ("Door closed").setMessage (aProposition).setPositiveButton ("Yes", new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
                unlockExitAndGo ();
            }
        }).setNegativeButton ("No", new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
            }
        }).show ();

    }

    private void saveFile (String aFileName)
    {
        try
        {
            OutputStream s = openFileOutput (aFileName, 0);
            saveStream (s);
        }
        catch (FileNotFoundException e)
        {
        }

    }

    private void saveSave (String aName)
    {
        saveFile (_BaseFile + "." + aName + ".save");
    }

    private void saveStream (OutputStream s)
    {
        try
        {
            BufferedWriter w = new BufferedWriter (new OutputStreamWriter (s));
            try
            {
                _Game.save (w);
            }
            finally
            {
                w.close ();
            }
        }
        catch (IOException e)
        {
            log (e.getMessage ());
        }
    }

    private void slideOutOfRoom ()
    {
        if (_SlideOut == null)
        {
            moveTo (_CurrentExit.getDestination ());
        }
        else
        {
            Layout_Room.startAnimation (_SlideOut);
            _SlideOut = null;
        }
    }

    private void unlockExitAndGo ()
    {
        if (_CurrentExit.isLocked () && _CurrentTool != null)
        {
            _CurrentExit.operateWith (_CurrentTool);
        }
        _CurrentTool = null;
        if (_CurrentExit.isLocked ())
        {
            log ("The door will not unlock");
            return;
        }

        if (!_CurrentExit.isOpen ())
        {
            _CurrentExit.operate ();
        }

        if (!_CurrentExit.isOpen ())
        {
            log ("The door will not open");
            return;
        }

        slideOutOfRoom ();
    }

    private void debug (String aMessage)
    {
        Log.d (LOGTAG, aMessage);
    }

}
