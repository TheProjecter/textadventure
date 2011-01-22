package streambolics.textAdventure;

import streambolics.core.FieldParser;
import streambolics.core.Tokenizer;

public abstract class ItemFieldInstruction extends ItemInstruction
{
    @Override
    public void parse (Tokenizer t) throws GameEngineException
    {
        super.parse (t);
        _Parser = getGame ().getItemFieldParser (t.getWord ());
    }

    private FieldParser<Item> _Parser;

    public ItemFieldInstruction (Game aGame)
    {
        super (aGame);
    }

    public FieldParser<Item> getParser ()
    {
        return _Parser;
    }
}
