package streambolics.textAdventure;

import streambolics.core.FieldParser;

public abstract class ExitFieldParser extends FieldParser<Item>
{
    protected abstract void setExit (Item aObject, Exit aExit);

    @Override
    public void setValue (Item aObject, String aValue)
    {
        setExit (aObject, new Exit (aObject.getGame (), aObject, aValue));
    }

    @Override
    public String getValue (Item aObject)
    {
        return "";
    }

    @Override
    public boolean isValue (Item aObject, String aValue)
    {
        return false;
    }

}
