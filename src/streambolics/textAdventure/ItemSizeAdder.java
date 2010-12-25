package streambolics.textAdventure;

public class ItemSizeAdder implements ItemVisitor
{
    private int _TotalSize;

    @Override
    public void visit(Item i)
    {
        _TotalSize += i.getSize();
    }

    public ItemSizeAdder()
    {
        _TotalSize = 0;
    }

    public int getSize()
    {
        return _TotalSize;
    }
}
