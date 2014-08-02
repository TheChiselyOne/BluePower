/*
 * This file is part of Blue Power.
 *
 *     Blue Power is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Blue Power is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Blue Power.  If not, see <http://www.gnu.org/licenses/>
 *
 *     @author Quetzi
 */

package com.bluepowermod.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.bluepowermod.client.gui.widget.BaseWidget;
import com.bluepowermod.client.gui.widget.IGuiWidget;
import com.bluepowermod.client.gui.widget.WidgetColor;
import com.bluepowermod.client.gui.widget.WidgetMode;
import com.bluepowermod.containers.ContainerRegulator;
import com.bluepowermod.network.NetworkHandler;
import com.bluepowermod.network.messages.MessageGuiUpdate;
import com.bluepowermod.references.Refs;
import com.bluepowermod.tileentities.tier2.TileRegulator;

public class GuiRegulator extends GuiBase {
    
    private static final ResourceLocation resLoc = new ResourceLocation(Refs.MODID, "textures/gui/regulator.png");
    protected TileRegulator               regulator;
    
    public GuiRegulator(InventoryPlayer invPlayer, TileRegulator regulator) {
    
        super(new ContainerRegulator(invPlayer, regulator), resLoc);
        this.regulator = regulator;
        xSize = 212;
    }
    
    @Override
    public void initGui() {
    
        super.initGui();
        WidgetColor colorWidget = new WidgetColor(0, guiLeft + 135, guiTop + 55);
        colorWidget.value = regulator.color.ordinal();
        addWidget(colorWidget);
        
        WidgetMode modeWidget = new WidgetMode(1, guiLeft + 135, guiTop + 20, 216, 2, Refs.MODID + ":textures/gui/regulator.png");
        modeWidget.value = regulator.mode;
        addWidget(modeWidget);
    }
    
    @Override
    public void actionPerformed(IGuiWidget widget) {
    
        BaseWidget baseWidget = (BaseWidget) widget;
        NetworkHandler.sendToServer(new MessageGuiUpdate(regulator, widget.getID(), baseWidget.value));
    }
}
