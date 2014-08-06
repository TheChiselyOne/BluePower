package com.bluepowermod;

import com.bluepowermod.api.BPApi.IBPApi;
import com.bluepowermod.api.compat.IMultipartCompat;
import com.bluepowermod.api.part.IPartRegistry;
import com.bluepowermod.api.recipe.IAlloyFurnaceRegistry;
import com.bluepowermod.compat.CompatibilityUtils;
import com.bluepowermod.part.PartRegistry;
import com.bluepowermod.recipe.AlloyFurnaceRegistry;
import com.bluepowermod.util.Dependencies;

public class BluePowerAPI implements IBPApi {
    
    @Override
    public IMultipartCompat getMultipartCompat() {
    
        return (IMultipartCompat) CompatibilityUtils.getModule(Dependencies.FMP);
    }
    
    @Override
    public IPartRegistry getPartRegistry() {
    
        return PartRegistry.getInstance();
    }
    
    @Override
    public IAlloyFurnaceRegistry getAlloyFurnaceRegistry() {
    
        return AlloyFurnaceRegistry.getInstance();
    }
}
