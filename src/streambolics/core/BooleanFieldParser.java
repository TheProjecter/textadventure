package streambolics.core;

public abstract class BooleanFieldParser<T> extends FieldParser<T>
{
    protected abstract void setConvertedValue (T aObject, boolean aValue);

    protected abstract boolean getConvertedValue (T aObject);

    @Override
    public void setValue (T aObject, String aValue)
    {
        setConvertedValue (aObject, Boolean.parseBoolean (aValue));
    }

    @Override
    public String getValue (T aObject)
    {
        return getConvertedValue (aObject) ? "true" : "false";
    }

    @Override
    public boolean isValue (T aObject, String aValue)
    {
        return getConvertedValue (aObject) == Boolean.parseBoolean (aValue);
    }

    public void flipValue (T aObject)
    {
        setConvertedValue (aObject, !getConvertedValue (aObject));
    }
}
