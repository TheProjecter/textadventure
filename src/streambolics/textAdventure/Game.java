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

import streambolics.core.BooleanFieldParser;
import streambolics.core.FieldParser;
import streambolics.core.IntegerFieldParser;
import streambolics.core.Logger;
import streambolics.core.Tokenizer;
import android.util.Log;

public class Game implements Logger
{
    private final String LOGTAG = "Game";
    private HashMap<String, FieldParser<Item>> _ItemParsers = new HashMap<String, FieldParser<Item>> ();

    private HashMap<String, Item> _Items = new HashMap<String, Item> ();
    private List<Rule> _Rules = new ArrayList<Rule> ();
    private String _Description = "";
    private Item _Player;
    private UserInterface _UserInterface;

    public Game (UserInterface aUserInterface)
    {
        _UserInterface = aUserInterface;
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

    private void addDescription (String l)
    {
        _Description = (_Description + '\n' + l).trim ();
    }

    public void addRule (Rule r)
    {
        Log.d (LOGTAG, "Rule added");
        _Rules.add (r);
    }

    public boolean applyRules (Rule.Type aType, Item aItem1, Item aItem2) throws GameEngineException
    {
        for (Rule r : _Rules)
        {
            if (r.apply (aType, aItem1, aItem2))
            {
                return true;
            }
        }
        return false;
    }

    public boolean AnyRuleApplies (Rule.Type aType, Item aItem1, Item aItem2)
    {
        for (Rule r : _Rules)
        {
            if (r.appliesTo (aType, aItem1, aItem2))
            {
                return true;
            }
        }
        return false;
    }

    private FieldParser<Item> computeItemFieldParser (String aName) throws GameEngineException
    {
        if (aName.equals ("OPEN"))
        {
            return new BooleanFieldParser<Item> ()
            {
                @Override
                protected boolean getConvertedValue (Item aObject)
                {
                    return aObject.isOpen ();
                }

                @Override
                protected void setConvertedValue (Item aObject, boolean aValue)
                {
                    aObject.setOpen (aValue);
                }
            };
        }
        else if (aName.equals ("LOCATION"))
        {
            return new ItemFieldParser (this)
            {
                @Override
                protected Item getConvertedValue (Item aObject)
                {
                    return aObject.getContainer ();
                }

                @Override
                protected void setConvertedValue (Item aObject, Item aValue)
                {
                    aObject.setContainer (aValue);
                }
            };
        }
        else if (aName.equals ("LIGHT"))
        {
            return new ItemFieldParser (this)
            {
                @Override
                protected Item getConvertedValue (Item aObject)
                {
                    return aObject.getLightSource ();
                }

                @Override
                protected void setConvertedValue (Item aObject, Item aValue)
                {
                    aObject.setLightSource (aValue);
                    aValue.setProbableTheme ("LAMP");
                }
            };
        }
        else if (aName.equals ("KEY"))
        {
            return new ItemFieldParser (this)
            {
                @Override
                protected Item getConvertedValue (Item aObject)
                {
                    return aObject.getKey ();
                }

                @Override
                protected void setConvertedValue (Item aObject, Item aValue)
                {
                    aObject.setKey (aValue);
                    aValue.setProbableTheme ("KEY");
                }
            };
        }
        else if (aName.equals ("ICON"))
        {
            return new FieldParser<Item> ()
            {
                @Override
                public void setValue (Item aObject, String aValue)
                {
                    aObject.setTheme (aValue);
                }

                @Override
                public String getValue (Item aObject)
                {
                    return aObject.getTheme ();
                }
            };
        }
        else if (aName.equals ("LOCKED"))
        {
            return new BooleanFieldParser<Item> ()
            {
                @Override
                protected boolean getConvertedValue (Item aObject)
                {
                    return aObject.isLocked ();
                }

                @Override
                protected void setConvertedValue (Item aObject, boolean aValue)
                {
                    aObject.setLocked (aValue);
                }
            };
        }
        else if (aName.equals ("LIT"))
        {
            return new BooleanFieldParser<Item> ()
            {
                @Override
                protected boolean getConvertedValue (Item aObject)
                {
                    return aObject.isLit ();
                }

                @Override
                protected void setConvertedValue (Item aObject, boolean aValue)
                {
                    aObject.setLit (aValue);
                }
            };
        }
        else if (aName.equals ("FIXED"))
        {
            return new BooleanFieldParser<Item> ()
            {
                @Override
                protected boolean getConvertedValue (Item aObject)
                {
                    return aObject.isFixed ();
                }

                @Override
                protected void setConvertedValue (Item aObject, boolean aValue)
                {
                    aObject.setFixed (aValue);
                }
            };
        }
        else if (aName.equals ("CONCEALED"))
        {
            return new BooleanFieldParser<Item> ()
            {
                @Override
                protected boolean getConvertedValue (Item aObject)
                {
                    return aObject.isConcealed ();
                }

                @Override
                protected void setConvertedValue (Item aObject, boolean aValue)
                {
                    aObject.setConcealed (aValue);
                }
            };
        }
        else if (aName.equals ("SIZE"))
        {
            return new IntegerFieldParser<Item> ()
            {
                @Override
                protected int getConvertedValue (Item aObject)
                {
                    return aObject.getSize ();
                }

                @Override
                protected void setConvertedValue (Item aObject, int aValue)
                {
                    aObject.setSize (aValue);
                }
            };
        }
        else if (aName.equals ("INNERSIZE"))
        {
            return new IntegerFieldParser<Item> ()
            {
                @Override
                protected int getConvertedValue (Item aObject)
                {
                    return aObject.getInnerSize ();
                }

                @Override
                protected void setConvertedValue (Item aObject, int aValue)
                {
                    aObject.setInnerSize (aValue);
                }
            };
        }
        else if (aName.equals ("OPENINGSIZE"))
        {
            return new IntegerFieldParser<Item> ()
            {
                @Override
                protected int getConvertedValue (Item aObject)
                {
                    return aObject.getOpeningSize ();
                }

                @Override
                protected void setConvertedValue (Item aObject, int aValue)
                {
                    aObject.setOpeningSize (aValue);
                }
            };
        }
        else if (aName.equals ("NORTH"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setNorth (aExit);
                }
            };
        }
        else if (aName.equals ("SOUTH"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setSouth (aExit);
                }
            };
        }
        else if (aName.equals ("EAST"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setEast (aExit);
                }
            };
        }
        else if (aName.equals ("WEST"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setWest (aExit);
                }
            };
        }
        else if (aName.equals ("SOUTHWEST"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setSouthWest (aExit);
                }
            };
        }
        else if (aName.equals ("NORTHWEST"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setNorthWest (aExit);
                }
            };
        }
        else if (aName.equals ("SOUTHEAST"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setSouthEast (aExit);
                }
            };
        }
        else if (aName.equals ("NORTHEAST"))
        {
            return new ExitFieldParser ()
            {
                @Override
                protected void setExit (Item aObject, Exit aExit)
                {
                    aObject.setNorthEast (aExit);
                }
            };
        }

        throw new GameEngineException ("Invalid Item property name " + aName);
    }

    public void declareLoss (String aMessage)
    {
        _UserInterface.declareLoss (aMessage);
    }

    public void declareWin (String aMessage)
    {
        _UserInterface.declareWin (aMessage);
    }

    public int getContainedSize (Item aContainer)
    {
        ItemSizeAdder a = new ItemSizeAdder ();
        visitChildren (aContainer, a);
        return a.getSize ();
    }

    public FieldParser<Item> getItemFieldParser (String aName) throws GameEngineException
    {
        aName = aName.toUpperCase ();
        if (_ItemParsers.containsKey (aName))
        {
            return _ItemParsers.get (aName);
        }
        else
        {
            FieldParser<Item> p = computeItemFieldParser (aName);
            _ItemParsers.put (aName, p);
            return p;
        }
    }

    public Item getObviousKey (Item aDoor)
    {
        ObviousKeyFinder f = new ObviousKeyFinder (this, aDoor);
        visitPlayerInventory (f);
        return f.getFoundItem ();
    }

    public Item getPlayer ()
    {
        if (_Player == null)
        {
            _Player = accessItem (Item.PLAYER_NAME);
            _Player.makeProbableContainer ();
        }
        return _Player;
    }

    public void itemClicked (Item aItem)
    {
        _UserInterface.itemClicked (aItem);
    }

    public void log (String aMessage)
    {
        _UserInterface.log (aMessage);
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
                addRule (rule);
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

    public void visitAll (ItemVisitor aVisitor)
    {
        for (Item i : _Items.values ())
        {
            aVisitor.visit (i);
        }
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

    public void visitPlayerInventory (ItemVisitor aVisitor)
    {
        visitChildren (getPlayer (), aVisitor);
    }
}
