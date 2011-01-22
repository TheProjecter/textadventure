package streambolics.core;

public abstract class ConvertedFieldParser<T, V> extends FieldParser<T>
{
    protected abstract void setConvertedValue (T aObject, V aValue);

    protected abstract V getConvertedValue (T aObject);

    protected abstract V convertToInternal (String aValue);

    protected abstract String convertFromInternal (V aValue);

    @Override
    public void setValue (T aObject, String aValue)
    {
        setConvertedValue (aObject, convertToInternal (aValue));
    }

    @Override
    public String getValue (T aObject)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isValue (T aObject, String aValue)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
