package streambolics.textAdventure;

public abstract class ObviousItemFinder implements ItemVisitor
{
    private Item _Item;
    private int _Count;

    public ObviousItemFinder()
    {
        _Count = 0;
        _Item = null;
    }

    protected abstract boolean itemFits(Item i);

    @Override
    public void visit(Item i)
    {
        if (itemFits(i))
        {
            _Count += 1;
            _Item = i;
        }
    }

    public boolean itemFound()
    {
        return _Count == 1 && _Item != null;
    }

    public Item getFoundItem()
    {
        if (_Count == 1)
        {
            return _Item;
        }
        else
        {
            return null;
        }
    }
}
