package streambolics.textAdventure;

public class ObviousKeyFinder extends ObviousItemFinder
{
    private Game _Game;
    private Item _Door;

    public ObviousKeyFinder(Game aGame, Item aDoor)
    {
        _Game = aGame;
        _Door = aDoor;
    }

    @Override
    protected boolean itemFits(Item i)
    {
        // TODO : Enumerate the scriptlets to see if one is ok.
        return _Door != null && i == _Door.getKey();
    }

}
