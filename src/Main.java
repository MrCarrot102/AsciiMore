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
 * <p>
 * Nie jest to zbyt poyzteczne ale chetnie wykorzystam to do innego projektu kiedyś tam,
 * w idealnym swiecie polaczyc to z kodem cpp ktory wczesniej napisalem AsciiCube
 */

public class Main
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

    // skaluje obraz do zadanej szerokości znaków, zachoujac proporcje z korekta pod czcionke terminala
    private static BufferedImage resize(BufferedImage original, int targetWidth)
    {
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        int targetHeight = (int) ((double) originalHeight / originalWidth * targetWidth * CHAR_ASPECT_RATIO);
        if (targetHeight <= 0) targetHeight = 1;

        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        var g = resized.createGraphics();
        g.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resized;
    }

    /**
     * drukowanie obrazu jako ascii bo terminal ssie pale i nie wyswietla ladnych obrazkow
     * nie wiem po co to komu ale moze
     * pozdrawiam Cegiełkę kochaną i Grubaska, i ludzi z ISSP :))
     * I wszystkich który to moze czytaja :)
     * moze ktos to przeczyta
     */
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
                double normalized = luminance / 255.0;
                if (invert) normalized = 1.0 - normalized;

                int index = (int) (normalized * (RAMP.length() - 1));
                char ch = RAMP.charAt(index);

                if (color)
                {
                    sb.append(String.format("\u001B[38;2;%d;%d;%dm%c", c.getRed(), c.getGreen(), c.getBlue(), ch));
                } else
                {
                    sb.append(ch);
                }
            }

            if (color) sb.append("\u001B[0m");
            sb.append("\n");
        }

        System.out.print(sb);
    }
}

/**
 * Trochę wspolczuje jak ktos czyta kod do tego ale w sumie powodzenia i milego
 * ciekawe pomysly na wykorzystanie tego kodu wyslac pomysli na:
 * MozeKiedysCosZTymZrobieZamiastMiecToWDupie@NiePrawdziwyMail.com
 */
