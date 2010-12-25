package streambolics.textAdventure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import streambolics.core.Tokenizer;

public class Game
{
    private HashMap<String, Item> _Items = new HashMap<String, Item> ();
    private List<Rule> _Rules = new ArrayList<Rule> ();
    private String _Description = "";
    private Item _Player;

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
            _Player = accessItem ("Player");
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

    public void parse (BufferedReader r) throws IOException
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

    public void Log (String aMessage)
    {
        // TODO Auto-generated method stub
    }

    public void declareLoss (String aMessage)
    {
        // TODO Auto-generated method stub
    }

    public void declareWin (String aMessage)
    {
        // TODO Auto-generated method stub

    }
}
