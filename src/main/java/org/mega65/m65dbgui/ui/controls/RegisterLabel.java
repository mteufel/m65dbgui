package org.mega65.m65dbgui.ui.controls;

import jakarta.inject.Inject;
import org.mega65.m65dbgui.services.FontService;

import javax.swing.*;


public class RegisterLabel extends MulticolorLabel {

    @Inject
    public RegisterLabel(FontService fontService) {
        setHorizontalAlignment(SwingConstants.LEFT);
        setOpaque(false);
        setFont(fontService.getMonoDefaultFont());
    }


}
