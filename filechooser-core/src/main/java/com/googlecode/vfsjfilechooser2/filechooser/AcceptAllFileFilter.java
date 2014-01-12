package com.googlecode.vfsjfilechooser2.filechooser;

import com.googlecode.vfsjfilechooser2.utils.VFSResources;

/** Default AcceptAll file filter */
public class AcceptAllFileFilter extends AbstractVFSFileFilter<Object> {

    public AcceptAllFileFilter() {
    }

    @Override
    public boolean accept(Object f) {
        return true;
    }

    @Override
    public String getDescription() {
        return VFSResources.getMessage("VFSJFileChooser.acceptAllFileFilterText");
    }

    @Override
    public String toString() {
        return getDescription();
    }

}
