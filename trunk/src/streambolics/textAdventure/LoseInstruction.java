package streambolics.textAdventure;

public class LoseInstruction extends MessageInstruction
{

    @Override
    public boolean run ()
    {
        getGame ().declareLoss (getMessage ());
        return true;
    }

}
