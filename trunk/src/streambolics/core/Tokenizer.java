package streambolics.core;

public class Tokenizer
{
    private String _Line;

    public Tokenizer(String aLine)
    {
        _Line = aLine;
    }

    public String getRemainder()
    {
        String r = _Line;
        _Line = "";
        return r;
    }

    public String getFixed(int i)
    {
        String r = _Line.substring(0, i);
        _Line = _Line.substring(i).trim();
        return r;
    }

    public String getWord()
    {
        return getUpTo(" ");
    }

    public String getUpTo(String s)
    {
        int p = _Line.indexOf(s);
        if (p >= 0)
        {
            return getFixed(p);
        }
        else
        {
            return getRemainder();
        }
    }

    public boolean hasMore()
    {
        return _Line.length() > 0;
    }
}
