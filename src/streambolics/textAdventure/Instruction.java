package streambolics.textAdventure;

import streambolics.core.Tokenizer;

public abstract class Instruction
{
    private Game _Game;

    public abstract boolean run ();

    public abstract void parse (Tokenizer t);

    public void setGame (Game aGame)
    {
        _Game = aGame;
    }

    public Game getGame ()
    {
        return _Game;
    }
}
