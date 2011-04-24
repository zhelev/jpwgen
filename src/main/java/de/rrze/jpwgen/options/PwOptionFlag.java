package de.rrze.jpwgen.options;

/**
 * @autor Sheldon Fuchs
 * */
public abstract class PwOptionFlag implements IPwOption {
    private String _value;

    public String getValue() {
	return 	_value;
    }

    public void setValue(String value) {
	_value = value;
    }
}
