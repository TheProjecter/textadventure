package streambolics.textAdventure;

import streambolics.core.Tokenizer;

public abstract class ItemFieldValueInstruction extends ItemFieldInstruction
{
    private String _Value;

    public String getValue ()
    {
        return _Value;
    }

    public ItemFieldValueInstruction (Game aGame)
    {
        super (aGame);
    }

    @Override
    public void parse (Tokenizer t) throws GameEngineException
    {
        super.parse (t);
        _Value = t.getRemainder ();
    }

}
