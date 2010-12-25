package streambolics.textAdventure;

import streambolics.core.Tokenizer;

public abstract class ItemInstruction extends Instruction
{
    private Item _Target;
    private String _PropName;
    private String _PropValue;

    @Override
    public void parse (Tokenizer t)
    {
        // TODO Auto-generated method stub

    }

    protected Item getTarget ()
    {
        return _Target;
    }

    protected String getPropName ()
    {
        return _PropName;
    }

    protected String getPropValue ()
    {
        return _PropValue;
    }
}
