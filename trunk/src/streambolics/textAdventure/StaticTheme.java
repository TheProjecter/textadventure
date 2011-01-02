package streambolics.textAdventure;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class StaticTheme implements ThemeProvider
{
    private Drawable _Floor;
    private Drawable _Wall;
    private Drawable _OpenDoor;
    private Drawable _ClosedDoor;
    private Drawable _Inventory;
    private int _TextColor;

    public StaticTheme (Drawable aInventory, Drawable aFloor, Drawable aWall, Drawable aOpenDoor, Drawable aClosedDoor, int aTextColor)
    {
        init (aInventory, aFloor, aWall, aOpenDoor, aClosedDoor, aTextColor);
    }

    public StaticTheme (int aInventory, Context aContext)
    {
        init (aInventory, aContext);
    }

    private void init (int aInventory, Context aContext)
    {
        init (aContext.getResources ().getDrawable (aInventory), aContext);
    }

    private void init (Drawable aInventory, Context aContext)
    {
        init (aInventory, StockDrawables.Floor (aContext), StockDrawables.Wall (aContext), StockDrawables.OpenDoor (aContext),
                StockDrawables.ClosedDoor (aContext), Color.BLACK);
    }

    private void init (Drawable aInventory, Drawable aFloor, Drawable aWall, Drawable aOpenDoor, Drawable aClosedDoor, int aTextColor)

    {
        _Floor = aFloor;
        _Wall = aWall;
        _OpenDoor = aOpenDoor;
        _ClosedDoor = aClosedDoor;
        _Inventory = aInventory;
        _TextColor = aTextColor;
    }

    @Override
    public Drawable getFloorDrawable (Context aContext)
    {
        return _Floor;
    }

    @Override
    public Drawable getWallDrawable (Context aContext)
    {
        return _Wall;
    }

    @Override
    public Drawable getOpenDoorDrawable (Context aContext)
    {
        return _OpenDoor;
    }

    @Override
    public Drawable getClosedDoorDrawable (Context aContext)
    {
        return _ClosedDoor;
    }

    @Override
    public Drawable getInventoryDrawable (Context aContext)
    {
        return _Inventory;
    }

    @Override
    public int getTextColor (Context aContext)
    {
        return _TextColor;
    }

}
