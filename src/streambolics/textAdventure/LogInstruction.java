package streambolics.textAdventure;

public class LogInstruction extends MessageInstruction
{

    @Override
    public boolean run ()
    {
        getGame ().Log (getMessage ());
        return true;
    }

}
