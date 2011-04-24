package de.rrze.jpwgen.options;

/**
 * @autor Sheldon Fuchs
 * */
public abstract class PwOptionSwitch implements IPwOption {
    private boolean _enabled;

    public boolean isEnabled() {
	return _enabled;
    }

    public void setIsEnabled(boolean enabled) {
	_enabled = enabled;
    }
}
