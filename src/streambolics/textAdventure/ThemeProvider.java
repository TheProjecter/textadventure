package streambolics.textAdventure;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface ThemeProvider
{
    public Drawable getFloorDrawable (Context aContext);

    public Drawable getWallDrawable (Context aContext);

    public Drawable getOpenDoorDrawable (Context aContext);

    public Drawable getClosedDoorDrawable (Context aContext);

    public Drawable getInventoryDrawable (Context aContext);

    public int getTextColor (Context aContext);
}
