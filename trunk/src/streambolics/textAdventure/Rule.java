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

public class Rule
{
    private Game _Game;
    private Item _Item1;
    private Item _Item2;
    private Item _Item3;
    private List<Instruction> _Instructions = new ArrayList<Instruction> ();

    public Rule (Game aGame, Tokenizer aLine)
    {
        super ();
        _Game = aGame;
        if (aLine.hasMore ())
        {
            _Item1 = aGame.accessItem (aLine.getWord ());
        }
        if (aLine.hasMore ())
        {
            _Item2 = aGame.accessItem (aLine.getWord ());
        }
        if (aLine.hasMore ())
        {
            _Item3 = aGame.accessItem (aLine.getWord ());
        }
    }

    public boolean hasItem (Item i)
    {
        return i == _Item1 || i == _Item2 || i == _Item3;
    }

    public boolean run ()
    {
        for (Instruction i : _Instructions)
        {
            if (!i.run ())
            {
                return false;
            }
        }
        return true;
    }

    public void parse (Tokenizer t)
    {
        String cmd = t.getWord ();
        Instruction i = null;
        if (cmd.equals ("IF"))
        {
            i = new IfInstruction ();
        }
        else if (cmd.equals ("SET"))
        {
            i = new SetInstruction ();
        }
        else if (cmd.equals ("SAY"))
        {
            i = new LogInstruction ();
        }
        else if (cmd.equals ("WIN"))
        {
            i = new WinInstruction ();
        }
        else if (cmd.equals ("LOSE"))
        {
            i = new LoseInstruction ();
        }

        if (i != null)
        {
            i.setGame (_Game);
            i.parse (t);
            _Instructions.add (i);
        }
    }
}
