package streambolics.textAdventure;

import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class StockThemes
{
    private static HashMap<String, ThemeProvider> _Themes = new HashMap<String, ThemeProvider> ();
    private static ThemeProvider _Default;
    private static Resources _Resources;

    public static ThemeProvider getTheme (Context aContext, String aName)
    {
        buildThemes (aContext);
        String s = aName.toUpperCase ();
        if (_Themes.containsKey (s))
        {
            return _Themes.get (s);
        }
        else
        {
            return _Default;
        }
    }

    public static ThemeProvider getDefault (Context aContext)
    {
        buildThemes (aContext);
        return _Default;
    }

    private static Drawable d (int i)
    {
        return _Resources.getDrawable (i);
    }

    private static void buildThemes (Context aContext)
    {
        if (_Default != null)
        {
            return;
        }
        _Resources = aContext.getResources ();

        Drawable b = StockDrawables.PitchBlack (aContext);
        Drawable f = StockDrawables.Floor (aContext);
        Drawable w = StockDrawables.Wall (aContext);
        Drawable g = StockDrawables.Grass (aContext);
        _Default = new StaticTheme (b, b, b, b, b, Color.GRAY);
        _Themes.put ("ROOM", new StaticTheme (f, f, w, StockDrawables.OpenDoor (aContext), StockDrawables.ClosedDoor (aContext), Color.BLACK));
        _Themes.put ("KEY", new StaticTheme (R.drawable.key, aContext));
        _Themes.put ("GARDEN", new StaticTheme (g, g, StockDrawables.Bush (aContext), g, StockDrawables.Bush (aContext), Color.BLACK));
        _Themes.put ("STAIRS", new StaticTheme (f, f, d (R.drawable.stairs), d (R.drawable.stairs), d (R.drawable.stairs), Color.BLACK));
    }
}
