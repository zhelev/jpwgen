package de.rrze.jpwgen.flags;

import java.util.ArrayList;
import java.util.List;

public class PwGeneratorFlagBuilder
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<IPwFlag> flagList = new ArrayList<IPwFlag>();

	int flags = 0;

	public PwGeneratorFlagBuilder()
	{
	}

	public PwGeneratorFlagBuilder(int flags)
	{
		this.flags = flags;
	}

	public PwGeneratorFlagBuilder setIncludeCapitals()
	{
		flagList.add(new PwCapitalizeFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeNumerals()
	{
		flagList.add(new PwNumeralsFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeSymbols()
	{
		flagList.add(new PwSymbolsFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setIncludeReducedSymbols()
	{
		flagList.add(new PwReducedSymbolsFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setFilterAmbiguous()
	{
		flagList.add(new PwAmbiguousFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithSmallLetter()
	{
		flagList.add(new StartsNoSmallLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithSmallLetter()
	{
		flagList.add(new EndsNoSmallLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithUpperLetter()
	{
		flagList.add(new StartsNoUpperLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithUpperLetter()
	{
		flagList.add(new EndsNoUpperLetterFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartWithDigit()
	{
		flagList.add(new StartsNoDigitFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithDigit()
	{
		flagList.add(new EndsNoDigitFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotStartsWithSymbol()
	{
		flagList.add(new StartsNoSymbolFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setDoNotEndWithSymbol()
	{
		flagList.add(new EndsNoSymbolFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Capital()
	{
		flagList.add(new Only1CapitalFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Symbol()
	{
		flagList.add(new Only1SymbolFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Symbols()
	{
		flagList.add(new AtLeast2SymbolsFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setOnly1Digit()
	{
		flagList.add(new Only1DigitFlag());
		return this;
	}

	public PwGeneratorFlagBuilder setAtLeast2Digits()
	{
		flagList.add(new AtLeast2DigitsFlag());
		return this;
	}

	public synchronized int build()
	{
		int build = flags;

		for (IPwFlag fl : flagList)
		{
			build = fl.mask(build);
		}

		return build;
	}
}
