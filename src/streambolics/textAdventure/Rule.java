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

import java.util.ArrayList;
import java.util.List;

import streambolics.core.Tokenizer;
import android.util.Log;

public class Rule extends GameObject
{
    public enum Type
    {
        ALWAYS, INSIDE, REMOVE, ADD, USEON, OPERATE
    };

    private final String LOGTAG = "Rule";
    private Type _Type;
    private Item _Item1;
    private Item _Item2;
    private List<Instruction> _Instructions = new ArrayList<Instruction> ();

    private static Type parseType (String aType) throws GameEngineException
    {
        aType = aType.toUpperCase ();
        if (aType.equals ("ALWAYS"))
        {
            return Type.ALWAYS;
        }
        else if (aType.equals ("INSIDE"))
        {
            return Type.INSIDE;
        }
        else if (aType.equals ("REMOVE"))
        {
            return Type.REMOVE;
        }
        else if (aType.equals ("ADD"))
        {
            return Type.ADD;
        }
        else if (aType.equals ("USEON"))
        {
            return Type.USEON;
        }
        else if (aType.equals ("OPERATE"))
        {
            return Type.OPERATE;
        }

        throw new GameEngineException ("Unknown rule type " + aType);
    }

    public Rule (Game aGame, Tokenizer aLine) throws GameEngineException
    {
        super (aGame);

        _Type = parseType (aLine.getWord ());

        if (aLine.hasMore ())
        {
            _Item1 = aGame.accessItem (aLine.getWord ());
        }
        if (aLine.hasMore ())
        {
            _Item2 = aGame.accessItem (aLine.getWord ());
        }
    }

    public boolean appliesTo (Type aType, Item aItem1, Item aItem2)
    {
        return _Type == aType && _Item1 == aItem1 & _Item2 == aItem2;
    }

    public boolean apply (Type aType, Item aItem1, Item aItem2) throws GameEngineException
    {
        Log.d (LOGTAG, "trying rule");
        return appliesTo (aType, aItem1, aItem2) && run ();
    }

    /***
     * Executes the instructions in the rule.
     * 
     * @return true if the rule was useful, ie did change something in the game.
     *         false if the rule did not change anything.
     * 
     * @throws GameEngineException
     */

    public boolean run () throws GameEngineException
    {
        boolean running = true;
        boolean useful = false;
        for (Instruction i : _Instructions)
        {
            if (running)
            {
                running = i.run ();
                if (i.isUseful ())
                {
                    useful = true;
                }
            }
            else
            {
                running = i.skip ();
            }
        }
        return useful;
    }

    public void parse (Tokenizer t) throws GameEngineException
    {
        String cmd = t.getWord ();
        Instruction i = null;
        if (cmd.equals ("IF"))
        {
            i = new IfInstruction (getGame ());
        }
        else if (cmd.equals ("IFNOT"))
        {
            i = new IfNotInstruction (getGame ());
        }
        else if (cmd.equals ("FLIP"))
        {
            i = new FlipInstruction (getGame ());
        }
        else if (cmd.equals ("SET"))
        {
            i = new SetInstruction (getGame ());
        }
        else if (cmd.equals ("SAY"))
        {
            i = new LogInstruction (getGame ());
        }
        else if (cmd.equals ("WIN"))
        {
            i = new WinInstruction (getGame ());
        }
        else if (cmd.equals ("LOSE"))
        {
            i = new LoseInstruction (getGame ());
        }

        if (i == null)
        {
            throw new GameEngineException ("Unknown rule instruction: " + cmd);
        }
        else
        {
            i.parse (t);
            _Instructions.add (i);
        }
    }
}
