package streambolics.textAdventure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import streambolics.android.SquareBoardLayout;
import streambolics.android.StandardTextView;
import streambolics.core.Logger;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class TextAdventureRoom extends Activity implements Logger
{
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
    private InventoryView Text_Contents;

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
        }
        catch (IOException e)
        {
            Log (e.getMessage ());
        }
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

    private void loadResource (int rid)
    {
        loadStream (getResources ().openRawResource (rid));
    }

    private void loadGame ()
    {
        _Game = new Game ();
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

    private void loadSave (String aName)
    {
        loadFile (_BaseFile + "." + aName + ".save");
        _CurrentRoom = _Player.getContainer ();
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
            Log (e.getMessage ());
        }
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

    private void describeRoom ()
    {
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

        if (_CurrentRoom.hasLight ())
        {
            Text_Room.setText (_CurrentRoom.getDescription ());
        }
        else
        {
            Text_Room.setText ("It's dark in here...");
        }
        Text_Inventory.setContainer (_Player);
        Text_Contents.setContainer (_CurrentRoom);
    }

    private void moveTo (Item aRoom)
    {
        _Player.setContainer (aRoom);
        _CurrentRoom = aRoom;
        describeRoom ();
    }

    private Exit _AttemptedExit;
    private Item _AttemptedKey;

    private void unlockExitAndGo ()
    {
        if (_AttemptedExit.isLocked () && _AttemptedKey != null)
        {
            _AttemptedExit.operateWith (_AttemptedKey, this);
        }

        if (_AttemptedExit.isLocked ())
        {
            Log ("The door will not unlock");
            return;
        }

        if (!_AttemptedExit.isOpen ())
        {
            _AttemptedExit.operate (this);
        }

        if (!_AttemptedExit.isOpen ())
        {
            Log ("The door will not open");
            return;
        }

        SlideOutOfRoom ();
    }

    private TranslateAnimation _SlideOut;

    private void SlideOutOfRoom ()
    {
        if (_SlideOut == null)
        {
            moveTo (_AttemptedExit.getDestination ());
        }
        else
        {
            Layout_Room.startAnimation (_SlideOut);
            _SlideOut = null;
        }
    }

    private void proposeOpenExit (Exit aExit, Item aKey, String aProposition)
    {
        _AttemptedExit = aExit;
        _AttemptedKey = null;
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

    private void moveTo (Exit aExit, int slidex, int slidey)
    {
        _SlideOut = new TranslateAnimation (Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, slidex, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, slidey);
        _SlideOut.setDuration (600);
        _SlideOut.setAnimationListener (new AnimationListener ()
        {

            @Override
            public void onAnimationEnd (Animation animation)
            {
                moveTo (_AttemptedExit.getDestination ());
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

        ClearEvents ();
        if (aExit == null)
        {
            Log ("No exit in that direction");
        }
        else if (aExit.isOpen ())
        {
            // TODO: Check here that player fits the door !!!
            _AttemptedExit = aExit;
            SlideOutOfRoom ();
        }
        else if (aExit.isLocked ())
        {
            Item k = getObviousKey (aExit);

            if (k == null)
            {
                Log ("The exit is locked");
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

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);

        Layout_Room = new RoomLayout (this);
        Text_Inventory = new InventoryView (this);
        Text_Events = new StandardTextView (this);
        Layout_All = new SquareBoardLayout (this, Layout_Room, Text_Inventory, Text_Events);

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
        Text_Contents = Layout_Room.getContentsView ();

        Button_North.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getNorthExit (), 0, 1);
            }
        });

        Button_South.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getSouthExit (), 0, -1);
            }
        });

        Button_East.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getEastExit (), -1, 0);
            }
        });

        Button_West.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getWestExit (), 1, 0);
            }
        });

        Button_SouthEast.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getSouthEastExit (), -1, -1);
            }
        });
        Button_NorthWest.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getNorthWestExit (), 1, 1);
            }
        });
        Button_SouthWest.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getSouthWestExit (), 1, -1);
            }
        });
        Button_NorthEast.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                moveTo (_CurrentRoom.getNorthEastExit (), -1, 1);
            }
        });

        // TODO : retrieve the game name from the intent.
        _BaseFile = "BuiltIn";
        loadGame ();

        // TODO : retrieve the save name from the intent.
        loadSave ("Autosave");

        describeRoom ();
    }

    @Override
    public void onPause ()
    {
        super.onPause ();
        saveSave ("Autosave");
    }

    @Override
    public void Log (String aMessage)
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

    private void Beuh (String s)
    {
        Toast.makeText (this, s, Toast.LENGTH_SHORT).show ();
    }

    private void ClearEvents ()
    {
        if (Text_Events == null)
        {
            Beuh ("Null");
        }
        else
            try
            {
                Text_Events.setText ("");
            }
            catch (Throwable t)
            {
                Beuh (t.getClass ().getName ());
            }
    }

    private void Debug (String aMessage)
    {
        new AlertDialog.Builder (this).setTitle ("Debug").setMessage (aMessage).setNeutralButton ("Continue", new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {
            }
        }).show ();
    }

}
