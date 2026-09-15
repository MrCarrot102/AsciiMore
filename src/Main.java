import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Koweter obrazu do ASCII art wyświetlanego w terminalu
 * <p>
 * Użycie:
 * Java AsciiArt <ścieżkaDoObrazu> [szerokośćWZnakach] [--color] [--invert]
 *
 */

public class AsciiArt
{
    // znaki od najciemniejszego do najjaśniejszego
    private static final String RAMP = "@%#*+=-;. ";

    // współczynnik korekcji proporcji znaku w terminalu
    private static final double CHAR_ASPECT_RATIO = 0.5;

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.out.println("Użycie: java AsciiArt <plikObrazu> [szerokość] [--color] [--invert]");
            return;
        }

        String path = args[0];
        int targetWidth = 100;
        boolean color = false;
        boolean invert = false;

        for (int i = 1; i < args.length; i++)
        {
            String arg = args[i];
            if (arg.equalsIgnoreCase("--color"))
            {
                color = true;
            } else if (arg.equalsIgnoreCase("--invert"))
            {
                invert = true;
            } else
            {
                try
                {
                    targetWidth = Integer.parseInt(arg);

                } catch (NumberFormatException ignored)
                {

                }
            }
        }

        try
        {
            BufferedImage original = ImageIO.read(new File(path));
            if (original == null)
            {
                System.out.println("Nie udało się wczytać obrazu: " + path);
                return;
            }
            BufferedImage resized = resize(original, targetWidth);
            printAscii(resized, color, invert);
        } catch (IOException e)
        {
            System.out.println("Błąd wczytywania pliku: " + e.getMessage());
        }
    }

    // Skaluje obraz do zadanej szerokości znaków, zachoujac proporcje z korekta pod czcionke terminala
    private static BufferedImage resize(BufferedImage original, int targetWidth)
    {
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        int targetHeight = (int) ((double) originalHeight / originalWidth * tergetWidth * CHAR_ASPECT_RATIO);
        if (targetHeight <= 0) targetHeight = 1;

        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        var g = resized.createGraphics();
        g.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resized;
    }

    // drukowanie obrazu jako ascii bo terminal ssie pale i nie wyswietla ladnych obrazkow
    // nie wiem po co to komu ale moze
    // pozdrawiam Cegiełkę kochaną i Grubaska :))
    // moze ktos to przeczyta
    private static void printAscii(BufferedImage img, boolean color, boolean invert)
    {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                int rgb = img.getRGB(x, y);
                Color c = new Color(rgb);

                double luminance = 0.2126 * c.getRed() + 0.7152 * c.getGreen() + 0.0722 * c.getBlue();
                // jeszcze trzeba skończyć ale poszedłem spać a dzisiaj dopisałem jedna linię i więcej mi się nie chce :( 
            }

        }
    }

}