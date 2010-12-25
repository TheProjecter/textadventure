package streambolics.textAdventure;

import java.util.Random;

public class ItemRandomizer
{
    private Item _Item;
    private int _Count;
    private static Random _Random = new Random();

    public void add(Item i)
    {
        _Count += 1;

        if (_Random.nextInt(_Count) == 0)
        {
            _Item = i;
        }
    }

    public Item any()
    {
        return _Item;
    }
}
