package streambolics.textAdventure;

import streambolics.core.BooleanFieldParser;
import streambolics.core.Tokenizer;

public class FlipInstruction extends ItemFieldInstruction
{
    BooleanFieldParser<Item> _BoolParser;

    public FlipInstruction (Game aGame)
    {
        super (aGame);
    }

    @Override
    public void parse (Tokenizer t) throws GameEngineException
    {
        super.parse (t);
        _BoolParser = (BooleanFieldParser<Item>) getParser ();
    }

    @Override
    public boolean run () throws GameEngineException
    {
        _BoolParser.flipValue (getTarget ());
        return true;
    }

    @Override
    public boolean isUseful ()
    {
        return true;
    }

}
