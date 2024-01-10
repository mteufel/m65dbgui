package org.mega65.m65dbgui.services;

import com.formdev.flatlaf.util.SystemInfo;
import com.kitfox.svg.app.beans.SVGIcon;

import java.awt.*;
import java.net.URISyntaxException;


public class IconService {

    public SVGIcon getReset() {
        return getToolbarIcon("reset.svg");
    }

    public SVGIcon getRefresh() {
        return getToolbarIcon("refresh.svg");
    }

    public SVGIcon getIconConneced() {
        return getToolbarIcon("connected.svg");
    }

    public SVGIcon getIconDisonneced() {
        return getToolbarIcon("disconnected.svg");
    }

    public SVGIcon getSearch() {
        return geSmalltToolbarIcon("search.svg");
    }

    public SVGIcon getFirst() {
        return geSmalltToolbarIcon("first.svg");
    }

    public SVGIcon getPrevious() {
        return geSmalltToolbarIcon("previous.svg");
    }

    public SVGIcon getNext() {
        return geSmalltToolbarIcon("next.svg");
    }

    public SVGIcon getLast() {
        return geSmalltToolbarIcon("last.svg");
    }

    public SVGIcon getMore() {
        return geSmalltToolbarIcon("more.svg");
    }

    public SVGIcon getDissassebly() {
        return getToolbarIcon("disassembly.svg");
    }



    public SVGIcon geSmalltToolbarIcon(String iconName) {
        int width = 16;
        int height = 16;
        if (SystemInfo.isMacFullWindowContentSupported) {
            width=12;
            height=12;
        }
        return getIcon(iconName, width, height);
    }


    public SVGIcon getToolbarIcon(String iconName) {
        int width = 22;
        int height = 22;
        if (SystemInfo.isMacFullWindowContentSupported) {
            width=20;
            height=20;
        }
        return getIcon(iconName, width, height);
    }

    public SVGIcon getIcon(String iconName, int width, int height) {

        String path = "/icons/" + iconName;

        SVGIcon icon = new SVGIcon();
        icon.setScaleToFit(false);
        icon.setPreferredSize(new Dimension(width, height));
        icon.setAntiAlias(true);
        try {
            icon.setSvgURI( this.getClass().getResource(path).toURI());
        } catch (URISyntaxException e) {
            // TODO
        }
        return icon;

    }

}
