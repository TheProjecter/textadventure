package streambolics.textAdventure;

import streambolics.core.Tokenizer;

public abstract class MessageInstruction extends Instruction
{
    private String _Message;

    @Override
    public void parse (Tokenizer t)
    {
        _Message = t.getRemainder ();
    }

    protected String getMessage ()
    {
        return _Message;
    }
}
