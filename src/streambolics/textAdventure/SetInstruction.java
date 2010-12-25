package streambolics.textAdventure;

public class SetInstruction extends ItemInstruction
{

    @Override
    public boolean run ()
    {
        getTarget ().setNamedProperty (getPropName (), getPropValue ());
        return true;
    }

}
