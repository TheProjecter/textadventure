package streambolics.textAdventure;

import java.io.BufferedWriter;
import java.io.IOException;

public class SaveVisitor implements ItemVisitor
{
    private BufferedWriter _Writer;

    public SaveVisitor(BufferedWriter aWriter)
    {
        _Writer = aWriter;
    }

    @Override
    public void visit(Item i)
    {
        try
        {
            i.Save(_Writer);
        }
        catch (IOException e)
        {
        }
    }

}
