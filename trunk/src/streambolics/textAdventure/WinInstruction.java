package streambolics.textAdventure;

public class WinInstruction extends MessageInstruction
{

    @Override
    public boolean run ()
    {
        getGame ().declareWin (getMessage ());
        return true;
    }

}
