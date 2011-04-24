package de.rrze.jpwgen.options;

import java.util.ArrayList;
import java.util.List;


/**
 * Class following the builder concept - maps the CLI arguments to classes for
 * easier options management.
 * 
 * @autor Sheldon Fuchs
 * */
public class PwGeneratorOptionBuilder
{
	List<PwOptionFlag> _flags = new ArrayList<PwOptionFlag>();
	List<PwOptionSwitch> _switches = new ArrayList<PwOptionSwitch>();
	List<IPwOption> _options = new ArrayList<IPwOption>();

	public PwGeneratorOptionBuilder setNumberOfPasswords(int numberOfPasswords)
	{
		PwOptionFlag flag = new PwNumberPassword();
		flag.setValue(Integer.toString(numberOfPasswords));
		_flags.add(flag);
		return this;
	}

	public PwGeneratorOptionBuilder setPasswordLength(int passwordLength)
	{
		PwOptionFlag flag = new PwPasswordLength();
		flag.setValue(Integer.toString(passwordLength));
		_flags.add(flag);
		return this;
	}

	public PwGeneratorOptionBuilder setIncludeOneCapital()
	{
		_options.add(new PwCapitalizeOption());
		return this;
	}

	public PwGeneratorOptionBuilder setIncludeNumerals(boolean includeNumerals)
	{
		addSwitch(includeNumerals, new PwNumeralsOption());
		return this;
	}

	public PwGeneratorOptionBuilder setIncludeSymbols(boolean includeSymbols)
	{
		addSwitch(includeSymbols, new PwSymbolsOption());
		return this;
	}

	public PwGeneratorOptionBuilder setIncludeAmbiguous(boolean includeAmbiguous)
	{
		addSwitch(includeAmbiguous, new PwAmbiguousOption());
		return this;
	}

	public PwGeneratorOptionBuilder setUseRandom()
	{
		_options.add(new PwRandomOption());
		return this;
	}

	public PwGeneratorOptionBuilder setPrintAsColumn(boolean clColumn)
	{
		_options.add(new PwColumnOptions());
		return this;
	}

	public PwGeneratorOptionBuilder setSRAlgorithm(String clSRAlgorithm)
	{
		PwOptionFlag flag = new SRAlgorithm();
		flag.setValue(clSRAlgorithm);
		_flags.add(flag);
		return this;
	}

	public PwGeneratorOptionBuilder setMaxAttempts(int clMaxAttempts)
	{
		PwOptionFlag flag = new MaxAttempts();
		flag.setValue(Integer.toString(clMaxAttempts));
		_flags.add(flag);
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotStartWithSmallLetter()
	{
		_options.add(new StartsNoSmallLetterOption());
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotEndWithSmallLetter()
	{
		_options.add(new EndsNoSmallLetterOption());
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotStartWithUpperLetter()
	{
		_options.add(new StartsNoUpperLetterOption());
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotEndWithUpperLetter()
	{
		_options.add(new EndsNoUpperLetterOption());
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotStartWithDigit()
	{
		_options.add(new StartsNoDigitOption());
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotEndWithDigit()
	{
		_options.add(new EndsNoDigitOption());
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotStartsWithSymbol()
	{
		_options.add(new StartsNoSymbolOption());
		return this;
	}

	public PwGeneratorOptionBuilder setDoNotEndWithSymbol()
	{
		_options.add(new EndsNoSymbolOption());
		return this;
	}

	public PwGeneratorOptionBuilder setOnly1Capital()
	{
		_options.add(new Only1CapitalOption());
		return this;
	}

	public PwGeneratorOptionBuilder setOnly1Symbol()
	{
		_options.add(new Only1SymbolOption());
		return this;
	}

	public PwGeneratorOptionBuilder setAtLeast2Symbols()
	{
		_options.add(new AtLeast2SymbolsOption());
		return this;
	}

	public PwGeneratorOptionBuilder setOnly1Digit()
	{
		_options.add(new Only1DigitOption());
		return this;
	}

	public PwGeneratorOptionBuilder setAtLeast2Digits()
	{
		_options.add(new AtLeast2DigitsOption());
		return this;
	}

	private void addSwitch(boolean enabled, PwOptionSwitch option)
	{
		option.setIsEnabled(enabled);
		_switches.add(option);
	}

	public synchronized String[] build()
	{
		List<String> options = new ArrayList<String>();

		for (PwOptionFlag flag : _flags)
		{
			options.add("-" + flag.getOptionName());
			options.add(flag.getValue());
		}

		for (PwOptionSwitch optionSwitch : _switches)
		{
			options.add("-" + optionSwitch.getOptionName());
		}

		for (IPwOption option : _options)
		{
			options.add("-" + option.getOptionName());
		}

		return options.toArray(new String[options.size()]);
	}

}
