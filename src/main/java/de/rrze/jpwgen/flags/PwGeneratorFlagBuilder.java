package de.rrze.jpwgen.flags;

import java.util.ArrayList;
import java.util.List;

import de.rrze.jpwgen.IPwGenConstants;

public class PwGeneratorFlagBuilder
{
	List<IPwFlag> _options = new ArrayList<IPwFlag>();

	int flags = 0;

	public PwGeneratorFlagBuilder()
	{
	}

	public PwGeneratorFlagBuilder(int flags)
	{
		this.flags = flags;
	}

	public PwGeneratorFlagBuilder setIncludeOneCapital(boolean enabled)
	{
		_options.add(new PwCapitalizeFlag(enabled));
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeNumerals(boolean enabled)
	{
		_options.add(new PwNumeralsFlag(enabled));
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeSymbols(
			IPwGenConstants.SYMBOL_OPTIONS type)
	{
		_options.add(new PwSymbolsFlag(type));
		return this;
	}

	public PwGeneratorFlagBuilder setFilterAmbiguous(boolean enabled)
	{
		_options.add(new PwAmbiguousFlag(enabled));
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithSmallLetter()
	{
		_options.add(new StartsNoSmallLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithSmallLetter()
	{
		_options.add(new EndsNoSmallLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithUpperLetter()
	{
		_options.add(new StartsNoUpperLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithUpperLetter()
	{
		_options.add(new EndsNoUpperLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithDigit()
	{
		_options.add(new StartsNoDigitFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithDigit()
	{
		_options.add(new EndsNoDigitFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartsWithSymbol()
	{
		_options.add(new StartsNoSymbolFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithSymbol()
	{
		_options.add(new EndsNoSymbolFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Capital()
	{
		_options.add(new Only1CapitalFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Symbol()
	{
		_options.add(new Only1SymbolFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Symbols()
	{
		_options.add(new AtLeast2SymbolsFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Digit()
	{
		_options.add(new Only1DigitFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Digits()
	{
		_options.add(new AtLeast2DigitsFlag());
		return this;
	}

	public synchronized int build()
	{
		int build = flags;

		for (IPwFlag option : _options)
		{
			build = option.mask(build);
		}

		return build;
	}
}
