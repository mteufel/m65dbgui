package org.mega65.m65dbgui.services;


import jakarta.inject.Singleton;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Singleton
public class FontService {

    Logger logger = Util.getLogger(FontService.class);
    HashMap<String, Font> fontCash = new HashMap<>();

    public Font getMonoDefaultFont() {
        return getFont("jetbrains-mono.ttf", Font.PLAIN, 16);
        //return getFont("mega65-80.ttf", Font.PLAIN, 24);
    }

    public Font getMonoSmallFont() {
        return getFont("jetbrains-mono.ttf", Font.PLAIN, 15);
    }

    public Font getMega80() {
        return getFont("mega65-80.ttf", Font.PLAIN, 26);
    }

    public Font getFont(String fontName, int style, float size) {

        String key = fontName + "-" + style + "-" + size;
        Font font = fontCash.get(key);
        if (font == null) {
            font = loadFont(fontName, style, size);
            fontCash.put(key, font);
        }
        return font;

    }

    private Font loadFont(String fontName, int style, float size) {

        Font font = null;

        try {

            String path = "/fonts/" + fontName;

            InputStream is = this.getClass().getResourceAsStream(path);
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(style, size);
            logger.info("Font " + path + " loaded successfully");

        } catch (IOException e) {
            logger.error("IOException error" , e);
        } catch (FontFormatException e) {
            logger.error("FontFormatException error" , e);
        }

        return font;

    }



    public String getPetscii(long by) {
        char result =  (char) by;

        if (by >= 0x00 && by<=0x1f
             || by >= 0x80 && by<=0x9f) {
            result = (char) 0x2e;
        }

        if (by >= 0x60 && by <= 0x7f) {
            result = (char) (by + 0x0a0);
        }

        if (by >= 0xa0 && by <= 0xbf) {
            result = (char) (by + 0x80);
        }

        if (by >= 0xc0 && by <= 0xfe ) {
            result = (char) (by + 0xe0);
        }

        if (by == 0xff) {
            result = (char) 0x7e;
        }

        return Character.valueOf(result).toString();
    }

}
