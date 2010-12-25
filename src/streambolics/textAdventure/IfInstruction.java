package streambolics.textAdventure;

public class IfInstruction extends ItemInstruction
{
    @Override
    public boolean run ()
    {
        return getTarget ().getNamedProperty (getPropName ()).equals (getPropValue ());
    }

}
