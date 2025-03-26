package org.mini


import platform.UIKit.UIScreen
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import org.mini.data.CrossConfigDevice

class CrossConfigDevice: CrossConfigDevice{

    override fun isDarkModeEnabled(): Boolean {
        val osTheme: UITraitCollection = UIScreen.mainScreen.traitCollection
        return osTheme.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
    }
}