package com.hfad.avc;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.List;

public interface ChainHolder {
    List<WeakReference<Fragment>> getChain();
}