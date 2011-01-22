package streambolics.core;

public abstract class IntegerFieldParser<T> extends FieldParser<T>
{
    protected abstract void setConvertedValue (T aObject, int aValue);

    protected abstract int getConvertedValue (T aObject);

    @Override
    public void setValue (T aObject, String aValue)
    {
        setConvertedValue (aObject, Integer.parseInt (aValue));
    }

    @Override
    public String getValue (T aObject)
    {
        return Integer.toString (getConvertedValue (aObject));
    }

    @Override
    public boolean isValue (T aObject, String aValue)
    {
        return getConvertedValue (aObject) == Integer.parseInt (aValue);
    }
}
