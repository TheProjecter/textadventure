package streambolics.core;

public abstract class FieldParser<T>
{
    public abstract void setValue (T aObject, String aValue);

    public abstract String getValue (T aObject);

    public boolean isValue (T aObject, String aValue)
    {
        return getValue (aObject).equals (aValue);
    }
}
