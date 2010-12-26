package streambolics.textAdventure;

import java.util.HashMap;

import android.content.Context;

public class StockThemes
{
    private static HashMap<String, ThemeProvider> _Themes = new HashMap<String, ThemeProvider> ();
    private static ThemeProvider _Default;

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

    private static void buildThemes (Context aContext)
    {
        if (_Default != null)
        {
            return;
        }
        _Default = new StaticTheme (StockDrawables.Floor (aContext), StockDrawables.Floor (aContext), StockDrawables.Wall (aContext),
                StockDrawables.OpenDoor (aContext), StockDrawables.ClosedDoor (aContext));
        _Themes.put ("ROOM", _Default);
        _Themes.put ("KEY", new StaticTheme (R.drawable.key, aContext));
        _Themes.put ("GARDEN", new StaticTheme (StockDrawables.Grass (aContext), StockDrawables.Grass (aContext), StockDrawables.Bush (aContext),
                StockDrawables.Grass (aContext), StockDrawables.Bush (aContext)

        ));
    }
}
