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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import streambolics.core.Logger;
import streambolics.core.Tokenizer;

public class Game implements Logger
{
    private HashMap<String, Item> _Items = new HashMap<String, Item> ();
    private List<Rule> _Rules = new ArrayList<Rule> ();
    private String _Description = "";
    private Item _Player;
    private UserInterface _UserInterface;

    public Game (UserInterface aUserInterface)
    {
        _UserInterface = aUserInterface;
    }

    private void addDescription (String l)
    {
        _Description = (_Description + '\n' + l).trim ();
    }

    public Item accessItem (String aName)
    {
        if (aName.startsWith ("*"))
        {
            return accessItem (aName.substring (1)).getContainer ();
        }

        if (aName.startsWith ("?"))
        {
            Tokenizer t = new Tokenizer (aName.substring (1));
            ItemRandomizer r = new ItemRandomizer ();
            while (t.hasMore ())
            {
                String n = t.getWord ();
                r.add (accessItem (n));
            }
            return r.any ();
        }

        if (_Items.containsKey (aName))
        {
            return _Items.get (aName);
        }
        Item i = new Item (this, aName);
        _Items.put (aName, i);
        return i;
    }

    public Item getPlayer ()
    {
        if (_Player == null)
        {
            _Player = accessItem (Item.PLAYER_NAME);
        }
        return _Player;
    }

    public void visitChildren (Item aContainer, ItemVisitor aVisitor)
    {
        for (Item i : _Items.values ())
        {
            if (i.getContainer () == aContainer)
            {
                aVisitor.visit (i);
            }
        }
    }

    public void visitAll (ItemVisitor aVisitor)
    {
        for (Item i : _Items.values ())
        {
            aVisitor.visit (i);
        }
    }

    public void visitPlayerInventory (ItemVisitor aVisitor)
    {
        visitChildren (getPlayer (), aVisitor);
    }

    public Item getObviousKey (Item aDoor)
    {
        ObviousKeyFinder f = new ObviousKeyFinder (this, aDoor);
        visitPlayerInventory (f);
        return f.getFoundItem ();
    }

    public int getContainedSize (Item aContainer)
    {
        ItemSizeAdder a = new ItemSizeAdder ();
        visitChildren (aContainer, a);
        return a.getSize ();
    }

    public void parse (BufferedReader r) throws IOException, GameEngineException
    {
        String s;
        Item i = null;
        Rule rule = null;
        while ((s = r.readLine ()) != null)
        {
            s = s.trim ();
            Tokenizer t = new Tokenizer (s);
            if (s.equals ("") || s.startsWith (";"))
            {
            }
            else if (s.startsWith ("%%"))
            {
                t.getFixed (2);
                i = null;
                rule = new Rule (this, t);
            }
            else if (s.startsWith ("%"))
            {
                t.getFixed (1);
                rule = null;
                i = accessItem (t.getWord ());
                if (t.hasMore ())
                {
                    i.setDescription (t.getRemainder ());
                }
            }
            else if (i != null)
            {
                i.parse (t);
            }
            else if (rule != null)
            {
                rule.parse (t);
            }
            else
            {
                addDescription (s);
            }
        }

    }

    public void save (BufferedWriter w)
    {
        SaveVisitor v = new SaveVisitor (w);
        visitAll (v);
    }

    public void addRule (Rule r)
    {
        _Rules.add (r);
    }

    public void log (String aMessage)
    {
        _UserInterface.log (aMessage);
    }

    public void declareLoss (String aMessage)
    {
        _UserInterface.declareLoss (aMessage);
    }

    public void declareWin (String aMessage)
    {
        _UserInterface.declareWin (aMessage);
    }

    public void itemClicked (Item aItem)
    {
        _UserInterface.itemClicked (aItem);
    }

    public void verify () throws GameEngineException
    {
        for (Item i : _Items.values ())
        {
            if (!i.hasDescription ())
            {
                throw new GameEngineException (i.getName () + " does not have a description");
            }

        }
    }
}