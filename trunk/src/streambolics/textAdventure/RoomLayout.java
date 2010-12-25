package streambolics.textAdventure;

import streambolics.android.CustomLayout;
import streambolics.android.SizeProxyDrawable;
import streambolics.android.StandardTextView;
import streambolics.android.StockAnimations;
import streambolics.android.StockTransformations;
import android.content.Context;

public class RoomLayout extends CustomLayout
{
    private WallButton _North;
    private WallButton _South;
    private WallButton _East;
    private WallButton _West;
    private WallButton _NorthEast;
    private WallButton _SouthEast;
    private WallButton _NorthWest;
    private WallButton _SouthWest;
    private StandardTextView _Description;
    private InventoryView _Contents;

    public RoomLayout (Context aContext)
    {
        super (aContext);

        _North = new WallButton (aContext, StockAnimations.getApparateNorth (), new StraightWallDrawable (), null);
        addView (_North);

        _South = new WallButton (aContext, StockAnimations.getApparateSouth (), new StraightWallDrawable (), StockTransformations.Rotate180 ());
        addView (_South);

        _East = new WallButton (aContext, StockAnimations.getApparateEast (), new StraightWallDrawable (), StockTransformations.Rotate90 ());
        addView (_East);

        _West = new WallButton (aContext, StockAnimations.getApparateWest (), new StraightWallDrawable (), StockTransformations.Rotate270 ());
        addView (_West);

        _NorthEast = new WallButton (aContext, StockAnimations.getApparateNorthEast (), new CornerWallDrawable (), null);
        addView (_NorthEast);

        _SouthEast = new WallButton (aContext, StockAnimations.getApparateSouthEast (), new CornerWallDrawable (), StockTransformations.Rotate90 ());
        addView (_SouthEast);

        _NorthWest = new WallButton (aContext, StockAnimations.getApparateNorthWest (), new CornerWallDrawable (), StockTransformations.Rotate270 ());
        addView (_NorthWest);

        _SouthWest = new WallButton (aContext, StockAnimations.getApparateSouthWest (), new CornerWallDrawable (), StockTransformations.Rotate180 ());
        addView (_SouthWest);

        _Description = new StandardTextView (aContext);
        SizeProxyDrawable b = new SizeProxyDrawable ();
        b.setProxyed (StockDrawables.TiledFloor (getContext ()));
        _Description.setBackgroundDrawable (b);
        addView (_Description);

        _Contents = new InventoryView (aContext);
        addView (_Contents);
    }

    @Override
    protected void onLayout (boolean aChanged, int aL, int aT, int aR, int aB)
    {
        int ww = 64;
        int width = aR - aL;

        if (ww < width / 6)
        {
            ww = width / 6;
        }
        if (ww > width / 4)
        {
            ww = width / 4;
        }

        int hh = (width - 2 * ww) / 2;

        _NorthWest.layout (aL, aT, aL + ww, aT + ww);
        _North.layout (aL + ww, aT, aR - ww, aT + ww);
        _NorthEast.layout (aR - ww, aT, aR, aT + ww);
        _West.layout (aL, aT + ww, aL + ww, aB - ww);
        _East.layout (aR - ww, aT + ww, aR, aB - ww);
        _SouthWest.layout (aL, aB - ww, aL + ww, aB);
        _South.layout (aL + ww, aB - ww, aR - ww, aB);
        _SouthEast.layout (aR - ww, aB - ww, aR, aB);

        _Description.layout (aL + ww, aT + ww, aR - ww, aT + ww + hh);
        _Contents.layout (aL + ww, aT + ww + hh, aR - ww, aB - ww);
    }

    public WallButton getNorthButton ()
    {
        return _North;
    }

    public WallButton getSouthButton ()
    {
        return _South;
    }

    public WallButton getEastButton ()
    {
        return _East;
    }

    public WallButton getWestButton ()
    {
        return _West;
    }

    public WallButton getNorthEastButton ()
    {
        return _NorthEast;
    }

    public WallButton getNorthWestButton ()
    {
        return _NorthWest;
    }

    public WallButton getSouthEastButton ()
    {
        return _SouthEast;
    }

    public WallButton getSouthWestButton ()
    {
        return _SouthWest;
    }

    public StandardTextView getDescriptionView ()
    {
        return _Description;
    }

    public InventoryView getContentsView ()
    {
        return _Contents;
    }

}
