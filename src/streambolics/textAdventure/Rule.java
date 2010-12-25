package streambolics.textAdventure;

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
