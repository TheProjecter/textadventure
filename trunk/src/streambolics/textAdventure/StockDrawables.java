package streambolics.textAdventure;

import streambolics.android.SurroundedDrawable;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class StockDrawables
{
    private static Drawable _ClosedDoor;
    private static Drawable _OpenDoor;
    private static Drawable _Wall;
    private static Drawable _Floor;
    private static Drawable _Floor1;
    private static Drawable _PitchBlack;

    private static SurroundedDrawable _TiledFloor;

    public static Drawable ClosedDoor (Context aContext)
    {
        if (_ClosedDoor == null)
        {
            _ClosedDoor = aContext.getResources ().getDrawable (R.drawable.closeddoor);
        }
        return _ClosedDoor;
    }

    public static Drawable OpenDoor (Context aContext)
    {
        if (_OpenDoor == null)
        {
            _OpenDoor = aContext.getResources ().getDrawable (R.drawable.opendoor);
        }
        return _OpenDoor;
    }

    public static Drawable Wall (Context aContext)
    {
        if (_Wall == null)
        {
            _Wall = aContext.getResources ().getDrawable (R.drawable.wall);
        }
        return _Wall;
    }

    public static Drawable Floor (Context aContext)
    {
        if (_Floor == null)
        {
            _Floor = aContext.getResources ().getDrawable (R.drawable.floor2);
        }
        return _Floor;
    }

    public static Drawable Floor1 (Context aContext)
    {
        if (_Floor1 == null)
        {
            _Floor1 = aContext.getResources ().getDrawable (R.drawable.floor1);
        }
        return _Floor1;
    }

    public static Drawable PitchBlack (Context aContext)
    {
        if (_PitchBlack == null)
        {
            _PitchBlack = aContext.getResources ().getDrawable (R.drawable.pitchblack);
        }
        return _PitchBlack;
    }

    public static Drawable TiledFloor (Context aContext)
    {
        if (_TiledFloor == null)
        {
            _TiledFloor = new SurroundedDrawable (Floor (aContext));
            _TiledFloor.setAbove (Floor1 (aContext));
            _TiledFloor.setLeft (_Floor);
            _TiledFloor.setRight (_Floor1);
            _TiledFloor.setAboveLeft (_Floor1);
            _TiledFloor.setAboveRight (_Floor);
        }
        return _TiledFloor;
    }

}
